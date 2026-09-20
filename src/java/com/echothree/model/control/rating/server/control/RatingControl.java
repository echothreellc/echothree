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

package com.echothree.model.control.rating.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.rating.common.choice.RatingTypeListItemChoicesBean;
import com.echothree.model.control.rating.common.transfer.RatingTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTypeDescriptionTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTypeListItemDescriptionTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTypeListItemTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTypeTransfer;
import com.echothree.model.control.rating.server.transfer.RatingTransferCache;
import com.echothree.model.control.rating.server.transfer.RatingTypeDescriptionTransferCache;
import com.echothree.model.control.rating.server.transfer.RatingTypeListItemDescriptionTransferCache;
import com.echothree.model.control.rating.server.transfer.RatingTypeListItemTransferCache;
import com.echothree.model.control.rating.server.transfer.RatingTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.rating.common.pk.RatingTypeListItemPK;
import com.echothree.model.data.rating.common.pk.RatingTypePK;
import com.echothree.model.data.rating.server.entity.Rating;
import com.echothree.model.data.rating.server.entity.RatingDetail;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeDescription;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.rating.server.entity.RatingTypeListItemDescription;
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
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.rating.RatingForeignKeys.RATINGS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.rating.RatingForeignKeys.RATING_DETAILS_RATING_TYPE_LIST_ITEM_FK;
import static com.echothree.model.jooq.server.keys.rating.RatingForeignKeys.RATING_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.rating.RatingForeignKeys.RATING_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.rating.RatingForeignKeys.RATING_TYPE_LIST_ITEMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.rating.RatingForeignKeys.RATING_TYPE_LIST_ITEMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.rating.RatingForeignKeys.RATING_TYPE_LIST_ITEM_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.rating.RatingDetails.RatingDetails;
import static com.echothree.model.jooq.server.tables.rating.RatingTypeDescriptions.RatingTypeDescriptions;
import static com.echothree.model.jooq.server.tables.rating.RatingTypeDetails.RatingTypeDetails;
import static com.echothree.model.jooq.server.tables.rating.RatingTypeListItemDescriptions.RatingTypeListItemDescriptions;
import static com.echothree.model.jooq.server.tables.rating.RatingTypeListItemDetails.RatingTypeListItemDetails;
import static com.echothree.model.jooq.server.tables.rating.RatingTypeListItems.RatingTypeListItems;
import static com.echothree.model.jooq.server.tables.rating.RatingTypes.RatingTypes;
import static com.echothree.model.jooq.server.tables.rating.Ratings.Ratings;
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
public class RatingControl
        extends BaseModelControl {
    
    /** Creates a new instance of RatingControl */
    protected RatingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Rating Types
    // --------------------------------------------------------------------------------

    @Inject
    protected RatingTypeDetailFactory ratingTypeDetailFactory;

    @Inject
    private RatingTypeFactory ratingTypeFactory;

    @Inject
    private RatingTypeTransferCache ratingTypeTransferCache;

    public RatingType createRatingType(EntityType entityType, String ratingTypeName, Sequence ratingSequence, Integer sortOrder,
            BasePK createdBy) {
        var ratingType = ratingTypeFactory.create();
        var ratingTypeDetail = ratingTypeDetailFactory.create(ratingType, entityType,
                ratingTypeName, ratingSequence, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        ratingType = ratingTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                ratingType.getPrimaryKey());
        ratingType.setActiveDetail(ratingTypeDetail);
        ratingType.setLastDetail(ratingTypeDetail);
        ratingType.store();
        
        sendEvent(ratingType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return ratingType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.RatingType */
    public RatingType getRatingTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new RatingTypePK(entityInstance.getEntityUniqueId());

        return ratingTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public RatingType getRatingTypeByEntityInstance(EntityInstance entityInstance) {
        return getRatingTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public RatingType getRatingTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getRatingTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countRatingTypesByEntityType(final EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(RatingTypes)
                .join(RatingTypeDetails).onKey(RATING_TYPES_ACTIVE_DETAIL_FK)
                .where(RatingTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<RatingType> getRatingTypes(EntityType entityType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RatingTypes.fields())
                .from(RatingTypes)
                .join(RatingTypeDetails).onKey(RATING_TYPES_ACTIVE_DETAIL_FK)
                .where(RatingTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(RatingTypeDetails.SORT_ORDER, RatingTypeDetails.RATING_TYPE_NAME),
                    RatingTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<RatingType> getRatingTypes(EntityType entityType) {
        return getRatingTypes(entityType, EntityPermission.READ_ONLY);
    }
    
    public List<RatingType> getRatingTypesForUpdate(EntityType entityType) {
        return getRatingTypes(entityType, EntityPermission.READ_WRITE);
    }
    
    private RatingType getRatingTypeByName(EntityType entityType, String ratingTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RatingTypes.fields())
                .from(RatingTypes)
                .join(RatingTypeDetails).onKey(RATING_TYPES_ACTIVE_DETAIL_FK)
                .where(RatingTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        RatingTypeDetails.RATING_TYPE_NAME.eq(ratingTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingTypeFactory.getEntityFromQuery(entityPermission, query);
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
        return ratingTypeTransferCache.getRatingTypeTransfer(userVisit, ratingType);
    }
    
    public List<RatingTypeTransfer> getRatingTypeTransfers(UserVisit userVisit, EntityType entityType) {
        return getRatingTypeTransfers(userVisit, getRatingTypes(entityType));
    }

    public List<RatingTypeTransfer> getRatingTypeTransfers(UserVisit userVisit, Collection<RatingType> ratingTypes) {
        List<RatingTypeTransfer> ratingTypeTransfers = new ArrayList<>(ratingTypes.size());

        ratingTypes.forEach((ratingType) ->
                ratingTypeTransfers.add(ratingTypeTransferCache.getRatingTypeTransfer(userVisit, ratingType))
        );

        return ratingTypeTransfers;
    }
    
    public void updateRatingTypeFromValue(RatingTypeDetailValue ratingTypeDetailValue, BasePK updatedBy) {
        if(ratingTypeDetailValue.hasBeenModified()) {
            var ratingType = ratingTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     ratingTypeDetailValue.getRatingTypePK());
            var ratingTypeDetail = ratingType.getActiveDetailForUpdate();
            
            ratingTypeDetail.setThruTime(session.getStartTime());
            ratingTypeDetail.store();

            var ratingTypePK = ratingTypeDetail.getRatingTypePK(); // Not updated
            var entityTypePK = ratingTypeDetail.getEntityTypePK(); // Not updated
            var ratingTypeName = ratingTypeDetailValue.getRatingTypeName();
            var ratingSequencePK = ratingTypeDetailValue.getRatingSequencePK();
            var sortOrder = ratingTypeDetailValue.getSortOrder();
            
            ratingTypeDetail = ratingTypeDetailFactory.create(ratingTypePK, entityTypePK, ratingTypeName,
                    ratingSequencePK, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            ratingType.setActiveDetail(ratingTypeDetail);
            ratingType.setLastDetail(ratingTypeDetail);
            
            sendEvent(ratingTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteRatingType(RatingType ratingType, BasePK deletedBy) {
        deleteRatingTypeDescriptionsByRatingType(ratingType, deletedBy);
        deleteRatingTypeListItemsByRatingType(ratingType, deletedBy);

        var ratingTypeDetail = ratingType.getLastDetailForUpdate();
        ratingTypeDetail.setThruTime(session.getStartTime());
        ratingType.setActiveDetail(null);
        ratingType.store();
        
        sendEvent(ratingType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
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

    @Inject
    private RatingTypeDescriptionFactory ratingTypeDescriptionFactory;

    @Inject
    private RatingTypeDescriptionTransferCache ratingTypeDescriptionTransferCache;

    public RatingTypeDescription createRatingTypeDescription(RatingType ratingType, Language language, String description,
            BasePK createdBy) {
        var ratingTypeDescription = ratingTypeDescriptionFactory.create(ratingType,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(ratingType.getPrimaryKey(), EventTypes.MODIFY, ratingTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return ratingTypeDescription;
    }
    
    private RatingTypeDescription getRatingTypeDescription(RatingType ratingType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RatingTypeDescriptions.fields())
                .from(RatingTypeDescriptions)
                .where(RatingTypeDescriptions.RATING_TYPE.eq(ratingType.getPrimaryKey()),
                        RatingTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        RatingTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(RatingTypeDescriptions.fields())
                    .from(RatingTypeDescriptions)
                    .join(Languages).onKey(RATING_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(RatingTypeDescriptions.RATING_TYPE.eq(ratingType.getPrimaryKey()),
                            RatingTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    RatingTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(RatingTypeDescriptions.fields())
                    .from(RatingTypeDescriptions)
                    .where(RatingTypeDescriptions.RATING_TYPE.eq(ratingType.getPrimaryKey()),
                            RatingTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return ratingTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<RatingTypeDescription> getRatingTypeDescriptionsByRatingType(RatingType ratingType) {
        return getRatingTypeDescriptionsByRatingType(ratingType, EntityPermission.READ_ONLY);
    }
    
    public List<RatingTypeDescription> getRatingTypeDescriptionsByRatingTypeForUpdate(RatingType ratingType) {
        return getRatingTypeDescriptionsByRatingType(ratingType, EntityPermission.READ_WRITE);
    }
    
    public String getBestRatingTypeDescription(RatingType ratingType, Language language) {
        String description;
        var ratingTypeDescription = getRatingTypeDescription(ratingType, language);
        
        if(ratingTypeDescription == null && !language.getIsDefault()) {
            ratingTypeDescription = getRatingTypeDescription(ratingType, partyControl.getDefaultLanguage());
        }
        
        if(ratingTypeDescription == null) {
            description = ratingType.getLastDetail().getRatingTypeName();
        } else {
            description = ratingTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public RatingTypeDescriptionTransfer getRatingTypeDescriptionTransfer(UserVisit userVisit, RatingTypeDescription ratingTypeDescription) {
        return ratingTypeDescriptionTransferCache.getRatingTypeDescriptionTransfer(userVisit, ratingTypeDescription);
    }
    
    public List<RatingTypeDescriptionTransfer> getRatingTypeDescriptionTransfers(UserVisit userVisit, RatingType ratingType) {
        var ratingTypeDescriptions = getRatingTypeDescriptionsByRatingType(ratingType);
        List<RatingTypeDescriptionTransfer> ratingTypeDescriptionTransfers = new ArrayList<>(ratingTypeDescriptions.size());
        
        ratingTypeDescriptions.forEach((ratingTypeDescription) ->
                ratingTypeDescriptionTransfers.add(ratingTypeDescriptionTransferCache.getRatingTypeDescriptionTransfer(userVisit, ratingTypeDescription))
        );
        
        return ratingTypeDescriptionTransfers;
    }
    
    public void updateRatingTypeDescriptionFromValue(RatingTypeDescriptionValue ratingTypeDescriptionValue, BasePK updatedBy) {
        if(ratingTypeDescriptionValue.hasBeenModified()) {
            var ratingTypeDescription = ratingTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, ratingTypeDescriptionValue.getPrimaryKey());
            
            ratingTypeDescription.setThruTime(session.getStartTime());
            ratingTypeDescription.store();

            var ratingType = ratingTypeDescription.getRatingType();
            var language = ratingTypeDescription.getLanguage();
            var description = ratingTypeDescriptionValue.getDescription();
            
            ratingTypeDescription = ratingTypeDescriptionFactory.create(ratingType, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(ratingType.getPrimaryKey(), EventTypes.MODIFY, ratingTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteRatingTypeDescription(RatingTypeDescription ratingTypeDescription, BasePK deletedBy) {
        ratingTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(ratingTypeDescription.getRatingTypePK(), EventTypes.MODIFY, ratingTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteRatingTypeDescriptionsByRatingType(RatingType ratingType, BasePK deletedBy) {
        var ratingTypeDescriptions = getRatingTypeDescriptionsByRatingTypeForUpdate(ratingType);
        
        ratingTypeDescriptions.forEach((ratingTypeDescription) -> 
                deleteRatingTypeDescription(ratingTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Rating Type List Items
    // --------------------------------------------------------------------------------

    @Inject
    protected RatingTypeListItemDetailFactory ratingTypeListItemDetailFactory;

    @Inject
    private RatingTypeListItemFactory ratingTypeListItemFactory;

    @Inject
    private RatingTypeListItemTransferCache ratingTypeListItemTransferCache;

    public RatingTypeListItem createRatingTypeListItem(RatingType ratingType, String ratingTypeListItemName, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultRatingTypeListItem = getDefaultRatingTypeListItem(ratingType);
        var defaultFound = defaultRatingTypeListItem != null;
        
        if(defaultFound && isDefault) {
            var defaultRatingTypeListItemDetailValue = getDefaultRatingTypeListItemDetailValueForUpdate(ratingType);
            
            defaultRatingTypeListItemDetailValue.setIsDefault(false);
            updateRatingTypeListItemFromValue(defaultRatingTypeListItemDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var ratingTypeListItem = ratingTypeListItemFactory.create();
        var ratingTypeListItemDetail = ratingTypeListItemDetailFactory.create(
                ratingTypeListItem, ratingType, ratingTypeListItemName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        ratingTypeListItem = ratingTypeListItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                ratingTypeListItem.getPrimaryKey());
        ratingTypeListItem.setActiveDetail(ratingTypeListItemDetail);
        ratingTypeListItem.setLastDetail(ratingTypeListItemDetail);
        ratingTypeListItem.store();
        
        sendEvent(ratingTypeListItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return ratingTypeListItem;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.RatingTypeListItem */
    public RatingTypeListItem getRatingTypeListItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new RatingTypeListItemPK(entityInstance.getEntityUniqueId());

        return ratingTypeListItemFactory.getEntityFromPK(entityPermission, pk);
    }

    public RatingTypeListItem getRatingTypeListItemByEntityInstance(EntityInstance entityInstance) {
        return getRatingTypeListItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public RatingTypeListItem getRatingTypeListItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getRatingTypeListItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countRatingTypeListItemsByRatingType(final RatingType ratingType) {
        return session.getDslContext()
                .selectCount()
                .from(RatingTypeListItems)
                .join(RatingTypeListItemDetails).onKey(RATING_TYPE_LIST_ITEMS_ACTIVE_DETAIL_FK)
                .where(RatingTypeListItemDetails.RATING_TYPE.eq(ratingType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private RatingTypeListItem getRatingTypeListItemByName(RatingType ratingType, String ratingTypeListItemName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RatingTypeListItems.fields())
                .from(RatingTypeListItems)
                .join(RatingTypeListItemDetails).onKey(RATING_TYPE_LIST_ITEMS_ACTIVE_DETAIL_FK)
                .where(RatingTypeListItemDetails.RATING_TYPE.eq(ratingType.getPrimaryKey()),
                        RatingTypeListItemDetails.RATING_TYPE_LIST_ITEM_NAME.eq(ratingTypeListItemName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingTypeListItemFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(RatingTypeListItems.fields())
                .from(RatingTypeListItems)
                .join(RatingTypeListItemDetails).onKey(RATING_TYPE_LIST_ITEMS_ACTIVE_DETAIL_FK)
                .where(RatingTypeListItemDetails.RATING_TYPE.eq(ratingType.getPrimaryKey()),
                        RatingTypeListItemDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingTypeListItemFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(RatingTypeListItems.fields())
                .from(RatingTypeListItems)
                .join(RatingTypeListItemDetails).onKey(RATING_TYPE_LIST_ITEMS_ACTIVE_DETAIL_FK)
                .where(RatingTypeListItemDetails.RATING_TYPE.eq(ratingType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(RatingTypeListItemDetails.SORT_ORDER, RatingTypeListItemDetails.RATING_TYPE_LIST_ITEM_NAME),
                    RatingTypeListItemFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingTypeListItemFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<RatingTypeListItem> getRatingTypeListItems(RatingType ratingType) {
        return getRatingTypeListItems(ratingType, EntityPermission.READ_ONLY);
    }
    
    public List<RatingTypeListItem> getRatingTypeListItemsForUpdate(RatingType ratingType) {
        return getRatingTypeListItems(ratingType, EntityPermission.READ_WRITE);
    }
    
    public RatingTypeListItemTransfer getRatingTypeListItemTransfer(UserVisit userVisit, RatingTypeListItem ratingTypeListItem) {
        return ratingTypeListItemTransferCache.getRatingTypeListItemTransfer(userVisit, ratingTypeListItem);
    }
    
    public List<RatingTypeListItemTransfer> getRatingTypeListItemTransfers(UserVisit userVisit, Collection<RatingTypeListItem> ratingTypeListItems) {
        List<RatingTypeListItemTransfer> ratingTypeListItemTransfers = new ArrayList<>(ratingTypeListItems.size());
        
        ratingTypeListItems.forEach((ratingTypeListItem) ->
                ratingTypeListItemTransfers.add(ratingTypeListItemTransferCache.getRatingTypeListItemTransfer(userVisit, ratingTypeListItem))
        );
        
        return ratingTypeListItemTransfers;
    }
    
    public List<RatingTypeListItemTransfer> getRatingTypeListItemTransfers(UserVisit userVisit, RatingType ratingType) {
        return getRatingTypeListItemTransfers(userVisit, getRatingTypeListItems(ratingType));
    }
    
    private void updateRatingTypeListItemFromValue(RatingTypeListItemDetailValue ratingTypeListItemDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(ratingTypeListItemDetailValue.hasBeenModified()) {
            var ratingTypeListItem = ratingTypeListItemFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     ratingTypeListItemDetailValue.getRatingTypeListItemPK());
            var ratingTypeListItemDetail = ratingTypeListItem.getActiveDetailForUpdate();
            
            ratingTypeListItemDetail.setThruTime(session.getStartTime());
            ratingTypeListItemDetail.store();

            var ratingTypeListItemPK = ratingTypeListItemDetail.getRatingTypeListItemPK();
            var ratingType = ratingTypeListItemDetail.getRatingType();
            var ratingTypePK = ratingType.getPrimaryKey();
            var ratingTypeListItemName = ratingTypeListItemDetailValue.getRatingTypeListItemName();
            var isDefault = ratingTypeListItemDetailValue.getIsDefault();
            var sortOrder = ratingTypeListItemDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultRatingTypeListItem = getDefaultRatingTypeListItem(ratingType);
                var defaultFound = defaultRatingTypeListItem != null && !defaultRatingTypeListItem.equals(ratingTypeListItem);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultRatingTypeListItemDetailValue = getDefaultRatingTypeListItemDetailValueForUpdate(ratingType);
                    
                    defaultRatingTypeListItemDetailValue.setIsDefault(false);
                    updateRatingTypeListItemFromValue(defaultRatingTypeListItemDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            ratingTypeListItemDetail = ratingTypeListItemDetailFactory.create(ratingTypeListItemPK,
                    ratingTypePK, ratingTypeListItemName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            ratingTypeListItem.setActiveDetail(ratingTypeListItemDetail);
            ratingTypeListItem.setLastDetail(ratingTypeListItemDetail);
            
            sendEvent(ratingTypeListItemPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateRatingTypeListItemFromValue(RatingTypeListItemDetailValue ratingTypeListItemDetailValue, BasePK updatedBy) {
        updateRatingTypeListItemFromValue(ratingTypeListItemDetailValue, true, updatedBy);
    }
    
    public RatingTypeListItemChoicesBean getRatingTypeListItemChoices(String defaultRatingTypeListItemChoice, Language language,
            boolean allowNullChoice, RatingType ratingType) {
        var ratingTypeListItems = getRatingTypeListItems(ratingType);
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
            var ratingTypeListItemDetail = ratingTypeListItem.getLastDetail();
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
    
    private void deleteRatingTypeListItem(RatingTypeListItem ratingTypeListItem, boolean checkDefault, BasePK deletedBy) {
        deleteRatingTypeListItemDescriptionsByRatingTypeListItem(ratingTypeListItem, deletedBy);
        deleteRatingsByRatingTypeListItem(ratingTypeListItem, deletedBy);
        
        var ratingTypeListItemDetail = ratingTypeListItem.getLastDetailForUpdate();
        ratingTypeListItemDetail.setThruTime(session.getStartTime());
        ratingTypeListItem.setActiveDetail(null);
        ratingTypeListItem.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var ratingType = ratingTypeListItemDetail.getRatingType();
            var defaultRatingTypeListItem = getDefaultRatingTypeListItem(ratingType);
            if(defaultRatingTypeListItem == null) {
                var ratingTypePriorities = getRatingTypeListItemsForUpdate(ratingType);

                if(!ratingTypePriorities.isEmpty()) {
                    var iter = ratingTypePriorities.iterator();
                    if(iter.hasNext()) {
                        defaultRatingTypeListItem = iter.next();
                    }
                    var ratingTypeListItemDetailValue = Objects.requireNonNull(defaultRatingTypeListItem).getLastDetailForUpdate().getRatingTypeListItemDetailValue().clone();

                    ratingTypeListItemDetailValue.setIsDefault(true);
                    updateRatingTypeListItemFromValue(ratingTypeListItemDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(ratingTypeListItem.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteRatingTypeListItem(RatingTypeListItem ratingTypeListItem, BasePK deletedBy) {
        deleteRatingTypeListItem(ratingTypeListItem, true, deletedBy);
    }

    public void deleteRatingTypeListItemsByRatingType(RatingType ratingType, BasePK deletedBy) {
        var ratingTypeListItems = getRatingTypeListItemsForUpdate(ratingType);

        ratingTypeListItems.forEach((ratingTypeListItem) ->
                deleteRatingTypeListItem(ratingTypeListItem, false, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Rating Type List Item Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private RatingTypeListItemDescriptionFactory ratingTypeListItemDescriptionFactory;

    @Inject
    private RatingTypeListItemDescriptionTransferCache ratingTypeListItemDescriptionTransferCache;

    public RatingTypeListItemDescription createRatingTypeListItemDescription(RatingTypeListItem ratingTypeListItem, Language language,
            String description, BasePK createdBy) {
        var ratingTypeListItemDescription = ratingTypeListItemDescriptionFactory.create(
                ratingTypeListItem, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(ratingTypeListItem.getPrimaryKey(), EventTypes.MODIFY, ratingTypeListItemDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return ratingTypeListItemDescription;
    }
    
    private RatingTypeListItemDescription getRatingTypeListItemDescription(RatingTypeListItem ratingTypeListItem, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(RatingTypeListItemDescriptions.fields())
                .from(RatingTypeListItemDescriptions)
                .where(RatingTypeListItemDescriptions.RATING_TYPE_LIST_ITEM.eq(ratingTypeListItem.getPrimaryKey()),
                        RatingTypeListItemDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        RatingTypeListItemDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingTypeListItemDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(RatingTypeListItemDescriptions.fields())
                    .from(RatingTypeListItemDescriptions)
                    .join(Languages).onKey(RATING_TYPE_LIST_ITEM_DESCRIPTIONS_LANGUAGE_FK)
                    .where(RatingTypeListItemDescriptions.RATING_TYPE_LIST_ITEM.eq(ratingTypeListItem.getPrimaryKey()),
                            RatingTypeListItemDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    RatingTypeListItemDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(RatingTypeListItemDescriptions.fields())
                    .from(RatingTypeListItemDescriptions)
                    .where(RatingTypeListItemDescriptions.RATING_TYPE_LIST_ITEM.eq(ratingTypeListItem.getPrimaryKey()),
                            RatingTypeListItemDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return ratingTypeListItemDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<RatingTypeListItemDescription> getRatingTypeListItemDescriptionsByRatingTypeListItem(RatingTypeListItem ratingTypeListItem) {
        return getRatingTypeListItemDescriptionsByRatingTypeListItem(ratingTypeListItem, EntityPermission.READ_ONLY);
    }
    
    public List<RatingTypeListItemDescription> getRatingTypeListItemDescriptionsByRatingTypeListItemForUpdate(RatingTypeListItem ratingTypeListItem) {
        return getRatingTypeListItemDescriptionsByRatingTypeListItem(ratingTypeListItem, EntityPermission.READ_WRITE);
    }
    
    public String getBestRatingTypeListItemDescription(RatingTypeListItem ratingTypeListItem, Language language) {
        String description;
        var ratingTypeListItemDescription = getRatingTypeListItemDescription(ratingTypeListItem, language);
        
        if(ratingTypeListItemDescription == null && !language.getIsDefault()) {
            ratingTypeListItemDescription = getRatingTypeListItemDescription(ratingTypeListItem, partyControl.getDefaultLanguage());
        }
        
        if(ratingTypeListItemDescription == null) {
            description = ratingTypeListItem.getLastDetail().getRatingTypeListItemName();
        } else {
            description = ratingTypeListItemDescription.getDescription();
        }
        
        return description;
    }
    
    public RatingTypeListItemDescriptionTransfer getRatingTypeListItemDescriptionTransfer(UserVisit userVisit, RatingTypeListItemDescription ratingTypeListItemDescription) {
        return ratingTypeListItemDescriptionTransferCache.getRatingTypeListItemDescriptionTransfer(userVisit, ratingTypeListItemDescription);
    }
    
    public List<RatingTypeListItemDescriptionTransfer> getRatingTypeListItemDescriptionTransfers(UserVisit userVisit, RatingTypeListItem ratingTypeListItem) {
        var ratingTypeListItemDescriptions = getRatingTypeListItemDescriptionsByRatingTypeListItem(ratingTypeListItem);
        List<RatingTypeListItemDescriptionTransfer> ratingTypeListItemDescriptionTransfers = new ArrayList<>(ratingTypeListItemDescriptions.size());
        
        ratingTypeListItemDescriptions.forEach((ratingTypeListItemDescription) ->
                ratingTypeListItemDescriptionTransfers.add(ratingTypeListItemDescriptionTransferCache.getRatingTypeListItemDescriptionTransfer(userVisit, ratingTypeListItemDescription))
        );
        
        return ratingTypeListItemDescriptionTransfers;
    }
    
    public void updateRatingTypeListItemDescriptionFromValue(RatingTypeListItemDescriptionValue ratingTypeListItemDescriptionValue, BasePK updatedBy) {
        if(ratingTypeListItemDescriptionValue.hasBeenModified()) {
            var ratingTypeListItemDescription = ratingTypeListItemDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, ratingTypeListItemDescriptionValue.getPrimaryKey());
            
            ratingTypeListItemDescription.setThruTime(session.getStartTime());
            ratingTypeListItemDescription.store();

            var ratingTypeListItem = ratingTypeListItemDescription.getRatingTypeListItem();
            var language = ratingTypeListItemDescription.getLanguage();
            var description = ratingTypeListItemDescriptionValue.getDescription();
            
            ratingTypeListItemDescription = ratingTypeListItemDescriptionFactory.create(ratingTypeListItem, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(ratingTypeListItem.getPrimaryKey(), EventTypes.MODIFY, ratingTypeListItemDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteRatingTypeListItemDescription(RatingTypeListItemDescription ratingTypeListItemDescription, BasePK deletedBy) {
        ratingTypeListItemDescription.setThruTime(session.getStartTime());
        
        sendEvent(ratingTypeListItemDescription.getRatingTypeListItemPK(), EventTypes.MODIFY, ratingTypeListItemDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteRatingTypeListItemDescriptionsByRatingTypeListItem(RatingTypeListItem ratingTypeListItem, BasePK deletedBy) {
        var ratingTypeListItemDescriptions = getRatingTypeListItemDescriptionsByRatingTypeListItemForUpdate(ratingTypeListItem);
        
        ratingTypeListItemDescriptions.forEach((ratingTypeListItemDescription) -> 
                deleteRatingTypeListItemDescription(ratingTypeListItemDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Ratings
    // --------------------------------------------------------------------------------

    @Inject
    protected RatingDetailFactory ratingDetailFactory;

    @Inject
    private RatingFactory ratingFactory;

    @Inject
    private RatingTransferCache ratingTransferCache;

    public Rating createRating(String ratingName, RatingTypeListItem ratingTypeListItem, EntityInstance ratedEntityInstance,
            EntityInstance ratedByEntityInstance, BasePK createdBy) {
        var rating = ratingFactory.create();
        var ratingDetail = ratingDetailFactory.create(rating, ratingName, ratingTypeListItem,
                ratedEntityInstance, ratedByEntityInstance, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        rating = ratingFactory.getEntityFromPK(EntityPermission.READ_WRITE, rating.getPrimaryKey());
        rating.setActiveDetail(ratingDetail);
        rating.setLastDetail(ratingDetail);
        rating.store();
        
        sendEvent(rating.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        sendEvent(ratedEntityInstance, EventTypes.TOUCH, rating.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return rating;
    }
    
    private Rating getRatingByName(String ratingName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Ratings.fields())
                .from(Ratings)
                .join(RatingDetails).onKey(RATINGS_ACTIVE_DETAIL_FK)
                .where(RatingDetails.RATING_NAME.eq(ratingName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingFactory.getEntityFromQuery(entityPermission, query);
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
        var rating = getRatingByNameForUpdate(ratingName);
        
        return rating == null? null: getRatingDetailValue(rating.getLastDetailForUpdate());
    }
    
    private Rating getRating(EntityInstance ratedEntityInstance, EntityInstance ratedByEntityInstance,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Ratings.fields())
                .from(Ratings)
                .join(RatingDetails).onKey(RATINGS_ACTIVE_DETAIL_FK)
                .where(RatingDetails.RATED_ENTITY_INSTANCE.eq(ratedEntityInstance.getPrimaryKey()),
                        RatingDetails.RATED_BY_ENTITY_INSTANCE.eq(ratedByEntityInstance.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(Ratings.fields())
                .from(Ratings)
                .join(RatingDetails).onKey(RATINGS_ACTIVE_DETAIL_FK)
                .where(RatingDetails.RATED_ENTITY_INSTANCE.eq(ratedEntityInstance.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(RatingDetails.RATING_NAME),
                    RatingFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    
    public List<Rating> getRatingsByRatedEntityInstance(EntityInstance ratedEntityInstance) {
        return getRatingsByRatedEntityInstance(ratedEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<Rating> getRatingsByRatedEntityInstanceForUpdate(EntityInstance ratedEntityInstance) {
        return getRatingsByRatedEntityInstance(ratedEntityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Rating> getRatingsByRatedByEntityInstance(EntityInstance ratedByEntityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Ratings.fields())
                .from(Ratings)
                .join(RatingDetails).onKey(RATINGS_ACTIVE_DETAIL_FK)
                .where(RatingDetails.RATED_BY_ENTITY_INSTANCE.eq(ratedByEntityInstance.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(RatingDetails.RATING_NAME),
                    RatingFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Rating> getRatingsByRatedByEntityInstance(EntityInstance ratedByEntityInstance) {
        return getRatingsByRatedByEntityInstance(ratedByEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<Rating> getRatingsByRatedByEntityInstanceForUpdate(EntityInstance ratedByEntityInstance) {
        return getRatingsByRatedByEntityInstance(ratedByEntityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Rating> getRatingsByRatingTypeListItem(RatingTypeListItem ratingTypeListItem, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Ratings.fields())
                .from(Ratings)
                .join(RatingDetails).onKey(RATINGS_ACTIVE_DETAIL_FK)
                .where(RatingDetails.RATING_TYPE_LIST_ITEM.eq(ratingTypeListItem.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(RatingDetails.RATING_NAME),
                    RatingFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Rating> getRatingsByRatingTypeListItem(RatingTypeListItem ratingTypeListItem) {
        return getRatingsByRatingTypeListItem(ratingTypeListItem, EntityPermission.READ_ONLY);
    }
    
    public List<Rating> getRatingsByRatingTypeListItemForUpdate(RatingTypeListItem ratingTypeListItem) {
        return getRatingsByRatingTypeListItem(ratingTypeListItem, EntityPermission.READ_WRITE);
    }
    
    private List<Rating> getRatingsByRatedEntityInstanceAndRatingType(EntityInstance ratedEntityInstance, RatingType ratingType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Ratings.fields())
                .from(Ratings)
                .join(RatingDetails).onKey(RATINGS_ACTIVE_DETAIL_FK)
                .join(RatingTypeListItems).onKey(RATING_DETAILS_RATING_TYPE_LIST_ITEM_FK)
                .join(RatingTypeListItemDetails).onKey(RATING_TYPE_LIST_ITEMS_LAST_DETAIL_FK)
                .where(RatingDetails.RATED_ENTITY_INSTANCE.eq(ratedEntityInstance.getPrimaryKey()),
                        RatingTypeListItemDetails.RATING_TYPE.eq(ratingType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(RatingTypeListItemDetails.SORT_ORDER, RatingTypeListItemDetails.RATING_TYPE_LIST_ITEM_NAME),
                    RatingFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return ratingFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Rating> getRatingsByRatedEntityInstanceAndRatingType(EntityInstance ratedEntityInstance, RatingType ratingType) {
        return getRatingsByRatedEntityInstanceAndRatingType(ratedEntityInstance, ratingType, EntityPermission.READ_ONLY);
    }
    
    public List<Rating> getRatingsByRatedEntityInstanceAndRatingTypeForUpdate(EntityInstance ratedEntityInstance,
            RatingType ratingType) {
        return getRatingsByRatedEntityInstanceAndRatingType(ratedEntityInstance, ratingType, EntityPermission.READ_WRITE);
    }
    
    public RatingTransfer getRatingTransfer(UserVisit userVisit, Rating rating) {
        return ratingTransferCache.getRatingTransfer(userVisit, rating);
    }
    
    public List<RatingTransfer> getRatingTransfers(UserVisit userVisit, Collection<Rating> ratings) {
        List<RatingTransfer> ratingTransfers = new ArrayList<>(ratings.size());
        
        ratings.forEach((rating) ->
                ratingTransfers.add(ratingTransferCache.getRatingTransfer(userVisit, rating))
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
            var rating = ratingFactory.getEntityFromPK(EntityPermission.READ_WRITE, ratingDetailValue.getRatingPK());
            var ratingDetail = rating.getActiveDetailForUpdate();
            
            ratingDetail.setThruTime(session.getStartTime());
            ratingDetail.store();

            var ratingPK = ratingDetail.getRatingPK(); // Not updated
            var ratingName = ratingDetail.getRatingName(); // Not updated
            var ratingTypeListItemPK = ratingDetailValue.getRatingTypeListItemPK();
            var ratedEntityInstancePK = ratingDetail.getRatedEntityInstancePK(); // Not updated
            var ratedByEntityInstancePK = ratingDetail.getRatedByEntityInstancePK(); // Not updated
            
            ratingDetail = ratingDetailFactory.create(ratingPK, ratingName, ratingTypeListItemPK, ratedEntityInstancePK, ratedByEntityInstancePK,
                    session.getStartTime(), Session.MAX_TIME);
            
            rating.setActiveDetail(ratingDetail);
            rating.setLastDetail(ratingDetail);
            
            sendEvent(rating.getPrimaryKey(), EventTypes.MODIFY, null, null, updatedBy);
            sendEvent(ratingDetail.getRatedEntityInstance(), EventTypes.TOUCH, rating.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteRating(Rating rating, BasePK deletedBy) {
        var ratingDetail = rating.getLastDetailForUpdate();
        
        ratingDetail.setThruTime(session.getStartTime());
        rating.setActiveDetail(null);
        rating.store();
        
        sendEvent(rating.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
        sendEvent(ratingDetail.getRatedEntityInstance(), EventTypes.TOUCH, rating.getPrimaryKey(), EventTypes.DELETE, deletedBy);
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

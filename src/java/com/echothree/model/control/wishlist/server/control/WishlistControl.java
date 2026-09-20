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

package com.echothree.model.control.wishlist.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.common.OrderRoleTypes;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderLineControl;
import com.echothree.model.control.wishlist.common.choice.WishlistPriorityChoicesBean;
import com.echothree.model.control.wishlist.common.choice.WishlistTypeChoicesBean;
import com.echothree.model.control.wishlist.common.transfer.WishlistLineTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistPriorityDescriptionTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistPriorityTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeDescriptionTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeTransfer;
import com.echothree.model.control.wishlist.server.transfer.WishlistLineTransferCache;
import com.echothree.model.control.wishlist.server.transfer.WishlistPriorityDescriptionTransferCache;
import com.echothree.model.control.wishlist.server.transfer.WishlistPriorityTransferCache;
import com.echothree.model.control.wishlist.server.transfer.WishlistTransferCache;
import com.echothree.model.control.wishlist.server.transfer.WishlistTypeDescriptionTransferCache;
import com.echothree.model.control.wishlist.server.transfer.WishlistTypeTransferCache;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.factory.OrderFactory;
import com.echothree.model.data.order.server.factory.OrderLineFactory;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.common.pk.WishlistPriorityPK;
import com.echothree.model.data.wishlist.common.pk.WishlistTypePK;
import com.echothree.model.data.wishlist.server.entity.Wishlist;
import com.echothree.model.data.wishlist.server.entity.WishlistLine;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.model.data.wishlist.server.entity.WishlistPriorityDescription;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDescription;
import com.echothree.model.data.wishlist.server.factory.WishlistFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistLineFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistPriorityDescriptionFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistPriorityDetailFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistPriorityFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypeDescriptionFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypeDetailFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypeFactory;
import com.echothree.model.data.wishlist.server.value.WishlistLineValue;
import com.echothree.model.data.wishlist.server.value.WishlistPriorityDescriptionValue;
import com.echothree.model.data.wishlist.server.value.WishlistPriorityDetailValue;
import com.echothree.model.data.wishlist.server.value.WishlistTypeDescriptionValue;
import com.echothree.model.data.wishlist.server.value.WishlistTypeDetailValue;
import com.echothree.model.data.wishlist.server.value.WishlistValue;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDERS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_DETAILS_ORDER_TYPE_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_LINES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_LINES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_LINE_DETAILS_ORDER_FK;
import static com.echothree.model.jooq.server.keys.order.OrderForeignKeys.ORDER_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.wishlist.WishlistForeignKeys.WISHLISTS_ORDER_FK;
import static com.echothree.model.jooq.server.keys.wishlist.WishlistForeignKeys.WISHLIST_LINES_ORDER_LINE_FK;
import static com.echothree.model.jooq.server.keys.wishlist.WishlistForeignKeys.WISHLIST_PRIORITIES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.wishlist.WishlistForeignKeys.WISHLIST_PRIORITY_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.wishlist.WishlistForeignKeys.WISHLIST_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.wishlist.WishlistForeignKeys.WISHLIST_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.order.OrderDetails.OrderDetails;
import static com.echothree.model.jooq.server.tables.order.OrderLineDetails.OrderLineDetails;
import static com.echothree.model.jooq.server.tables.order.OrderLines.OrderLines;
import static com.echothree.model.jooq.server.tables.order.OrderRoleTypes.OrderRoleTypes;
import static com.echothree.model.jooq.server.tables.order.OrderRoles.OrderRoles;
import static com.echothree.model.jooq.server.tables.order.OrderTypeDetails.OrderTypeDetails;
import static com.echothree.model.jooq.server.tables.order.OrderTypes.OrderTypes;
import static com.echothree.model.jooq.server.tables.order.Orders.Orders;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.wishlist.WishlistLines.WishlistLines;
import static com.echothree.model.jooq.server.tables.wishlist.WishlistPriorities.WishlistPriorities;
import static com.echothree.model.jooq.server.tables.wishlist.WishlistPriorityDescriptions.WishlistPriorityDescriptions;
import static com.echothree.model.jooq.server.tables.wishlist.WishlistPriorityDetails.WishlistPriorityDetails;
import static com.echothree.model.jooq.server.tables.wishlist.WishlistTypeDescriptions.WishlistTypeDescriptions;
import static com.echothree.model.jooq.server.tables.wishlist.WishlistTypeDetails.WishlistTypeDetails;
import static com.echothree.model.jooq.server.tables.wishlist.WishlistTypes.WishlistTypes;
import static com.echothree.model.jooq.server.tables.wishlist.Wishlists.Wishlists;
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
public class WishlistControl
        extends BaseModelControl {
    
    @Inject
    protected OrderControl orderControl;

    @Inject
    protected OrderLineControl orderLineControl;

    /** Creates a new instance of WishlistControl */
    protected WishlistControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Types
    // --------------------------------------------------------------------------------

    @Inject
    protected WishlistTypeDetailFactory wishlistTypeDetailFactory;

    @Inject
    protected WishlistTypeFactory wishlistTypeFactory;

    @Inject
    private WishlistTypeTransferCache wishlistTypeTransferCache;

    public WishlistType createWishlistType(String wishlistTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultWishlistType = getDefaultWishlistType();
        var defaultFound = defaultWishlistType != null;
        
        if(defaultFound && isDefault) {
            var defaultWishlistTypeDetailValue = getDefaultWishlistTypeDetailValueForUpdate();
            
            defaultWishlistTypeDetailValue.setIsDefault(false);
            updateWishlistTypeFromValue(defaultWishlistTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var wishlistType = wishlistTypeFactory.create();
        var wishlistTypeDetail = wishlistTypeDetailFactory.create(wishlistType,
                wishlistTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        wishlistType = wishlistTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                wishlistType.getPrimaryKey());
        wishlistType.setActiveDetail(wishlistTypeDetail);
        wishlistType.setLastDetail(wishlistTypeDetail);
        wishlistType.store();
        
        sendEvent(wishlistType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return wishlistType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WishlistType */
    public WishlistType getWishlistTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new WishlistTypePK(entityInstance.getEntityUniqueId());

        return wishlistTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public WishlistType getWishlistTypeByEntityInstance(final EntityInstance entityInstance) {
        return getWishlistTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WishlistType getWishlistTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getWishlistTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public WishlistType getWishlistTypeByPK(WishlistTypePK pk) {
        return wishlistTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countWishlistTypes() {
        return session.getDslContext()
                .selectCount()
                .from(WishlistTypes)
                .join(WishlistTypeDetails).onKey(WISHLIST_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WishlistType> getWishlistTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WishlistTypes.fields())
                .from(WishlistTypes)
                .join(WishlistTypeDetails).onKey(WISHLIST_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WishlistTypeDetails.SORT_ORDER, WishlistTypeDetails.WISHLIST_TYPE_NAME),
                    WishlistTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return wishlistTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WishlistType> getWishlistTypes() {
        return getWishlistTypes(EntityPermission.READ_ONLY);
    }
    
    public List<WishlistType> getWishlistTypesForUpdate() {
        return getWishlistTypes(EntityPermission.READ_WRITE);
    }
    
    public WishlistType getDefaultWishlistType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WishlistTypes.fields())
                .from(WishlistTypes)
                .join(WishlistTypeDetails).onKey(WISHLIST_TYPES_ACTIVE_DETAIL_FK)
                .where(WishlistTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return wishlistTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WishlistType getDefaultWishlistType() {
        return getDefaultWishlistType(EntityPermission.READ_ONLY);
    }
    
    public WishlistType getDefaultWishlistTypeForUpdate() {
        return getDefaultWishlistType(EntityPermission.READ_WRITE);
    }
    
    public WishlistTypeDetailValue getDefaultWishlistTypeDetailValueForUpdate() {
        return getDefaultWishlistTypeForUpdate().getLastDetailForUpdate().getWishlistTypeDetailValue().clone();
    }
    
    public WishlistType getWishlistTypeByName(String wishlistTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WishlistTypes.fields())
                .from(WishlistTypes)
                .join(WishlistTypeDetails).onKey(WISHLIST_TYPES_ACTIVE_DETAIL_FK)
                .where(WishlistTypeDetails.WISHLIST_TYPE_NAME.eq(wishlistTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return wishlistTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WishlistType getWishlistTypeByName(String wishlistTypeName) {
        return getWishlistTypeByName(wishlistTypeName, EntityPermission.READ_ONLY);
    }
    
    public WishlistType getWishlistTypeByNameForUpdate(String wishlistTypeName) {
        return getWishlistTypeByName(wishlistTypeName, EntityPermission.READ_WRITE);
    }
    
    public WishlistTypeDetailValue getWishlistTypeDetailValueForUpdate(WishlistType wishlistType) {
        return wishlistType == null? null: wishlistType.getLastDetailForUpdate().getWishlistTypeDetailValue().clone();
    }
    
    public WishlistTypeDetailValue getWishlistTypeDetailValueByNameForUpdate(String wishlistTypeName) {
        return getWishlistTypeDetailValueForUpdate(getWishlistTypeByNameForUpdate(wishlistTypeName));
    }
    
    public WishlistTypeChoicesBean getWishlistTypeChoices(String defaultWishlistTypeChoice, Language language,
            boolean allowNullChoice) {
        var wishlistTypes = getWishlistTypes();
        var size = wishlistTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultWishlistTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var wishlistType : wishlistTypes) {
            var wishlistTypeDetail = wishlistType.getLastDetail();
            var label = getBestWishlistTypeDescription(wishlistType, language);
            var value = wishlistTypeDetail.getWishlistTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultWishlistTypeChoice != null && defaultWishlistTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && wishlistTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new WishlistTypeChoicesBean(labels, values, defaultValue);
    }
    
    public WishlistTypeTransfer getWishlistTypeTransfer(UserVisit userVisit, WishlistType wishlistType) {
        return wishlistTypeTransferCache.getWishlistTypeTransfer(userVisit, wishlistType);
    }

    public List<WishlistTypeTransfer> getWishlistTypeTransfers(UserVisit userVisit, Collection<WishlistType> wishlistTypes) {
        List<WishlistTypeTransfer> wishlistTypeTransfers = new ArrayList<>(wishlistTypes.size());

        wishlistTypes.forEach((wishlistType) ->
                wishlistTypeTransfers.add(wishlistTypeTransferCache.getWishlistTypeTransfer(userVisit, wishlistType))
        );

        return wishlistTypeTransfers;
    }

    public List<WishlistTypeTransfer> getWishlistTypeTransfers(UserVisit userVisit) {
        return getWishlistTypeTransfers(userVisit, getWishlistTypes());
    }

    private void updateWishlistTypeFromValue(WishlistTypeDetailValue wishlistTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(wishlistTypeDetailValue.hasBeenModified()) {
            var wishlistType = wishlistTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistTypeDetailValue.getWishlistTypePK());
            var wishlistTypeDetail = wishlistType.getActiveDetailForUpdate();
            
            wishlistTypeDetail.setThruTime(session.getStartTime());
            wishlistTypeDetail.store();

            var wishlistTypePK = wishlistTypeDetail.getWishlistTypePK();
            var wishlistTypeName = wishlistTypeDetailValue.getWishlistTypeName();
            var isDefault = wishlistTypeDetailValue.getIsDefault();
            var sortOrder = wishlistTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultWishlistType = getDefaultWishlistType();
                var defaultFound = defaultWishlistType != null && !defaultWishlistType.equals(wishlistType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultWishlistTypeDetailValue = getDefaultWishlistTypeDetailValueForUpdate();
                    
                    defaultWishlistTypeDetailValue.setIsDefault(false);
                    updateWishlistTypeFromValue(defaultWishlistTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            wishlistTypeDetail = wishlistTypeDetailFactory.create(wishlistTypePK, wishlistTypeName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            wishlistType.setActiveDetail(wishlistTypeDetail);
            wishlistType.setLastDetail(wishlistTypeDetail);
            
            sendEvent(wishlistTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateWishlistTypeFromValue(WishlistTypeDetailValue wishlistTypeDetailValue, BasePK updatedBy) {
        updateWishlistTypeFromValue(wishlistTypeDetailValue, true, updatedBy);
    }
    
    public void deleteWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        
        deleteWishlistTypeDescriptionsByWishlistType(wishlistType, deletedBy);
        deleteOrdersByWishlistType(wishlistType, deletedBy);
        deleteWishlistPrioritiesByWishlistType(wishlistType, deletedBy);
        orderControl.deleteOrdersByWishlistType(wishlistType, deletedBy);

        var wishlistTypeDetail = wishlistType.getLastDetailForUpdate();
        wishlistTypeDetail.setThruTime(session.getStartTime());
        wishlistType.setActiveDetail(null);
        wishlistType.store();
        
        // Check for default, and pick one if necessary
        var defaultWishlistType = getDefaultWishlistType();
        if(defaultWishlistType == null) {
            var wishlistTypes = getWishlistTypesForUpdate();
            
            if(!wishlistTypes.isEmpty()) {
                var iter = wishlistTypes.iterator();
                if(iter.hasNext()) {
                    defaultWishlistType = iter.next();
                }
                var wishlistTypeDetailValue = Objects.requireNonNull(defaultWishlistType).getLastDetailForUpdate().getWishlistTypeDetailValue().clone();
                
                wishlistTypeDetailValue.setIsDefault(true);
                updateWishlistTypeFromValue(wishlistTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(wishlistType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected WishlistTypeDescriptionFactory wishlistTypeDescriptionFactory;

    @Inject
    private WishlistTypeDescriptionTransferCache wishlistTypeDescriptionTransferCache;

    public WishlistTypeDescription createWishlistTypeDescription(WishlistType wishlistType, Language language, String description,
            BasePK createdBy) {
        var wishlistTypeDescription = wishlistTypeDescriptionFactory.create(wishlistType,
                language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(wishlistType.getPrimaryKey(), EventTypes.MODIFY, wishlistTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return wishlistTypeDescription;
    }
    
    private WishlistTypeDescription getWishlistTypeDescription(WishlistType wishlistType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WishlistTypeDescriptions.fields())
                .from(WishlistTypeDescriptions)
                .where(WishlistTypeDescriptions.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()),
                        WishlistTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        WishlistTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return wishlistTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WishlistTypeDescription getWishlistTypeDescription(WishlistType wishlistType, Language language) {
        return getWishlistTypeDescription(wishlistType, language, EntityPermission.READ_ONLY);
    }
    
    public WishlistTypeDescription getWishlistTypeDescriptionForUpdate(WishlistType wishlistType, Language language) {
        return getWishlistTypeDescription(wishlistType, language, EntityPermission.READ_WRITE);
    }
    
    public WishlistTypeDescriptionValue getWishlistTypeDescriptionValue(WishlistTypeDescription wishlistTypeDescription) {
        return wishlistTypeDescription == null? null: wishlistTypeDescription.getWishlistTypeDescriptionValue().clone();
    }
    
    public WishlistTypeDescriptionValue getWishlistTypeDescriptionValueForUpdate(WishlistType wishlistType, Language language) {
        return getWishlistTypeDescriptionValue(getWishlistTypeDescriptionForUpdate(wishlistType, language));
    }
    
    private List<WishlistTypeDescription> getWishlistTypeDescriptionsByWishlistType(WishlistType wishlistType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WishlistTypeDescriptions.fields())
                    .from(WishlistTypeDescriptions)
                    .join(Languages).onKey(WISHLIST_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(WishlistTypeDescriptions.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()),
                            WishlistTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    WishlistTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WishlistTypeDescriptions.fields())
                    .from(WishlistTypeDescriptions)
                    .where(WishlistTypeDescriptions.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()),
                            WishlistTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return wishlistTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WishlistTypeDescription> getWishlistTypeDescriptionsByWishlistType(WishlistType wishlistType) {
        return getWishlistTypeDescriptionsByWishlistType(wishlistType, EntityPermission.READ_ONLY);
    }
    
    public List<WishlistTypeDescription> getWishlistTypeDescriptionsByWishlistTypeForUpdate(WishlistType wishlistType) {
        return getWishlistTypeDescriptionsByWishlistType(wishlistType, EntityPermission.READ_WRITE);
    }
    
    public String getBestWishlistTypeDescription(WishlistType wishlistType, Language language) {
        String description;
        var wishlistTypeDescription = getWishlistTypeDescription(wishlistType, language);
        
        if(wishlistTypeDescription == null && !language.getIsDefault()) {
            wishlistTypeDescription = getWishlistTypeDescription(wishlistType, partyControl.getDefaultLanguage());
        }
        
        if(wishlistTypeDescription == null) {
            description = wishlistType.getLastDetail().getWishlistTypeName();
        } else {
            description = wishlistTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public WishlistTypeDescriptionTransfer getWishlistTypeDescriptionTransfer(UserVisit userVisit, WishlistTypeDescription wishlistTypeDescription) {
        return wishlistTypeDescriptionTransferCache.getWishlistTypeDescriptionTransfer(userVisit, wishlistTypeDescription);
    }
    
    public List<WishlistTypeDescriptionTransfer> getWishlistTypeDescriptionTransfers(UserVisit userVisit, WishlistType wishlistType) {
        var wishlistTypeDescriptions = getWishlistTypeDescriptionsByWishlistType(wishlistType);
        List<WishlistTypeDescriptionTransfer> wishlistTypeDescriptionTransfers = new ArrayList<>(wishlistTypeDescriptions.size());
        
        wishlistTypeDescriptions.forEach((wishlistTypeDescription) ->
                wishlistTypeDescriptionTransfers.add(wishlistTypeDescriptionTransferCache.getWishlistTypeDescriptionTransfer(userVisit, wishlistTypeDescription))
        );
        
        return wishlistTypeDescriptionTransfers;
    }
    
    public void updateWishlistTypeDescriptionFromValue(WishlistTypeDescriptionValue wishlistTypeDescriptionValue, BasePK updatedBy) {
        if(wishlistTypeDescriptionValue.hasBeenModified()) {
            var wishlistTypeDescription = wishlistTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistTypeDescriptionValue.getPrimaryKey());
            
            wishlistTypeDescription.setThruTime(session.getStartTime());
            wishlistTypeDescription.store();

            var wishlistType = wishlistTypeDescription.getWishlistType();
            var language = wishlistTypeDescription.getLanguage();
            var description = wishlistTypeDescriptionValue.getDescription();
            
            wishlistTypeDescription = wishlistTypeDescriptionFactory.create(wishlistType, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(wishlistType.getPrimaryKey(), EventTypes.MODIFY, wishlistTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWishlistTypeDescription(WishlistTypeDescription wishlistTypeDescription, BasePK deletedBy) {
        wishlistTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(wishlistTypeDescription.getWishlistTypePK(), EventTypes.MODIFY, wishlistTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWishlistTypeDescriptionsByWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        var wishlistTypeDescriptions = getWishlistTypeDescriptionsByWishlistTypeForUpdate(wishlistType);
        
        wishlistTypeDescriptions.forEach((wishlistTypeDescription) -> 
                deleteWishlistTypeDescription(wishlistTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Priorities
    // --------------------------------------------------------------------------------

    @Inject
    protected WishlistPriorityDetailFactory wishlistPriorityDetailFactory;

    @Inject
    protected WishlistPriorityFactory wishlistPriorityFactory;

    @Inject
    private WishlistPriorityTransferCache wishlistPriorityTransferCache;

    public WishlistPriority createWishlistPriority(WishlistType wishlistType, String wishlistPriorityName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultWishlistPriority = getDefaultWishlistPriority(wishlistType);
        var defaultFound = defaultWishlistPriority != null;
        
        if(defaultFound && isDefault) {
            var defaultWishlistPriorityDetailValue = getDefaultWishlistPriorityDetailValueForUpdate(wishlistType);
            
            defaultWishlistPriorityDetailValue.setIsDefault(false);
            updateWishlistPriorityFromValue(defaultWishlistPriorityDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var wishlistPriority = wishlistPriorityFactory.create();
        var wishlistPriorityDetail = wishlistPriorityDetailFactory.create(
                wishlistPriority, wishlistType, wishlistPriorityName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        wishlistPriority = wishlistPriorityFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                wishlistPriority.getPrimaryKey());
        wishlistPriority.setActiveDetail(wishlistPriorityDetail);
        wishlistPriority.setLastDetail(wishlistPriorityDetail);
        wishlistPriority.store();
        
        sendEvent(wishlistPriority.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return wishlistPriority;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.WishlistPriority */
    public WishlistPriority getWishlistPriorityByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new WishlistPriorityPK(entityInstance.getEntityUniqueId());

        return wishlistPriorityFactory.getEntityFromPK(entityPermission, pk);
    }

    public WishlistPriority getWishlistPriorityByEntityInstance(final EntityInstance entityInstance) {
        return getWishlistPriorityByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WishlistPriority getWishlistPriorityByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getWishlistPriorityByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countWishlistPrioritiesByWishlistType(final WishlistType wishlistType) {
        return session.getDslContext()
                .selectCount()
                .from(WishlistPriorities)
                .join(WishlistPriorityDetails).onKey(WISHLIST_PRIORITIES_ACTIVE_DETAIL_FK)
                .where(WishlistPriorityDetails.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WishlistPriority> getWishlistPriorities(WishlistType wishlistType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WishlistPriorities.fields())
                .from(WishlistPriorities)
                .join(WishlistPriorityDetails).onKey(WISHLIST_PRIORITIES_ACTIVE_DETAIL_FK)
                .where(WishlistPriorityDetails.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(WishlistPriorityDetails.SORT_ORDER, WishlistPriorityDetails.WISHLIST_PRIORITY_NAME),
                    WishlistPriorityFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return wishlistPriorityFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WishlistPriority> getWishlistPriorities(WishlistType wishlistType) {
        return getWishlistPriorities(wishlistType, EntityPermission.READ_ONLY);
    }
    
    public List<WishlistPriority> getWishlistPrioritiesForUpdate(WishlistType wishlistType) {
        return getWishlistPriorities(wishlistType, EntityPermission.READ_WRITE);
    }
    
    public WishlistPriority getDefaultWishlistPriority(WishlistType wishlistType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WishlistPriorities.fields())
                .from(WishlistPriorities)
                .join(WishlistPriorityDetails).onKey(WISHLIST_PRIORITIES_ACTIVE_DETAIL_FK)
                .where(WishlistPriorityDetails.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()),
                        WishlistPriorityDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return wishlistPriorityFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WishlistPriority getDefaultWishlistPriority(WishlistType wishlistType) {
        return getDefaultWishlistPriority(wishlistType, EntityPermission.READ_ONLY);
    }
    
    public WishlistPriority getDefaultWishlistPriorityForUpdate(WishlistType wishlistType) {
        return getDefaultWishlistPriority(wishlistType, EntityPermission.READ_WRITE);
    }
    
    public WishlistPriorityDetailValue getDefaultWishlistPriorityDetailValueForUpdate(WishlistType wishlistType) {
        return getDefaultWishlistPriorityForUpdate(wishlistType).getLastDetailForUpdate().getWishlistPriorityDetailValue().clone();
    }
    
    public WishlistPriority getWishlistPriorityByName(WishlistType wishlistType, String wishlistPriorityName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WishlistPriorities.fields())
                .from(WishlistPriorities)
                .join(WishlistPriorityDetails).onKey(WISHLIST_PRIORITIES_ACTIVE_DETAIL_FK)
                .where(WishlistPriorityDetails.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()),
                        WishlistPriorityDetails.WISHLIST_PRIORITY_NAME.eq(wishlistPriorityName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return wishlistPriorityFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WishlistPriority getWishlistPriorityByName(WishlistType wishlistType, String wishlistPriorityName) {
        return getWishlistPriorityByName(wishlistType, wishlistPriorityName, EntityPermission.READ_ONLY);
    }
    
    public WishlistPriority getWishlistPriorityByNameForUpdate(WishlistType wishlistType, String wishlistPriorityName) {
        return getWishlistPriorityByName(wishlistType, wishlistPriorityName, EntityPermission.READ_WRITE);
    }
    
    public WishlistPriorityDetailValue getWishlistPriorityDetailValueForUpdate(WishlistPriority wishlistPriority) {
        return wishlistPriority == null? null: wishlistPriority.getLastDetailForUpdate().getWishlistPriorityDetailValue().clone();
    }
    
    public WishlistPriorityDetailValue getWishlistPriorityDetailValueByNameForUpdate(WishlistType wishlistType, String wishlistPriorityName) {
        return getWishlistPriorityDetailValueForUpdate(getWishlistPriorityByNameForUpdate(wishlistType, wishlistPriorityName));
    }
    
    public WishlistPriorityChoicesBean getWishlistPriorityChoices(String defaultWishlistPriorityChoice, Language language,
            boolean allowNullChoice, WishlistType wishlistType) {
        var wishlistPriorities = getWishlistPriorities(wishlistType);
        var size = wishlistPriorities.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultWishlistPriorityChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var wishlistPriority : wishlistPriorities) {
            var wishlistPriorityDetail = wishlistPriority.getLastDetail();
            var label = getBestWishlistPriorityDescription(wishlistPriority, language);
            var value = wishlistPriorityDetail.getWishlistPriorityName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultWishlistPriorityChoice != null && defaultWishlistPriorityChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && wishlistPriorityDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new WishlistPriorityChoicesBean(labels, values, defaultValue);
    }
    
    public WishlistPriorityTransfer getWishlistPriorityTransfer(UserVisit userVisit, WishlistPriority wishlistPriority) {
        return wishlistPriorityTransferCache.getWishlistPriorityTransfer(userVisit, wishlistPriority);
    }

    public List<WishlistPriorityTransfer> getWishlistPriorityTransfers(UserVisit userVisit, Collection<WishlistPriority> wishlistPriorities) {
        List<WishlistPriorityTransfer> wishlistPriorityTransfers = new ArrayList<>(wishlistPriorities.size());

        wishlistPriorities.forEach((wishlistPriority) ->
                wishlistPriorityTransfers.add(wishlistPriorityTransferCache.getWishlistPriorityTransfer(userVisit, wishlistPriority))
        );

        return wishlistPriorityTransfers;
    }

    public List<WishlistPriorityTransfer> getWishlistPriorityTransfers(UserVisit userVisit, WishlistType wishlistType) {
        return getWishlistPriorityTransfers(userVisit, getWishlistPriorities(wishlistType));
    }

    private void updateWishlistPriorityFromValue(WishlistPriorityDetailValue wishlistPriorityDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(wishlistPriorityDetailValue.hasBeenModified()) {
            var wishlistPriority = wishlistPriorityFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistPriorityDetailValue.getWishlistPriorityPK());
            var wishlistPriorityDetail = wishlistPriority.getActiveDetailForUpdate();
            
            wishlistPriorityDetail.setThruTime(session.getStartTime());
            wishlistPriorityDetail.store();

            var wishlistPriorityPK = wishlistPriorityDetail.getWishlistPriorityPK();
            var wishlistType = wishlistPriorityDetail.getWishlistType();
            var wishlistTypePK = wishlistType.getPrimaryKey();
            var wishlistPriorityName = wishlistPriorityDetailValue.getWishlistPriorityName();
            var isDefault = wishlistPriorityDetailValue.getIsDefault();
            var sortOrder = wishlistPriorityDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultWishlistPriority = getDefaultWishlistPriority(wishlistType);
                var defaultFound = defaultWishlistPriority != null && !defaultWishlistPriority.equals(wishlistPriority);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultWishlistPriorityDetailValue = getDefaultWishlistPriorityDetailValueForUpdate(wishlistType);
                    
                    defaultWishlistPriorityDetailValue.setIsDefault(false);
                    updateWishlistPriorityFromValue(defaultWishlistPriorityDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            wishlistPriorityDetail = wishlistPriorityDetailFactory.create(wishlistPriorityPK,
                    wishlistTypePK, wishlistPriorityName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            wishlistPriority.setActiveDetail(wishlistPriorityDetail);
            wishlistPriority.setLastDetail(wishlistPriorityDetail);
            
            sendEvent(wishlistPriorityPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateWishlistPriorityFromValue(WishlistPriorityDetailValue wishlistPriorityDetailValue, BasePK updatedBy) {
        updateWishlistPriorityFromValue(wishlistPriorityDetailValue, true, updatedBy);
    }
    
    public void deleteWishlistPriority(WishlistPriority wishlistPriority, BasePK deletedBy) {
        deleteWishlistPriorityDescriptionsByWishlistPriority(wishlistPriority, deletedBy);
        deleteOrderLinesByWishlistPriority(wishlistPriority, deletedBy);

        var wishlistPriorityDetail = wishlistPriority.getLastDetailForUpdate();
        wishlistPriorityDetail.setThruTime(session.getStartTime());
        wishlistPriority.setActiveDetail(null);
        wishlistPriority.store();
        
        // Check for default, and pick one if necessary
        var wishlistType = wishlistPriorityDetail.getWishlistType();
        var defaultWishlistPriority = getDefaultWishlistPriority(wishlistType);
        if(defaultWishlistPriority == null) {
            var wishlistPriorities = getWishlistPrioritiesForUpdate(wishlistType);
            
            if(!wishlistPriorities.isEmpty()) {
                var iter = wishlistPriorities.iterator();
                if(iter.hasNext()) {
                    defaultWishlistPriority = iter.next();
                }
                var wishlistPriorityDetailValue = Objects.requireNonNull(defaultWishlistPriority).getLastDetailForUpdate().getWishlistPriorityDetailValue().clone();
                
                wishlistPriorityDetailValue.setIsDefault(true);
                updateWishlistPriorityFromValue(wishlistPriorityDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(wishlistPriority.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteWishlistPrioritiesByWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        var wishlistPriorities = getWishlistPrioritiesForUpdate(wishlistType);
        
        wishlistPriorities.forEach((wishlistPriority) -> 
                deleteWishlistPriority(wishlistPriority, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Priority Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected WishlistPriorityDescriptionFactory wishlistPriorityDescriptionFactory;

    @Inject
    private WishlistPriorityDescriptionTransferCache wishlistPriorityDescriptionTransferCache;

    public WishlistPriorityDescription createWishlistPriorityDescription(WishlistPriority wishlistPriority,
            Language language, String description, BasePK createdBy) {
        var wishlistPriorityDescription = wishlistPriorityDescriptionFactory.create(
                wishlistPriority, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(wishlistPriority.getPrimaryKey(), EventTypes.MODIFY, wishlistPriorityDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return wishlistPriorityDescription;
    }
    
    private WishlistPriorityDescription getWishlistPriorityDescription(WishlistPriority wishlistPriority, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WishlistPriorityDescriptions.fields())
                .from(WishlistPriorityDescriptions)
                .where(WishlistPriorityDescriptions.WISHLIST_PRIORITY.eq(wishlistPriority.getPrimaryKey()),
                        WishlistPriorityDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        WishlistPriorityDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return wishlistPriorityDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WishlistPriorityDescription getWishlistPriorityDescription(WishlistPriority wishlistPriority, Language language) {
        return getWishlistPriorityDescription(wishlistPriority, language, EntityPermission.READ_ONLY);
    }
    
    public WishlistPriorityDescription getWishlistPriorityDescriptionForUpdate(WishlistPriority wishlistPriority, Language language) {
        return getWishlistPriorityDescription(wishlistPriority, language, EntityPermission.READ_WRITE);
    }
    
    public WishlistPriorityDescriptionValue getWishlistPriorityDescriptionValue(WishlistPriorityDescription wishlistPriorityDescription) {
        return wishlistPriorityDescription == null? null: wishlistPriorityDescription.getWishlistPriorityDescriptionValue().clone();
    }
    
    public WishlistPriorityDescriptionValue getWishlistPriorityDescriptionValueForUpdate(WishlistPriority wishlistPriority, Language language) {
        return getWishlistPriorityDescriptionValue(getWishlistPriorityDescriptionForUpdate(wishlistPriority, language));
    }
    
    private List<WishlistPriorityDescription> getWishlistPriorityDescriptionsByWishlistPriority(WishlistPriority wishlistPriority, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WishlistPriorityDescriptions.fields())
                    .from(WishlistPriorityDescriptions)
                    .join(Languages).onKey(WISHLIST_PRIORITY_DESCRIPTIONS_LANGUAGE_FK)
                    .where(WishlistPriorityDescriptions.WISHLIST_PRIORITY.eq(wishlistPriority.getPrimaryKey()),
                            WishlistPriorityDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    WishlistPriorityDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WishlistPriorityDescriptions.fields())
                    .from(WishlistPriorityDescriptions)
                    .where(WishlistPriorityDescriptions.WISHLIST_PRIORITY.eq(wishlistPriority.getPrimaryKey()),
                            WishlistPriorityDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return wishlistPriorityDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WishlistPriorityDescription> getWishlistPriorityDescriptionsByWishlistPriority(WishlistPriority wishlistPriority) {
        return getWishlistPriorityDescriptionsByWishlistPriority(wishlistPriority, EntityPermission.READ_ONLY);
    }
    
    public List<WishlistPriorityDescription> getWishlistPriorityDescriptionsByWishlistPriorityForUpdate(WishlistPriority wishlistPriority) {
        return getWishlistPriorityDescriptionsByWishlistPriority(wishlistPriority, EntityPermission.READ_WRITE);
    }
    
    public String getBestWishlistPriorityDescription(WishlistPriority wishlistPriority, Language language) {
        String description;
        var wishlistPriorityDescription = getWishlistPriorityDescription(wishlistPriority, language);
        
        if(wishlistPriorityDescription == null && !language.getIsDefault()) {
            wishlistPriorityDescription = getWishlistPriorityDescription(wishlistPriority, partyControl.getDefaultLanguage());
        }
        
        if(wishlistPriorityDescription == null) {
            description = wishlistPriority.getLastDetail().getWishlistPriorityName();
        } else {
            description = wishlistPriorityDescription.getDescription();
        }
        
        return description;
    }
    
    public WishlistPriorityDescriptionTransfer getWishlistPriorityDescriptionTransfer(UserVisit userVisit, WishlistPriorityDescription wishlistPriorityDescription) {
        return wishlistPriorityDescriptionTransferCache.getWishlistPriorityDescriptionTransfer(userVisit, wishlistPriorityDescription);
    }
    
    public List<WishlistPriorityDescriptionTransfer> getWishlistPriorityDescriptionTransfers(UserVisit userVisit, WishlistPriority wishlistPriority) {
        var wishlistPriorityDescriptions = getWishlistPriorityDescriptionsByWishlistPriority(wishlistPriority);
        List<WishlistPriorityDescriptionTransfer> wishlistPriorityDescriptionTransfers = new ArrayList<>(wishlistPriorityDescriptions.size());
        
        wishlistPriorityDescriptions.forEach((wishlistPriorityDescription) ->
                wishlistPriorityDescriptionTransfers.add(wishlistPriorityDescriptionTransferCache.getWishlistPriorityDescriptionTransfer(userVisit, wishlistPriorityDescription))
        );
        
        return wishlistPriorityDescriptionTransfers;
    }
    
    public void updateWishlistPriorityDescriptionFromValue(WishlistPriorityDescriptionValue wishlistPriorityDescriptionValue, BasePK updatedBy) {
        if(wishlistPriorityDescriptionValue.hasBeenModified()) {
            var wishlistPriorityDescription = wishlistPriorityDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistPriorityDescriptionValue.getPrimaryKey());
            
            wishlistPriorityDescription.setThruTime(session.getStartTime());
            wishlistPriorityDescription.store();

            var wishlistPriority = wishlistPriorityDescription.getWishlistPriority();
            var language = wishlistPriorityDescription.getLanguage();
            var description = wishlistPriorityDescriptionValue.getDescription();
            
            wishlistPriorityDescription = wishlistPriorityDescriptionFactory.create(wishlistPriority, language,
                    description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(wishlistPriority.getPrimaryKey(), EventTypes.MODIFY, wishlistPriorityDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWishlistPriorityDescription(WishlistPriorityDescription wishlistPriorityDescription, BasePK deletedBy) {
        wishlistPriorityDescription.setThruTime(session.getStartTime());
        
        sendEvent(wishlistPriorityDescription.getWishlistPriorityPK(), EventTypes.MODIFY, wishlistPriorityDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWishlistPriorityDescriptionsByWishlistPriority(WishlistPriority wishlistPriority, BasePK deletedBy) {
        var wishlistPriorityDescriptions = getWishlistPriorityDescriptionsByWishlistPriorityForUpdate(wishlistPriority);
        
        wishlistPriorityDescriptions.forEach((wishlistPriorityDescription) -> 
                deleteWishlistPriorityDescription(wishlistPriorityDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlists
    // --------------------------------------------------------------------------------

    @Inject
    protected WishlistFactory wishlistFactory;

    public Wishlist createWishlist(Order order, OfferUse offerUse, WishlistType wishlistType, BasePK createdBy) {
        var wishlist = wishlistFactory.create(order, offerUse, wishlistType,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, wishlist.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return wishlist;
    }

    public long countWishlistsByOfferUse(final OfferUse offerUse) {
        return session.getDslContext()
                .selectCount()
                .from(Wishlists)
                .where(Wishlists.OFFER_USE.eq(offerUse.getPrimaryKey()),
                        Wishlists.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWishlistsByWishlistType(final WishlistType wishlistType) {
        return session.getDslContext()
                .selectCount()
                .from(Wishlists)
                .where(Wishlists.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()),
                        Wishlists.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Wishlist getWishlist(Order order, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Wishlists.fields())
                .from(Wishlists)
                .where(Wishlists.ORDER.eq(order.getPrimaryKey()),
                        Wishlists.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return wishlistFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Wishlist getWishlist(Order order) {
        return getWishlist(order, EntityPermission.READ_ONLY);
    }
    
    public Wishlist getWishlistForUpdate(Order order) {
        return getWishlist(order, EntityPermission.READ_WRITE);
    }
    
    public WishlistValue getWishlistValue(Wishlist wishlist) {
        return wishlist == null? null: wishlist.getWishlistValue().clone();
    }
    
    public WishlistValue getWishlistValueForUpdate(Order order) {
        return getWishlistValue(getWishlistForUpdate(order));
    }
    
    private List<Wishlist> getWishlistsByWishlistType(WishlistType wishlistType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Wishlists.fields())
                    .from(Wishlists)
                    .join(Orders).onKey(WISHLISTS_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(Wishlists.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()),
                            Wishlists.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderDetails.ORDER_NAME),
                    WishlistFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Wishlists.fields())
                    .from(Wishlists)
                    .where(Wishlists.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()),
                            Wishlists.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return wishlistFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Wishlist> getWishlistsByWishlistType(WishlistType wishlistType) {
        return getWishlistsByWishlistType(wishlistType, EntityPermission.READ_ONLY);
    }
    
    public List<Wishlist> getWishlistsByWishlistTypeForUpdate(WishlistType wishlistType) {
        return getWishlistsByWishlistType(wishlistType, EntityPermission.READ_WRITE);
    }
    
    public void updateWishlistFromValue(WishlistValue wishlistValue, BasePK updatedBy) {
        if(wishlistValue.hasBeenModified()) {
            var wishlist = wishlistFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    wishlistValue.getPrimaryKey());
            
            wishlist.setThruTime(session.getStartTime());
            wishlist.store();

            var orderPK = wishlist.getOrderPK(); // Not updated
            var offerUsePK = wishlistValue.getOfferUsePK();
            var wishlistTypePK = wishlistValue.getWishlistTypePK();
            
            wishlist = wishlistFactory.create(orderPK, offerUsePK, wishlistTypePK,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(orderPK, EventTypes.MODIFY, wishlist.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWishlist(Wishlist wishlist, BasePK deletedBy) {
        wishlist.setThruTime(session.getStartTime());
        
        sendEvent(wishlist.getOrderPK(), EventTypes.MODIFY, wishlist.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWishlistByOrder(Order order, BasePK deletedBy) {
        deleteWishlist(getWishlistForUpdate(order), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Lines
    // --------------------------------------------------------------------------------

    @Inject
    protected WishlistLineFactory wishlistLineFactory;

    public WishlistLine createWishlistLine(OrderLine orderLine, OfferUse offerUse, WishlistPriority wishlistPriority,
            AssociateReferral associateReferral, String comment, BasePK createdBy) {
        var wishlistLine = wishlistLineFactory.create(orderLine, offerUse,
                wishlistPriority, associateReferral, comment, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(orderLine.getPrimaryKey(), EventTypes.MODIFY, wishlistLine.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return wishlistLine;
    }

    public long countWishlistLinesByOfferUse(final OfferUse offerUse) {
        return session.getDslContext()
                .selectCount()
                .from(WishlistLines)
                .where(WishlistLines.OFFER_USE.eq(offerUse.getPrimaryKey()),
                        WishlistLines.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWishlistLinesByWishlistPriority(final WishlistPriority wishlistPriority) {
        return session.getDslContext()
                .selectCount()
                .from(WishlistLines)
                .where(WishlistLines.WISHLIST_PRIORITY.eq(wishlistPriority.getPrimaryKey()),
                        WishlistLines.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countWishlistLinesByAssociateReferral(final AssociateReferral associateReferral) {
        return session.getDslContext()
                .selectCount()
                .from(WishlistLines)
                .where(WishlistLines.ASSOCIATE_REFERRAL.eq(associateReferral.getPrimaryKey()),
                        WishlistLines.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<WishlistLine> getWishlistLinesByWishlistPriority(WishlistPriority wishlistPriority,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(WishlistLines.fields())
                    .from(WishlistLines)
                    .join(OrderLines).onKey(WISHLIST_LINES_ORDER_LINE_FK)
                    .join(OrderLineDetails).onKey(ORDER_LINES_LAST_DETAIL_FK)
                    .join(Orders).onKey(ORDER_LINE_DETAILS_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(WishlistLines.WISHLIST_PRIORITY.eq(wishlistPriority.getPrimaryKey()),
                            WishlistLines.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderDetails.ORDER_NAME, OrderLineDetails.ORDER_LINE_SEQUENCE),
                    WishlistLineFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(WishlistLines.fields())
                    .from(WishlistLines)
                    .where(WishlistLines.WISHLIST_PRIORITY.eq(wishlistPriority.getPrimaryKey()),
                            WishlistLines.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return wishlistLineFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<WishlistLine> getWishlistLinesByWishlistPriority(WishlistPriority wishlistPriority) {
        return getWishlistLinesByWishlistPriority(wishlistPriority, EntityPermission.READ_ONLY);
    }
    
    public List<WishlistLine> getWishlistLinesByWishlistPriorityForUpdate(WishlistPriority wishlistPriority) {
        return getWishlistLinesByWishlistPriority(wishlistPriority, EntityPermission.READ_WRITE);
    }
    
    private WishlistLine getWishlistLine(OrderLine orderLine, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(WishlistLines.fields())
                .from(WishlistLines)
                .where(WishlistLines.ORDER_LINE.eq(orderLine.getPrimaryKey()),
                        WishlistLines.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return wishlistLineFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public WishlistLine getWishlistLine(OrderLine orderLine) {
        return getWishlistLine(orderLine, EntityPermission.READ_ONLY);
    }
    
    public WishlistLine getWishlistLineForUpdate(OrderLine orderLine) {
        return getWishlistLine(orderLine, EntityPermission.READ_WRITE);
    }
    
    public WishlistLineValue getWishlistLineValue(WishlistLine wishlistLine) {
        return wishlistLine == null? null: wishlistLine.getWishlistLineValue().clone();
    }
    
    public WishlistLineValue getWishlistLineValueForUpdate(OrderLine orderLine) {
        return getWishlistLineValue(getWishlistLineForUpdate(orderLine));
    }
    
    public void updateWishlistLineFromValue(WishlistLineValue wishlistLineValue, BasePK updatedBy) {
        if(wishlistLineValue.hasBeenModified()) {
            var wishlistLine = wishlistLineFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    wishlistLineValue.getPrimaryKey());
            
            wishlistLine.setThruTime(session.getStartTime());
            wishlistLine.store();

            var orderLinePK = wishlistLine.getOrderLinePK(); // Not updated
            var offerUsePK = wishlistLineValue.getOfferUsePK();
            var wishlistPriorityPK = wishlistLineValue.getWishlistPriorityPK();
            var associateReferralPK = wishlistLineValue.getAssociateReferralPK();
            var comment = wishlistLineValue.getComment();
            
            wishlistLine = wishlistLineFactory.create(orderLinePK, offerUsePK,
                    wishlistPriorityPK, associateReferralPK, comment, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(orderLinePK, EventTypes.MODIFY, wishlistLine.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWishlistLine(WishlistLine wishlistLine, BasePK deletedBy) {
        wishlistLine.setThruTime(session.getStartTime());
        
        sendEvent(wishlistLine.getOrderLinePK(), EventTypes.MODIFY, wishlistLine.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWishlistLineByOrderLine(OrderLine orderLine, BasePK deletedBy) {
        deleteWishlistLine(getWishlistLineForUpdate(orderLine), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlists
    // --------------------------------------------------------------------------------

    @Inject
    protected OrderFactory orderFactory;

    @Inject
    private WishlistTransferCache wishlistTransferCache;

    private Order getWishlist(Party companyParty, Party party, WishlistType wishlistType, Currency currency,
            EntityPermission entityPermission) {
        var billFromRole = OrderRoles.as("billFromRole");
        var billFromRoleType = OrderRoleTypes.as("billFromRoleType");
        var billToRole = OrderRoles.as("billToRole");
        var billToRoleType = OrderRoleTypes.as("billToRoleType");
        var baseQuery = session.getDslContext()
                .select(Orders.fields())
                .from(Orders)
                .join(OrderDetails).onKey(ORDERS_ACTIVE_DETAIL_FK)
                .join(OrderTypes).onKey(ORDER_DETAILS_ORDER_TYPE_FK)
                .join(OrderTypeDetails).onKey(ORDER_TYPES_LAST_DETAIL_FK)
                .join(Wishlists).onKey(WISHLISTS_ORDER_FK)
                .join(billFromRole).on(billFromRole.ORDER.eq(Orders.ORDER))
                .join(billFromRoleType).on(billFromRoleType.ORDER_ROLE_TYPE.eq(billFromRole.ORDER_ROLE_TYPE))
                .join(billToRole).on(billToRole.ORDER.eq(Orders.ORDER))
                .join(billToRoleType).on(billToRoleType.ORDER_ROLE_TYPE.eq(billToRole.ORDER_ROLE_TYPE))
                .where(OrderTypeDetails.ORDER_TYPE_NAME.eq(com.echothree.model.control.order.common.OrderTypes.WISHLIST.name()),
                        Wishlists.THRU_TIME.eq(Session.MAX_TIME),
                        billFromRole.PARTY.eq(companyParty.getPrimaryKey()),
                        billFromRoleType.ORDER_ROLE_TYPE_NAME.eq(com.echothree.model.control.order.common.OrderRoleTypes.BILL_FROM.name()),
                        billFromRole.THRU_TIME.eq(Session.MAX_TIME),
                        billToRole.PARTY.eq(party.getPrimaryKey()),
                        billToRoleType.ORDER_ROLE_TYPE_NAME.eq(com.echothree.model.control.order.common.OrderRoleTypes.BILL_TO.name()),
                        billToRole.THRU_TIME.eq(Session.MAX_TIME),
                        OrderDetails.CURRENCY.eq(currency.getPrimaryKey()),
                        Wishlists.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Order getWishlist(Party companyParty, Party party, WishlistType wishlistType, Currency currency) {
        return getWishlist(companyParty, party, wishlistType, currency, EntityPermission.READ_ONLY);
    }
    
    public Order getWishlistForUpdate(Party companyParty, Party party, WishlistType wishlistType, Currency currency) {
        return getWishlist(companyParty, party, wishlistType, currency, EntityPermission.READ_WRITE);
    }
    
    private List<Order> getWishlists(Party companyParty, Party party, EntityPermission entityPermission) {
        var billFromRole = OrderRoles.as("billFromRole");
        var billFromRoleType = OrderRoleTypes.as("billFromRoleType");
        var billToRole = OrderRoles.as("billToRole");
        var billToRoleType = OrderRoleTypes.as("billToRoleType");
        var baseQuery = session.getDslContext()
                .select(Orders.fields())
                .from(Orders)
                .join(OrderDetails).onKey(ORDERS_ACTIVE_DETAIL_FK)
                .join(OrderTypes).onKey(ORDER_DETAILS_ORDER_TYPE_FK)
                .join(OrderTypeDetails).onKey(ORDER_TYPES_LAST_DETAIL_FK)
                .join(Wishlists).onKey(WISHLISTS_ORDER_FK)
                .join(billFromRole).on(billFromRole.ORDER.eq(Orders.ORDER))
                .join(billFromRoleType).on(billFromRoleType.ORDER_ROLE_TYPE.eq(billFromRole.ORDER_ROLE_TYPE))
                .join(billToRole).on(billToRole.ORDER.eq(Orders.ORDER))
                .join(billToRoleType).on(billToRoleType.ORDER_ROLE_TYPE.eq(billToRole.ORDER_ROLE_TYPE))
                .where(OrderTypeDetails.ORDER_TYPE_NAME.eq(com.echothree.model.control.order.common.OrderTypes.WISHLIST.name()),
                        Wishlists.THRU_TIME.eq(Session.MAX_TIME),
                        billFromRole.PARTY.eq(companyParty.getPrimaryKey()),
                        billFromRoleType.ORDER_ROLE_TYPE_NAME.eq(com.echothree.model.control.order.common.OrderRoleTypes.BILL_FROM.name()),
                        billFromRole.THRU_TIME.eq(Session.MAX_TIME),
                        billToRole.PARTY.eq(party.getPrimaryKey()),
                        billToRoleType.ORDER_ROLE_TYPE_NAME.eq(com.echothree.model.control.order.common.OrderRoleTypes.BILL_TO.name()),
                        billToRole.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(OrderDetails.ORDER_NAME), OrderFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Order> getWishlists(Party companyParty, Party party) {
        return getWishlists(companyParty, party, EntityPermission.READ_ONLY);
    }
    
    public List<Order> getWishlistsForUpdate(Party companyParty, Party party) {
        return getWishlists(companyParty, party, EntityPermission.READ_WRITE);
    }
    
    public WishlistTransfer getWishlistTransfer(UserVisit userVisit, Order order) {
        return wishlistTransferCache.getWishlistTransfer(userVisit, order);
    }
    
    private List<Order> getOrdersByWishlistType(WishlistType wishlistType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Orders.fields())
                .from(Orders)
                .join(Wishlists).onKey(WISHLISTS_ORDER_FK)
                .where(Wishlists.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()),
                        Wishlists.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Orders.fields())
                    .from(Orders)
                    .join(Wishlists).onKey(WISHLISTS_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(Wishlists.WISHLIST_TYPE.eq(wishlistType.getPrimaryKey()),
                            Wishlists.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderDetails.ORDER_NAME),
                    OrderFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Order> getOrdersByWishlistType(WishlistType wishlistType) {
        return getOrdersByWishlistType(wishlistType, EntityPermission.READ_ONLY);
    }
    
    public List<Order> getOrdersByWishlistTypeForUpdate(WishlistType wishlistType) {
        return getOrdersByWishlistType(wishlistType, EntityPermission.READ_WRITE);
    }
    
    public List<WishlistTransfer> getWishlistTransfers(UserVisit userVisit, Party companyParty, Party party) {
        var orders = getWishlists(companyParty, party);
        List<WishlistTransfer> wishlistTransfers = new ArrayList<>(orders.size());
        
        orders.forEach((order) ->
                wishlistTransfers.add(wishlistTransferCache.getWishlistTransfer(userVisit, order))
        );
        
        return wishlistTransfers;
    }
    
    public void deleteOrdersByWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        var orders = getOrdersByWishlistTypeForUpdate(wishlistType);
        
        orders.forEach((order) -> {
            orderControl.deleteOrder(order, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Lines
    // --------------------------------------------------------------------------------

    @Inject
    protected OrderLineFactory orderLineFactory;

    @Inject
    private WishlistLineTransferCache wishlistLineTransferCache;

    private OrderLine getWishlistLine(Order order, Integer orderLineSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderLines.fields())
                .from(OrderLines)
                .join(OrderLineDetails).onKey(ORDER_LINES_ACTIVE_DETAIL_FK)
                .where(OrderLineDetails.ORDER.eq(order.getPrimaryKey()),
                        OrderLineDetails.ORDER_LINE_SEQUENCE.eq(orderLineSequence));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderLineFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public OrderLine getWishlistLine(Order order, Integer orderLineSequence) {
        return getWishlistLine(order, orderLineSequence, EntityPermission.READ_ONLY);
    }
    
    public OrderLine getWishlistLineForUpdate(Order order, Integer orderLineSequence) {
        return getWishlistLine(order, orderLineSequence, EntityPermission.READ_WRITE);
    }
    
    private OrderLine getWishlistLineByItem(Order order, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderLines.fields())
                .from(OrderLines)
                .join(OrderLineDetails).onKey(ORDER_LINES_ACTIVE_DETAIL_FK)
                .where(OrderLineDetails.ORDER.eq(order.getPrimaryKey()),
                        OrderLineDetails.ITEM.eq(item.getPrimaryKey()),
                        OrderLineDetails.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        OrderLineDetails.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderLineFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public OrderLine getWishlistLineByItem(Order order, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getWishlistLineByItem(order, item, inventoryCondition, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public OrderLine getWishlistLineByItemForUpdate(Order order, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType) {
        return getWishlistLineByItem(order, item, inventoryCondition, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private List<OrderLine> getWishlistLinesByOrder(Order order, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderLines.fields())
                .from(OrderLines)
                .join(OrderLineDetails).onKey(ORDER_LINES_ACTIVE_DETAIL_FK)
                .where(OrderLineDetails.ORDER.eq(order.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(OrderLineDetails.ORDER_LINE_SEQUENCE),
                    OrderLineFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderLineFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<OrderLine> getWishlistLinesByOrder(Order order) {
        return getWishlistLinesByOrder(order, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getWishlistLinesByOrderForUpdate(Order order) {
        return getWishlistLinesByOrder(order, EntityPermission.READ_WRITE);
    }
    
    public long countWishlistLinesByOrder(Order order) {
        return session.getDslContext()
                .selectCount()
                .from(OrderLines)
                .join(OrderLineDetails).onKey(ORDER_LINES_ACTIVE_DETAIL_FK)
                .where(OrderLineDetails.ORDER.eq(order.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<OrderLine> getWishlistLinesByItem(Item item, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderLines.fields())
                .from(OrderLines)
                .join(OrderLineDetails).onKey(ORDER_LINES_ACTIVE_DETAIL_FK)
                .where(OrderLineDetails.ITEM.eq(item.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderLines.fields())
                    .from(OrderLines)
                    .join(OrderLineDetails).onKey(ORDER_LINES_ACTIVE_DETAIL_FK)
                    .join(Orders).onKey(ORDER_LINE_DETAILS_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(OrderLineDetails.ITEM.eq(item.getPrimaryKey()))
                    .orderBy(OrderDetails.ORDER_NAME, OrderLineDetails.ORDER_LINE_SEQUENCE),
                    OrderLineFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderLineFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<OrderLine> getWishlistLinesByItem(Item item) {
        return getWishlistLinesByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getWishlistLinesByItemForUpdate(Item item) {
        return getWishlistLinesByItem(item, EntityPermission.READ_WRITE);
    }
    
    public long countWishlistLinesByItem(Item item) {
        return session.getDslContext()
                .selectCount()
                .from(OrderLines)
                .join(OrderLineDetails).onKey(ORDER_LINES_ACTIVE_DETAIL_FK)
                .where(OrderLineDetails.ITEM.eq(item.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<OrderLine> getOrderLinesByWishlistPriority(WishlistPriority wishlistPriority,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OrderLines.fields())
                .from(OrderLines)
                .join(WishlistLines).onKey(WISHLIST_LINES_ORDER_LINE_FK)
                .where(WishlistLines.WISHLIST_PRIORITY.eq(wishlistPriority.getPrimaryKey()),
                        WishlistLines.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OrderLines.fields())
                    .from(OrderLines)
                    .join(WishlistLines).onKey(WISHLIST_LINES_ORDER_LINE_FK)
                    .join(OrderLineDetails).onKey(ORDER_LINES_LAST_DETAIL_FK)
                    .join(Orders).onKey(ORDER_LINE_DETAILS_ORDER_FK)
                    .join(OrderDetails).onKey(ORDERS_LAST_DETAIL_FK)
                    .where(WishlistLines.WISHLIST_PRIORITY.eq(wishlistPriority.getPrimaryKey()),
                            WishlistLines.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OrderDetails.ORDER_NAME, OrderLineDetails.ORDER_LINE_SEQUENCE),
                    OrderLineFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return orderLineFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<OrderLine> getOrderLinesByWishlistPriority(WishlistPriority wishlistPriority) {
        return getOrderLinesByWishlistPriority(wishlistPriority, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getOrderLinesByWishlistPriorityForUpdate(WishlistPriority wishlistPriority) {
        return getOrderLinesByWishlistPriority(wishlistPriority, EntityPermission.READ_WRITE);
    }
    
    public WishlistLineTransfer getWishlistLineTransfer(UserVisit userVisit, OrderLine orderLine) {
        return wishlistLineTransferCache.getWishlistLineTransfer(userVisit, orderLine);
    }
    
    public List<WishlistLineTransfer> getWishlistLineTransfers(UserVisit userVisit, Collection<OrderLine> orderLines) {
        List<WishlistLineTransfer> wishlistLineTransfers = new ArrayList<>(orderLines.size());
        
        orderLines.forEach((orderLine) ->
                wishlistLineTransfers.add(wishlistLineTransferCache.getWishlistLineTransfer(userVisit, orderLine))
        );
        
        return wishlistLineTransfers;
    }
    
    public List<WishlistLineTransfer> getWishlistLineTransfersByOrder(UserVisit userVisit, Order order) {
        return getWishlistLineTransfers(userVisit, getWishlistLinesByOrder(order));
    }
    
    public List<WishlistLineTransfer> getWishlistLineTransfersByItem(UserVisit userVisit, Item item) {
        return getWishlistLineTransfers(userVisit, getWishlistLinesByItem(item));
    }
    
    public void deleteOrderLinesByWishlistPriority(WishlistPriority wishlistPriority, BasePK deletedBy) {
        var orderLines = getOrderLinesByWishlistPriorityForUpdate(wishlistPriority);
        
        orderLines.forEach((orderLine) -> {
            orderLineControl.deleteOrderLine(orderLine, deletedBy);
        });
    }
    
}

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

package com.echothree.model.control.wishlist.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.common.OrderRoleTypes;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderLineControl;
import com.echothree.model.control.wishlist.common.choice.WishlistTypeChoicesBean;
import com.echothree.model.control.wishlist.common.choice.WishlistPriorityChoicesBean;
import com.echothree.model.control.wishlist.common.transfer.WishlistLineTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeDescriptionTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistPriorityDescriptionTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistPriorityTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeTransfer;
import com.echothree.model.control.wishlist.server.transfer.WishlistTransferCaches;
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
import com.echothree.model.data.wishlist.common.pk.WishlistTypePK;
import com.echothree.model.data.wishlist.common.pk.WishlistPriorityPK;
import com.echothree.model.data.wishlist.server.entity.Wishlist;
import com.echothree.model.data.wishlist.server.entity.WishlistLine;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDescription;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.model.data.wishlist.server.entity.WishlistPriorityDescription;
import com.echothree.model.data.wishlist.server.factory.WishlistFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistLineFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypeDescriptionFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypeDetailFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypeFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistPriorityDescriptionFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistPriorityDetailFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistPriorityFactory;
import com.echothree.model.data.wishlist.server.value.WishlistLineValue;
import com.echothree.model.data.wishlist.server.value.WishlistTypeDescriptionValue;
import com.echothree.model.data.wishlist.server.value.WishlistTypeDetailValue;
import com.echothree.model.data.wishlist.server.value.WishlistPriorityDescriptionValue;
import com.echothree.model.data.wishlist.server.value.WishlistPriorityDetailValue;
import com.echothree.model.data.wishlist.server.value.WishlistValue;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WishlistControl
        extends BaseModelControl {
    
    /** Creates a new instance of WishlistControl */
    protected WishlistControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Transfer Caches
    // --------------------------------------------------------------------------------
    
    private WishlistTransferCaches wishlistTransferCaches;
    
    public WishlistTransferCaches getWishlistTransferCaches() {
        if(wishlistTransferCaches == null) {
            wishlistTransferCaches = new WishlistTransferCaches(userVisit, this);
        }
        
        return wishlistTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Types
    // --------------------------------------------------------------------------------
    
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

        var wishlistType = WishlistTypeFactory.getInstance().create();
        var wishlistTypeDetail = WishlistTypeDetailFactory.getInstance().create(wishlistType,
                wishlistTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        wishlistType = WishlistTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

        return WishlistTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public WishlistType getWishlistTypeByEntityInstance(final EntityInstance entityInstance) {
        return getWishlistTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WishlistType getWishlistTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getWishlistTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public WishlistType getWishlistTypeByPK(WishlistTypePK pk) {
        return WishlistTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countWishlistTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM wishlisttypes, wishlisttypedetails " +
                "WHERE wshlty_activedetailid = wshltydt_wishlisttypedetailid");
    }

    private List<WishlistType> getWishlistTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM wishlisttypes, wishlisttypedetails " +
                    "WHERE wshlty_activedetailid = wshltydt_wishlisttypedetailid " +
                    "ORDER BY wshltydt_sortorder, wshltydt_wishlisttypename " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM wishlisttypes, wishlisttypedetails " +
                    "WHERE wshlty_activedetailid = wshltydt_wishlisttypedetailid " +
                    "FOR UPDATE";
        }

        var ps = WishlistTypeFactory.getInstance().prepareStatement(query);
        
        return WishlistTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<WishlistType> getWishlistTypes() {
        return getWishlistTypes(EntityPermission.READ_ONLY);
    }
    
    public List<WishlistType> getWishlistTypesForUpdate() {
        return getWishlistTypes(EntityPermission.READ_WRITE);
    }
    
    public WishlistType getDefaultWishlistType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM wishlisttypes, wishlisttypedetails " +
                    "WHERE wshlty_activedetailid = wshltydt_wishlisttypedetailid AND wshltydt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM wishlisttypes, wishlisttypedetails " +
                    "WHERE wshlty_activedetailid = wshltydt_wishlisttypedetailid AND wshltydt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = WishlistTypeFactory.getInstance().prepareStatement(query);
        
        return WishlistTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        WishlistType wishlistType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypes, wishlisttypedetails " +
                        "WHERE wshlty_activedetailid = wshltydt_wishlisttypedetailid AND wshltydt_wishlisttypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypes, wishlisttypedetails " +
                        "WHERE wshlty_activedetailid = wshltydt_wishlisttypedetailid AND wshltydt_wishlisttypename = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, wishlistTypeName);
            
            wishlistType = WishlistTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistType;
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
        return getWishlistTransferCaches().getWishlistTypeTransferCache().getWishlistTypeTransfer(userVisit, wishlistType);
    }

    public List<WishlistTypeTransfer> getWishlistTypeTransfers(UserVisit userVisit, Collection<WishlistType> wishlistTypes) {
        List<WishlistTypeTransfer> wishlistTypeTransfers = new ArrayList<>(wishlistTypes.size());
        var wishlistTypeTransferCache = getWishlistTransferCaches(userVisit).getWishlistTypeTransferCache();

        wishlistTypes.forEach((wishlistType) ->
                wishlistTypeTransfers.add(wishlistTypeTransferCache.getWishlistTypeTransfer(wishlistType))
        );

        return wishlistTypeTransfers;
    }

    public List<WishlistTypeTransfer> getWishlistTypeTransfers(UserVisit userVisit) {
        return getWishlistTypeTransfers(userVisit, getWishlistTypes());
    }

    private void updateWishlistTypeFromValue(WishlistTypeDetailValue wishlistTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(wishlistTypeDetailValue.hasBeenModified()) {
            var wishlistType = WishlistTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistTypeDetailValue.getWishlistTypePK());
            var wishlistTypeDetail = wishlistType.getActiveDetailForUpdate();
            
            wishlistTypeDetail.setThruTime(session.START_TIME_LONG);
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
            
            wishlistTypeDetail = WishlistTypeDetailFactory.getInstance().create(wishlistTypePK, wishlistTypeName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            wishlistType.setActiveDetail(wishlistTypeDetail);
            wishlistType.setLastDetail(wishlistTypeDetail);
            
            sendEvent(wishlistTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateWishlistTypeFromValue(WishlistTypeDetailValue wishlistTypeDetailValue, BasePK updatedBy) {
        updateWishlistTypeFromValue(wishlistTypeDetailValue, true, updatedBy);
    }
    
    public void deleteWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        var orderControl = Session.getModelController(OrderControl.class);
        
        deleteWishlistTypeDescriptionsByWishlistType(wishlistType, deletedBy);
        deleteOrdersByWishlistType(wishlistType, deletedBy);
        deleteWishlistPrioritiesByWishlistType(wishlistType, deletedBy);
        orderControl.deleteOrdersByWishlistType(wishlistType, deletedBy);

        var wishlistTypeDetail = wishlistType.getLastDetailForUpdate();
        wishlistTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public WishlistTypeDescription createWishlistTypeDescription(WishlistType wishlistType, Language language, String description,
            BasePK createdBy) {
        var wishlistTypeDescription = WishlistTypeDescriptionFactory.getInstance().create(wishlistType,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(wishlistType.getPrimaryKey(), EventTypes.MODIFY, wishlistTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return wishlistTypeDescription;
    }
    
    private WishlistTypeDescription getWishlistTypeDescription(WishlistType wishlistType, Language language, EntityPermission entityPermission) {
        WishlistTypeDescription wishlistTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypedescriptions " +
                        "WHERE wshltyd_wshlty_wishlisttypeid = ? AND wshltyd_lang_languageid = ? AND wshltyd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypedescriptions " +
                        "WHERE wshltyd_wshlty_wishlisttypeid = ? AND wshltyd_lang_languageid = ? AND wshltyd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            wishlistTypeDescription = WishlistTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistTypeDescription;
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
        List<WishlistTypeDescription> wishlistTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypedescriptions, languages " +
                        "WHERE wshltyd_wshlty_wishlisttypeid = ? AND wshltyd_thrutime = ? " +
                        "AND wshltyd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypedescriptions " +
                        "WHERE wshltyd_wshlty_wishlisttypeid = ? AND wshltyd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            wishlistTypeDescriptions = WishlistTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistTypeDescriptions;
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
        return getWishlistTransferCaches().getWishlistTypeDescriptionTransferCache().getWishlistTypeDescriptionTransfer(userVisit, wishlistTypeDescription);
    }
    
    public List<WishlistTypeDescriptionTransfer> getWishlistTypeDescriptionTransfers(UserVisit userVisit, WishlistType wishlistType) {
        var wishlistTypeDescriptions = getWishlistTypeDescriptionsByWishlistType(wishlistType);
        List<WishlistTypeDescriptionTransfer> wishlistTypeDescriptionTransfers = new ArrayList<>(wishlistTypeDescriptions.size());
        var wishlistTypeDescriptionTransferCache = getWishlistTransferCaches(userVisit).getWishlistTypeDescriptionTransferCache();
        
        wishlistTypeDescriptions.forEach((wishlistTypeDescription) ->
                wishlistTypeDescriptionTransfers.add(wishlistTypeDescriptionTransferCache.getWishlistTypeDescriptionTransfer(wishlistTypeDescription))
        );
        
        return wishlistTypeDescriptionTransfers;
    }
    
    public void updateWishlistTypeDescriptionFromValue(WishlistTypeDescriptionValue wishlistTypeDescriptionValue, BasePK updatedBy) {
        if(wishlistTypeDescriptionValue.hasBeenModified()) {
            var wishlistTypeDescription = WishlistTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistTypeDescriptionValue.getPrimaryKey());
            
            wishlistTypeDescription.setThruTime(session.START_TIME_LONG);
            wishlistTypeDescription.store();

            var wishlistType = wishlistTypeDescription.getWishlistType();
            var language = wishlistTypeDescription.getLanguage();
            var description = wishlistTypeDescriptionValue.getDescription();
            
            wishlistTypeDescription = WishlistTypeDescriptionFactory.getInstance().create(wishlistType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(wishlistType.getPrimaryKey(), EventTypes.MODIFY, wishlistTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWishlistTypeDescription(WishlistTypeDescription wishlistTypeDescription, BasePK deletedBy) {
        wishlistTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(wishlistTypeDescription.getWishlistTypePK(), EventTypes.MODIFY, wishlistTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWishlistTypeDescriptionsByWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        var wishlistTypeDescriptions = getWishlistTypeDescriptionsByWishlistTypeForUpdate(wishlistType);
        
        wishlistTypeDescriptions.forEach((wishlistTypeDescription) -> 
                deleteWishlistTypeDescription(wishlistTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Type Priorities
    // --------------------------------------------------------------------------------
    
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

        var wishlistPriority = WishlistPriorityFactory.getInstance().create();
        var wishlistPriorityDetail = WishlistPriorityDetailFactory.getInstance().create(session,
                wishlistPriority, wishlistType, wishlistPriorityName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        wishlistPriority = WishlistPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
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

        return WishlistPriorityFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public WishlistPriority getWishlistPriorityByEntityInstance(final EntityInstance entityInstance) {
        return getWishlistPriorityByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public WishlistPriority getWishlistPriorityByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getWishlistPriorityByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public WishlistPriority getWishlistPriorityByPK(WishlistPriorityPK pk) {
        return WishlistPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countWishlistPrioritiesByWishlistType(final WishlistType wishlistType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM wishlistpriorities, wishlistprioritydetails " +
                "WHERE wshlprty_activedetailid = wshlprtydt_wishlistprioritydetailid " +
                "AND wshlprtydt_wshlty_wishlisttypeid = ?",
                wishlistType);
    }

    private List<WishlistPriority> getWishlistPriorities(WishlistType wishlistType, EntityPermission entityPermission) {
        List<WishlistPriority> wishlistPriorities;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistpriorities, wishlistprioritydetails " +
                        "WHERE wshlprty_activedetailid = wshlprtydt_wishlistprioritydetailid AND wshlprtydt_wshlty_wishlisttypeid = ? " +
                        "ORDER BY wshlprtydt_sortorder, wshlprtydt_wishlistpriorityname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistpriorities, wishlistprioritydetails " +
                        "WHERE wshlprty_activedetailid = wshlprtydt_wishlistprioritydetailid AND wshlprtydt_wshlty_wishlisttypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistPriorityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistType.getPrimaryKey().getEntityId());
            
            wishlistPriorities = WishlistPriorityFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistPriorities;
    }
    
    public List<WishlistPriority> getWishlistPriorities(WishlistType wishlistType) {
        return getWishlistPriorities(wishlistType, EntityPermission.READ_ONLY);
    }
    
    public List<WishlistPriority> getWishlistPrioritiesForUpdate(WishlistType wishlistType) {
        return getWishlistPriorities(wishlistType, EntityPermission.READ_WRITE);
    }
    
    public WishlistPriority getDefaultWishlistPriority(WishlistType wishlistType, EntityPermission entityPermission) {
        WishlistPriority wishlistPriority;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistpriorities, wishlistprioritydetails " +
                        "WHERE wshlprty_activedetailid = wshlprtydt_wishlistprioritydetailid " +
                        "AND wshlprtydt_wshlty_wishlisttypeid = ? AND wshlprtydt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistpriorities, wishlistprioritydetails " +
                        "WHERE wshlprty_activedetailid = wshlprtydt_wishlistprioritydetailid " +
                        "AND wshlprtydt_wshlty_wishlisttypeid = ? AND wshlprtydt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = WishlistPriorityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistType.getPrimaryKey().getEntityId());
            
            wishlistPriority = WishlistPriorityFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistPriority;
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
        WishlistPriority wishlistPriority;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistpriorities, wishlistprioritydetails " +
                        "WHERE wshlprty_activedetailid = wshlprtydt_wishlistprioritydetailid " +
                        "AND wshlprtydt_wshlty_wishlisttypeid = ? AND wshlprtydt_wishlistpriorityname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistpriorities, wishlistprioritydetails " +
                        "WHERE wshlprty_activedetailid = wshlprtydt_wishlistprioritydetailid " +
                        "AND wshlprtydt_wshlty_wishlisttypeid = ? AND wshlprtydt_wishlistpriorityname = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistPriorityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistType.getPrimaryKey().getEntityId());
            ps.setString(2, wishlistPriorityName);
            
            wishlistPriority = WishlistPriorityFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistPriority;
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
        return getWishlistTransferCaches().getWishlistPriorityTransferCache().getWishlistPriorityTransfer(userVisit, wishlistPriority);
    }

    public List<WishlistPriorityTransfer> getWishlistPriorityTransfers(UserVisit userVisit, Collection<WishlistPriority> wishlistPriorities) {
        List<WishlistPriorityTransfer> wishlistPriorityTransfers = new ArrayList<>(wishlistPriorities.size());
        var wishlistPriorityTransferCache = getWishlistTransferCaches(userVisit).getWishlistPriorityTransferCache();

        wishlistPriorities.forEach((wishlistPriority) ->
                wishlistPriorityTransfers.add(wishlistPriorityTransferCache.getWishlistPriorityTransfer(wishlistPriority))
        );

        return wishlistPriorityTransfers;
    }

    public List<WishlistPriorityTransfer> getWishlistPriorityTransfers(UserVisit userVisit, WishlistType wishlistType) {
        return getWishlistPriorityTransfers(userVisit, getWishlistPriorities(wishlistType));
    }

    private void updateWishlistPriorityFromValue(WishlistPriorityDetailValue wishlistPriorityDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(wishlistPriorityDetailValue.hasBeenModified()) {
            var wishlistPriority = WishlistPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistPriorityDetailValue.getWishlistPriorityPK());
            var wishlistPriorityDetail = wishlistPriority.getActiveDetailForUpdate();
            
            wishlistPriorityDetail.setThruTime(session.START_TIME_LONG);
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
            
            wishlistPriorityDetail = WishlistPriorityDetailFactory.getInstance().create(wishlistPriorityPK,
                    wishlistTypePK, wishlistPriorityName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        wishlistPriorityDetail.setThruTime(session.START_TIME_LONG);
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
    //   Wishlist Type Priority Descriptions
    // --------------------------------------------------------------------------------
    
    public WishlistPriorityDescription createWishlistPriorityDescription(WishlistPriority wishlistPriority,
            Language language, String description, BasePK createdBy) {
        var wishlistPriorityDescription = WishlistPriorityDescriptionFactory.getInstance().create(session,
                wishlistPriority, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(wishlistPriority.getPrimaryKey(), EventTypes.MODIFY, wishlistPriorityDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return wishlistPriorityDescription;
    }
    
    private WishlistPriorityDescription getWishlistPriorityDescription(WishlistPriority wishlistPriority, Language language, EntityPermission entityPermission) {
        WishlistPriorityDescription wishlistPriorityDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistprioritydescriptions " +
                        "WHERE wshlprtyd_wshlprty_wishlistpriorityid = ? AND wshlprtyd_lang_languageid = ? AND wshlprtyd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistprioritydescriptions " +
                        "WHERE wshlprtyd_wshlprty_wishlistpriorityid = ? AND wshlprtyd_lang_languageid = ? AND wshlprtyd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistPriorityDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistPriority.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            wishlistPriorityDescription = WishlistPriorityDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistPriorityDescription;
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
        List<WishlistPriorityDescription> wishlistPriorityDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistprioritydescriptions, languages " +
                        "WHERE wshlprtyd_wshlprty_wishlistpriorityid = ? AND wshlprtyd_thrutime = ? " +
                        "AND wshlprtyd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistprioritydescriptions " +
                        "WHERE wshlprtyd_wshlprty_wishlistpriorityid = ? AND wshlprtyd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistPriorityDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistPriority.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            wishlistPriorityDescriptions = WishlistPriorityDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistPriorityDescriptions;
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
        return getWishlistTransferCaches().getWishlistPriorityDescriptionTransferCache().getWishlistPriorityDescriptionTransfer(userVisit, wishlistPriorityDescription);
    }
    
    public List<WishlistPriorityDescriptionTransfer> getWishlistPriorityDescriptionTransfers(UserVisit userVisit, WishlistPriority wishlistPriority) {
        var wishlistPriorityDescriptions = getWishlistPriorityDescriptionsByWishlistPriority(wishlistPriority);
        List<WishlistPriorityDescriptionTransfer> wishlistPriorityDescriptionTransfers = new ArrayList<>(wishlistPriorityDescriptions.size());
        var wishlistPriorityDescriptionTransferCache = getWishlistTransferCaches(userVisit).getWishlistPriorityDescriptionTransferCache();
        
        wishlistPriorityDescriptions.forEach((wishlistPriorityDescription) ->
                wishlistPriorityDescriptionTransfers.add(wishlistPriorityDescriptionTransferCache.getWishlistPriorityDescriptionTransfer(wishlistPriorityDescription))
        );
        
        return wishlistPriorityDescriptionTransfers;
    }
    
    public void updateWishlistPriorityDescriptionFromValue(WishlistPriorityDescriptionValue wishlistPriorityDescriptionValue, BasePK updatedBy) {
        if(wishlistPriorityDescriptionValue.hasBeenModified()) {
            var wishlistPriorityDescription = WishlistPriorityDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistPriorityDescriptionValue.getPrimaryKey());
            
            wishlistPriorityDescription.setThruTime(session.START_TIME_LONG);
            wishlistPriorityDescription.store();

            var wishlistPriority = wishlistPriorityDescription.getWishlistPriority();
            var language = wishlistPriorityDescription.getLanguage();
            var description = wishlistPriorityDescriptionValue.getDescription();
            
            wishlistPriorityDescription = WishlistPriorityDescriptionFactory.getInstance().create(wishlistPriority, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(wishlistPriority.getPrimaryKey(), EventTypes.MODIFY, wishlistPriorityDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWishlistPriorityDescription(WishlistPriorityDescription wishlistPriorityDescription, BasePK deletedBy) {
        wishlistPriorityDescription.setThruTime(session.START_TIME_LONG);
        
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
    
    public Wishlist createWishlist(Order order, OfferUse offerUse, WishlistType wishlistType, BasePK createdBy) {
        var wishlist = WishlistFactory.getInstance().create(order, offerUse, wishlistType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(order.getPrimaryKey(), EventTypes.MODIFY, wishlist.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return wishlist;
    }

    public long countWishlistsByOfferUse(final OfferUse offerUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM wishlists " +
                        "WHERE wshl_ofruse_offeruseid = ? AND wshl_thrutime = ?",
                offerUse, Session.MAX_TIME_LONG);
    }

    public long countWishlistsByWishlistType(final WishlistType wishlistType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM wishlists " +
                        "WHERE wshl_wshlty_wishlisttypeid = ? AND wshl_thrutime = ?",
                wishlistType, Session.MAX_TIME_LONG);
    }

    private Wishlist getWishlist(Order order, EntityPermission entityPermission) {
        Wishlist wishlist;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlists " +
                        "WHERE wshl_ord_orderid = ? AND wshl_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlists " +
                        "WHERE wshl_ord_orderid = ? AND wshl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, order.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            wishlist = WishlistFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlist;
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
        List<Wishlist> wishlists;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlists, orders, orderdetails " +
                        "WHERE wshl_wshlty_wishlisttypeid = ? AND wshl_thrutime = ? " +
                        "AND wshl_ord_orderid = ord_orderid AND ord_lastdetailid = orddt_orderdetailid " +
                        "ORDER BY orddt_ordername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlists " +
                        "WHERE wshl_wshlty_wishlisttypeid = ? AND wshl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            wishlists = WishlistFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlists;
    }
    
    public List<Wishlist> getWishlistsByWishlistType(WishlistType wishlistType) {
        return getWishlistsByWishlistType(wishlistType, EntityPermission.READ_ONLY);
    }
    
    public List<Wishlist> getWishlistsByWishlistTypeForUpdate(WishlistType wishlistType) {
        return getWishlistsByWishlistType(wishlistType, EntityPermission.READ_WRITE);
    }
    
    public void updateWishlistFromValue(WishlistValue wishlistValue, BasePK updatedBy) {
        if(wishlistValue.hasBeenModified()) {
            var wishlist = WishlistFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    wishlistValue.getPrimaryKey());
            
            wishlist.setThruTime(session.START_TIME_LONG);
            wishlist.store();

            var orderPK = wishlist.getOrderPK(); // Not updated
            var offerUsePK = wishlistValue.getOfferUsePK();
            var wishlistTypePK = wishlistValue.getWishlistTypePK();
            
            wishlist = WishlistFactory.getInstance().create(orderPK, offerUsePK, wishlistTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(orderPK, EventTypes.MODIFY, wishlist.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWishlist(Wishlist wishlist, BasePK deletedBy) {
        wishlist.setThruTime(session.START_TIME_LONG);
        
        sendEvent(wishlist.getOrderPK(), EventTypes.MODIFY, wishlist.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWishlistByOrder(Order order, BasePK deletedBy) {
        deleteWishlist(getWishlistForUpdate(order), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Lines
    // --------------------------------------------------------------------------------
    
    public WishlistLine createWishlistLine(OrderLine orderLine, OfferUse offerUse, WishlistPriority wishlistPriority,
            AssociateReferral associateReferral, String comment, BasePK createdBy) {
        var wishlistLine = WishlistLineFactory.getInstance().create(orderLine, offerUse,
                wishlistPriority, associateReferral, comment, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(orderLine.getPrimaryKey(), EventTypes.MODIFY, wishlistLine.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return wishlistLine;
    }

    public long countWishlistLinesByOfferUse(final OfferUse offerUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM wishlistlines " +
                        "WHERE wshll_ofruse_offeruseid = ? AND wshll_thrutime = ?",
                offerUse, Session.MAX_TIME_LONG);
    }

    public long countWishlistLinesByWishlistPriority(final WishlistPriority wishlistPriority) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM wishlistlines " +
                        "WHERE wshll_wshlprty_wishlistpriorityid = ? AND wshll_thrutime = ?",
                wishlistPriority, Session.MAX_TIME_LONG);
    }

    public long countWishlistLinesByAssociateReferral(final AssociateReferral associateReferral) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM wishlistlines " +
                        "WHERE wshll_ascrfr_associatereferralid = ? AND wshll_thrutime = ?",
                associateReferral, Session.MAX_TIME_LONG);
    }

    private List<WishlistLine> getWishlistLinesByWishlistPriority(WishlistPriority wishlistPriority,
            EntityPermission entityPermission) {
        List<WishlistLine> wishlistLines;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlists, orderlines, orderlinedetails, orders, orderdetails " +
                        "WHERE wshll_wshlprty_wishlistpriorityid = ? AND wshll_thrutime = ? " +
                        "AND wshll_ordl_orderlineid = ordl_orderlineid AND ordl_activedetailid = ordldt_orderlinedetailid " +
                        "AND ordldt_ord_orderid = ord_orderid AND ord_lastdetailid = orddt_orderdetailid " +
                        "ORDER BY orddt_ordername, ordldt_orderlinesequence";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistlines " +
                        "WHERE wshll_wshlprty_wishlistpriorityid = ? AND wshll_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistPriority.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            wishlistLines = WishlistLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistLines;
    }
    
    public List<WishlistLine> getWishlistLinesByWishlistPriority(WishlistPriority wishlistPriority) {
        return getWishlistLinesByWishlistPriority(wishlistPriority, EntityPermission.READ_ONLY);
    }
    
    public List<WishlistLine> getWishlistLinesByWishlistPriorityForUpdate(WishlistPriority wishlistPriority) {
        return getWishlistLinesByWishlistPriority(wishlistPriority, EntityPermission.READ_WRITE);
    }
    
    private WishlistLine getWishlistLine(OrderLine orderLine, EntityPermission entityPermission) {
        WishlistLine wishlistLine;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistlines " +
                        "WHERE wshll_ordl_orderlineid = ? AND wshll_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistlines " +
                        "WHERE wshll_ordl_orderlineid = ? AND wshll_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = WishlistLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, orderLine.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            wishlistLine = WishlistLineFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistLine;
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
            var wishlistLine = WishlistLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    wishlistLineValue.getPrimaryKey());
            
            wishlistLine.setThruTime(session.START_TIME_LONG);
            wishlistLine.store();

            var orderLinePK = wishlistLine.getOrderLinePK(); // Not updated
            var offerUsePK = wishlistLineValue.getOfferUsePK();
            var wishlistPriorityPK = wishlistLineValue.getWishlistPriorityPK();
            var associateReferralPK = wishlistLineValue.getAssociateReferralPK();
            var comment = wishlistLineValue.getComment();
            
            wishlistLine = WishlistLineFactory.getInstance().create(orderLinePK, offerUsePK,
                    wishlistPriorityPK, associateReferralPK, comment, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(orderLinePK, EventTypes.MODIFY, wishlistLine.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteWishlistLine(WishlistLine wishlistLine, BasePK deletedBy) {
        wishlistLine.setThruTime(session.START_TIME_LONG);
        
        sendEvent(wishlistLine.getOrderLinePK(), EventTypes.MODIFY, wishlistLine.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteWishlistLineByOrderLine(OrderLine orderLine, BasePK deletedBy) {
        deleteWishlistLine(getWishlistLineForUpdate(orderLine), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlists
    // --------------------------------------------------------------------------------
    
    private Order getWishlist(Party companyParty, Party party, WishlistType wishlistType, Currency currency,
            EntityPermission entityPermission) {
        Order order;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orders, orderdetails, ordertypes, ordertypedetails, wishlists, orderroletypes orta, orderroles ora, " +
                        "orderroletypes ortb, orderroles orb " +
                        "WHERE ord_activedetailid = orddt_orderdetailid " +
                        "AND orddt_ordtyp_ordertypeid = ordtyp_ordertypeid " +
                        "AND ordtyp_activedetailid = ordtypdt_ordertypedetailid AND ordtypdt_ordertypename = ? AND orddt_cur_currencyid = ? " +
                        "AND ord_orderid = wshl_ord_orderid AND wshl_wshlty_wishlisttypeid = ? AND wshl_thrutime = ? " +
                        "AND ord_orderid = ora.ordr_ord_orderid AND ora.ordr_par_partyid = ? AND orta.ordrtyp_orderroletypename = ? " +
                        "AND orta.ordrtyp_orderroletypeid = ora.ordr_ordrtyp_orderroletypeid AND ora.ordr_thrutime = ? " +
                        "AND ord_orderid = orb.ordr_ord_orderid AND orb.ordr_par_partyid = ? AND ortb.ordrtyp_orderroletypename = ? " +
                        "AND ortb.ordrtyp_orderroletypeid = orb.ordr_ordrtyp_orderroletypeid AND orb.ordr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orders, orderdetails, ordertypes, ordertypedetails, wishlists, orderroletypes orta, orderroles ora, " +
                        "orderroletypes ortb, orderroles orb " +
                        "WHERE ord_activedetailid = orddt_orderdetailid " +
                        "AND orddt_ordtyp_ordertypeid = ordtyp_ordertypeid " +
                        "AND ordtyp_activedetailid = ordtypdt_ordertypedetailid AND ordtypdt_ordertypename = ? AND orddt_cur_currencyid = ? " +
                        "AND ord_orderid = wshl_ord_orderid AND wshl_wshlty_wishlisttypeid = ? AND wshl_thrutime = ? " +
                        "AND ord_orderid = ora.ordr_ord_orderid AND ora.ordr_par_partyid = ? AND orta.ordrtyp_orderroletypename = ? " +
                        "AND orta.ordrtyp_orderroletypeid = ora.ordr_ordrtyp_orderroletypeid AND ora.ordr_thrutime = ? " +
                        "AND ord_orderid = orb.ordr_ord_orderid AND orb.ordr_par_partyid = ? AND ortb.ordrtyp_orderroletypename = ? " +
                        "AND ortb.ordrtyp_orderroletypeid = orb.ordr_ordrtyp_orderroletypeid AND orb.ordr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, OrderTypes.WISHLIST.name());
            ps.setLong(2, currency.getPrimaryKey().getEntityId());
            ps.setLong(3, wishlistType.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            ps.setLong(5, companyParty.getPrimaryKey().getEntityId());
            ps.setString(6, OrderRoleTypes.BILL_FROM.name());
            ps.setLong(7, Session.MAX_TIME);
            ps.setLong(8, party.getPrimaryKey().getEntityId());
            ps.setString(9, OrderRoleTypes.BILL_TO.name());
            ps.setLong(10, Session.MAX_TIME);
            
            order = OrderFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return order;
    }
    
    public Order getWishlist(Party companyParty, Party party, WishlistType wishlistType, Currency currency) {
        return getWishlist(companyParty, party, wishlistType, currency, EntityPermission.READ_ONLY);
    }
    
    public Order getWishlistForUpdate(Party companyParty, Party party, WishlistType wishlistType, Currency currency) {
        return getWishlist(companyParty, party, wishlistType, currency, EntityPermission.READ_WRITE);
    }
    
    private List<Order> getWishlists(Party companyParty, Party party, EntityPermission entityPermission) {
        List<Order> orders;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orders, orderdetails, ordertypes, ordertypedetails, wishlists, orderroletypes orta, orderroles ora, " +
                        "orderroletypes ortb, orderroles orb " +
                        "WHERE ord_activedetailid = orddt_orderdetailid " +
                        "AND orddt_ordtyp_ordertypeid = ordtyp_ordertypeid " +
                        "AND ordtyp_activedetailid = ordtypdt_ordertypedetailid AND ordtypdt_ordertypename = ? " +
                        "AND ord_orderid = wshl_ord_orderid AND wshl_thrutime = ? " +
                        "AND ord_orderid = ora.ordr_ord_orderid AND ora.ordr_par_partyid = ? AND orta.ordrtyp_orderroletypename = ? " +
                        "AND orta.ordrtyp_orderroletypeid = ora.ordr_ordrtyp_orderroletypeid AND ora.ordr_thrutime = ? " +
                        "AND ord_orderid = orb.ordr_ord_orderid AND orb.ordr_par_partyid = ? AND ortb.ordrtyp_orderroletypename = ? " +
                        "AND ortb.ordrtyp_orderroletypeid = orb.ordr_ordrtyp_orderroletypeid AND orb.ordr_thrutime = ?";
                // TODO: "ORDER BY" needed, probably by the order's created time
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orders, orderdetails, ordertypes, ordertypedetails, wishlists, orderroletypes orta, orderroles ora, " +
                        "orderroletypes ortb, orderroles orb " +
                        "WHERE ord_activedetailid = orddt_orderdetailid " +
                        "AND orddt_ordtyp_ordertypeid = ordtyp_ordertypeid " +
                        "AND ordtyp_activedetailid = ordtypdt_ordertypedetailid AND ordtypdt_ordertypename = ? " +
                        "AND ord_orderid = wshl_ord_orderid AND wshl_thrutime = ? " +
                        "AND ord_orderid = ora.ordr_ord_orderid AND ora.ordr_par_partyid = ? AND orta.ordrtyp_orderroletypename = ? " +
                        "AND orta.ordrtyp_orderroletypeid = ora.ordr_ordrtyp_orderroletypeid AND ora.ordr_thrutime = ? " +
                        "AND ord_orderid = orb.ordr_ord_orderid AND orb.ordr_par_partyid = ? AND ortb.ordrtyp_orderroletypename = ? " +
                        "AND ortb.ordrtyp_orderroletypeid = orb.ordr_ordrtyp_orderroletypeid AND orb.ordr_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, OrderTypes.WISHLIST.name());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, companyParty.getPrimaryKey().getEntityId());
            ps.setString(4, OrderRoleTypes.BILL_FROM.name());
            ps.setLong(5, Session.MAX_TIME);
            ps.setLong(6, party.getPrimaryKey().getEntityId());
            ps.setString(7, OrderRoleTypes.BILL_TO.name());
            ps.setLong(8, Session.MAX_TIME);
            
            orders = OrderFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orders;
    }
    
    public List<Order> getWishlists(Party companyParty, Party party) {
        return getWishlists(companyParty, party, EntityPermission.READ_ONLY);
    }
    
    public List<Order> getWishlistsForUpdate(Party companyParty, Party party) {
        return getWishlists(companyParty, party, EntityPermission.READ_WRITE);
    }
    
    public WishlistTransfer getWishlistTransfer(UserVisit userVisit, Order order) {
        return getWishlistTransferCaches().getWishlistTransferCache().getWishlistTransfer(userVisit, order);
    }
    
    private List<Order> getOrdersByWishlistType(WishlistType wishlistType, EntityPermission entityPermission) {
        List<Order> orders;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlists, orders " +
                        "WHERE wshl_wshlty_wishlisttypeid = ? AND wshl_thrutime = ? " +
                        "AND wshl_ord_orderid = ord_orderid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlists, orders " +
                        "WHERE wshl_wshlty_wishlisttypeid = ? AND wshl_thrutime = ? " +
                        "AND wshl_ord_orderid = ord_orderid " +
                        "FOR UPDATE";
            }

            var ps = OrderFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            orders = OrderFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orders;
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
        var wishlistTransferCache = getWishlistTransferCaches(userVisit).getWishlistTransferCache();
        
        orders.forEach((order) ->
                wishlistTransfers.add(wishlistTransferCache.getWishlistTransfer(order))
        );
        
        return wishlistTransfers;
    }
    
    public void deleteOrdersByWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        var orderControl = Session.getModelController(OrderControl.class);
        var orders = getOrdersByWishlistTypeForUpdate(wishlistType);
        
        orders.forEach((order) -> {
            orderControl.deleteOrder(order, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Lines
    // --------------------------------------------------------------------------------
    
    private OrderLine getWishlistLine(Order order, Integer orderLineSequence, EntityPermission entityPermission) {
        OrderLine orderLine;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? " +
                        "AND ordldt_orderlinesequence = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? " +
                        "AND ordldt_orderlinesequence = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, order.getPrimaryKey().getEntityId());
            ps.setInt(2, orderLineSequence);
            
            orderLine = OrderLineFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLine;
    }
    
    public OrderLine getWishlistLine(Order order, Integer orderLineSequence) {
        return getWishlistLine(order, orderLineSequence, EntityPermission.READ_ONLY);
    }
    
    public OrderLine getWishlistLineForUpdate(Order order, Integer orderLineSequence) {
        return getWishlistLine(order, orderLineSequence, EntityPermission.READ_WRITE);
    }
    
    private OrderLine getWishlistLineByItem(Order order, Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        OrderLine orderLine;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? " +
                        "AND ordldt_itm_itemid = ? AND ordldt_invcon_inventoryconditionid = ? " +
                        "AND ordldt_uomt_unitofmeasuretypeid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? " +
                        "AND ordldt_itm_itemid = ? AND ordldt_invcon_inventoryconditionid = ? " +
                        "AND ordldt_uomt_unitofmeasuretypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, order.getPrimaryKey().getEntityId());
            ps.setLong(2, item.getPrimaryKey().getEntityId());
            ps.setLong(3, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(4, unitOfMeasureType.getPrimaryKey().getEntityId());
            
            orderLine = OrderLineFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLine;
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
        List<OrderLine> orderLines;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_ord_orderid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, order.getPrimaryKey().getEntityId());
            
            orderLines = OrderLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLines;
    }
    
    public List<OrderLine> getWishlistLinesByOrder(Order order) {
        return getWishlistLinesByOrder(order, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getWishlistLinesByOrderForUpdate(Order order) {
        return getWishlistLinesByOrder(order, EntityPermission.READ_WRITE);
    }
    
    private List<OrderLine> getWishlistLinesByItem(Item item, EntityPermission entityPermission) {
        List<OrderLine> orderLines;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_itm_itemid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM orderlines, orderlinedetails " +
                        "WHERE ordl_activedetailid = ordldt_orderlinedetailid AND ordldt_itm_itemid = ? " +
                        "FOR UPDATE";
            }

            var ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            
            orderLines = OrderLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLines;
    }
    
    public List<OrderLine> getWishlistLinesByItem(Item item) {
        return getWishlistLinesByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getWishlistLinesByItemForUpdate(Item item) {
        return getWishlistLinesByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<OrderLine> getOrderLinesByWishlistPriority(WishlistPriority wishlistPriority,
            EntityPermission entityPermission) {
        List<OrderLine> orderLines;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistlines, orderlines " +
                        "WHERE wshll_wshlprty_wishlistpriorityid = ? AND wshll_thrutime = ? " +
                        "AND wshll_ordl_orderlineid = ordl_orderlineid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistlines, orderlines " +
                        "WHERE wshll_wshlprty_wishlistpriorityid = ? AND wshll_thrutime = ? " +
                        "AND wshll_ordl_orderlineid = ordl_orderlineid " +
                        "FOR UPDATE";
            }

            var ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistPriority.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            orderLines = OrderLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLines;
    }
    
    public List<OrderLine> getOrderLinesByWishlistPriority(WishlistPriority wishlistPriority) {
        return getOrderLinesByWishlistPriority(wishlistPriority, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getOrderLinesByWishlistPriorityForUpdate(WishlistPriority wishlistPriority) {
        return getOrderLinesByWishlistPriority(wishlistPriority, EntityPermission.READ_WRITE);
    }
    
    public WishlistLineTransfer getWishlistLineTransfer(UserVisit userVisit, OrderLine orderLine) {
        return getWishlistTransferCaches().getWishlistLineTransferCache().getWishlistLineTransfer(userVisit, orderLine);
    }
    
    public List<WishlistLineTransfer> getWishlistLineTransfers(UserVisit userVisit, Collection<OrderLine> orderLines) {
        List<WishlistLineTransfer> wishlistLineTransfers = new ArrayList<>(orderLines.size());
        var wishlistLineTransferCache = getWishlistTransferCaches(userVisit).getWishlistLineTransferCache();
        
        orderLines.forEach((orderLine) ->
                wishlistLineTransfers.add(wishlistLineTransferCache.getWishlistLineTransfer(orderLine))
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
        var orderLineControl = Session.getModelController(OrderLineControl.class);
        var orderLines = getOrderLinesByWishlistPriorityForUpdate(wishlistPriority);
        
        orderLines.forEach((orderLine) -> {
            orderLineControl.deleteOrderLine(orderLine, deletedBy);
        });
    }
    
}

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

package com.echothree.model.control.wishlist.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.order.common.OrderRoleTypes;
import com.echothree.model.control.order.common.OrderTypes;
import com.echothree.model.control.order.server.control.OrderControl;
import com.echothree.model.control.order.server.control.OrderLineControl;
import com.echothree.model.control.wishlist.common.choice.WishlistTypeChoicesBean;
import com.echothree.model.control.wishlist.common.choice.WishlistTypePriorityChoicesBean;
import com.echothree.model.control.wishlist.common.transfer.WishlistLineTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeDescriptionTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypePriorityDescriptionTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypePriorityTransfer;
import com.echothree.model.control.wishlist.common.transfer.WishlistTypeTransfer;
import com.echothree.model.control.wishlist.server.transfer.WishlistLineTransferCache;
import com.echothree.model.control.wishlist.server.transfer.WishlistTransferCache;
import com.echothree.model.control.wishlist.server.transfer.WishlistTransferCaches;
import com.echothree.model.control.wishlist.server.transfer.WishlistTypeDescriptionTransferCache;
import com.echothree.model.control.wishlist.server.transfer.WishlistTypePriorityDescriptionTransferCache;
import com.echothree.model.control.wishlist.server.transfer.WishlistTypePriorityTransferCache;
import com.echothree.model.control.wishlist.server.transfer.WishlistTypeTransferCache;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.associate.common.pk.AssociateReferralPK;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.common.pk.OfferUsePK;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.order.common.pk.OrderLinePK;
import com.echothree.model.data.order.common.pk.OrderPK;
import com.echothree.model.data.order.server.entity.Order;
import com.echothree.model.data.order.server.entity.OrderLine;
import com.echothree.model.data.order.server.factory.OrderFactory;
import com.echothree.model.data.order.server.factory.OrderLineFactory;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.wishlist.common.pk.WishlistTypePK;
import com.echothree.model.data.wishlist.common.pk.WishlistTypePriorityPK;
import com.echothree.model.data.wishlist.server.entity.Wishlist;
import com.echothree.model.data.wishlist.server.entity.WishlistLine;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDescription;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDetail;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriorityDescription;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriorityDetail;
import com.echothree.model.data.wishlist.server.factory.WishlistFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistLineFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypeDescriptionFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypeDetailFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypeFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypePriorityDescriptionFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypePriorityDetailFactory;
import com.echothree.model.data.wishlist.server.factory.WishlistTypePriorityFactory;
import com.echothree.model.data.wishlist.server.value.WishlistLineValue;
import com.echothree.model.data.wishlist.server.value.WishlistTypeDescriptionValue;
import com.echothree.model.data.wishlist.server.value.WishlistTypeDetailValue;
import com.echothree.model.data.wishlist.server.value.WishlistTypePriorityDescriptionValue;
import com.echothree.model.data.wishlist.server.value.WishlistTypePriorityDetailValue;
import com.echothree.model.data.wishlist.server.value.WishlistValue;
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

public class WishlistControl
        extends BaseModelControl {
    
    /** Creates a new instance of WishlistControl */
    public WishlistControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Transfer Caches
    // --------------------------------------------------------------------------------
    
    private WishlistTransferCaches wishlistTransferCaches;
    
    public WishlistTransferCaches getWishlistTransferCaches(UserVisit userVisit) {
        if(wishlistTransferCaches == null) {
            wishlistTransferCaches = new WishlistTransferCaches(userVisit, this);
        }
        
        return wishlistTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Types
    // --------------------------------------------------------------------------------
    
    public WishlistType createWishlistType(String wishlistTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        WishlistType defaultWishlistType = getDefaultWishlistType();
        boolean defaultFound = defaultWishlistType != null;
        
        if(defaultFound && isDefault) {
            WishlistTypeDetailValue defaultWishlistTypeDetailValue = getDefaultWishlistTypeDetailValueForUpdate();
            
            defaultWishlistTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateWishlistTypeFromValue(defaultWishlistTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        WishlistType wishlistType = WishlistTypeFactory.getInstance().create();
        WishlistTypeDetail wishlistTypeDetail = WishlistTypeDetailFactory.getInstance().create(wishlistType,
                wishlistTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        wishlistType = WishlistTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                wishlistType.getPrimaryKey());
        wishlistType.setActiveDetail(wishlistTypeDetail);
        wishlistType.setLastDetail(wishlistTypeDetail);
        wishlistType.store();
        
        sendEventUsingNames(wishlistType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return wishlistType;
    }
    
    private List<WishlistType> getWishlistTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM wishlisttypes, wishlisttypedetails " +
                    "WHERE wshlty_activedetailid = wshltydt_wishlisttypedetailid " +
                    "ORDER BY wshltydt_sortorder, wshltydt_wishlisttypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM wishlisttypes, wishlisttypedetails " +
                    "WHERE wshlty_activedetailid = wshltydt_wishlisttypedetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = WishlistTypeFactory.getInstance().prepareStatement(query);
        
        return WishlistTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<WishlistType> getWishlistTypes() {
        return getWishlistTypes(EntityPermission.READ_ONLY);
    }
    
    public List<WishlistType> getWishlistTypesForUpdate() {
        return getWishlistTypes(EntityPermission.READ_WRITE);
    }
    
    private WishlistType getDefaultWishlistType(EntityPermission entityPermission) {
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
        
        PreparedStatement ps = WishlistTypeFactory.getInstance().prepareStatement(query);
        
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
    
    private WishlistType getWishlistTypeByName(String wishlistTypeName, EntityPermission entityPermission) {
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
            
            PreparedStatement ps = WishlistTypeFactory.getInstance().prepareStatement(query);
            
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
        List<WishlistType> wishlistTypes = getWishlistTypes();
        int size = wishlistTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultWishlistTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var wishlistType : wishlistTypes) {
            WishlistTypeDetail wishlistTypeDetail = wishlistType.getLastDetail();
            String label = getBestWishlistTypeDescription(wishlistType, language);
            String value = wishlistTypeDetail.getWishlistTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultWishlistTypeChoice != null && defaultWishlistTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && wishlistTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new WishlistTypeChoicesBean(labels, values, defaultValue);
    }
    
    public WishlistTypeTransfer getWishlistTypeTransfer(UserVisit userVisit, WishlistType wishlistType) {
        return getWishlistTransferCaches(userVisit).getWishlistTypeTransferCache().getWishlistTypeTransfer(wishlistType);
    }
    
    public List<WishlistTypeTransfer> getWishlistTypeTransfers(UserVisit userVisit) {
        List<WishlistType> wishlistTypes = getWishlistTypes();
        List<WishlistTypeTransfer> wishlistTypeTransfers = new ArrayList<>(wishlistTypes.size());
        WishlistTypeTransferCache wishlistTypeTransferCache = getWishlistTransferCaches(userVisit).getWishlistTypeTransferCache();
        
        wishlistTypes.forEach((wishlistType) ->
                wishlistTypeTransfers.add(wishlistTypeTransferCache.getWishlistTypeTransfer(wishlistType))
        );
        
        return wishlistTypeTransfers;
    }
    
    private void updateWishlistTypeFromValue(WishlistTypeDetailValue wishlistTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(wishlistTypeDetailValue.hasBeenModified()) {
            WishlistType wishlistType = WishlistTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistTypeDetailValue.getWishlistTypePK());
            WishlistTypeDetail wishlistTypeDetail = wishlistType.getActiveDetailForUpdate();
            
            wishlistTypeDetail.setThruTime(session.START_TIME_LONG);
            wishlistTypeDetail.store();
            
            WishlistTypePK wishlistTypePK = wishlistTypeDetail.getWishlistTypePK();
            String wishlistTypeName = wishlistTypeDetailValue.getWishlistTypeName();
            Boolean isDefault = wishlistTypeDetailValue.getIsDefault();
            Integer sortOrder = wishlistTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                WishlistType defaultWishlistType = getDefaultWishlistType();
                boolean defaultFound = defaultWishlistType != null && !defaultWishlistType.equals(wishlistType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    WishlistTypeDetailValue defaultWishlistTypeDetailValue = getDefaultWishlistTypeDetailValueForUpdate();
                    
                    defaultWishlistTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateWishlistTypeFromValue(defaultWishlistTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            wishlistTypeDetail = WishlistTypeDetailFactory.getInstance().create(wishlistTypePK, wishlistTypeName,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            wishlistType.setActiveDetail(wishlistTypeDetail);
            wishlistType.setLastDetail(wishlistTypeDetail);
            
            sendEventUsingNames(wishlistTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateWishlistTypeFromValue(WishlistTypeDetailValue wishlistTypeDetailValue, BasePK updatedBy) {
        updateWishlistTypeFromValue(wishlistTypeDetailValue, true, updatedBy);
    }
    
    public void deleteWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        var orderControl = Session.getModelController(OrderControl.class);
        
        deleteWishlistTypeDescriptionsByWishlistType(wishlistType, deletedBy);
        deleteOrdersByWishlistType(wishlistType, deletedBy);
        deleteWishlistTypePrioritiesByWishlistType(wishlistType, deletedBy);
        orderControl.deleteOrdersByWishlistType(wishlistType, deletedBy);
        
        WishlistTypeDetail wishlistTypeDetail = wishlistType.getLastDetailForUpdate();
        wishlistTypeDetail.setThruTime(session.START_TIME_LONG);
        wishlistType.setActiveDetail(null);
        wishlistType.store();
        
        // Check for default, and pick one if necessary
        WishlistType defaultWishlistType = getDefaultWishlistType();
        if(defaultWishlistType == null) {
            List<WishlistType> wishlistTypes = getWishlistTypesForUpdate();
            
            if(!wishlistTypes.isEmpty()) {
                Iterator<WishlistType> iter = wishlistTypes.iterator();
                if(iter.hasNext()) {
                    defaultWishlistType = iter.next();
                }
                WishlistTypeDetailValue wishlistTypeDetailValue = Objects.requireNonNull(defaultWishlistType).getLastDetailForUpdate().getWishlistTypeDetailValue().clone();
                
                wishlistTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateWishlistTypeFromValue(wishlistTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(wishlistType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Type Descriptions
    // --------------------------------------------------------------------------------
    
    public WishlistTypeDescription createWishlistTypeDescription(WishlistType wishlistType, Language language, String description,
            BasePK createdBy) {
        WishlistTypeDescription wishlistTypeDescription = WishlistTypeDescriptionFactory.getInstance().create(wishlistType,
                language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(wishlistType.getPrimaryKey(), EventTypes.MODIFY.name(), wishlistTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = WishlistTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = WishlistTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        WishlistTypeDescription wishlistTypeDescription = getWishlistTypeDescription(wishlistType, language);
        
        if(wishlistTypeDescription == null && !language.getIsDefault()) {
            wishlistTypeDescription = getWishlistTypeDescription(wishlistType, getPartyControl().getDefaultLanguage());
        }
        
        if(wishlistTypeDescription == null) {
            description = wishlistType.getLastDetail().getWishlistTypeName();
        } else {
            description = wishlistTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public WishlistTypeDescriptionTransfer getWishlistTypeDescriptionTransfer(UserVisit userVisit, WishlistTypeDescription wishlistTypeDescription) {
        return getWishlistTransferCaches(userVisit).getWishlistTypeDescriptionTransferCache().getWishlistTypeDescriptionTransfer(wishlistTypeDescription);
    }
    
    public List<WishlistTypeDescriptionTransfer> getWishlistTypeDescriptionTransfers(UserVisit userVisit, WishlistType wishlistType) {
        List<WishlistTypeDescription> wishlistTypeDescriptions = getWishlistTypeDescriptionsByWishlistType(wishlistType);
        List<WishlistTypeDescriptionTransfer> wishlistTypeDescriptionTransfers = new ArrayList<>(wishlistTypeDescriptions.size());
        WishlistTypeDescriptionTransferCache wishlistTypeDescriptionTransferCache = getWishlistTransferCaches(userVisit).getWishlistTypeDescriptionTransferCache();
        
        wishlistTypeDescriptions.forEach((wishlistTypeDescription) ->
                wishlistTypeDescriptionTransfers.add(wishlistTypeDescriptionTransferCache.getWishlistTypeDescriptionTransfer(wishlistTypeDescription))
        );
        
        return wishlistTypeDescriptionTransfers;
    }
    
    public void updateWishlistTypeDescriptionFromValue(WishlistTypeDescriptionValue wishlistTypeDescriptionValue, BasePK updatedBy) {
        if(wishlistTypeDescriptionValue.hasBeenModified()) {
            WishlistTypeDescription wishlistTypeDescription = WishlistTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistTypeDescriptionValue.getPrimaryKey());
            
            wishlistTypeDescription.setThruTime(session.START_TIME_LONG);
            wishlistTypeDescription.store();
            
            WishlistType wishlistType = wishlistTypeDescription.getWishlistType();
            Language language = wishlistTypeDescription.getLanguage();
            String description = wishlistTypeDescriptionValue.getDescription();
            
            wishlistTypeDescription = WishlistTypeDescriptionFactory.getInstance().create(wishlistType, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(wishlistType.getPrimaryKey(), EventTypes.MODIFY.name(), wishlistTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteWishlistTypeDescription(WishlistTypeDescription wishlistTypeDescription, BasePK deletedBy) {
        wishlistTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(wishlistTypeDescription.getWishlistTypePK(), EventTypes.MODIFY.name(), wishlistTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteWishlistTypeDescriptionsByWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        List<WishlistTypeDescription> wishlistTypeDescriptions = getWishlistTypeDescriptionsByWishlistTypeForUpdate(wishlistType);
        
        wishlistTypeDescriptions.forEach((wishlistTypeDescription) -> 
                deleteWishlistTypeDescription(wishlistTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Type Priorities
    // --------------------------------------------------------------------------------
    
    public WishlistTypePriority createWishlistTypePriority(WishlistType wishlistType, String wishlistTypePriorityName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        WishlistTypePriority defaultWishlistTypePriority = getDefaultWishlistTypePriority(wishlistType);
        boolean defaultFound = defaultWishlistTypePriority != null;
        
        if(defaultFound && isDefault) {
            WishlistTypePriorityDetailValue defaultWishlistTypePriorityDetailValue = getDefaultWishlistTypePriorityDetailValueForUpdate(wishlistType);
            
            defaultWishlistTypePriorityDetailValue.setIsDefault(Boolean.FALSE);
            updateWishlistTypePriorityFromValue(defaultWishlistTypePriorityDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        WishlistTypePriority wishlistTypePriority = WishlistTypePriorityFactory.getInstance().create();
        WishlistTypePriorityDetail wishlistTypePriorityDetail = WishlistTypePriorityDetailFactory.getInstance().create(session,
                wishlistTypePriority, wishlistType, wishlistTypePriorityName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        wishlistTypePriority = WishlistTypePriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                wishlistTypePriority.getPrimaryKey());
        wishlistTypePriority.setActiveDetail(wishlistTypePriorityDetail);
        wishlistTypePriority.setLastDetail(wishlistTypePriorityDetail);
        wishlistTypePriority.store();
        
        sendEventUsingNames(wishlistTypePriority.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return wishlistTypePriority;
    }
    
    private List<WishlistTypePriority> getWishlistTypePriorities(WishlistType wishlistType, EntityPermission entityPermission) {
        List<WishlistTypePriority> wishlistTypePriorities;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypepriorities, wishlisttypeprioritydetails " +
                        "WHERE wshltyprty_activedetailid = wshltyprtydt_wishlisttypeprioritydetailid AND wshltyprtydt_wshlty_wishlisttypeid = ? " +
                        "ORDER BY wshltyprtydt_sortorder, wshltyprtydt_wishlisttypepriorityname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypepriorities, wishlisttypeprioritydetails " +
                        "WHERE wshltyprty_activedetailid = wshltyprtydt_wishlisttypeprioritydetailid AND wshltyprtydt_wshlty_wishlisttypeid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WishlistTypePriorityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistType.getPrimaryKey().getEntityId());
            
            wishlistTypePriorities = WishlistTypePriorityFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistTypePriorities;
    }
    
    public List<WishlistTypePriority> getWishlistTypePriorities(WishlistType wishlistType) {
        return getWishlistTypePriorities(wishlistType, EntityPermission.READ_ONLY);
    }
    
    public List<WishlistTypePriority> getWishlistTypePrioritiesForUpdate(WishlistType wishlistType) {
        return getWishlistTypePriorities(wishlistType, EntityPermission.READ_WRITE);
    }
    
    private WishlistTypePriority getDefaultWishlistTypePriority(WishlistType wishlistType, EntityPermission entityPermission) {
        WishlistTypePriority wishlistTypePriority;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypepriorities, wishlisttypeprioritydetails " +
                        "WHERE wshltyprty_activedetailid = wshltyprtydt_wishlisttypeprioritydetailid " +
                        "AND wshltyprtydt_wshlty_wishlisttypeid = ? AND wshltyprtydt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypepriorities, wishlisttypeprioritydetails " +
                        "WHERE wshltyprty_activedetailid = wshltyprtydt_wishlisttypeprioritydetailid " +
                        "AND wshltyprtydt_wshlty_wishlisttypeid = ? AND wshltyprtydt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WishlistTypePriorityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistType.getPrimaryKey().getEntityId());
            
            wishlistTypePriority = WishlistTypePriorityFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistTypePriority;
    }
    
    public WishlistTypePriority getDefaultWishlistTypePriority(WishlistType wishlistType) {
        return getDefaultWishlistTypePriority(wishlistType, EntityPermission.READ_ONLY);
    }
    
    public WishlistTypePriority getDefaultWishlistTypePriorityForUpdate(WishlistType wishlistType) {
        return getDefaultWishlistTypePriority(wishlistType, EntityPermission.READ_WRITE);
    }
    
    public WishlistTypePriorityDetailValue getDefaultWishlistTypePriorityDetailValueForUpdate(WishlistType wishlistType) {
        return getDefaultWishlistTypePriorityForUpdate(wishlistType).getLastDetailForUpdate().getWishlistTypePriorityDetailValue().clone();
    }
    
    private WishlistTypePriority getWishlistTypePriorityByName(WishlistType wishlistType, String wishlistTypePriorityName, EntityPermission entityPermission) {
        WishlistTypePriority wishlistTypePriority;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypepriorities, wishlisttypeprioritydetails " +
                        "WHERE wshltyprty_activedetailid = wshltyprtydt_wishlisttypeprioritydetailid " +
                        "AND wshltyprtydt_wshlty_wishlisttypeid = ? AND wshltyprtydt_wishlisttypepriorityname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypepriorities, wishlisttypeprioritydetails " +
                        "WHERE wshltyprty_activedetailid = wshltyprtydt_wishlisttypeprioritydetailid " +
                        "AND wshltyprtydt_wshlty_wishlisttypeid = ? AND wshltyprtydt_wishlisttypepriorityname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WishlistTypePriorityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistType.getPrimaryKey().getEntityId());
            ps.setString(2, wishlistTypePriorityName);
            
            wishlistTypePriority = WishlistTypePriorityFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistTypePriority;
    }
    
    public WishlistTypePriority getWishlistTypePriorityByName(WishlistType wishlistType, String wishlistTypePriorityName) {
        return getWishlistTypePriorityByName(wishlistType, wishlistTypePriorityName, EntityPermission.READ_ONLY);
    }
    
    public WishlistTypePriority getWishlistTypePriorityByNameForUpdate(WishlistType wishlistType, String wishlistTypePriorityName) {
        return getWishlistTypePriorityByName(wishlistType, wishlistTypePriorityName, EntityPermission.READ_WRITE);
    }
    
    public WishlistTypePriorityDetailValue getWishlistTypePriorityDetailValueForUpdate(WishlistTypePriority wishlistTypePriority) {
        return wishlistTypePriority == null? null: wishlistTypePriority.getLastDetailForUpdate().getWishlistTypePriorityDetailValue().clone();
    }
    
    public WishlistTypePriorityDetailValue getWishlistTypePriorityDetailValueByNameForUpdate(WishlistType wishlistType, String wishlistTypePriorityName) {
        return getWishlistTypePriorityDetailValueForUpdate(getWishlistTypePriorityByNameForUpdate(wishlistType, wishlistTypePriorityName));
    }
    
    public WishlistTypePriorityChoicesBean getWishlistTypePriorityChoices(String defaultWishlistTypePriorityChoice, Language language,
            boolean allowNullChoice, WishlistType wishlistType) {
        List<WishlistTypePriority> wishlistTypePriorities = getWishlistTypePriorities(wishlistType);
        int size = wishlistTypePriorities.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultWishlistTypePriorityChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var wishlistTypePriority : wishlistTypePriorities) {
            WishlistTypePriorityDetail wishlistTypePriorityDetail = wishlistTypePriority.getLastDetail();
            String label = getBestWishlistTypePriorityDescription(wishlistTypePriority, language);
            String value = wishlistTypePriorityDetail.getWishlistTypePriorityName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultWishlistTypePriorityChoice != null && defaultWishlistTypePriorityChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && wishlistTypePriorityDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new WishlistTypePriorityChoicesBean(labels, values, defaultValue);
    }
    
    public WishlistTypePriorityTransfer getWishlistTypePriorityTransfer(UserVisit userVisit, WishlistTypePriority wishlistTypePriority) {
        return getWishlistTransferCaches(userVisit).getWishlistTypePriorityTransferCache().getWishlistTypePriorityTransfer(wishlistTypePriority);
    }
    
    public List<WishlistTypePriorityTransfer> getWishlistTypePriorityTransfers(UserVisit userVisit, WishlistType wishlistType) {
        List<WishlistTypePriority> wishlistTypePriorities = getWishlistTypePriorities(wishlistType);
        List<WishlistTypePriorityTransfer> wishlistTypePriorityTransfers = new ArrayList<>(wishlistTypePriorities.size());
        WishlistTypePriorityTransferCache wishlistTypePriorityTransferCache = getWishlistTransferCaches(userVisit).getWishlistTypePriorityTransferCache();
        
        wishlistTypePriorities.forEach((wishlistTypePriority) ->
                wishlistTypePriorityTransfers.add(wishlistTypePriorityTransferCache.getWishlistTypePriorityTransfer(wishlistTypePriority))
        );
        
        return wishlistTypePriorityTransfers;
    }
    
    private void updateWishlistTypePriorityFromValue(WishlistTypePriorityDetailValue wishlistTypePriorityDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(wishlistTypePriorityDetailValue.hasBeenModified()) {
            WishlistTypePriority wishlistTypePriority = WishlistTypePriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistTypePriorityDetailValue.getWishlistTypePriorityPK());
            WishlistTypePriorityDetail wishlistTypePriorityDetail = wishlistTypePriority.getActiveDetailForUpdate();
            
            wishlistTypePriorityDetail.setThruTime(session.START_TIME_LONG);
            wishlistTypePriorityDetail.store();
            
            WishlistTypePriorityPK wishlistTypePriorityPK = wishlistTypePriorityDetail.getWishlistTypePriorityPK();
            WishlistType wishlistType = wishlistTypePriorityDetail.getWishlistType();
            WishlistTypePK wishlistTypePK = wishlistType.getPrimaryKey();
            String wishlistTypePriorityName = wishlistTypePriorityDetailValue.getWishlistTypePriorityName();
            Boolean isDefault = wishlistTypePriorityDetailValue.getIsDefault();
            Integer sortOrder = wishlistTypePriorityDetailValue.getSortOrder();
            
            if(checkDefault) {
                WishlistTypePriority defaultWishlistTypePriority = getDefaultWishlistTypePriority(wishlistType);
                boolean defaultFound = defaultWishlistTypePriority != null && !defaultWishlistTypePriority.equals(wishlistTypePriority);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    WishlistTypePriorityDetailValue defaultWishlistTypePriorityDetailValue = getDefaultWishlistTypePriorityDetailValueForUpdate(wishlistType);
                    
                    defaultWishlistTypePriorityDetailValue.setIsDefault(Boolean.FALSE);
                    updateWishlistTypePriorityFromValue(defaultWishlistTypePriorityDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            wishlistTypePriorityDetail = WishlistTypePriorityDetailFactory.getInstance().create(wishlistTypePriorityPK,
                    wishlistTypePK, wishlistTypePriorityName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            wishlistTypePriority.setActiveDetail(wishlistTypePriorityDetail);
            wishlistTypePriority.setLastDetail(wishlistTypePriorityDetail);
            
            sendEventUsingNames(wishlistTypePriorityPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateWishlistTypePriorityFromValue(WishlistTypePriorityDetailValue wishlistTypePriorityDetailValue, BasePK updatedBy) {
        updateWishlistTypePriorityFromValue(wishlistTypePriorityDetailValue, true, updatedBy);
    }
    
    public void deleteWishlistTypePriority(WishlistTypePriority wishlistTypePriority, BasePK deletedBy) {
        deleteWishlistTypePriorityDescriptionsByWishlistTypePriority(wishlistTypePriority, deletedBy);
        deleteOrderLinesByWishlistTypePriority(wishlistTypePriority, deletedBy);
        
        WishlistTypePriorityDetail wishlistTypePriorityDetail = wishlistTypePriority.getLastDetailForUpdate();
        wishlistTypePriorityDetail.setThruTime(session.START_TIME_LONG);
        wishlistTypePriority.setActiveDetail(null);
        wishlistTypePriority.store();
        
        // Check for default, and pick one if necessary
        WishlistType wishlistType = wishlistTypePriorityDetail.getWishlistType();
        WishlistTypePriority defaultWishlistTypePriority = getDefaultWishlistTypePriority(wishlistType);
        if(defaultWishlistTypePriority == null) {
            List<WishlistTypePriority> wishlistTypePriorities = getWishlistTypePrioritiesForUpdate(wishlistType);
            
            if(!wishlistTypePriorities.isEmpty()) {
                Iterator<WishlistTypePriority> iter = wishlistTypePriorities.iterator();
                if(iter.hasNext()) {
                    defaultWishlistTypePriority = iter.next();
                }
                WishlistTypePriorityDetailValue wishlistTypePriorityDetailValue = Objects.requireNonNull(defaultWishlistTypePriority).getLastDetailForUpdate().getWishlistTypePriorityDetailValue().clone();
                
                wishlistTypePriorityDetailValue.setIsDefault(Boolean.TRUE);
                updateWishlistTypePriorityFromValue(wishlistTypePriorityDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(wishlistTypePriority.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteWishlistTypePrioritiesByWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        List<WishlistTypePriority> wishlistTypePriorities = getWishlistTypePrioritiesForUpdate(wishlistType);
        
        wishlistTypePriorities.forEach((wishlistTypePriority) -> 
                deleteWishlistTypePriority(wishlistTypePriority, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Type Priority Descriptions
    // --------------------------------------------------------------------------------
    
    public WishlistTypePriorityDescription createWishlistTypePriorityDescription(WishlistTypePriority wishlistTypePriority,
            Language language, String description, BasePK createdBy) {
        WishlistTypePriorityDescription wishlistTypePriorityDescription = WishlistTypePriorityDescriptionFactory.getInstance().create(session,
                wishlistTypePriority, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(wishlistTypePriority.getPrimaryKey(), EventTypes.MODIFY.name(), wishlistTypePriorityDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return wishlistTypePriorityDescription;
    }
    
    private WishlistTypePriorityDescription getWishlistTypePriorityDescription(WishlistTypePriority wishlistTypePriority, Language language, EntityPermission entityPermission) {
        WishlistTypePriorityDescription wishlistTypePriorityDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypeprioritydescriptions " +
                        "WHERE wshltyprtyd_wshltyprty_wishlisttypepriorityid = ? AND wshltyprtyd_lang_languageid = ? AND wshltyprtyd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypeprioritydescriptions " +
                        "WHERE wshltyprtyd_wshltyprty_wishlisttypepriorityid = ? AND wshltyprtyd_lang_languageid = ? AND wshltyprtyd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WishlistTypePriorityDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistTypePriority.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            wishlistTypePriorityDescription = WishlistTypePriorityDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistTypePriorityDescription;
    }
    
    public WishlistTypePriorityDescription getWishlistTypePriorityDescription(WishlistTypePriority wishlistTypePriority, Language language) {
        return getWishlistTypePriorityDescription(wishlistTypePriority, language, EntityPermission.READ_ONLY);
    }
    
    public WishlistTypePriorityDescription getWishlistTypePriorityDescriptionForUpdate(WishlistTypePriority wishlistTypePriority, Language language) {
        return getWishlistTypePriorityDescription(wishlistTypePriority, language, EntityPermission.READ_WRITE);
    }
    
    public WishlistTypePriorityDescriptionValue getWishlistTypePriorityDescriptionValue(WishlistTypePriorityDescription wishlistTypePriorityDescription) {
        return wishlistTypePriorityDescription == null? null: wishlistTypePriorityDescription.getWishlistTypePriorityDescriptionValue().clone();
    }
    
    public WishlistTypePriorityDescriptionValue getWishlistTypePriorityDescriptionValueForUpdate(WishlistTypePriority wishlistTypePriority, Language language) {
        return getWishlistTypePriorityDescriptionValue(getWishlistTypePriorityDescriptionForUpdate(wishlistTypePriority, language));
    }
    
    private List<WishlistTypePriorityDescription> getWishlistTypePriorityDescriptionsByWishlistTypePriority(WishlistTypePriority wishlistTypePriority, EntityPermission entityPermission) {
        List<WishlistTypePriorityDescription> wishlistTypePriorityDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypeprioritydescriptions, languages " +
                        "WHERE wshltyprtyd_wshltyprty_wishlisttypepriorityid = ? AND wshltyprtyd_thrutime = ? " +
                        "AND wshltyprtyd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlisttypeprioritydescriptions " +
                        "WHERE wshltyprtyd_wshltyprty_wishlisttypepriorityid = ? AND wshltyprtyd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WishlistTypePriorityDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistTypePriority.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            wishlistTypePriorityDescriptions = WishlistTypePriorityDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistTypePriorityDescriptions;
    }
    
    public List<WishlistTypePriorityDescription> getWishlistTypePriorityDescriptionsByWishlistTypePriority(WishlistTypePriority wishlistTypePriority) {
        return getWishlistTypePriorityDescriptionsByWishlistTypePriority(wishlistTypePriority, EntityPermission.READ_ONLY);
    }
    
    public List<WishlistTypePriorityDescription> getWishlistTypePriorityDescriptionsByWishlistTypePriorityForUpdate(WishlistTypePriority wishlistTypePriority) {
        return getWishlistTypePriorityDescriptionsByWishlistTypePriority(wishlistTypePriority, EntityPermission.READ_WRITE);
    }
    
    public String getBestWishlistTypePriorityDescription(WishlistTypePriority wishlistTypePriority, Language language) {
        String description;
        WishlistTypePriorityDescription wishlistTypePriorityDescription = getWishlistTypePriorityDescription(wishlistTypePriority, language);
        
        if(wishlistTypePriorityDescription == null && !language.getIsDefault()) {
            wishlistTypePriorityDescription = getWishlistTypePriorityDescription(wishlistTypePriority, getPartyControl().getDefaultLanguage());
        }
        
        if(wishlistTypePriorityDescription == null) {
            description = wishlistTypePriority.getLastDetail().getWishlistTypePriorityName();
        } else {
            description = wishlistTypePriorityDescription.getDescription();
        }
        
        return description;
    }
    
    public WishlistTypePriorityDescriptionTransfer getWishlistTypePriorityDescriptionTransfer(UserVisit userVisit, WishlistTypePriorityDescription wishlistTypePriorityDescription) {
        return getWishlistTransferCaches(userVisit).getWishlistTypePriorityDescriptionTransferCache().getWishlistTypePriorityDescriptionTransfer(wishlistTypePriorityDescription);
    }
    
    public List<WishlistTypePriorityDescriptionTransfer> getWishlistTypePriorityDescriptionTransfers(UserVisit userVisit, WishlistTypePriority wishlistTypePriority) {
        List<WishlistTypePriorityDescription> wishlistTypePriorityDescriptions = getWishlistTypePriorityDescriptionsByWishlistTypePriority(wishlistTypePriority);
        List<WishlistTypePriorityDescriptionTransfer> wishlistTypePriorityDescriptionTransfers = new ArrayList<>(wishlistTypePriorityDescriptions.size());
        WishlistTypePriorityDescriptionTransferCache wishlistTypePriorityDescriptionTransferCache = getWishlistTransferCaches(userVisit).getWishlistTypePriorityDescriptionTransferCache();
        
        wishlistTypePriorityDescriptions.forEach((wishlistTypePriorityDescription) ->
                wishlistTypePriorityDescriptionTransfers.add(wishlistTypePriorityDescriptionTransferCache.getWishlistTypePriorityDescriptionTransfer(wishlistTypePriorityDescription))
        );
        
        return wishlistTypePriorityDescriptionTransfers;
    }
    
    public void updateWishlistTypePriorityDescriptionFromValue(WishlistTypePriorityDescriptionValue wishlistTypePriorityDescriptionValue, BasePK updatedBy) {
        if(wishlistTypePriorityDescriptionValue.hasBeenModified()) {
            WishlistTypePriorityDescription wishlistTypePriorityDescription = WishlistTypePriorityDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     wishlistTypePriorityDescriptionValue.getPrimaryKey());
            
            wishlistTypePriorityDescription.setThruTime(session.START_TIME_LONG);
            wishlistTypePriorityDescription.store();
            
            WishlistTypePriority wishlistTypePriority = wishlistTypePriorityDescription.getWishlistTypePriority();
            Language language = wishlistTypePriorityDescription.getLanguage();
            String description = wishlistTypePriorityDescriptionValue.getDescription();
            
            wishlistTypePriorityDescription = WishlistTypePriorityDescriptionFactory.getInstance().create(wishlistTypePriority, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(wishlistTypePriority.getPrimaryKey(), EventTypes.MODIFY.name(), wishlistTypePriorityDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteWishlistTypePriorityDescription(WishlistTypePriorityDescription wishlistTypePriorityDescription, BasePK deletedBy) {
        wishlistTypePriorityDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(wishlistTypePriorityDescription.getWishlistTypePriorityPK(), EventTypes.MODIFY.name(), wishlistTypePriorityDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteWishlistTypePriorityDescriptionsByWishlistTypePriority(WishlistTypePriority wishlistTypePriority, BasePK deletedBy) {
        List<WishlistTypePriorityDescription> wishlistTypePriorityDescriptions = getWishlistTypePriorityDescriptionsByWishlistTypePriorityForUpdate(wishlistTypePriority);
        
        wishlistTypePriorityDescriptions.forEach((wishlistTypePriorityDescription) -> 
                deleteWishlistTypePriorityDescription(wishlistTypePriorityDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlists
    // --------------------------------------------------------------------------------
    
    public Wishlist createWishlist(Order order, OfferUse offerUse, WishlistType wishlistType, BasePK createdBy) {
        Wishlist wishlist = WishlistFactory.getInstance().create(order, offerUse, wishlistType,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(order.getPrimaryKey(), EventTypes.MODIFY.name(), wishlist.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = WishlistFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = WishlistFactory.getInstance().prepareStatement(query);
            
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
            Wishlist wishlist = WishlistFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    wishlistValue.getPrimaryKey());
            
            wishlist.setThruTime(session.START_TIME_LONG);
            wishlist.store();
            
            OrderPK orderPK = wishlist.getOrderPK(); // Not updated
            OfferUsePK offerUsePK = wishlistValue.getOfferUsePK();
            WishlistTypePK wishlistTypePK = wishlistValue.getWishlistTypePK();
            
            wishlist = WishlistFactory.getInstance().create(orderPK, offerUsePK, wishlistTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(orderPK, EventTypes.MODIFY.name(), wishlist.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteWishlist(Wishlist wishlist, BasePK deletedBy) {
        wishlist.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(wishlist.getOrderPK(), EventTypes.MODIFY.name(), wishlist.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteWishlistByOrder(Order order, BasePK deletedBy) {
        deleteWishlist(getWishlistForUpdate(order), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Wishlist Lines
    // --------------------------------------------------------------------------------
    
    public WishlistLine createWishlistLine(OrderLine orderLine, OfferUse offerUse, WishlistTypePriority wishlistTypePriority,
            AssociateReferral associateReferral, String comment, BasePK createdBy) {
        WishlistLine wishlistLine = WishlistLineFactory.getInstance().create(orderLine, offerUse,
                wishlistTypePriority, associateReferral, comment, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(orderLine.getPrimaryKey(), EventTypes.MODIFY.name(), wishlistLine.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return wishlistLine;
    }

    public long countWishlistLinesByOfferUse(final OfferUse offerUse) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM wishlistlines " +
                        "WHERE wshll_ofruse_offeruseid = ? AND wshll_thrutime = ?",
                offerUse, Session.MAX_TIME_LONG);
    }

    public long countWishlistLinesByWishlistTypePriority(final WishlistTypePriority wishlistTypePriority) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM wishlistlines " +
                        "WHERE wshll_wshltyprty_wishlisttypepriorityid = ? AND wshll_thrutime = ?",
                wishlistTypePriority, Session.MAX_TIME_LONG);
    }

    public long countWishlistLinesByAssociateReferral(final AssociateReferral associateReferral) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM wishlistlines " +
                        "WHERE wshll_ascrfr_associatereferralid = ? AND wshll_thrutime = ?",
                associateReferral, Session.MAX_TIME_LONG);
    }

    private List<WishlistLine> getWishlistLinesByWishlistTypePriority(WishlistTypePriority wishlistTypePriority,
            EntityPermission entityPermission) {
        List<WishlistLine> wishlistLines;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlists, orderlines, orderlinedetails, orders, orderdetails " +
                        "WHERE wshll_wshltyprty_wishlisttypepriorityid = ? AND wshll_thrutime = ? " +
                        "AND wshll_ordl_orderlineid = ordl_orderlineid AND ordl_activedetailid = ordldt_orderlinedetailid " +
                        "AND ordldt_ord_orderid = ord_orderid AND ord_lastdetailid = orddt_orderdetailid " +
                        "ORDER BY orddt_ordername, ordldt_orderlinesequence";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistlines " +
                        "WHERE wshll_wshltyprty_wishlisttypepriorityid = ? AND wshll_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WishlistLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistTypePriority.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            wishlistLines = WishlistLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return wishlistLines;
    }
    
    public List<WishlistLine> getWishlistLinesByWishlistTypePriority(WishlistTypePriority wishlistTypePriority) {
        return getWishlistLinesByWishlistTypePriority(wishlistTypePriority, EntityPermission.READ_ONLY);
    }
    
    public List<WishlistLine> getWishlistLinesByWishlistTypePriorityForUpdate(WishlistTypePriority wishlistTypePriority) {
        return getWishlistLinesByWishlistTypePriority(wishlistTypePriority, EntityPermission.READ_WRITE);
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
            
            PreparedStatement ps = WishlistLineFactory.getInstance().prepareStatement(query);
            
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
            WishlistLine wishlistLine = WishlistLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    wishlistLineValue.getPrimaryKey());
            
            wishlistLine.setThruTime(session.START_TIME_LONG);
            wishlistLine.store();
            
            OrderLinePK orderLinePK = wishlistLine.getOrderLinePK(); // Not updated
            OfferUsePK offerUsePK = wishlistLineValue.getOfferUsePK();
            WishlistTypePriorityPK wishlistTypePriorityPK = wishlistLineValue.getWishlistTypePriorityPK();
            AssociateReferralPK associateReferralPK = wishlistLineValue.getAssociateReferralPK();
            String comment = wishlistLineValue.getComment();
            
            wishlistLine = WishlistLineFactory.getInstance().create(orderLinePK, offerUsePK,
                    wishlistTypePriorityPK, associateReferralPK, comment, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(orderLinePK, EventTypes.MODIFY.name(), wishlistLine.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteWishlistLine(WishlistLine wishlistLine, BasePK deletedBy) {
        wishlistLine.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(wishlistLine.getOrderLinePK(), EventTypes.MODIFY.name(), wishlistLine.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
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
            
            PreparedStatement ps = OrderFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = OrderFactory.getInstance().prepareStatement(query);
            
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
        return getWishlistTransferCaches(userVisit).getWishlistTransferCache().getWishlistTransfer(order);
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
            
            PreparedStatement ps = OrderFactory.getInstance().prepareStatement(query);
            
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
        List<Order> orders = getWishlists(companyParty, party);
        List<WishlistTransfer> wishlistTransfers = new ArrayList<>(orders.size());
        WishlistTransferCache wishlistTransferCache = getWishlistTransferCaches(userVisit).getWishlistTransferCache();
        
        orders.forEach((order) ->
                wishlistTransfers.add(wishlistTransferCache.getWishlistTransfer(order))
        );
        
        return wishlistTransfers;
    }
    
    public void deleteOrdersByWishlistType(WishlistType wishlistType, BasePK deletedBy) {
        var orderControl = Session.getModelController(OrderControl.class);
        List<Order> orders = getOrdersByWishlistTypeForUpdate(wishlistType);
        
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
            
            PreparedStatement ps = OrderLineFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = OrderLineFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = OrderLineFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = OrderLineFactory.getInstance().prepareStatement(query);
            
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
    
    private List<OrderLine> getOrderLinesByWishlistTypePriority(WishlistTypePriority wishlistTypePriority,
            EntityPermission entityPermission) {
        List<OrderLine> orderLines;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistlines, orderlines " +
                        "WHERE wshll_wshltyprty_wishlisttypepriorityid = ? AND wshll_thrutime = ? " +
                        "AND wshll_ordl_orderlineid = ordl_orderlineid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM wishlistlines, orderlines " +
                        "WHERE wshll_wshltyprty_wishlisttypepriorityid = ? AND wshll_thrutime = ? " +
                        "AND wshll_ordl_orderlineid = ordl_orderlineid " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = OrderLineFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, wishlistTypePriority.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            orderLines = OrderLineFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return orderLines;
    }
    
    public List<OrderLine> getOrderLinesByWishlistTypePriority(WishlistTypePriority wishlistTypePriority) {
        return getOrderLinesByWishlistTypePriority(wishlistTypePriority, EntityPermission.READ_ONLY);
    }
    
    public List<OrderLine> getOrderLinesByWishlistTypePriorityForUpdate(WishlistTypePriority wishlistTypePriority) {
        return getOrderLinesByWishlistTypePriority(wishlistTypePriority, EntityPermission.READ_WRITE);
    }
    
    public WishlistLineTransfer getWishlistLineTransfer(UserVisit userVisit, OrderLine orderLine) {
        return getWishlistTransferCaches(userVisit).getWishlistLineTransferCache().getWishlistLineTransfer(orderLine);
    }
    
    public List<WishlistLineTransfer> getWishlistLineTransfers(UserVisit userVisit, List<OrderLine> orderLines) {
        List<WishlistLineTransfer> wishlistLineTransfers = new ArrayList<>(orderLines.size());
        WishlistLineTransferCache wishlistLineTransferCache = getWishlistTransferCaches(userVisit).getWishlistLineTransferCache();
        
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
    
    public void deleteOrderLinesByWishlistTypePriority(WishlistTypePriority wishlistTypePriority, BasePK deletedBy) {
        var orderLineControl = Session.getModelController(OrderLineControl.class);
        List<OrderLine> orderLines = getOrderLinesByWishlistTypePriorityForUpdate(wishlistTypePriority);
        
        orderLines.forEach((orderLine) -> {
            orderLineControl.deleteOrderLine(orderLine, deletedBy);
        });
    }
    
}

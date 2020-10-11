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

package com.echothree.model.control.shipping.server;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.control.shipping.common.choice.ShippingMethodChoicesBean;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodCarrierServiceTransfer;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodDescriptionTransfer;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.model.control.shipping.server.transfer.ShippingMethodCarrierServiceTransferCache;
import com.echothree.model.control.shipping.server.transfer.ShippingMethodDescriptionTransferCache;
import com.echothree.model.control.shipping.server.transfer.ShippingMethodTransferCache;
import com.echothree.model.control.shipping.server.transfer.ShippingTransferCaches;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipment.server.entity.ShipmentTypeShippingMethod;
import com.echothree.model.data.shipping.common.pk.ShippingMethodPK;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.shipping.server.entity.ShippingMethodCarrierService;
import com.echothree.model.data.shipping.server.entity.ShippingMethodDescription;
import com.echothree.model.data.shipping.server.entity.ShippingMethodDetail;
import com.echothree.model.data.shipping.server.factory.ShippingMethodCarrierServiceFactory;
import com.echothree.model.data.shipping.server.factory.ShippingMethodDescriptionFactory;
import com.echothree.model.data.shipping.server.factory.ShippingMethodDetailFactory;
import com.echothree.model.data.shipping.server.factory.ShippingMethodFactory;
import com.echothree.model.data.shipping.server.value.ShippingMethodCarrierServiceValue;
import com.echothree.model.data.shipping.server.value.ShippingMethodDescriptionValue;
import com.echothree.model.data.shipping.server.value.ShippingMethodDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShippingControl
        extends BaseModelControl {
    
    /** Creates a new instance of ShippingControl */
    public ShippingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Shipping Transfer Caches
    // --------------------------------------------------------------------------------
    
    private ShippingTransferCaches shippingTransferCaches = null;
    
    public ShippingTransferCaches getShippingTransferCaches(UserVisit userVisit) {
        if(shippingTransferCaches == null) {
            shippingTransferCaches = new ShippingTransferCaches(userVisit, this);
        }
        
        return shippingTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Shipping Methods
    // --------------------------------------------------------------------------------
    
    public ShippingMethod createShippingMethod(String shippingMethodName, Selector geoCodeSelector, Selector itemSelector, Integer sortOrder,
            BasePK createdBy) {
        ShippingMethod shippingMethod = ShippingMethodFactory.getInstance().create();
        ShippingMethodDetail shippingMethodDetail = ShippingMethodDetailFactory.getInstance().create(session,
                shippingMethod, shippingMethodName, geoCodeSelector, itemSelector, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        shippingMethod = ShippingMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                shippingMethod.getPrimaryKey());
        shippingMethod.setActiveDetail(shippingMethodDetail);
        shippingMethod.setLastDetail(shippingMethodDetail);
        shippingMethod.store();
        
        sendEventUsingNames(shippingMethod.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return shippingMethod;
    }
    
    private List<ShippingMethod> getShippingMethods(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM shippingmethods, shippingmethoddetails " +
                    "WHERE shm_activedetailid = shmdt_shippingmethoddetailid " +
                    "ORDER BY shmdt_sortorder, shmdt_shippingmethodname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM shippingmethods, carrierservicedetails " +
                    "WHERE shm_activedetailid = shmdt_shippingmethoddetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = ShippingMethodFactory.getInstance().prepareStatement(query);
        
        return ShippingMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<ShippingMethod> getShippingMethods() {
        return getShippingMethods(EntityPermission.READ_ONLY);
    }
    
    public List<ShippingMethod> getShippingMethodsForUpdate() {
        return getShippingMethods(EntityPermission.READ_WRITE);
    }
    
    public ShippingMethod getShippingMethodByName(String shippingMethodName, EntityPermission entityPermission) {
        ShippingMethod shippingMethod = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethods, shippingmethoddetails " +
                        "WHERE shm_activedetailid = shmdt_shippingmethoddetailid " +
                        "AND shmdt_shippingmethodname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethods, shippingmethoddetails " +
                        "WHERE shm_activedetailid = shmdt_shippingmethoddetailid " +
                        "AND shmdt_shippingmethodname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ShippingMethodFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, shippingMethodName);
            
            shippingMethod = ShippingMethodFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return shippingMethod;
    }
    
    public ShippingMethod getShippingMethodByName(String shippingMethodName) {
        return getShippingMethodByName(shippingMethodName, EntityPermission.READ_ONLY);
    }
    
    public ShippingMethod getShippingMethodByNameForUpdate(String shippingMethodName) {
        return getShippingMethodByName(shippingMethodName, EntityPermission.READ_WRITE);
    }
    
    public ShippingMethodDetailValue getShippingMethodDetailValueForUpdate(ShippingMethod shippingMethod) {
        return shippingMethod == null? null: shippingMethod.getLastDetailForUpdate().getShippingMethodDetailValue().clone();
    }
    
    public ShippingMethodDetailValue getShippingMethodDetailValueByNameForUpdate(String shippingMethodName) {
        return getShippingMethodDetailValueForUpdate(getShippingMethodByNameForUpdate(shippingMethodName));
    }
    
    public ShippingMethodChoicesBean getShippingMethodChoices(String defaultShippingMethodChoice, Language language,
            boolean allowNullChoice) {
        List<ShippingMethod> shippingMethods = getShippingMethods();
        int size = shippingMethods.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultShippingMethodChoice == null) {
                defaultValue = "";
            }
        }
        
        for(ShippingMethod shippingMethod: shippingMethods) {
            ShippingMethodDetail shippingMethodDetail = shippingMethod.getLastDetail();
            String label = getBestShippingMethodDescription(shippingMethod, language);
            String value = shippingMethodDetail.getShippingMethodName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultShippingMethodChoice == null? false: defaultShippingMethodChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new ShippingMethodChoicesBean(labels, values, defaultValue);
    }
    
    public ShippingMethodChoicesBean getShippingMethodChoices(String defaultShippingMethodChoice, Language language,
            boolean allowNullChoice, ShipmentType shipmentType) {
        var shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        List<ShipmentTypeShippingMethod> shipmentTypeShippingMethods = shipmentControl.getShipmentTypeShippingMethodsByShipmentType(shipmentType);
        int size = shipmentTypeShippingMethods.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultShippingMethodChoice == null) {
                defaultValue = "";
            }
        }
        
        for(ShipmentTypeShippingMethod shipmentTypeShippingMethod: shipmentTypeShippingMethods) {
            ShippingMethod shippingMethod = shipmentTypeShippingMethod.getShippingMethod();
            ShippingMethodDetail shippingMethodDetail = shippingMethod.getLastDetail();
            String label = getBestShippingMethodDescription(shippingMethod, language);
            String value = shippingMethodDetail.getShippingMethodName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultShippingMethodChoice == null? false: defaultShippingMethodChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && shipmentTypeShippingMethod.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ShippingMethodChoicesBean(labels, values, defaultValue);
    }
    
    public ShippingMethodTransfer getShippingMethodTransfer(UserVisit userVisit, ShippingMethod shippingMethod) {
        return getShippingTransferCaches(userVisit).getShippingMethodTransferCache().getShippingMethodTransfer(shippingMethod);
    }
    
    public List<ShippingMethodTransfer> getShippingMethodTransfers(UserVisit userVisit) {
        List<ShippingMethod> carrierPartyPriorities = getShippingMethods();
        List<ShippingMethodTransfer> shippingMethodTransfers = new ArrayList<>(carrierPartyPriorities.size());
        ShippingMethodTransferCache shippingMethodTransferCache = getShippingTransferCaches(userVisit).getShippingMethodTransferCache();
        
        carrierPartyPriorities.stream().forEach((shippingMethod) -> {
            shippingMethodTransfers.add(shippingMethodTransferCache.getShippingMethodTransfer(shippingMethod));
        });
        
        return shippingMethodTransfers;
    }
    
    public void updateShippingMethodFromValue(ShippingMethodDetailValue shippingMethodDetailValue, BasePK updatedBy) {
        if(shippingMethodDetailValue.hasBeenModified()) {
            ShippingMethod shippingMethod = ShippingMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     shippingMethodDetailValue.getShippingMethodPK());
            ShippingMethodDetail shippingMethodDetail = shippingMethod.getActiveDetailForUpdate();
            
            shippingMethodDetail.setThruTime(session.START_TIME_LONG);
            shippingMethodDetail.store();
            
            ShippingMethodPK shippingMethodPK = shippingMethodDetail.getShippingMethodPK();
            String shippingMethodName = shippingMethodDetailValue.getShippingMethodName();
            SelectorPK geoCodeSelectorPK = shippingMethodDetailValue.getGeoCodeSelectorPK();
            SelectorPK itemSelectorPK = shippingMethodDetailValue.getItemSelectorPK();
            Integer sortOrder = shippingMethodDetailValue.getSortOrder();
            
            shippingMethodDetail = ShippingMethodDetailFactory.getInstance().create(shippingMethodPK, shippingMethodName,
                    geoCodeSelectorPK, itemSelectorPK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            shippingMethod.setActiveDetail(shippingMethodDetail);
            shippingMethod.setLastDetail(shippingMethodDetail);
            
            sendEventUsingNames(shippingMethodPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void deleteShippingMethod(ShippingMethod shippingMethod, BasePK deletedBy) {
        var returnPolicyControl = (ReturnPolicyControl)Session.getModelController(ReturnPolicyControl.class);
        var shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        
        returnPolicyControl.deleteReturnTypeShippingMethodsByShippingMethod(shippingMethod, deletedBy);
        shipmentControl.deleteShipmentTypeShippingMethodsByShippingMethod(shippingMethod, deletedBy);
        deleteShippingMethodDescriptionsByShippingMethod(shippingMethod, deletedBy);
        deleteShippingMethodCarrierServicesByShippingMethod(shippingMethod, deletedBy);
        
        ShippingMethodDetail shippingMethodDetail = shippingMethod.getLastDetailForUpdate();
        shippingMethodDetail.setThruTime(session.START_TIME_LONG);
        shippingMethod.setActiveDetail(null);
        shippingMethod.store();
        
        sendEventUsingNames(shippingMethod.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Shipping Method Descriptions
    // --------------------------------------------------------------------------------
    
    public ShippingMethodDescription createShippingMethodDescription(ShippingMethod shippingMethod, Language language, String description,
            BasePK createdBy) {
        ShippingMethodDescription shippingMethodDescription = ShippingMethodDescriptionFactory.getInstance().create(shippingMethod,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(shippingMethod.getPrimaryKey(), EventTypes.MODIFY.name(), shippingMethodDescription.getPrimaryKey(),
                null, createdBy);
        
        return shippingMethodDescription;
    }
    
    private ShippingMethodDescription getShippingMethodDescription(ShippingMethod shippingMethod, Language language, EntityPermission entityPermission) {
        ShippingMethodDescription shippingMethodDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethoddescriptions " +
                        "WHERE shmd_shm_shippingmethodid = ? AND shmd_lang_languageid = ? AND shmd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethoddescriptions " +
                        "WHERE shmd_shm_shippingmethodid = ? AND shmd_lang_languageid = ? AND shmd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ShippingMethodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, shippingMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            shippingMethodDescription = ShippingMethodDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return shippingMethodDescription;
    }
    
    public ShippingMethodDescription getShippingMethodDescription(ShippingMethod shippingMethod, Language language) {
        return getShippingMethodDescription(shippingMethod, language, EntityPermission.READ_ONLY);
    }
    
    public ShippingMethodDescription getShippingMethodDescriptionForUpdate(ShippingMethod shippingMethod, Language language) {
        return getShippingMethodDescription(shippingMethod, language, EntityPermission.READ_WRITE);
    }
    
    public ShippingMethodDescriptionValue getShippingMethodDescriptionValue(ShippingMethodDescription shippingMethodDescription) {
        return shippingMethodDescription == null? null: shippingMethodDescription.getShippingMethodDescriptionValue().clone();
    }
    
    public ShippingMethodDescriptionValue getShippingMethodDescriptionValueForUpdate(ShippingMethod shippingMethod, Language language) {
        return getShippingMethodDescriptionValue(getShippingMethodDescriptionForUpdate(shippingMethod, language));
    }
    
    private List<ShippingMethodDescription> getShippingMethodDescriptionsByShippingMethod(ShippingMethod shippingMethod, EntityPermission entityPermission) {
        List<ShippingMethodDescription> shippingMethodDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethoddescriptions, languages " +
                        "WHERE shmd_shm_shippingmethodid = ? AND shmd_thrutime = ? AND shmd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethoddescriptions " +
                        "WHERE shmd_shm_shippingmethodid = ? AND shmd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ShippingMethodDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, shippingMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            shippingMethodDescriptions = ShippingMethodDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return shippingMethodDescriptions;
    }
    
    public List<ShippingMethodDescription> getShippingMethodDescriptionsByShippingMethod(ShippingMethod shippingMethod) {
        return getShippingMethodDescriptionsByShippingMethod(shippingMethod, EntityPermission.READ_ONLY);
    }
    
    public List<ShippingMethodDescription> getShippingMethodDescriptionsByShippingMethodForUpdate(ShippingMethod shippingMethod) {
        return getShippingMethodDescriptionsByShippingMethod(shippingMethod, EntityPermission.READ_WRITE);
    }
    
    public String getBestShippingMethodDescription(ShippingMethod shippingMethod, Language language) {
        String description;
        ShippingMethodDescription shippingMethodDescription = getShippingMethodDescription(shippingMethod, language);
        
        if(shippingMethodDescription == null && !language.getIsDefault()) {
            shippingMethodDescription = getShippingMethodDescription(shippingMethod, getPartyControl().getDefaultLanguage());
        }
        
        if(shippingMethodDescription == null) {
            description = shippingMethod.getLastDetail().getShippingMethodName();
        } else {
            description = shippingMethodDescription.getDescription();
        }
        
        return description;
    }
    
    public ShippingMethodDescriptionTransfer getShippingMethodDescriptionTransfer(UserVisit userVisit, ShippingMethodDescription shippingMethodDescription) {
        return getShippingTransferCaches(userVisit).getShippingMethodDescriptionTransferCache().getShippingMethodDescriptionTransfer(shippingMethodDescription);
    }
    
    public List<ShippingMethodDescriptionTransfer> getShippingMethodDescriptionTransfers(UserVisit userVisit, ShippingMethod shippingMethod) {
        List<ShippingMethodDescription> shippingMethodDescriptions = getShippingMethodDescriptionsByShippingMethod(shippingMethod);
        List<ShippingMethodDescriptionTransfer> shippingMethodDescriptionTransfers = new ArrayList<>(shippingMethodDescriptions.size());
        ShippingMethodDescriptionTransferCache shippingMethodDescriptionTransferCache = getShippingTransferCaches(userVisit).getShippingMethodDescriptionTransferCache();
        
        shippingMethodDescriptions.stream().forEach((shippingMethodDescription) -> {
            shippingMethodDescriptionTransfers.add(shippingMethodDescriptionTransferCache.getShippingMethodDescriptionTransfer(shippingMethodDescription));
        });
        
        return shippingMethodDescriptionTransfers;
    }
    
    public void updateShippingMethodDescriptionFromValue(ShippingMethodDescriptionValue shippingMethodDescriptionValue, BasePK updatedBy) {
        if(shippingMethodDescriptionValue.hasBeenModified()) {
            ShippingMethodDescription shippingMethodDescription = ShippingMethodDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     shippingMethodDescriptionValue.getPrimaryKey());
            
            shippingMethodDescription.setThruTime(session.START_TIME_LONG);
            shippingMethodDescription.store();
            
            ShippingMethod shippingMethod = shippingMethodDescription.getShippingMethod();
            Language language = shippingMethodDescription.getLanguage();
            String description = shippingMethodDescriptionValue.getDescription();
            
            shippingMethodDescription = ShippingMethodDescriptionFactory.getInstance().create(shippingMethod, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(shippingMethod.getPrimaryKey(), EventTypes.MODIFY.name(), shippingMethodDescription.getPrimaryKey(),
                    null, updatedBy);
        }
    }
    
    public void deleteShippingMethodDescription(ShippingMethodDescription shippingMethodDescription, BasePK deletedBy) {
        shippingMethodDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(shippingMethodDescription.getShippingMethodPK(), EventTypes.MODIFY.name(),
                shippingMethodDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteShippingMethodDescriptionsByShippingMethod(ShippingMethod shippingMethod, BasePK deletedBy) {
        List<ShippingMethodDescription> shippingMethodDescriptions = getShippingMethodDescriptionsByShippingMethodForUpdate(shippingMethod);
        
        shippingMethodDescriptions.stream().forEach((shippingMethodDescription) -> {
            deleteShippingMethodDescription(shippingMethodDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Carrier Service Options
    // --------------------------------------------------------------------------------
    
    public ShippingMethodCarrierService createShippingMethodCarrierService(ShippingMethod shippingMethod, CarrierService carrierService,
            BasePK createdBy) {
        ShippingMethodCarrierService shippingMethodCarrierService = ShippingMethodCarrierServiceFactory.getInstance().create(session,
                shippingMethod, carrierService, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(shippingMethod.getPrimaryKey(), EventTypes.MODIFY.name(), shippingMethodCarrierService.getPrimaryKey(),
                null, createdBy);
        
        return shippingMethodCarrierService;
    }
    
    private ShippingMethodCarrierService getShippingMethodCarrierService(ShippingMethod shippingMethod, CarrierService carrierService,
            EntityPermission entityPermission) {
        ShippingMethodCarrierService shippingMethodCarrierService = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethodcarrierservices " +
                        "WHERE shmcrrsrv_shm_shippingmethodid = ? AND shmcrrsrv_crrsrv_carrierserviceid = ? AND shmcrrsrv_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethodcarrierservices " +
                        "WHERE shmcrrsrv_shm_shippingmethodid = ? AND shmcrrsrv_crrsrv_carrierserviceid = ? AND shmcrrsrv_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ShippingMethodCarrierServiceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, shippingMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, carrierService.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            shippingMethodCarrierService = ShippingMethodCarrierServiceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return shippingMethodCarrierService;
    }
    
    public ShippingMethodCarrierService getShippingMethodCarrierService(ShippingMethod shippingMethod, CarrierService carrierService) {
        return getShippingMethodCarrierService(shippingMethod, carrierService, EntityPermission.READ_ONLY);
    }
    
    public ShippingMethodCarrierService getShippingMethodCarrierServiceForUpdate(ShippingMethod shippingMethod, CarrierService carrierService) {
        return getShippingMethodCarrierService(shippingMethod, carrierService, EntityPermission.READ_WRITE);
    }
    
    public ShippingMethodCarrierServiceValue getShippingMethodCarrierServiceValue(ShippingMethodCarrierService shippingMethodCarrierService) {
        return shippingMethodCarrierService == null? null: shippingMethodCarrierService.getShippingMethodCarrierServiceValue().clone();
    }
    
    public ShippingMethodCarrierServiceValue getShippingMethodCarrierServiceValueForUpdate(ShippingMethod shippingMethod, CarrierService carrierService) {
        return getShippingMethodCarrierServiceValue(getShippingMethodCarrierServiceForUpdate(shippingMethod, carrierService));
    }
    
    private List<ShippingMethodCarrierService> getShippingMethodCarrierServicesByShippingMethod(ShippingMethod shippingMethod, EntityPermission entityPermission) {
        List<ShippingMethodCarrierService> shippingMethodCarrierServices = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethodcarrierservices, carrierservices, carrierservicedetails " +
                        "WHERE shmcrrsrv_shm_shippingmethodid = ? AND shmcrrsrv_thrutime = ? " +
                        "AND shmcrrsrv_crrsrv_carrierserviceid = crrsrv_carrierserviceid AND crrsrv_activedetailid = crrsrvdt_carrierservicedetailid " +
                        "ORDER BY crrsrvdt_sortorder, crrsrvdt_carrierservicename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethodcarrierservices " +
                        "WHERE shmcrrsrv_shm_shippingmethodid = ? AND shmcrrsrv_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ShippingMethodCarrierServiceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, shippingMethod.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            shippingMethodCarrierServices = ShippingMethodCarrierServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return shippingMethodCarrierServices;
    }
    
    public List<ShippingMethodCarrierService> getShippingMethodCarrierServicesByShippingMethod(ShippingMethod shippingMethod) {
        return getShippingMethodCarrierServicesByShippingMethod(shippingMethod, EntityPermission.READ_ONLY);
    }
    
    public List<ShippingMethodCarrierService> getShippingMethodCarrierServicesByShippingMethodForUpdate(ShippingMethod shippingMethod) {
        return getShippingMethodCarrierServicesByShippingMethod(shippingMethod, EntityPermission.READ_WRITE);
    }
    
    private List<ShippingMethodCarrierService> getShippingMethodCarrierServicesByCarrierService(CarrierService carrierService, EntityPermission entityPermission) {
        List<ShippingMethodCarrierService> shippingMethodCarrierServices = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethodcarrierservices, shippingmethods, shippingmethoddetails " +
                        "WHERE shmcrrsrv_crrsrv_carrierserviceid = ? AND shmcrrsrv_thrutime = ? " +
                        "AND shmcrrsrv_shm_shippingmethodid = shm_shippingmethodid AND shm_activedetailid = shmdt_shippingmethoddetailid " +
                        "ORDER BY shmdt_sortorder, shmdt_shippingmethodname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM shippingmethodcarrierservices " +
                        "WHERE shmcrrsrv_crrsrv_carrierserviceid = ? AND shmcrrsrv_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = ShippingMethodCarrierServiceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, carrierService.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            shippingMethodCarrierServices = ShippingMethodCarrierServiceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return shippingMethodCarrierServices;
    }
    
    public List<ShippingMethodCarrierService> getShippingMethodCarrierServicesByCarrierService(CarrierService carrierService) {
        return getShippingMethodCarrierServicesByCarrierService(carrierService, EntityPermission.READ_ONLY);
    }
    
    public List<ShippingMethodCarrierService> getShippingMethodCarrierServicesByCarrierServiceForUpdate(CarrierService carrierService) {
        return getShippingMethodCarrierServicesByCarrierService(carrierService, EntityPermission.READ_WRITE);
    }
    
    public ShippingMethodCarrierServiceTransfer getShippingMethodCarrierServiceTransfer(UserVisit userVisit,
            ShippingMethodCarrierService shippingMethodCarrierService) {
        return getShippingTransferCaches(userVisit).getShippingMethodCarrierServiceTransferCache().getShippingMethodCarrierServiceTransfer(shippingMethodCarrierService);
    }
    
    public List<ShippingMethodCarrierServiceTransfer> getShippingMethodCarrierServiceTransfers(UserVisit userVisit,
            List<ShippingMethodCarrierService> shippingMethodCarrierServices) {
        List<ShippingMethodCarrierServiceTransfer> shippingMethodCarrierServiceTransfers = new ArrayList<>(shippingMethodCarrierServices.size());
        ShippingMethodCarrierServiceTransferCache shippingMethodCarrierServiceTransferCache = getShippingTransferCaches(userVisit).getShippingMethodCarrierServiceTransferCache();
        
        shippingMethodCarrierServices.stream().forEach((shippingMethodCarrierService) -> {
            shippingMethodCarrierServiceTransfers.add(shippingMethodCarrierServiceTransferCache.getShippingMethodCarrierServiceTransfer(shippingMethodCarrierService));
        });
        
        return shippingMethodCarrierServiceTransfers;
    }
    
    public List<ShippingMethodCarrierServiceTransfer> getShippingMethodCarrierServiceTransfersByShippingMethod(UserVisit userVisit,
            ShippingMethod shippingMethod) {
        return getShippingMethodCarrierServiceTransfers(userVisit, getShippingMethodCarrierServicesByShippingMethod(shippingMethod));
    }
    
    public List<ShippingMethodCarrierServiceTransfer> getShippingMethodCarrierServiceTransfersByCarrierService(UserVisit userVisit,
            CarrierService carrierService) {
        return getShippingMethodCarrierServiceTransfers(userVisit, getShippingMethodCarrierServicesByCarrierService(carrierService));
    }
    
    public void deleteShippingMethodCarrierService(ShippingMethodCarrierService shippingMethodCarrierService, BasePK deletedBy) {
        shippingMethodCarrierService.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(shippingMethodCarrierService.getShippingMethod().getPrimaryKey(), EventTypes.MODIFY.name(),
                shippingMethodCarrierService.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteShippingMethodCarrierServices(List<ShippingMethodCarrierService> shippingMethodCarrierServices, BasePK deletedBy) {
        shippingMethodCarrierServices.stream().forEach((shippingMethodCarrierService) -> {
            deleteShippingMethodCarrierService(shippingMethodCarrierService, deletedBy);
        });
    }
    
    public void deleteShippingMethodCarrierServicesByShippingMethod(ShippingMethod shippingMethod, BasePK deletedBy) {
        deleteShippingMethodCarrierServices(getShippingMethodCarrierServicesByShippingMethodForUpdate(shippingMethod), deletedBy);
    }
    
    public void deleteShippingMethodCarrierServicesByCarrierService(CarrierService carrierService, BasePK deletedBy) {
        deleteShippingMethodCarrierServices(getShippingMethodCarrierServicesByCarrierServiceForUpdate(carrierService), deletedBy);
    }
    
}

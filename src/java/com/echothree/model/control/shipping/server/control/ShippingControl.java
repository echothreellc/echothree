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

package com.echothree.model.control.shipping.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.returnpolicy.server.control.ReturnPolicyControl;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.control.shipping.common.choice.ShippingMethodChoicesBean;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodCarrierServiceTransfer;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodDescriptionTransfer;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipping.common.pk.ShippingMethodPK;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.model.data.shipping.server.entity.ShippingMethodCarrierService;
import com.echothree.model.data.shipping.server.entity.ShippingMethodDescription;
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
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ShippingControl
        extends BaseShippingControl {
    
    /** Creates a new instance of ShippingControl */
    protected ShippingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Shipping Methods
    // --------------------------------------------------------------------------------
    
    public ShippingMethod createShippingMethod(String shippingMethodName, Selector geoCodeSelector, Selector itemSelector, Integer sortOrder,
            BasePK createdBy) {
        var shippingMethod = ShippingMethodFactory.getInstance().create();
        var shippingMethodDetail = ShippingMethodDetailFactory.getInstance().create(session,
                shippingMethod, shippingMethodName, geoCodeSelector, itemSelector, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        shippingMethod = ShippingMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                shippingMethod.getPrimaryKey());
        shippingMethod.setActiveDetail(shippingMethodDetail);
        shippingMethod.setLastDetail(shippingMethodDetail);
        shippingMethod.store();
        
        sendEvent(shippingMethod.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return shippingMethod;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ShippingMethod */
    public ShippingMethod getShippingMethodByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ShippingMethodPK(entityInstance.getEntityUniqueId());

        return ShippingMethodFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ShippingMethod getShippingMethodByEntityInstance(EntityInstance entityInstance) {
        return getShippingMethodByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ShippingMethod getShippingMethodByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getShippingMethodByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countShippingMethods() {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM shippingmethods
                        JOIN shippingmethoddetails ON shm_activedetailid = shmdt_shippingmethoddetailid
                        """);
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

        var ps = ShippingMethodFactory.getInstance().prepareStatement(query);
        
        return ShippingMethodFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<ShippingMethod> getShippingMethods() {
        return getShippingMethods(EntityPermission.READ_ONLY);
    }
    
    public List<ShippingMethod> getShippingMethodsForUpdate() {
        return getShippingMethods(EntityPermission.READ_WRITE);
    }
    
    public ShippingMethod getShippingMethodByName(String shippingMethodName, EntityPermission entityPermission) {
        ShippingMethod shippingMethod;
        
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

            var ps = ShippingMethodFactory.getInstance().prepareStatement(query);
            
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
        var shippingMethods = getShippingMethods();
        var size = shippingMethods.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultShippingMethodChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var shippingMethod : shippingMethods) {
            var shippingMethodDetail = shippingMethod.getLastDetail();
            var label = getBestShippingMethodDescription(shippingMethod, language);
            var value = shippingMethodDetail.getShippingMethodName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultShippingMethodChoice != null && defaultShippingMethodChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new ShippingMethodChoicesBean(labels, values, defaultValue);
    }
    
    public ShippingMethodChoicesBean getShippingMethodChoices(String defaultShippingMethodChoice, Language language,
            boolean allowNullChoice, ShipmentType shipmentType) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentTypeShippingMethods = shipmentControl.getShipmentTypeShippingMethodsByShipmentType(shipmentType);
        var size = shipmentTypeShippingMethods.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultShippingMethodChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var shipmentTypeShippingMethod : shipmentTypeShippingMethods) {
            var shippingMethod = shipmentTypeShippingMethod.getShippingMethod();
            var shippingMethodDetail = shippingMethod.getLastDetail();
            var label = getBestShippingMethodDescription(shippingMethod, language);
            var value = shippingMethodDetail.getShippingMethodName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultShippingMethodChoice != null && defaultShippingMethodChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && shipmentTypeShippingMethod.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new ShippingMethodChoicesBean(labels, values, defaultValue);
    }
    
    public ShippingMethodTransfer getShippingMethodTransfer(UserVisit userVisit, ShippingMethod shippingMethod) {
        return shippingMethodTransferCache.getShippingMethodTransfer(userVisit, shippingMethod);
    }

    public List<ShippingMethodTransfer> getShippingMethodTransfers(UserVisit userVisit, Collection<ShippingMethod> entities) {
        List<ShippingMethodTransfer> shippingMethodTransfers = new ArrayList<>(entities.size());

        entities.forEach((shippingMethod) ->
                shippingMethodTransfers.add(shippingMethodTransferCache.getShippingMethodTransfer(userVisit, shippingMethod))
        );

        return shippingMethodTransfers;
    }

    public List<ShippingMethodTransfer> getShippingMethodTransfers(UserVisit userVisit) {
        return getShippingMethodTransfers(userVisit, getShippingMethods());
    }

    public void updateShippingMethodFromValue(ShippingMethodDetailValue shippingMethodDetailValue, BasePK updatedBy) {
        if(shippingMethodDetailValue.hasBeenModified()) {
            var shippingMethod = ShippingMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     shippingMethodDetailValue.getShippingMethodPK());
            var shippingMethodDetail = shippingMethod.getActiveDetailForUpdate();
            
            shippingMethodDetail.setThruTime(session.START_TIME_LONG);
            shippingMethodDetail.store();

            var shippingMethodPK = shippingMethodDetail.getShippingMethodPK();
            var shippingMethodName = shippingMethodDetailValue.getShippingMethodName();
            var geoCodeSelectorPK = shippingMethodDetailValue.getGeoCodeSelectorPK();
            var itemSelectorPK = shippingMethodDetailValue.getItemSelectorPK();
            var sortOrder = shippingMethodDetailValue.getSortOrder();
            
            shippingMethodDetail = ShippingMethodDetailFactory.getInstance().create(shippingMethodPK, shippingMethodName,
                    geoCodeSelectorPK, itemSelectorPK, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            shippingMethod.setActiveDetail(shippingMethodDetail);
            shippingMethod.setLastDetail(shippingMethodDetail);
            
            sendEvent(shippingMethodPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public ShippingMethod getShippingMethodByPK(ShippingMethodPK pk) {
        return ShippingMethodFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public void deleteShippingMethod(ShippingMethod shippingMethod, BasePK deletedBy) {
        var returnPolicyControl = Session.getModelController(ReturnPolicyControl.class);
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        
        returnPolicyControl.deleteReturnTypeShippingMethodsByShippingMethod(shippingMethod, deletedBy);
        shipmentControl.deleteShipmentTypeShippingMethodsByShippingMethod(shippingMethod, deletedBy);
        deleteShippingMethodDescriptionsByShippingMethod(shippingMethod, deletedBy);
        deleteShippingMethodCarrierServicesByShippingMethod(shippingMethod, deletedBy);

        var shippingMethodDetail = shippingMethod.getLastDetailForUpdate();
        shippingMethodDetail.setThruTime(session.START_TIME_LONG);
        shippingMethod.setActiveDetail(null);
        shippingMethod.store();
        
        sendEvent(shippingMethod.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Shipping Method Descriptions
    // --------------------------------------------------------------------------------
    
    public ShippingMethodDescription createShippingMethodDescription(ShippingMethod shippingMethod, Language language, String description,
            BasePK createdBy) {
        var shippingMethodDescription = ShippingMethodDescriptionFactory.getInstance().create(shippingMethod,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(shippingMethod.getPrimaryKey(), EventTypes.MODIFY, shippingMethodDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return shippingMethodDescription;
    }
    
    private ShippingMethodDescription getShippingMethodDescription(ShippingMethod shippingMethod, Language language, EntityPermission entityPermission) {
        ShippingMethodDescription shippingMethodDescription;
        
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

            var ps = ShippingMethodDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<ShippingMethodDescription> shippingMethodDescriptions;
        
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

            var ps = ShippingMethodDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var shippingMethodDescription = getShippingMethodDescription(shippingMethod, language);
        
        if(shippingMethodDescription == null && !language.getIsDefault()) {
            shippingMethodDescription = getShippingMethodDescription(shippingMethod, partyControl.getDefaultLanguage());
        }
        
        if(shippingMethodDescription == null) {
            description = shippingMethod.getLastDetail().getShippingMethodName();
        } else {
            description = shippingMethodDescription.getDescription();
        }
        
        return description;
    }
    
    public ShippingMethodDescriptionTransfer getShippingMethodDescriptionTransfer(UserVisit userVisit, ShippingMethodDescription shippingMethodDescription) {
        return shippingMethodDescriptionTransferCache.getShippingMethodDescriptionTransfer(userVisit, shippingMethodDescription);
    }
    
    public List<ShippingMethodDescriptionTransfer> getShippingMethodDescriptionTransfers(UserVisit userVisit, ShippingMethod shippingMethod) {
        var shippingMethodDescriptions = getShippingMethodDescriptionsByShippingMethod(shippingMethod);
        List<ShippingMethodDescriptionTransfer> shippingMethodDescriptionTransfers = new ArrayList<>(shippingMethodDescriptions.size());
        
        shippingMethodDescriptions.forEach((shippingMethodDescription) ->
                shippingMethodDescriptionTransfers.add(shippingMethodDescriptionTransferCache.getShippingMethodDescriptionTransfer(userVisit, shippingMethodDescription))
        );
        
        return shippingMethodDescriptionTransfers;
    }
    
    public void updateShippingMethodDescriptionFromValue(ShippingMethodDescriptionValue shippingMethodDescriptionValue, BasePK updatedBy) {
        if(shippingMethodDescriptionValue.hasBeenModified()) {
            var shippingMethodDescription = ShippingMethodDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     shippingMethodDescriptionValue.getPrimaryKey());
            
            shippingMethodDescription.setThruTime(session.START_TIME_LONG);
            shippingMethodDescription.store();

            var shippingMethod = shippingMethodDescription.getShippingMethod();
            var language = shippingMethodDescription.getLanguage();
            var description = shippingMethodDescriptionValue.getDescription();
            
            shippingMethodDescription = ShippingMethodDescriptionFactory.getInstance().create(shippingMethod, language,
                    description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(shippingMethod.getPrimaryKey(), EventTypes.MODIFY, shippingMethodDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteShippingMethodDescription(ShippingMethodDescription shippingMethodDescription, BasePK deletedBy) {
        shippingMethodDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(shippingMethodDescription.getShippingMethodPK(), EventTypes.MODIFY,
                shippingMethodDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteShippingMethodDescriptionsByShippingMethod(ShippingMethod shippingMethod, BasePK deletedBy) {
        var shippingMethodDescriptions = getShippingMethodDescriptionsByShippingMethodForUpdate(shippingMethod);
        
        shippingMethodDescriptions.forEach((shippingMethodDescription) -> 
                deleteShippingMethodDescription(shippingMethodDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Carrier Service Options
    // --------------------------------------------------------------------------------
    
    public ShippingMethodCarrierService createShippingMethodCarrierService(ShippingMethod shippingMethod, CarrierService carrierService,
            BasePK createdBy) {
        var shippingMethodCarrierService = ShippingMethodCarrierServiceFactory.getInstance().create(session,
                shippingMethod, carrierService, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(shippingMethod.getPrimaryKey(), EventTypes.MODIFY, shippingMethodCarrierService.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return shippingMethodCarrierService;
    }
    
    private ShippingMethodCarrierService getShippingMethodCarrierService(ShippingMethod shippingMethod, CarrierService carrierService,
            EntityPermission entityPermission) {
        ShippingMethodCarrierService shippingMethodCarrierService;
        
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

            var ps = ShippingMethodCarrierServiceFactory.getInstance().prepareStatement(query);
            
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
        List<ShippingMethodCarrierService> shippingMethodCarrierServices;
        
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

            var ps = ShippingMethodCarrierServiceFactory.getInstance().prepareStatement(query);
            
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
        List<ShippingMethodCarrierService> shippingMethodCarrierServices;
        
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

            var ps = ShippingMethodCarrierServiceFactory.getInstance().prepareStatement(query);
            
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
        return shippingMethodCarrierServiceTransferCache.getShippingMethodCarrierServiceTransfer(userVisit, shippingMethodCarrierService);
    }
    
    public List<ShippingMethodCarrierServiceTransfer> getShippingMethodCarrierServiceTransfers(UserVisit userVisit,
            List<ShippingMethodCarrierService> shippingMethodCarrierServices) {
        List<ShippingMethodCarrierServiceTransfer> shippingMethodCarrierServiceTransfers = new ArrayList<>(shippingMethodCarrierServices.size());
        
        shippingMethodCarrierServices.forEach((shippingMethodCarrierService) ->
                shippingMethodCarrierServiceTransfers.add(shippingMethodCarrierServiceTransferCache.getShippingMethodCarrierServiceTransfer(userVisit, shippingMethodCarrierService))
        );
        
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
        
        sendEvent(shippingMethodCarrierService.getShippingMethod().getPrimaryKey(), EventTypes.MODIFY,
                shippingMethodCarrierService.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteShippingMethodCarrierServices(List<ShippingMethodCarrierService> shippingMethodCarrierServices, BasePK deletedBy) {
        shippingMethodCarrierServices.forEach((shippingMethodCarrierService) -> 
                deleteShippingMethodCarrierService(shippingMethodCarrierService, deletedBy)
        );
    }
    
    public void deleteShippingMethodCarrierServicesByShippingMethod(ShippingMethod shippingMethod, BasePK deletedBy) {
        deleteShippingMethodCarrierServices(getShippingMethodCarrierServicesByShippingMethodForUpdate(shippingMethod), deletedBy);
    }
    
    public void deleteShippingMethodCarrierServicesByCarrierService(CarrierService carrierService, BasePK deletedBy) {
        deleteShippingMethodCarrierServices(getShippingMethodCarrierServicesByCarrierServiceForUpdate(carrierService), deletedBy);
    }
    
}

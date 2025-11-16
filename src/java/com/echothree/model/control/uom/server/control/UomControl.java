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

package com.echothree.model.control.uom.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureKindChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureKindUseTypeChoicesBean;
import com.echothree.model.control.uom.common.choice.UnitOfMeasureTypeChoicesBean;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureEquivalentTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindDescriptionTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindUseTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindUseTypeTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeDescriptionTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeVolumeTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeWeightTransfer;
import com.echothree.model.control.uom.server.transfer.UomTransferCaches;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.accounting.server.entity.SymbolPosition;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureKindPK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureKindUsePK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureKindUseTypePK;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureTypePK;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureEquivalent;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseTypeDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeVolume;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeWeight;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureEquivalentFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindDescriptionFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindDetailFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindUseFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindUseTypeDescriptionFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureKindUseTypeFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeDescriptionFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeDetailFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeVolumeFactory;
import com.echothree.model.data.uom.server.factory.UnitOfMeasureTypeWeightFactory;
import com.echothree.model.data.uom.server.value.UnitOfMeasureEquivalentValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindDescriptionValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindDetailValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureKindUseValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeDescriptionValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeDetailValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeVolumeValue;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeWeightValue;
import com.echothree.model.data.user.server.entity.UserVisit;
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
import javax.inject.Inject;

@RequestScoped
public class UomControl
        extends BaseModelControl {
    
    /** Creates a new instance of UomControl */
    protected UomControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit of Measure Transfer Caches
    // --------------------------------------------------------------------------------
    
    @Inject
    UomTransferCaches uomTransferCaches;

    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kinds
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureKind createUnitOfMeasureKind(String unitOfMeasureKindName, Integer fractionDigits, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultUnitOfMeasureKind = getDefaultUnitOfMeasureKind();
        var defaultFound = defaultUnitOfMeasureKind != null;
        
        if(defaultFound && isDefault) {
            var defaultUnitOfMeasureKindDetailValue = getDefaultUnitOfMeasureKindDetailValueForUpdate();
            
            defaultUnitOfMeasureKindDetailValue.setIsDefault(false);
            updateUnitOfMeasureKindFromValue(defaultUnitOfMeasureKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var unitOfMeasureKind = UnitOfMeasureKindFactory.getInstance().create();
        var unitOfMeasureKindDetail = UnitOfMeasureKindDetailFactory.getInstance().create(session, unitOfMeasureKind, unitOfMeasureKindName,
                fractionDigits, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        unitOfMeasureKind = UnitOfMeasureKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, unitOfMeasureKind.getPrimaryKey());
        unitOfMeasureKind.setActiveDetail(unitOfMeasureKindDetail);
        unitOfMeasureKind.setLastDetail(unitOfMeasureKindDetail);
        unitOfMeasureKind.store();
        
        sendEvent(unitOfMeasureKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return unitOfMeasureKind;
    }

    public long countUnitOfMeasureKinds() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM unitofmeasurekinds, unitofmeasurekinddetails " +
                "WHERE uomk_activedetailid = uomkdt_unitofmeasurekinddetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UnitOfMeasureKind */
    public UnitOfMeasureKind getUnitOfMeasureKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UnitOfMeasureKindPK(entityInstance.getEntityUniqueId());

        return UnitOfMeasureKindFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public UnitOfMeasureKind getUnitOfMeasureKindByEntityInstance(EntityInstance entityInstance) {
        return getUnitOfMeasureKindByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UnitOfMeasureKind getUnitOfMeasureKindByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUnitOfMeasureKindByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<UnitOfMeasureKind> getUnitOfMeasureKinds(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM unitofmeasurekinds, unitofmeasurekinddetails " +
                    "WHERE uomk_activedetailid = uomkdt_unitofmeasurekinddetailid " +
                    "ORDER BY uomkdt_sortorder, uomkdt_unitofmeasurekindname " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM unitofmeasurekinds, unitofmeasurekinddetails " +
                    "WHERE uomk_activedetailid = uomkdt_unitofmeasurekinddetailid " +
                    "FOR UPDATE";
        }

        var ps = UnitOfMeasureKindFactory.getInstance().prepareStatement(query);
        
        return UnitOfMeasureKindFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<UnitOfMeasureKind> getUnitOfMeasureKinds() {
        return getUnitOfMeasureKinds(EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureKind> getUnitOfMeasureKindsForUpdate() {
        return getUnitOfMeasureKinds(EntityPermission.READ_WRITE);
    }
    
    public List<UnitOfMeasureKind> getUnitOfMeasureKindsByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        List<UnitOfMeasureKind> unitOfMeasureKinds;
        
        try {
            var ps = UnitOfMeasureKindFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM unitofmeasurekinds, unitofmeasurekinddetails, unitofmeasurekinduses " +
                    "WHERE uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                    "AND uomk_unitofmeasurekindid = uomku_uomk_unitofmeasurekindid AND uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_thrutime = ? " +
                    "ORDER BY uomkdt_sortorder, uomkdt_unitofmeasurekindname " +
                    "_LIMIT_");
            
            ps.setLong(1, Session.MAX_TIME);
            ps.setLong(2, unitOfMeasureKindUseType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            unitOfMeasureKinds = UnitOfMeasureKindFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureKinds;
    }
    
    private UnitOfMeasureKind getDefaultUnitOfMeasureKind(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM unitofmeasurekinds, unitofmeasurekinddetails " +
                    "WHERE uomk_activedetailid = uomkdt_unitofmeasurekinddetailid AND uomkdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM unitofmeasurekinds, unitofmeasurekinddetails " +
                    "WHERE uomk_activedetailid = uomkdt_unitofmeasurekinddetailid AND uomkdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = UnitOfMeasureKindFactory.getInstance().prepareStatement(query);
        
        return UnitOfMeasureKindFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public UnitOfMeasureKind getDefaultUnitOfMeasureKind() {
        return getDefaultUnitOfMeasureKind(EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKind getDefaultUnitOfMeasureKindForUpdate() {
        return getDefaultUnitOfMeasureKind(EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindDetailValue getDefaultUnitOfMeasureKindDetailValueForUpdate() {
        return getDefaultUnitOfMeasureKindForUpdate().getLastDetailForUpdate().getUnitOfMeasureKindDetailValue().clone();
    }
    
    private UnitOfMeasureKind getUnitOfMeasureKindByName(String unitOfMeasureKindName, EntityPermission entityPermission) {
        UnitOfMeasureKind unitOfMeasureKind;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinds, unitofmeasurekinddetails " +
                        "WHERE uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_unitofmeasurekindname = ? " +
                        "AND uomkdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinds, unitofmeasurekinddetails " +
                        "WHERE uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_unitofmeasurekindname = ? " +
                        "AND uomkdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureKindFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, unitOfMeasureKindName);
            ps.setLong(2, Session.MAX_TIME);
            
            unitOfMeasureKind = UnitOfMeasureKindFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureKind;
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByName(String unitOfMeasureKindName) {
        return getUnitOfMeasureKindByName(unitOfMeasureKindName, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByNameForUpdate(String unitOfMeasureKindName) {
        return getUnitOfMeasureKindByName(unitOfMeasureKindName, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindDetailValue getUnitOfMeasureKindDetailValueForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return unitOfMeasureKind == null? null: unitOfMeasureKind.getLastDetailForUpdate().getUnitOfMeasureKindDetailValue().clone();
    }
    
    public UnitOfMeasureKindDetailValue getUnitOfMeasureKindDetailValueByNameForUpdate(String unitOfMeasureKindName) {
        return getUnitOfMeasureKindDetailValueForUpdate(getUnitOfMeasureKindByNameForUpdate(unitOfMeasureKindName));
    }
    
    public UnitOfMeasureKindTransfer getUnitOfMeasureKindTransfer(UserVisit userVisit, UnitOfMeasureKind unitOfMeasureKind) {
        return uomTransferCaches.getUnitOfMeasureKindTransferCache().getUnitOfMeasureKindTransfer(userVisit, unitOfMeasureKind);
    }
    
    public List<UnitOfMeasureKindTransfer> getUnitOfMeasureKindTransfers(UserVisit userVisit, Collection<UnitOfMeasureKind> unitOfMeasureKinds) {
        List<UnitOfMeasureKindTransfer> unitOfMeasureKindTransfers = new ArrayList<>(unitOfMeasureKinds.size());
        var unitOfMeasureKindTransferCache = uomTransferCaches.getUnitOfMeasureKindTransferCache();
        
        unitOfMeasureKinds.forEach((unitOfMeasureKind) ->
                unitOfMeasureKindTransfers.add(unitOfMeasureKindTransferCache.getUnitOfMeasureKindTransfer(userVisit, unitOfMeasureKind))
        );
        
        return unitOfMeasureKindTransfers;
    }
    
    public List<UnitOfMeasureKindTransfer> getUnitOfMeasureKindTransfers(UserVisit userVisit) {
        return getUnitOfMeasureKindTransfers(userVisit, getUnitOfMeasureKinds());
    }
    
    public UnitOfMeasureKindChoicesBean getUnitOfMeasureKindChoices(String defaultUnitOfMeasureKindChoice, Language language,
            boolean allowNullChoice) {
        var unitOfMeasureKinds = getUnitOfMeasureKinds();
        
        return getUnitOfMeasureKindChoices(defaultUnitOfMeasureKindChoice, language, allowNullChoice, unitOfMeasureKinds);
    }
    
    public UnitOfMeasureKindChoicesBean getUnitOfMeasureKindChoicesByUnitOfMeasureKindUseType(String defaultUnitOfMeasureKindChoice, Language language,
            boolean allowNullChoice, UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        var unitOfMeasureKinds = getUnitOfMeasureKindsByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        
        return getUnitOfMeasureKindChoices(defaultUnitOfMeasureKindChoice, language, allowNullChoice, unitOfMeasureKinds);
    }
    
    private UnitOfMeasureKindChoicesBean getUnitOfMeasureKindChoices(final String defaultUnitOfMeasureKindChoice,
            final Language language, final boolean allowNullChoice, final List<UnitOfMeasureKind> unitOfMeasureKinds) {
        var size = unitOfMeasureKinds.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUnitOfMeasureKindChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var unitOfMeasureKind : unitOfMeasureKinds) {
            var unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetail();
            
            var label = getBestUnitOfMeasureKindDescription(unitOfMeasureKind, language);
            var value = unitOfMeasureKindDetail.getUnitOfMeasureKindName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultUnitOfMeasureKindChoice != null && defaultUnitOfMeasureKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && unitOfMeasureKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UnitOfMeasureKindChoicesBean(labels, values, defaultValue);
    }
    
    public UnitOfMeasureChoicesBean getUnitOfMeasureChoicesByUnitOfMeasureKindUseType(String defaultUnitOfMeasureChoice,
            Language language, UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        var unitOfMeasureKinds = getUnitOfMeasureKindsByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        var labels = new ArrayList<String>();
        var values = new ArrayList<String>();
        String defaultValue = null;
        var uomkIter = unitOfMeasureKinds.iterator();
        
        while(uomkIter.hasNext()) {
            var unitOfMeasureKind = uomkIter.next();
            var uomkDescription = getBestUnitOfMeasureKindDescription(unitOfMeasureKind, language);
            var uomkName = unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName();
            var unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
            var uomtIter = unitOfMeasureTypes.iterator();
            
            if(uomkDescription == null) {
                uomkDescription = uomkName;
            }
            
            while(uomtIter.hasNext()) {
                var unitOfMeasureType = uomtIter.next();
                var uomtDescription = getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, language);
                var uomtName = unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName();
                
                if(uomtDescription == null) {
                    uomtDescription = uomtName;
                }
                
                var label = uomkDescription + ", " + uomtDescription;
                var value = uomkName + ':' + uomtName;
                
                labels.add(label == null? value: label);
                values.add(value);
                
                var usingDefaultChoice = Objects.equals(defaultUnitOfMeasureChoice, value);
                if(usingDefaultChoice || defaultValue == null)
                    defaultValue = value;
            }
        }
        
        return new UnitOfMeasureChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUnitOfMeasureKindFromValue(UnitOfMeasureKindDetailValue unitOfMeasureKindDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(unitOfMeasureKindDetailValue.hasBeenModified()) {
            var unitOfMeasureKind = UnitOfMeasureKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureKindDetailValue.getUnitOfMeasureKindPK());
            var unitOfMeasureKindDetail = unitOfMeasureKind.getActiveDetailForUpdate();
            
            unitOfMeasureKindDetail.setThruTime(session.START_TIME_LONG);
            unitOfMeasureKindDetail.store();

            var unitOfMeasureKindPK = unitOfMeasureKindDetail.getUnitOfMeasureKindPK();
            var unitOfMeasureKindName = unitOfMeasureKindDetailValue.getUnitOfMeasureKindName();
            var fractionDigits = unitOfMeasureKindDetailValue.getFractionDigits();
            var isDefault = unitOfMeasureKindDetailValue.getIsDefault();
            var sortOrder = unitOfMeasureKindDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultUnitOfMeasureKind = getDefaultUnitOfMeasureKind();
                var defaultFound = defaultUnitOfMeasureKind != null && !defaultUnitOfMeasureKind.equals(unitOfMeasureKind);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultUnitOfMeasureKindDetailValue = getDefaultUnitOfMeasureKindDetailValueForUpdate();
                    
                    defaultUnitOfMeasureKindDetailValue.setIsDefault(false);
                    updateUnitOfMeasureKindFromValue(defaultUnitOfMeasureKindDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            unitOfMeasureKindDetail = UnitOfMeasureKindDetailFactory.getInstance().create(unitOfMeasureKindPK, unitOfMeasureKindName, fractionDigits, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            unitOfMeasureKind.setActiveDetail(unitOfMeasureKindDetail);
            unitOfMeasureKind.setLastDetail(unitOfMeasureKindDetail);
            
            sendEvent(unitOfMeasureKindPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateUnitOfMeasureKindFromValue(UnitOfMeasureKindDetailValue unitOfMeasureKindDetailValue, BasePK updatedBy) {
        updateUnitOfMeasureKindFromValue(unitOfMeasureKindDetailValue, true, updatedBy);
    }
    
    public void deleteUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        deleteUnitOfMeasureKindUseByUnitOfMeasureKind(unitOfMeasureKind, deletedBy);
        deleteUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(unitOfMeasureKind, deletedBy);
        deleteUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind, deletedBy);

        var unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetailForUpdate();
        unitOfMeasureKindDetail.setThruTime(session.START_TIME_LONG);
        unitOfMeasureKindDetail.store();
        unitOfMeasureKind.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var defaultUnitOfMeasureKind = getDefaultUnitOfMeasureKind();
        if(defaultUnitOfMeasureKind == null) {
            var unitOfMeasureKinds = getUnitOfMeasureKindsForUpdate();
            
            if(!unitOfMeasureKinds.isEmpty()) {
                var iter = unitOfMeasureKinds.iterator();
                if(iter.hasNext()) {
                    defaultUnitOfMeasureKind = (UnitOfMeasureKind)iter.next();
                }
                var unitOfMeasureKindDetailValue = Objects.requireNonNull(defaultUnitOfMeasureKind).getLastDetailForUpdate().getUnitOfMeasureKindDetailValue().clone();
                
                unitOfMeasureKindDetailValue.setIsDefault(true);
                updateUnitOfMeasureKindFromValue(unitOfMeasureKindDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(unitOfMeasureKind.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Descriptions
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureKindDescription createUnitOfMeasureKindDescription(UnitOfMeasureKind unitOfMeasureKind, Language language, String description, BasePK createdBy) {
        var unitOfMeasureKindDescription = UnitOfMeasureKindDescriptionFactory.getInstance().create(unitOfMeasureKind, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(unitOfMeasureKind.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureKindDescription;
    }
    
    private UnitOfMeasureKindDescription getUnitOfMeasureKindDescription(UnitOfMeasureKind unitOfMeasureKind, Language language, EntityPermission entityPermission) {
        UnitOfMeasureKindDescription unitOfMeasureKindDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinddescriptions " +
                        "WHERE uomkd_uomk_unitofmeasurekindid = ? AND uomkd_lang_languageid = ? AND uomkd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinddescriptions " +
                        "WHERE uomkd_uomk_unitofmeasurekindid = ? AND uomkd_lang_languageid = ? AND uomkd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureKindDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureKind.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            unitOfMeasureKindDescription = UnitOfMeasureKindDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureKindDescription;
    }
    
    
    public UnitOfMeasureKindDescription getUnitOfMeasureKindDescription(UnitOfMeasureKind unitOfMeasureKind, Language language) {
        return getUnitOfMeasureKindDescription(unitOfMeasureKind, language, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKindDescription getUnitOfMeasureKindDescriptionForUpdate(UnitOfMeasureKind unitOfMeasureKind, Language language) {
        return getUnitOfMeasureKindDescription(unitOfMeasureKind, language, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindDescriptionValue getUnitOfMeasureKindDescriptionValue(UnitOfMeasureKindDescription unitOfMeasureKindDescription) {
        return unitOfMeasureKindDescription == null? null: unitOfMeasureKindDescription.getUnitOfMeasureKindDescriptionValue().clone();
    }
    
    public UnitOfMeasureKindDescriptionValue getUnitOfMeasureKindDescriptionValueForUpdate(UnitOfMeasureKind unitOfMeasureKind, Language language) {
        return getUnitOfMeasureKindDescriptionValue(getUnitOfMeasureKindDescriptionForUpdate(unitOfMeasureKind, language));
    }
    
    private List<UnitOfMeasureKindDescription> getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, EntityPermission entityPermission) {
        List<UnitOfMeasureKindDescription> unitOfMeasureKindDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinddescriptions, languages " +
                        "WHERE uomkd_uomk_unitofmeasurekindid = ? AND uomkd_thrutime = ? AND uomkd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinddescriptions " +
                        "WHERE uomkd_uomk_unitofmeasurekindid = ? AND uomkd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureKindDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureKind.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            unitOfMeasureKindDescriptions = UnitOfMeasureKindDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureKindDescriptions;
    }
    
    public List<UnitOfMeasureKindDescription> getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureKindDescription> getUnitOfMeasureKindDescriptionsByUnitOfMeasureKindForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    public String getBestUnitOfMeasureKindDescription(UnitOfMeasureKind unitOfMeasureKind, Language language) {
        String description;
        var unitOfMeasureKindDescription = getUnitOfMeasureKindDescription(unitOfMeasureKind, language);
        
        if(unitOfMeasureKindDescription == null && !language.getIsDefault()) {
            unitOfMeasureKindDescription = getUnitOfMeasureKindDescription(unitOfMeasureKind, partyControl.getDefaultLanguage());
        }
        
        if(unitOfMeasureKindDescription == null) {
            description = unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName();
        } else {
            description = unitOfMeasureKindDescription.getDescription();
        }
        
        return description;
    }
    
    public UnitOfMeasureKindDescriptionTransfer getUnitOfMeasureKindDescriptionTransfer(UserVisit userVisit, UnitOfMeasureKindDescription unitOfMeasureKindDescription) {
        return uomTransferCaches.getUnitOfMeasureKindDescriptionTransferCache().getUnitOfMeasureKindDescriptionTransfer(userVisit, unitOfMeasureKindDescription);
    }
    
    public List<UnitOfMeasureKindDescriptionTransfer> getUnitOfMeasureKindDescriptionTransfers(UserVisit userVisit, UnitOfMeasureKind unitOfMeasureKind) {
        var unitOfMeasureKindDescriptions = getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(unitOfMeasureKind);
        List<UnitOfMeasureKindDescriptionTransfer> unitOfMeasureKindDescriptionTransfers = new ArrayList<>(unitOfMeasureKindDescriptions.size());
        var unitOfMeasureKindDescriptionTransferCache = uomTransferCaches.getUnitOfMeasureKindDescriptionTransferCache();
        
        unitOfMeasureKindDescriptions.forEach((unitOfMeasureKindDescription) ->
                unitOfMeasureKindDescriptionTransfers.add(unitOfMeasureKindDescriptionTransferCache.getUnitOfMeasureKindDescriptionTransfer(userVisit, unitOfMeasureKindDescription))
        );
        
        return unitOfMeasureKindDescriptionTransfers;
    }
    
    public void updateUnitOfMeasureKindDescriptionFromValue(UnitOfMeasureKindDescriptionValue unitOfMeasureKindDescriptionValue, BasePK updatedBy) {
        if(unitOfMeasureKindDescriptionValue.hasBeenModified()) {
            var unitOfMeasureKindDescription = UnitOfMeasureKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureKindDescriptionValue.getPrimaryKey());
            
            unitOfMeasureKindDescription.setThruTime(session.START_TIME_LONG);
            unitOfMeasureKindDescription.store();

            var unitOfMeasureKind = unitOfMeasureKindDescription.getUnitOfMeasureKind();
            var language = unitOfMeasureKindDescription.getLanguage();
            var description = unitOfMeasureKindDescriptionValue.getDescription();
            
            unitOfMeasureKindDescription = UnitOfMeasureKindDescriptionFactory.getInstance().create(unitOfMeasureKind,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(unitOfMeasureKind.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureKindDescription(UnitOfMeasureKindDescription unitOfMeasureKindDescription, BasePK deletedBy) {
        unitOfMeasureKindDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(unitOfMeasureKindDescription.getUnitOfMeasureKindPK(), EventTypes.MODIFY, unitOfMeasureKindDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        var unitOfMeasureKindDescriptions = getUnitOfMeasureKindDescriptionsByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
        
        unitOfMeasureKindDescriptions.forEach((unitOfMeasureKindDescription) -> 
                deleteUnitOfMeasureKindDescription(unitOfMeasureKindDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Types
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureType createUnitOfMeasureType(UnitOfMeasureKind unitOfMeasureKind, String unitOfMeasureTypeName,
            SymbolPosition symbolPosition, Boolean suppressSymbolSeparator, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultUnitOfMeasureType = getDefaultUnitOfMeasureType(unitOfMeasureKind);
        var defaultFound = defaultUnitOfMeasureType != null;
        
        if(defaultFound && isDefault) {
            var defaultUnitOfMeasureTypeDetailValue = getDefaultUnitOfMeasureTypeDetailValueForUpdate(unitOfMeasureKind);
            
            defaultUnitOfMeasureTypeDetailValue.setIsDefault(false);
            updateUnitOfMeasureTypeFromValue(defaultUnitOfMeasureTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var unitOfMeasureType = UnitOfMeasureTypeFactory.getInstance().create();
        var unitOfMeasureTypeDetail = UnitOfMeasureTypeDetailFactory.getInstance().create(unitOfMeasureType,
                unitOfMeasureKind, unitOfMeasureTypeName, symbolPosition, suppressSymbolSeparator, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        unitOfMeasureType = UnitOfMeasureTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, unitOfMeasureType.getPrimaryKey());
        unitOfMeasureType.setActiveDetail(unitOfMeasureTypeDetail);
        unitOfMeasureType.setLastDetail(unitOfMeasureTypeDetail);
        unitOfMeasureType.store();
        
        sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return unitOfMeasureType;
    }
    
    public long countUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM unitofmeasuretypes, unitofmeasuretypedetails " +
                "WHERE uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_uomk_unitofmeasurekindid = ?",
                unitOfMeasureKind);
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UnitOfMeasureType */
    public UnitOfMeasureType getUnitOfMeasureTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UnitOfMeasureTypePK(entityInstance.getEntityUniqueId());

        return UnitOfMeasureTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public UnitOfMeasureType getUnitOfMeasureTypeByEntityInstance(EntityInstance entityInstance) {
        return getUnitOfMeasureTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UnitOfMeasureType getUnitOfMeasureTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUnitOfMeasureTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<UnitOfMeasureType> getUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, EntityPermission entityPermission) {
        List<UnitOfMeasureType> unitOfMeasureTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_uomk_unitofmeasurekindid = ? " +
                        "AND uomtdt_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_uomk_unitofmeasurekindid = ? " +
                        "AND uomtdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureKind.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            unitOfMeasureTypes = UnitOfMeasureTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureTypes;
    }
    
    public List<UnitOfMeasureType> getUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureType> getUnitOfMeasureTypesByUnitOfMeasureKindForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    private UnitOfMeasureType getDefaultUnitOfMeasureType(UnitOfMeasureKind unitOfMeasureKind, EntityPermission entityPermission) {
        UnitOfMeasureType unitOfMeasureType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE uomt_activedetailid = uomtdt_unitofmeasuretypedetailid AND uomtdt_uomk_unitofmeasurekindid = ? " +
                        "AND uomtdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE uomt_activedetailid = uomtdt_unitofmeasuretypedetailid AND uomtdt_uomk_unitofmeasurekindid = ? " +
                        "AND uomtdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureKind.getPrimaryKey().getEntityId());
            
            unitOfMeasureType = UnitOfMeasureTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureType;
    }
    
    public UnitOfMeasureType getDefaultUnitOfMeasureType(UnitOfMeasureKind unitOfMeasureKind) {
        return getDefaultUnitOfMeasureType(unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureType getDefaultUnitOfMeasureTypeForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getDefaultUnitOfMeasureType(unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureTypeDetailValue getDefaultUnitOfMeasureTypeDetailValueForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getDefaultUnitOfMeasureTypeForUpdate(unitOfMeasureKind).getLastDetailForUpdate().getUnitOfMeasureTypeDetailValue().clone();
    }
    
    private UnitOfMeasureType getUnitOfMeasureTypeByName(UnitOfMeasureKind unitOfMeasureKind, String unitOfMeasureTypeName,
            EntityPermission entityPermission) {
        UnitOfMeasureType unitOfMeasureType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_uomk_unitofmeasurekindid = ? " +
                        "AND uomtdt_unitofmeasuretypename = ? AND uomtdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_uomk_unitofmeasurekindid = ? " +
                        "AND uomtdt_unitofmeasuretypename = ? AND uomtdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureKind.getPrimaryKey().getEntityId());
            ps.setString(2, unitOfMeasureTypeName);
            ps.setLong(3, Session.MAX_TIME);
            
            unitOfMeasureType = UnitOfMeasureTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureType;
    }
    
    public UnitOfMeasureType getUnitOfMeasureTypeByName(UnitOfMeasureKind unitOfMeasureKind, String unitOfMeasureTypeName) {
        return getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureType getUnitOfMeasureTypeByNameForUpdate(UnitOfMeasureKind unitOfMeasureKind, String unitOfMeasureTypeName) {
        return getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureTypeDetailValue getUnitOfMeasureTypeDetailValueForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return unitOfMeasureType == null? null: unitOfMeasureType.getLastDetailForUpdate().getUnitOfMeasureTypeDetailValue().clone();
    }
    
    public UnitOfMeasureTypeDetailValue getUnitOfMeasureTypeDetailValueByNameForUpdate(UnitOfMeasureKind unitOfMeasureKind,
            String unitOfMeasureTypeName) {
        return getUnitOfMeasureTypeDetailValueForUpdate(getUnitOfMeasureTypeByNameForUpdate(unitOfMeasureKind, unitOfMeasureTypeName));
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureTypeTransfer(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        return uomTransferCaches.getUnitOfMeasureTypeTransferCache().getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType);
    }
    
    public List<UnitOfMeasureTypeTransfer> getUnitOfMeasureTypeTransfers(UserVisit userVisit, Collection<UnitOfMeasureType> unitOfMeasureTypes) {
        List<UnitOfMeasureTypeTransfer> unitOfMeasureTypeTransfers = new ArrayList<>(unitOfMeasureTypes.size());
        var unitOfMeasureTypeTransferCache = uomTransferCaches.getUnitOfMeasureTypeTransferCache();
        
        unitOfMeasureTypes.forEach((unitOfMeasureType) ->
                unitOfMeasureTypeTransfers.add(unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType))
        );
        
        return unitOfMeasureTypeTransfers;
    }
    
    public List<UnitOfMeasureTypeTransfer> getUnitOfMeasureTypeTransfersByUnitOfMeasureKind(UserVisit userVisit, UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureTypeTransfers(userVisit, getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind));
    }
    
    public UnitOfMeasureTypeChoicesBean getUnitOfMeasureTypeChoices(String defaultUnitOfMeasureTypeChoice, Language language,
            boolean allowNullChoice, UnitOfMeasureKind unitOfMeasureKind) {
        var unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
        var size = unitOfMeasureTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUnitOfMeasureTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var unitOfMeasureType : unitOfMeasureTypes) {
            var unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();
            
            var label = getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, language);
            var value = unitOfMeasureTypeDetail.getUnitOfMeasureTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultUnitOfMeasureTypeChoice != null && defaultUnitOfMeasureTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && unitOfMeasureTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UnitOfMeasureTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUnitOfMeasureTypeFromValue(UnitOfMeasureTypeDetailValue unitOfMeasureTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(unitOfMeasureTypeDetailValue.hasBeenModified()) {
            var unitOfMeasureType = UnitOfMeasureTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeDetailValue.getUnitOfMeasureTypePK());
            var unitOfMeasureTypeDetail = unitOfMeasureType.getActiveDetailForUpdate();
            
            unitOfMeasureTypeDetail.setThruTime(session.START_TIME_LONG);
            unitOfMeasureTypeDetail.store();

            var unitOfMeasureTypePK = unitOfMeasureTypeDetail.getUnitOfMeasureTypePK();
            var unitOfMeasureKindPK = unitOfMeasureTypeDetail.getUnitOfMeasureKindPK();
            var unitOfMeasureKind = unitOfMeasureTypeDetail.getUnitOfMeasureKind();
            var unitOfMeasureTypeName = unitOfMeasureTypeDetailValue.getUnitOfMeasureTypeName();
            var symbolPositionPK = unitOfMeasureTypeDetailValue.getSymbolPositionPK();
            var suppressSymbolSeparator = unitOfMeasureTypeDetailValue.getSuppressSymbolSeparator();
            var isDefault = unitOfMeasureTypeDetailValue.getIsDefault();
            var sortOrder = unitOfMeasureTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultUnitOfMeasureType = getDefaultUnitOfMeasureType(unitOfMeasureKind);
                var defaultFound = defaultUnitOfMeasureType != null && !defaultUnitOfMeasureType.equals(unitOfMeasureType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultUnitOfMeasureTypeDetailValue = getDefaultUnitOfMeasureTypeDetailValueForUpdate(unitOfMeasureKind);
                    
                    defaultUnitOfMeasureTypeDetailValue.setIsDefault(false);
                    updateUnitOfMeasureTypeFromValue(defaultUnitOfMeasureTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            unitOfMeasureTypeDetail = UnitOfMeasureTypeDetailFactory.getInstance().create(unitOfMeasureTypePK, unitOfMeasureKindPK,
                    unitOfMeasureTypeName, symbolPositionPK, suppressSymbolSeparator, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            unitOfMeasureType.setActiveDetail(unitOfMeasureTypeDetail);
            unitOfMeasureType.setLastDetail(unitOfMeasureTypeDetail);
            
            sendEvent(unitOfMeasureTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateUnitOfMeasureTypeFromValue(UnitOfMeasureTypeDetailValue unitOfMeasureTypeDetailValue, BasePK updatedBy) {
        updateUnitOfMeasureTypeFromValue(unitOfMeasureTypeDetailValue, true, updatedBy);
    }
    
    public void deleteUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        var vendorControl = Session.getModelController(VendorControl.class);
        
        deleteUnitOfMeasureEquivalentsByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureTypeVolumeByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureTypeWeightByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        itemControl.deleteItemUnitOfMeasureTypesByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        vendorControl.deleteVendorItemCostsByUnitOfMeasureType(unitOfMeasureType, deletedBy);

        var unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetailForUpdate();
        unitOfMeasureTypeDetail.setThruTime(session.START_TIME_LONG);
        unitOfMeasureTypeDetail.store();
        unitOfMeasureType.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var unitOfMeasureKind = unitOfMeasureTypeDetail.getUnitOfMeasureKind();
        var defaultUnitOfMeasureType = getDefaultUnitOfMeasureType(unitOfMeasureKind);
        if(defaultUnitOfMeasureType == null) {
            var unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
            
            if(!unitOfMeasureTypes.isEmpty()) {
                var iter = unitOfMeasureTypes.iterator();
                if(iter.hasNext()) {
                    defaultUnitOfMeasureType = (UnitOfMeasureType)iter.next();
                }
                var unitOfMeasureTypeDetailValue = Objects.requireNonNull(defaultUnitOfMeasureType).getLastDetailForUpdate().getUnitOfMeasureTypeDetailValue().clone();
                
                unitOfMeasureTypeDetailValue.setIsDefault(true);
                updateUnitOfMeasureTypeFromValue(unitOfMeasureTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        var unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
        
        unitOfMeasureTypes.forEach((unitOfMeasureType) -> 
                deleteUnitOfMeasureType(unitOfMeasureType, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Descriptions
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureTypeDescription createUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language,
            String singularDescription, String pluralDescription, String symbol, BasePK createdBy) {
        var unitOfMeasureTypeDescription = UnitOfMeasureTypeDescriptionFactory.getInstance().create(session,
                unitOfMeasureType, language, singularDescription, pluralDescription, symbol, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(unitOfMeasureType.getLastDetail().getUnitOfMeasureTypePK(), EventTypes.MODIFY, unitOfMeasureTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureTypeDescription;
    }
    
    private UnitOfMeasureTypeDescription getUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language, EntityPermission entityPermission) {
        UnitOfMeasureTypeDescription unitOfMeasureTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypedescriptions " +
                        "WHERE uomtd_uomt_unitofmeasuretypeid = ? AND uomtd_lang_languageid = ? AND uomtd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypedescriptions " +
                        "WHERE uomtd_uomt_unitofmeasuretypeid = ? AND uomtd_lang_languageid = ? AND uomtd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            unitOfMeasureTypeDescription = UnitOfMeasureTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureTypeDescription;
    }
    
    public UnitOfMeasureTypeDescription getUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getUnitOfMeasureTypeDescription(unitOfMeasureType, language, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureTypeDescription getUnitOfMeasureTypeDescriptionForUpdate(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getUnitOfMeasureTypeDescription(unitOfMeasureType, language, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureTypeDescriptionValue getUnitOfMeasureTypeDescriptionValue(UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        return unitOfMeasureTypeDescription == null? null: unitOfMeasureTypeDescription.getUnitOfMeasureTypeDescriptionValue().clone();
    }
    
    public UnitOfMeasureTypeDescriptionValue getUnitOfMeasureTypeDescriptionValueForUpdate(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getUnitOfMeasureTypeDescriptionValue(getUnitOfMeasureTypeDescriptionForUpdate(unitOfMeasureType, language));
    }
    
    private List<UnitOfMeasureTypeDescription> getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        List<UnitOfMeasureTypeDescription> unitOfMeasureTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypedescriptions, languages " +
                        "WHERE uomtd_uomt_unitofmeasuretypeid = ? AND uomtd_thrutime = ? AND uomtd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypedescriptions " +
                        "WHERE uomtd_uomt_unitofmeasuretypeid = ? AND uomtd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            unitOfMeasureTypeDescriptions = UnitOfMeasureTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureTypeDescriptions;
    }
    
    public List<UnitOfMeasureTypeDescription> getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureTypeDescription> getUnitOfMeasureTypeDescriptionsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureTypeDescription getBestUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language) {
        var unitOfMeasureTypeDescription = getUnitOfMeasureTypeDescription(unitOfMeasureType, language);
        
        if(unitOfMeasureTypeDescription == null && !language.getIsDefault()) {
            unitOfMeasureTypeDescription = getUnitOfMeasureTypeDescription(unitOfMeasureType, partyControl.getDefaultLanguage());
        }
        
        return unitOfMeasureTypeDescription;
    }
    
    public String getBestSingularUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        String description;
        
        if(unitOfMeasureTypeDescription == null) {
            description = unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName();
        } else {
            description = unitOfMeasureTypeDescription.getSingularDescription();
        }
        
        return description;
    }
    
    public String getBestSingularUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, getBestUnitOfMeasureTypeDescription(unitOfMeasureType, language));
    }
    
    public String getBestPluralUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        String description;
        
        if(unitOfMeasureTypeDescription == null) {
            description = unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName();
        } else {
            description = unitOfMeasureTypeDescription.getPluralDescription();
        }
        
        return description;
    }
    
    public String getBestPluralUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getBestPluralUnitOfMeasureTypeDescription(unitOfMeasureType, getBestUnitOfMeasureTypeDescription(unitOfMeasureType, language));
    }
    
    public String getBestUnitOfMeasureTypeDescriptionSymbol(UnitOfMeasureType unitOfMeasureType, UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        String symbol;
        
        if(unitOfMeasureTypeDescription == null) {
            symbol = unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName();
        } else {
            symbol = unitOfMeasureTypeDescription.getSymbol();
        }
        
        return symbol;
    }
    
    public String getBestUnitOfMeasureTypeDescriptionSymbol(UnitOfMeasureType unitOfMeasureType, Language language) {
        return getBestUnitOfMeasureTypeDescriptionSymbol(unitOfMeasureType, getBestUnitOfMeasureTypeDescription(unitOfMeasureType, language));
    }
    
    public UnitOfMeasureTypeDescriptionTransfer getUnitOfMeasureTypeDescriptionTransfer(UserVisit userVisit, UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        return uomTransferCaches.getUnitOfMeasureTypeDescriptionTransferCache().getUnitOfMeasureTypeDescriptionTransfer(userVisit, unitOfMeasureTypeDescription);
    }
    
    public List<UnitOfMeasureTypeDescriptionTransfer> getUnitOfMeasureTypeDescriptionTransfers(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        var unitOfMeasureTypeDescriptions = getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(unitOfMeasureType);
        List<UnitOfMeasureTypeDescriptionTransfer> unitOfMeasureTypeDescriptionTransfers = new ArrayList<>(unitOfMeasureTypeDescriptions.size());
        var unitOfMeasureTypeDescriptionTransferCache = uomTransferCaches.getUnitOfMeasureTypeDescriptionTransferCache();
        
        unitOfMeasureTypeDescriptions.forEach((unitOfMeasureTypeDescription) ->
                unitOfMeasureTypeDescriptionTransfers.add(unitOfMeasureTypeDescriptionTransferCache.getUnitOfMeasureTypeDescriptionTransfer(userVisit, unitOfMeasureTypeDescription))
        );
        
        return unitOfMeasureTypeDescriptionTransfers;
    }
    
    public void updateUnitOfMeasureTypeDescriptionFromValue(UnitOfMeasureTypeDescriptionValue unitOfMeasureTypeDescriptionValue, BasePK updatedBy) {
        if(unitOfMeasureTypeDescriptionValue.hasBeenModified()) {
            var unitOfMeasureTypeDescription = UnitOfMeasureTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeDescriptionValue.getPrimaryKey());
            
            unitOfMeasureTypeDescription.setThruTime(session.START_TIME_LONG);
            unitOfMeasureTypeDescription.store();

            var unitOfMeasureType = unitOfMeasureTypeDescription.getUnitOfMeasureType();
            var language = unitOfMeasureTypeDescription.getLanguage();
            var singularDescription = unitOfMeasureTypeDescriptionValue.getSingularDescription();
            var pluralDescription = unitOfMeasureTypeDescriptionValue.getPluralDescription();
            var symbol = unitOfMeasureTypeDescriptionValue.getSymbol();
            
            unitOfMeasureTypeDescription = UnitOfMeasureTypeDescriptionFactory.getInstance().create(unitOfMeasureType, language,
                    singularDescription, pluralDescription, symbol, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureTypeDescription(UnitOfMeasureTypeDescription unitOfMeasureTypeDescription, BasePK deletedBy) {
        unitOfMeasureTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(unitOfMeasureTypeDescription.getUnitOfMeasureTypePK(), EventTypes.MODIFY, unitOfMeasureTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        var unitOfMeasureTypeDescriptions = getUnitOfMeasureTypeDescriptionsByUnitOfMeasureTypeForUpdate(unitOfMeasureType);
        
        unitOfMeasureTypeDescriptions.forEach((unitOfMeasureTypeDescription) -> 
                deleteUnitOfMeasureTypeDescription(unitOfMeasureTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Volumes
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureTypeVolume createUnitOfMeasureTypeVolume(UnitOfMeasureType unitOfMeasureType, Long height, Long width,
            Long depth, BasePK createdBy) {
        var unitOfMeasureTypeVolume = UnitOfMeasureTypeVolumeFactory.getInstance().create(session,
                unitOfMeasureType, height, width, depth, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureTypeVolume.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureTypeVolume;
    }
    
    private UnitOfMeasureTypeVolume getUnitOfMeasureTypeVolume(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        UnitOfMeasureTypeVolume unitOfMeasureTypeVolume;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypevolumes " +
                        "WHERE uomtvol_uomt_unitofmeasuretypeid = ? AND uomtvol_thrutime = ? ";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypevolumes " +
                        "WHERE uomtvol_uomt_unitofmeasuretypeid = ? AND uomtvol_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureTypeVolumeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            unitOfMeasureTypeVolume = UnitOfMeasureTypeVolumeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureTypeVolume;
    }
    
    public UnitOfMeasureTypeVolume getUnitOfMeasureTypeVolume(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeVolume(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureTypeVolume getUnitOfMeasureTypeVolumeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeVolume(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureTypeVolumeValue getUnitOfMeasureTypeVolumeValueForUpdate(UnitOfMeasureType unitOfMeasureType) {
        var unitOfMeasureTypeVolume = getUnitOfMeasureTypeVolumeForUpdate(unitOfMeasureType);
        
        return unitOfMeasureTypeVolume == null? null: unitOfMeasureTypeVolume.getUnitOfMeasureTypeVolumeValue().clone();
    }
    
    public UnitOfMeasureTypeVolumeTransfer getUnitOfMeasureTypeVolumeTransfer(UserVisit userVisit, UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        return unitOfMeasureTypeVolume == null? null: uomTransferCaches.getUnitOfMeasureTypeVolumeTransferCache().getUnitOfMeasureTypeVolumeTransfer(userVisit, unitOfMeasureTypeVolume);
    }
    
    public UnitOfMeasureTypeVolumeTransfer getUnitOfMeasureTypeVolumeTransfer(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeVolumeTransfer(userVisit, getUnitOfMeasureTypeVolume(unitOfMeasureType));
    }
    
    public void updateUnitOfMeasureTypeVolumeFromValue(UnitOfMeasureTypeVolumeValue unitOfMeasureTypeVolumeValue, BasePK updatedBy) {
        if(unitOfMeasureTypeVolumeValue.hasBeenModified()) {
            var unitOfMeasureTypeVolume = UnitOfMeasureTypeVolumeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeVolumeValue.getPrimaryKey());
            
            unitOfMeasureTypeVolume.setThruTime(session.START_TIME_LONG);
            unitOfMeasureTypeVolume.store();

            var unitOfMeasureTypePK = unitOfMeasureTypeVolume.getUnitOfMeasureTypePK(); // Not updated
            var height = unitOfMeasureTypeVolumeValue.getHeight();
            var width = unitOfMeasureTypeVolumeValue.getWidth();
            var depth = unitOfMeasureTypeVolumeValue.getDepth();
            
            unitOfMeasureTypeVolume = UnitOfMeasureTypeVolumeFactory.getInstance().create(unitOfMeasureTypePK, height,
                    width, depth, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(unitOfMeasureTypePK, EventTypes.MODIFY, unitOfMeasureTypeVolume.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureTypeVolume(UnitOfMeasureTypeVolume unitOfMeasureTypeVolume, BasePK deletedBy) {
        unitOfMeasureTypeVolume.setThruTime(session.START_TIME_LONG);
        
        sendEvent(unitOfMeasureTypeVolume.getUnitOfMeasureTypePK(), EventTypes.MODIFY, unitOfMeasureTypeVolume.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureTypeVolumeByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        var unitOfMeasureTypeVolume = getUnitOfMeasureTypeVolumeForUpdate(unitOfMeasureType);
        
        if(unitOfMeasureTypeVolume != null)
            deleteUnitOfMeasureTypeVolume(unitOfMeasureTypeVolume, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Weights
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureTypeWeight createUnitOfMeasureTypeWeight(UnitOfMeasureType unitOfMeasureType, Long weight, BasePK createdBy) {
        var unitOfMeasureTypeWeight = UnitOfMeasureTypeWeightFactory.getInstance().create(unitOfMeasureType, weight,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(unitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureTypeWeight.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureTypeWeight;
    }
    
    private UnitOfMeasureTypeWeight getUnitOfMeasureTypeWeight(UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        UnitOfMeasureTypeWeight unitOfMeasureTypeWeight;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypeweights " +
                        "WHERE uomtwght_uomt_unitofmeasuretypeid = ? AND uomtwght_thrutime = ? ";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypeweights " +
                        "WHERE uomtwght_uomt_unitofmeasuretypeid = ? AND uomtwght_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureTypeWeightFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            unitOfMeasureTypeWeight = UnitOfMeasureTypeWeightFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureTypeWeight;
    }
    
    public UnitOfMeasureTypeWeight getUnitOfMeasureTypeWeight(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeWeight(unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureTypeWeight getUnitOfMeasureTypeWeightForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeWeight(unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureTypeWeightValue getUnitOfMeasureTypeWeightValueForUpdate(UnitOfMeasureType unitOfMeasureType) {
        var unitOfMeasureTypeWeight = getUnitOfMeasureTypeWeightForUpdate(unitOfMeasureType);
        
        return unitOfMeasureTypeWeight == null? null: unitOfMeasureTypeWeight.getUnitOfMeasureTypeWeightValue().clone();
    }
    
    public UnitOfMeasureTypeWeightTransfer getUnitOfMeasureTypeWeightTransfer(UserVisit userVisit, UnitOfMeasureTypeWeight unitOfMeasureTypeWeight) {
        return unitOfMeasureTypeWeight == null? null: uomTransferCaches.getUnitOfMeasureTypeWeightTransferCache().getUnitOfMeasureTypeWeightTransfer(userVisit, unitOfMeasureTypeWeight);
    }
    
    public UnitOfMeasureTypeWeightTransfer getUnitOfMeasureTypeWeightTransfer(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeWeightTransfer(userVisit, getUnitOfMeasureTypeWeight(unitOfMeasureType));
    }
    
    public void updateUnitOfMeasureTypeWeightFromValue(UnitOfMeasureTypeWeightValue unitOfMeasureTypeWeightValue, BasePK updatedBy) {
        if(unitOfMeasureTypeWeightValue.hasBeenModified()) {
            var unitOfMeasureTypeWeight = UnitOfMeasureTypeWeightFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeWeightValue.getPrimaryKey());
            
            unitOfMeasureTypeWeight.setThruTime(session.START_TIME_LONG);
            unitOfMeasureTypeWeight.store();

            var unitOfMeasureTypePK = unitOfMeasureTypeWeight.getUnitOfMeasureTypePK(); // Not updated
            var weight = unitOfMeasureTypeWeightValue.getWeight();
            
            unitOfMeasureTypeWeight = UnitOfMeasureTypeWeightFactory.getInstance().create(unitOfMeasureTypePK, weight,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(unitOfMeasureTypePK, EventTypes.MODIFY, unitOfMeasureTypeWeight.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureTypeWeight(UnitOfMeasureTypeWeight unitOfMeasureTypeWeight, BasePK deletedBy) {
        unitOfMeasureTypeWeight.setThruTime(session.START_TIME_LONG);
        
        sendEvent(unitOfMeasureTypeWeight.getUnitOfMeasureTypePK(), EventTypes.MODIFY, unitOfMeasureTypeWeight.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureTypeWeightByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        var unitOfMeasureTypeWeight = getUnitOfMeasureTypeWeightForUpdate(unitOfMeasureType);
        
        if(unitOfMeasureTypeWeight != null)
            deleteUnitOfMeasureTypeWeight(unitOfMeasureTypeWeight, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Equivalents
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureEquivalent createUnitOfMeasureEquivalent(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType, Long toQuantity, BasePK createdBy) {
        var unitOfMeasureEquivalent = UnitOfMeasureEquivalentFactory.getInstance().create(session,
                fromUnitOfMeasureType, toUnitOfMeasureType, toQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(fromUnitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureEquivalent.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureEquivalent;
    }
    
    private UnitOfMeasureEquivalent getUnitOfMeasureEquivalent(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType, EntityPermission entityPermission) {
        UnitOfMeasureEquivalent unitOfMeasureEquivalent;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents " +
                        "WHERE uomeq_fromunitofmeasuretypeid = ? AND uomeq_tounitofmeasuretypeid = ? AND uomeq_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents " +
                        "WHERE uomeq_fromunitofmeasuretypeid = ? AND uomeq_tounitofmeasuretypeid = ? AND uomeq_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureEquivalentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, fromUnitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, toUnitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            unitOfMeasureEquivalent = UnitOfMeasureEquivalentFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureEquivalent;
    }
    
    public UnitOfMeasureEquivalent getUnitOfMeasureEquivalent(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalent(fromUnitOfMeasureType, toUnitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureEquivalent getUnitOfMeasureEquivalentForUpdate(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalent(fromUnitOfMeasureType, toUnitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureEquivalentValue getUnitOfMeasureEquivalentValueForUpdate(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType) {
        var unitOfMeasureEquivalent = getUnitOfMeasureEquivalentForUpdate(fromUnitOfMeasureType,
                toUnitOfMeasureType);
        
        return unitOfMeasureEquivalent == null? null: unitOfMeasureEquivalent.getUnitOfMeasureEquivalentValue().clone();
    }
    
    private List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind,
            EntityPermission entityPermission) {
        List<UnitOfMeasureEquivalent> unitOfMeasureEquivalents;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents, unitofmeasuretypedetails, unitofmeasurekinddetails " +
                        "WHERE uomeq_thrutime = ? " +
                        "AND uomeq_fromunitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "AND uomkdt_uomk_unitofmeasurekindid = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents, unitofmeasuretypedetails, unitofmeasurekinddetails " +
                        "WHERE AND uomeq_thrutime = ? " +
                        "AND uomeq_fromunitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "AND uomkdt_uomk_unitofmeasurekindid = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureEquivalentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, Session.MAX_TIME);
            ps.setLong(4, unitOfMeasureKind.getPrimaryKey().getEntityId());
            
            unitOfMeasureEquivalents = UnitOfMeasureEquivalentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureEquivalents;
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureEquivalentsByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByUnitOfMeasureKindForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureEquivalentsByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    private List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(UnitOfMeasureType fromUnitOfMeasureType,
            EntityPermission entityPermission) {
        List<UnitOfMeasureEquivalent> unitOfMeasureEquivalents;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents, unitofmeasuretypedetails " +
                        "WHERE uomeq_fromunitofmeasuretypeid = ? AND uomeq_thrutime = ? " +
                        "AND uomeq_fromunitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents " +
                        "WHERE uomeq_fromunitofmeasuretypeid = ? AND uomeq_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureEquivalentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, fromUnitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            unitOfMeasureEquivalents = UnitOfMeasureEquivalentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureEquivalents;
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(UnitOfMeasureType fromUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(fromUnitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByFromUnitOfMeasureTypeForUpdate(UnitOfMeasureType fromUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(fromUnitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    private List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByToUnitOfMeasureType(UnitOfMeasureType toUnitOfMeasureType,
            EntityPermission entityPermission) {
        List<UnitOfMeasureEquivalent> unitOfMeasureEquivalents;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents, unitofmeasuretypedetails " +
                        "WHERE uomeq_tounitofmeasuretypeid = ? AND uomeq_thrutime = ? " +
                        "AND uomeq_fromunitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents " +
                        "WHERE uomeq_tounitofmeasuretypeid = ? AND uomeq_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureEquivalentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, toUnitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            unitOfMeasureEquivalents = UnitOfMeasureEquivalentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureEquivalents;
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByToUnitOfMeasureType(UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentsByToUnitOfMeasureType(toUnitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByToUnitOfMeasureTypeForUpdate(UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentsByToUnitOfMeasureType(toUnitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureEquivalentTransfer getUnitOfMeasureEquivalentTransfer(UserVisit userVisit,
            UnitOfMeasureEquivalent unitOfMeasureEquivalent) {
        return uomTransferCaches.getUnitOfMeasureEquivalentTransferCache().getUnitOfMeasureEquivalentTransfer(userVisit, unitOfMeasureEquivalent);
    }
    
    private List<UnitOfMeasureEquivalentTransfer> getUnitOfMeasureEquivalentTransfers(final UserVisit userVisit,
            final List<UnitOfMeasureEquivalent> unitOfMeasureEquivalents) {
        List<UnitOfMeasureEquivalentTransfer> unitOfMeasureEquivalentTransfers = new ArrayList<>(unitOfMeasureEquivalents.size());
        var unitOfMeasureEquivalentTransferCache = uomTransferCaches.getUnitOfMeasureEquivalentTransferCache();
        
        unitOfMeasureEquivalents.forEach((unitOfMeasureEquivalent) ->
                unitOfMeasureEquivalentTransfers.add(unitOfMeasureEquivalentTransferCache.getUnitOfMeasureEquivalentTransfer(userVisit, unitOfMeasureEquivalent))
        );
        
        return unitOfMeasureEquivalentTransfers;
    }
    
    public List<UnitOfMeasureEquivalentTransfer> getUnitOfMeasureEquivalentTransfersByUnitOfMeasureKind(UserVisit userVisit,
            UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureEquivalentTransfers(userVisit, getUnitOfMeasureEquivalentsByUnitOfMeasureKind(unitOfMeasureKind));
    }
    
    public List<UnitOfMeasureEquivalentTransfer> getUnitOfMeasureEquivalentTransfersByFromUnitOfMeasureType(UserVisit userVisit,
            UnitOfMeasureType fromUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentTransfers(userVisit, getUnitOfMeasureEquivalentsByFromUnitOfMeasureType(fromUnitOfMeasureType));
    }
    
    public List<UnitOfMeasureEquivalentTransfer> getUnitOfMeasureEquivalentTransfersByToUnitOfMeasureType(UserVisit userVisit,
            UnitOfMeasureType toUnitOfMeasureType) {
        return getUnitOfMeasureEquivalentTransfers(userVisit, getUnitOfMeasureEquivalentsByToUnitOfMeasureType(toUnitOfMeasureType));
    }
    
    public void updateUnitOfMeasureEquivalentFromValue(UnitOfMeasureEquivalentValue unitOfMeasureEquivalentValue, BasePK updatedBy) {
        if(unitOfMeasureEquivalentValue.hasBeenModified()) {
            var unitOfMeasureEquivalent = UnitOfMeasureEquivalentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureEquivalentValue.getPrimaryKey());
            
            unitOfMeasureEquivalent.setThruTime(session.START_TIME_LONG);
            unitOfMeasureEquivalent.store();

            var fromUnitOfMeasureTypePK = unitOfMeasureEquivalent.getFromUnitOfMeasureTypePK(); // Not updated
            var toUnitOfMeasureTypePK = unitOfMeasureEquivalent.getToUnitOfMeasureTypePK(); // Not updated
            var toQuantity = unitOfMeasureEquivalentValue.getToQuantity();
            
            unitOfMeasureEquivalent = UnitOfMeasureEquivalentFactory.getInstance().create(fromUnitOfMeasureTypePK,
                    toUnitOfMeasureTypePK, toQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(fromUnitOfMeasureTypePK, EventTypes.MODIFY, unitOfMeasureEquivalent.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureEquivalent(UnitOfMeasureEquivalent unitOfMeasureEquivalent, BasePK deletedBy) {
        unitOfMeasureEquivalent.setThruTime(session.START_TIME_LONG);
        unitOfMeasureEquivalent.store();
        
        sendEvent(unitOfMeasureEquivalent.getFromUnitOfMeasureTypePK(), EventTypes.MODIFY, unitOfMeasureEquivalent.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureEquivalentsByFromUnitOfMeasureType(UnitOfMeasureType fromUnitOfMeasureType, BasePK deletedBy) {
        var unitOfMeasureEquivalents = getUnitOfMeasureEquivalentsByFromUnitOfMeasureTypeForUpdate(fromUnitOfMeasureType);
        
        unitOfMeasureEquivalents.forEach((unitOfMeasureEquivalent) -> 
                deleteUnitOfMeasureEquivalent(unitOfMeasureEquivalent, deletedBy)
        );
    }
    
    public void deleteUnitOfMeasureEquivalentsByToUnitOfMeasureType(UnitOfMeasureType toUnitOfMeasureType, BasePK deletedBy) {
        var unitOfMeasureEquivalents = getUnitOfMeasureEquivalentsByToUnitOfMeasureTypeForUpdate(toUnitOfMeasureType);
        
        unitOfMeasureEquivalents.forEach((unitOfMeasureEquivalent) -> 
                deleteUnitOfMeasureEquivalent(unitOfMeasureEquivalent, deletedBy)
        );
    }
    
    public void deleteUnitOfMeasureEquivalentsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteUnitOfMeasureEquivalentsByFromUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureEquivalentsByToUnitOfMeasureType(unitOfMeasureType, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Types
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureKindUseType createUnitOfMeasureKindUseType(String unitOfMeasureKindUseTypeName, Boolean allowMultiple, Boolean allowFractionDigits,
            Boolean isDefault, Integer sortOrder) {
        return UnitOfMeasureKindUseTypeFactory.getInstance().create(unitOfMeasureKindUseTypeName, allowMultiple, allowFractionDigits, isDefault, sortOrder);
    }

    public long countUnitOfMeasureKindUseType() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM unitofmeasurekindusetypes
                """);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UnitOfMeasureKindUseType */
    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UnitOfMeasureKindUseTypePK(entityInstance.getEntityUniqueId());

        return UnitOfMeasureKindUseTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getUnitOfMeasureKindUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUnitOfMeasureKindUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public List<UnitOfMeasureKindUseType> getUnitOfMeasureKindUseTypes() {
        var ps = UnitOfMeasureKindUseTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM unitofmeasurekindusetypes " +
                "ORDER BY uomkut_sortorder, uomkut_unitofmeasurekindusetypename " +
                "_LIMIT_");
        
        return UnitOfMeasureKindUseTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByName(String unitOfMeasureKindUseTypeName) {
        UnitOfMeasureKindUseType unitOfMeasureKindUseType;
        
        try {
            var ps = UnitOfMeasureKindUseTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM unitofmeasurekindusetypes " +
                    "WHERE uomkut_unitofmeasurekindusetypename = ?");
            
            ps.setString(1, unitOfMeasureKindUseTypeName);
            
            unitOfMeasureKindUseType = UnitOfMeasureKindUseTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureKindUseType;
    }
    
    public UnitOfMeasureKindUseTypeChoicesBean getUnitOfMeasureKindUseTypeChoices(String defaultUnitOfMeasureKindUseTypeChoice,
            Language language, boolean allowNullChoice) {
        var unitOfMeasureKindUseTypes = getUnitOfMeasureKindUseTypes();
        var size = unitOfMeasureKindUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUnitOfMeasureKindUseTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var unitOfMeasureKindUseType : unitOfMeasureKindUseTypes) {
            var label = getBestUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, language);
            var value = unitOfMeasureKindUseType.getUnitOfMeasureKindUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultUnitOfMeasureKindUseTypeChoice != null && defaultUnitOfMeasureKindUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && unitOfMeasureKindUseType.getIsDefault()))
                defaultValue = value;
        }
        
        return new UnitOfMeasureKindUseTypeChoicesBean(labels, values, defaultValue);
    }
    
    public UnitOfMeasureKindUseTypeTransfer getUnitOfMeasureKindUseTypeTransfer(UserVisit userVisit,
            UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return uomTransferCaches.getUnitOfMeasureKindUseTypeTransferCache().getUnitOfMeasureKindUseTypeTransfer(userVisit, unitOfMeasureKindUseType);
    }
    
    public List<UnitOfMeasureKindUseTypeTransfer> getUnitOfMeasureKindUseTypeTransfers(UserVisit userVisit, Collection<UnitOfMeasureKindUseType> unitOfMeasureKindUseTypes) {
        List<UnitOfMeasureKindUseTypeTransfer> unitOfMeasureKindUseTypeTransfers = new ArrayList<>(unitOfMeasureKindUseTypes.size());
        var unitOfMeasureKindUseTypeTransferCache = uomTransferCaches.getUnitOfMeasureKindUseTypeTransferCache();
        
        unitOfMeasureKindUseTypes.forEach((unitOfMeasureKindUseType) ->
                unitOfMeasureKindUseTypeTransfers.add(unitOfMeasureKindUseTypeTransferCache.getUnitOfMeasureKindUseTypeTransfer(userVisit, unitOfMeasureKindUseType))
        );
        
        return unitOfMeasureKindUseTypeTransfers;
    }
    
    public List<UnitOfMeasureKindUseTypeTransfer> getUnitOfMeasureKindUseTypeTransfers(UserVisit userVisit) {
        return getUnitOfMeasureKindUseTypeTransfers(userVisit, getUnitOfMeasureKindUseTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureKindUseTypeDescription createUnitOfMeasureKindUseTypeDescription(UnitOfMeasureKindUseType unitOfMeasureKindUseType, Language language, String description) {
        return UnitOfMeasureKindUseTypeDescriptionFactory.getInstance().create(unitOfMeasureKindUseType, language, description);
    }
    
    public UnitOfMeasureKindUseTypeDescription getUnitOfMeasureKindUseTypeDescription(UnitOfMeasureKindUseType unitOfMeasureKindUseType, Language language) {
        UnitOfMeasureKindUseTypeDescription unitOfMeasureKindUseTypeDescription;
        
        try {
            var ps = UnitOfMeasureKindUseTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM unitofmeasurekindusetypedescriptions " +
                    "WHERE uomkutd_uomkut_unitofmeasurekindusetypeid = ? AND uomkutd_lang_languageid = ?");
            
            ps.setLong(1, unitOfMeasureKindUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            unitOfMeasureKindUseTypeDescription = UnitOfMeasureKindUseTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureKindUseTypeDescription;
    }
    
    public String getBestUnitOfMeasureKindUseTypeDescription(UnitOfMeasureKindUseType unitOfMeasureKindUseType, Language language) {
        String description;
        var unitOfMeasureKindUseTypeDescription = getUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, language);
        
        if(unitOfMeasureKindUseTypeDescription == null && !language.getIsDefault()) {
            unitOfMeasureKindUseTypeDescription = getUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, partyControl.getDefaultLanguage());
        }
        
        if(unitOfMeasureKindUseTypeDescription == null) {
            description = unitOfMeasureKindUseType.getUnitOfMeasureKindUseTypeName();
        } else {
            description = unitOfMeasureKindUseTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Uses
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureKindUse createUnitOfMeasureKindUse(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            UnitOfMeasureKind unitOfMeasureKind, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultUnitOfMeasureKindUse = getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType);
        var defaultFound = defaultUnitOfMeasureKindUse != null;
        
        if(defaultFound && isDefault) {
            var defaultUnitOfMeasureKindUseValue = getDefaultUnitOfMeasureKindUseValueForUpdate(unitOfMeasureKindUseType);
            
            defaultUnitOfMeasureKindUseValue.setIsDefault(false);
            updateUnitOfMeasureKindUseFromValue(defaultUnitOfMeasureKindUseValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var unitOfMeasureKindUse = UnitOfMeasureKindUseFactory.getInstance().create(session,
                unitOfMeasureKindUseType, unitOfMeasureKind, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(unitOfMeasureKind.getPrimaryKey(), EventTypes.MODIFY, unitOfMeasureKindUse.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return unitOfMeasureKindUse;
    }
    
    public long countUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM unitofmeasurekinduses " +
                "WHERE uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_thrutime = ?",
                unitOfMeasureKindUseType, Session.MAX_TIME);
    }
    
    public long countUnitOfMeasureKindUsesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM unitofmeasurekinduses " +
                "WHERE uomku_uomk_unitofmeasurekindid = ? AND uomku_thrutime = ?",
                unitOfMeasureKind, Session.MAX_TIME);
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UnitOfMeasureKindUse */
    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UnitOfMeasureKindUsePK(entityInstance.getEntityUniqueId());

        return UnitOfMeasureKindUseFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByEntityInstance(EntityInstance entityInstance) {
        return getUnitOfMeasureKindUseByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUnitOfMeasureKindUseByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private UnitOfMeasureKindUse getUnitOfMeasureKindUse(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            UnitOfMeasureKind unitOfMeasureKind, EntityPermission entityPermission) {
        UnitOfMeasureKindUse unitOfMeasureKindUse;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses " +
                        "WHERE uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_uomk_unitofmeasurekindid = ? " +
                        "AND uomku_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses " +
                        "WHERE uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_uomk_unitofmeasurekindid = ? " +
                        "AND uomku_thrutime = ? " +
                        "FOR UPDATE";
            }
            var ps = UnitOfMeasureKindUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureKindUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureKind.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            unitOfMeasureKindUse = UnitOfMeasureKindUseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureKindUse;
    }
    
    public UnitOfMeasureKindUse getUnitOfMeasureKindUse(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUse(unitOfMeasureKindUseType, unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKindUse getUnitOfMeasureKindUseForUpdate(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUse(unitOfMeasureKindUseType, unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindUseValue getUnitOfMeasureKindUseValueForUpdate(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUseForUpdate(unitOfMeasureKindUseType, unitOfMeasureKind).getUnitOfMeasureKindUseValue().clone();
    }
    
    private UnitOfMeasureKindUse getDefaultUnitOfMeasureKindUse(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            EntityPermission entityPermission) {
        UnitOfMeasureKindUse unitOfMeasureKindUse;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses " +
                        "WHERE uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_isdefault = 1 AND uomku_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses " +
                        "WHERE uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_isdefault = 1 AND uomku_thrutime = ? " +
                        "FOR UPDATE";
            }
            var ps = UnitOfMeasureKindUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureKindUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            unitOfMeasureKindUse = UnitOfMeasureKindUseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureKindUse;
    }
    
    public UnitOfMeasureKindUse getDefaultUnitOfMeasureKindUse(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKindUse getDefaultUnitOfMeasureKindUseForUpdate(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindUseValue getDefaultUnitOfMeasureKindUseValueForUpdate(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getDefaultUnitOfMeasureKindUseForUpdate(unitOfMeasureKindUseType).getUnitOfMeasureKindUseValue().clone();
    }
    
    private List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind,
            EntityPermission entityPermission) {
        List<UnitOfMeasureKindUse> unitOfMeasureKindUses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses, unitofmeasurekindusetypes " +
                        "WHERE uomku_uomk_unitofmeasurekindid = ? AND uomku_thrutime = ? " +
                        "AND uomku_uomkut_unitofmeasurekindusetypeid = uomkut_unitofmeasurekindusetypeid " +
                        "ORDER BY uomku_sortorder, uomkut_sortorder, uomkut_unitofmeasurekindusetypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses " +
                        "WHERE uomku_uomk_unitofmeasurekindid = ? AND uomku_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureKindUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureKind.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            unitOfMeasureKindUses = UnitOfMeasureKindUseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureKindUses;
    }
    
    public List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKindForUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind, EntityPermission.READ_WRITE);
    }
    
    /** Get a List of UnitOfMeasureKindUses when the UnitOfMeasureKindUseType is allowed to be used by multiple
     * UnitOfMeasureKinds.
     */
    private List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            EntityPermission entityPermission) {
        List<UnitOfMeasureKindUse> unitOfMeasureKindUses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses, unitofmeasurekinddetails " +
                        "WHERE uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_thrutime = ? " +
                        "AND uomku_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "ORDER BY uomku_sortorder, uomkdt_sortorder, uomkdt_unitofmeasurekindname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses, unitofmeasurekinddetails " +
                        "WHERE uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_thrutime = ? " +
                        "AND uomku_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = UnitOfMeasureKindUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, unitOfMeasureKindUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            ps.setLong(3, Session.MAX_TIME);
            
            unitOfMeasureKindUses = UnitOfMeasureKindUseFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return unitOfMeasureKindUses;
    }
    
    public List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType, EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureKindUse> getUnitOfMeasureKindUsesByUnitOfMeasureKindUseTypeForUpdate(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType, EntityPermission.READ_WRITE);
    }
    
    /** Get a single UnitOfMeasureKindUse when the UnitOfMeasureKindUseType is allowed to be used by only one
     * UnitOfMeasureKind.
     */
    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        UnitOfMeasureKindUse unitOfMeasureKindUse;
        
        if(!unitOfMeasureKindUseType.getAllowMultiple()) {
            try {
                var ps = UnitOfMeasureKindUseFactory.getInstance().prepareStatement(
                        "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses " +
                        "WHERE uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_thrutime = ?");
                
                ps.setLong(1, unitOfMeasureKindUseType.getPrimaryKey().getEntityId());
                ps.setLong(2, Session.MAX_TIME);
                
                unitOfMeasureKindUse = UnitOfMeasureKindUseFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } else {
            throw new IllegalArgumentException();
        }
        
        return unitOfMeasureKindUse;
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        var unitOfMeasureKindUse = getUnitOfMeasureKindUseByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        
        return unitOfMeasureKindUse == null? null: unitOfMeasureKindUse.getUnitOfMeasureKind();
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(String unitOfMeasureKindUseTypeName) {
        UnitOfMeasureKindUse unitOfMeasureKindUse = null;
        var unitOfMeasureKindUseType = getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);
        
        if(unitOfMeasureKindUseType != null) {
            unitOfMeasureKindUse = getUnitOfMeasureKindUseByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        }
        
        return unitOfMeasureKindUse == null? null: unitOfMeasureKindUse.getUnitOfMeasureKind();
    }
    
    public UnitOfMeasureKindUseTransfer getUnitOfMeasureKindUseTransfer(UserVisit userVisit, UnitOfMeasureKindUse unitOfMeasureKindUse) {
        return unitOfMeasureKindUse == null? null: uomTransferCaches.getUnitOfMeasureKindUseTransferCache().getUnitOfMeasureKindUseTransfer(userVisit, unitOfMeasureKindUse);
    }
    
    public List<UnitOfMeasureKindUseTransfer> getUnitOfMeasureKindUseTransfers(final UserVisit userVisit,
            final Collection<UnitOfMeasureKindUse> unitOfMeasureKindUses) {
        List<UnitOfMeasureKindUseTransfer> unitOfMeasureKindUseTransfers = new ArrayList<>(unitOfMeasureKindUses.size());
        var unitOfMeasureKindUseTransferCache = uomTransferCaches.getUnitOfMeasureKindUseTransferCache();
        
        unitOfMeasureKindUses.forEach((unitOfMeasureKindUse) ->
                unitOfMeasureKindUseTransfers.add(unitOfMeasureKindUseTransferCache.getUnitOfMeasureKindUseTransfer(userVisit, unitOfMeasureKindUse))
        );
        
        return unitOfMeasureKindUseTransfers;
    }
    
    public List<UnitOfMeasureKindUseTransfer> getUnitOfMeasureKindUseTransfersByUnitOfMeasureKind(UserVisit userVisit,
            UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureKindUseTransfers(userVisit, getUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind));
    }
    
    public List<UnitOfMeasureKindUseTransfer> getUnitOfMeasureKindUseTransfersByUnitOfMeasureKindUseType(UserVisit userVisit,
            UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getUnitOfMeasureKindUseTransfers(userVisit, getUnitOfMeasureKindUsesByUnitOfMeasureKindUseType(unitOfMeasureKindUseType));
    }
    
    private void updateUnitOfMeasureKindUseFromValue(UnitOfMeasureKindUseValue unitOfMeasureKindUseValue, boolean checkDefault,
            BasePK updatedBy) {
        if(unitOfMeasureKindUseValue.hasBeenModified()) {
            var unitOfMeasureKindUse = UnitOfMeasureKindUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureKindUseValue.getPrimaryKey());
            
            unitOfMeasureKindUse.setThruTime(session.START_TIME_LONG);
            unitOfMeasureKindUse.store();

            var unitOfMeasureKindUseTypePK = unitOfMeasureKindUse.getUnitOfMeasureKindUseTypePK();
            var unitOfMeasureKindUseType = unitOfMeasureKindUse.getUnitOfMeasureKindUseType();
            var unitOfMeasureKindPK = unitOfMeasureKindUse.getUnitOfMeasureKindPK();
            var isDefault = unitOfMeasureKindUseValue.getIsDefault();
            var sortOrder = unitOfMeasureKindUseValue.getSortOrder();
            
            if(checkDefault) {
                var defaultUnitOfMeasureKindUse = getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType);
                var defaultFound = defaultUnitOfMeasureKindUse != null && !defaultUnitOfMeasureKindUse.equals(unitOfMeasureKindUse);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultUnitOfMeasureKindUseValue = getDefaultUnitOfMeasureKindUseValueForUpdate(unitOfMeasureKindUseType);
                    
                    defaultUnitOfMeasureKindUseValue.setIsDefault(false);
                    updateUnitOfMeasureKindUseFromValue(defaultUnitOfMeasureKindUseValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            unitOfMeasureKindUse = UnitOfMeasureKindUseFactory.getInstance().create(unitOfMeasureKindUseTypePK,
                    unitOfMeasureKindPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(unitOfMeasureKindPK, EventTypes.MODIFY, unitOfMeasureKindUse.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    /** Given a UnitOfMeasureKindUseValue, update only the isDefault property.
     */
    public void updateUnitOfMeasureKindUseFromValue(UnitOfMeasureKindUseValue unitOfMeasureKindUseValue, BasePK updatedBy) {
        updateUnitOfMeasureKindUseFromValue(unitOfMeasureKindUseValue, true, updatedBy);
    }
    
    public void deleteUnitOfMeasureKindUse(UnitOfMeasureKindUse unitOfMeasureKindUse, BasePK deletedBy) {
        unitOfMeasureKindUse.setThruTime(session.START_TIME_LONG);
        unitOfMeasureKindUse.store();
        
        // Check for default, and pick one if necessary
        var unitOfMeasureKindUseType = unitOfMeasureKindUse.getUnitOfMeasureKindUseType();
        var defaultUnitOfMeasureKindUse = getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType);
        if(defaultUnitOfMeasureKindUse == null) {
            var unitOfMeasureKindUses = getUnitOfMeasureKindUsesByUnitOfMeasureKindUseTypeForUpdate(unitOfMeasureKindUseType);
            
            if(!unitOfMeasureKindUses.isEmpty()) {
                var iter = unitOfMeasureKindUses.iterator();
                if(iter.hasNext()) {
                    defaultUnitOfMeasureKindUse = (UnitOfMeasureKindUse)iter.next();
                }
                var unitOfMeasureKindUseValue = defaultUnitOfMeasureKindUse.getUnitOfMeasureKindUseValue().clone();
                
                unitOfMeasureKindUseValue.setIsDefault(true);
                updateUnitOfMeasureKindUseFromValue(unitOfMeasureKindUseValue, false, deletedBy);
            }
        }
        
        sendEvent(unitOfMeasureKindUse.getUnitOfMeasureKindPK(), EventTypes.MODIFY, unitOfMeasureKindUse.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUnitOfMeasureKindUseByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        var unitOfMeasureKindUses = getUnitOfMeasureKindUsesByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
        
        unitOfMeasureKindUses.forEach((unitOfMeasureKindUse) -> 
                deleteUnitOfMeasureKindUse(unitOfMeasureKindUse, deletedBy)
        );
    }
    
}

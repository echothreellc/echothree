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
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureEquivalentTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureKindDescriptionTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureKindTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureKindUseTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureKindUseTypeTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureTypeDescriptionTransferCache;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureTypeTransferCache;
import com.echothree.model.control.uom.server.transfer.UomTransferCaches;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.accounting.common.pk.SymbolPositionPK;
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
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDetail;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseTypeDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDescription;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDetail;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UomControl
        extends BaseModelControl {
    
    /** Creates a new instance of UomControl */
    public UomControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Unit of Measure Transfer Caches
    // --------------------------------------------------------------------------------
    
    private UomTransferCaches uomTransferCaches;
    
    public UomTransferCaches getUomTransferCaches(UserVisit userVisit) {
        if(uomTransferCaches == null) {
            uomTransferCaches = new UomTransferCaches(userVisit, this);
        }
        
        return uomTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kinds
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureKind createUnitOfMeasureKind(String unitOfMeasureKindName, Integer fractionDigits, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        UnitOfMeasureKind defaultUnitOfMeasureKind = getDefaultUnitOfMeasureKind();
        boolean defaultFound = defaultUnitOfMeasureKind != null;
        
        if(defaultFound && isDefault) {
            UnitOfMeasureKindDetailValue defaultUnitOfMeasureKindDetailValue = getDefaultUnitOfMeasureKindDetailValueForUpdate();
            
            defaultUnitOfMeasureKindDetailValue.setIsDefault(Boolean.FALSE);
            updateUnitOfMeasureKindFromValue(defaultUnitOfMeasureKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        UnitOfMeasureKind unitOfMeasureKind = UnitOfMeasureKindFactory.getInstance().create();
        UnitOfMeasureKindDetail unitOfMeasureKindDetail = UnitOfMeasureKindDetailFactory.getInstance().create(session, unitOfMeasureKind, unitOfMeasureKindName,
                fractionDigits, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        unitOfMeasureKind = UnitOfMeasureKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, unitOfMeasureKind.getPrimaryKey());
        unitOfMeasureKind.setActiveDetail(unitOfMeasureKindDetail);
        unitOfMeasureKind.setLastDetail(unitOfMeasureKindDetail);
        unitOfMeasureKind.store();
        
        sendEventUsingNames(unitOfMeasureKind.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return unitOfMeasureKind;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.UnitOfMeasureKind */
    public UnitOfMeasureKind getUnitOfMeasureKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        UnitOfMeasureKindPK pk = new UnitOfMeasureKindPK(entityInstance.getEntityUniqueId());
        UnitOfMeasureKind unitOfMeasureKind = UnitOfMeasureKindFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return unitOfMeasureKind;
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
                    "ORDER BY uomkdt_sortorder, uomkdt_unitofmeasurekindname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM unitofmeasurekinds, unitofmeasurekinddetails " +
                    "WHERE uomk_activedetailid = uomkdt_unitofmeasurekinddetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = UnitOfMeasureKindFactory.getInstance().prepareStatement(query);
        
        return UnitOfMeasureKindFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<UnitOfMeasureKind> getUnitOfMeasureKinds() {
        return getUnitOfMeasureKinds(EntityPermission.READ_ONLY);
    }
    
    public List<UnitOfMeasureKind> getUnitOfMeasureKindsForUpdate() {
        return getUnitOfMeasureKinds(EntityPermission.READ_WRITE);
    }
    
    public List<UnitOfMeasureKind> getUnitOfMeasureKindsByUnitOfMeasureKindUseType(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        List<UnitOfMeasureKind> unitOfMeasureKinds = null;
        
        try {
            PreparedStatement ps = UnitOfMeasureKindFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM unitofmeasurekinds, unitofmeasurekinddetails, unitofmeasurekinduses " +
                    "WHERE uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                    "AND uomk_unitofmeasurekindid = uomku_uomk_unitofmeasurekindid AND uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_thrutime = ? " +
                    "ORDER BY uomkdt_sortorder, uomkdt_unitofmeasurekindname");
            
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
        
        PreparedStatement ps = UnitOfMeasureKindFactory.getInstance().prepareStatement(query);
        
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
            
            PreparedStatement ps = UnitOfMeasureKindFactory.getInstance().prepareStatement(query);
            
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
        return getUomTransferCaches(userVisit).getUnitOfMeasureKindTransferCache().getUnitOfMeasureKindTransfer(unitOfMeasureKind);
    }
    
    public List<UnitOfMeasureKindTransfer> getUnitOfMeasureKindTransfers(UserVisit userVisit, Collection<UnitOfMeasureKind> unitOfMeasureKinds) {
        List<UnitOfMeasureKindTransfer> unitOfMeasureKindTransfers = new ArrayList<>(unitOfMeasureKinds.size());
        UnitOfMeasureKindTransferCache unitOfMeasureKindTransferCache = getUomTransferCaches(userVisit).getUnitOfMeasureKindTransferCache();
        
        unitOfMeasureKinds.stream().forEach((unitOfMeasureKind) -> {
            unitOfMeasureKindTransfers.add(unitOfMeasureKindTransferCache.getUnitOfMeasureKindTransfer(unitOfMeasureKind));
        });
        
        return unitOfMeasureKindTransfers;
    }
    
    public List<UnitOfMeasureKindTransfer> getUnitOfMeasureKindTransfers(UserVisit userVisit) {
        return getUnitOfMeasureKindTransfers(userVisit, getUnitOfMeasureKinds());
    }
    
    public UnitOfMeasureKindChoicesBean getUnitOfMeasureKindChoices(String defaultUnitOfMeasureKindChoice, Language language,
            boolean allowNullChoice) {
        List<UnitOfMeasureKind> unitOfMeasureKinds = getUnitOfMeasureKinds();
        
        return getUnitOfMeasureKindChoices(defaultUnitOfMeasureKindChoice, language, allowNullChoice, unitOfMeasureKinds);
    }
    
    public UnitOfMeasureKindChoicesBean getUnitOfMeasureKindChoicesByUnitOfMeasureKindUseType(String defaultUnitOfMeasureKindChoice, Language language,
            boolean allowNullChoice, UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        List<UnitOfMeasureKind> unitOfMeasureKinds = getUnitOfMeasureKindsByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        
        return getUnitOfMeasureKindChoices(defaultUnitOfMeasureKindChoice, language, allowNullChoice, unitOfMeasureKinds);
    }
    
    private UnitOfMeasureKindChoicesBean getUnitOfMeasureKindChoices(final String defaultUnitOfMeasureKindChoice,
            final Language language, final boolean allowNullChoice, final List<UnitOfMeasureKind> unitOfMeasureKinds) {
        int size = unitOfMeasureKinds.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUnitOfMeasureKindChoice == null) {
                defaultValue = "";
            }
        }
        
        for(UnitOfMeasureKind unitOfMeasureKind: unitOfMeasureKinds) {
            UnitOfMeasureKindDetail unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetail();
            
            String label = getBestUnitOfMeasureKindDescription(unitOfMeasureKind, language);
            String value = unitOfMeasureKindDetail.getUnitOfMeasureKindName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultUnitOfMeasureKindChoice == null? false: defaultUnitOfMeasureKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && unitOfMeasureKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UnitOfMeasureKindChoicesBean(labels, values, defaultValue);
    }
    
    public UnitOfMeasureChoicesBean getUnitOfMeasureChoicesByUnitOfMeasureKindUseType(String defaultUnitOfMeasureChoice,
            Language language, UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        List<UnitOfMeasureKind> unitOfMeasureKinds = getUnitOfMeasureKindsByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        String defaultValue = null;
        Iterator<UnitOfMeasureKind> uomkIter = unitOfMeasureKinds.iterator();
        
        while(uomkIter.hasNext()) {
            UnitOfMeasureKind unitOfMeasureKind = uomkIter.next();
            String uomkDescription = getBestUnitOfMeasureKindDescription(unitOfMeasureKind, language);
            String uomkName = unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName();
            List<UnitOfMeasureType> unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
            Iterator<UnitOfMeasureType> uomtIter = unitOfMeasureTypes.iterator();
            
            if(uomkDescription == null) {
                uomkDescription = uomkName;
            }
            
            while(uomtIter.hasNext()) {
                UnitOfMeasureType unitOfMeasureType = uomtIter.next();
                String uomtDescription = getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, language);
                String uomtName = unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName();
                
                if(uomtDescription == null) {
                    uomtDescription = uomtName;
                }
                
                String label = new StringBuilder(uomkDescription).append(", ").append(uomtDescription).toString();
                String value = new StringBuilder(uomkName).append(':').append(uomtName).toString();
                
                labels.add(label == null? value: label);
                values.add(value);
                
                boolean usingDefaultChoice = defaultUnitOfMeasureChoice == null? false: defaultUnitOfMeasureChoice.equals(value);
                if(usingDefaultChoice || defaultValue == null)
                    defaultValue = value;
            }
        }
        
        return new UnitOfMeasureChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUnitOfMeasureKindFromValue(UnitOfMeasureKindDetailValue unitOfMeasureKindDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(unitOfMeasureKindDetailValue.hasBeenModified()) {
            UnitOfMeasureKind unitOfMeasureKind = UnitOfMeasureKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureKindDetailValue.getUnitOfMeasureKindPK());
            UnitOfMeasureKindDetail unitOfMeasureKindDetail = unitOfMeasureKind.getActiveDetailForUpdate();
            
            unitOfMeasureKindDetail.setThruTime(session.START_TIME_LONG);
            unitOfMeasureKindDetail.store();
            
            UnitOfMeasureKindPK unitOfMeasureKindPK = unitOfMeasureKindDetail.getUnitOfMeasureKindPK();
            String unitOfMeasureKindName = unitOfMeasureKindDetailValue.getUnitOfMeasureKindName();
            Integer fractionDigits = unitOfMeasureKindDetailValue.getFractionDigits();
            Boolean isDefault = unitOfMeasureKindDetailValue.getIsDefault();
            Integer sortOrder = unitOfMeasureKindDetailValue.getSortOrder();
            
            if(checkDefault) {
                UnitOfMeasureKind defaultUnitOfMeasureKind = getDefaultUnitOfMeasureKind();
                boolean defaultFound = defaultUnitOfMeasureKind != null && !defaultUnitOfMeasureKind.equals(unitOfMeasureKind);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    UnitOfMeasureKindDetailValue defaultUnitOfMeasureKindDetailValue = getDefaultUnitOfMeasureKindDetailValueForUpdate();
                    
                    defaultUnitOfMeasureKindDetailValue.setIsDefault(Boolean.FALSE);
                    updateUnitOfMeasureKindFromValue(defaultUnitOfMeasureKindDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            unitOfMeasureKindDetail = UnitOfMeasureKindDetailFactory.getInstance().create(unitOfMeasureKindPK, unitOfMeasureKindName, fractionDigits, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            unitOfMeasureKind.setActiveDetail(unitOfMeasureKindDetail);
            unitOfMeasureKind.setLastDetail(unitOfMeasureKindDetail);
            
            sendEventUsingNames(unitOfMeasureKindPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateUnitOfMeasureKindFromValue(UnitOfMeasureKindDetailValue unitOfMeasureKindDetailValue, BasePK updatedBy) {
        updateUnitOfMeasureKindFromValue(unitOfMeasureKindDetailValue, true, updatedBy);
    }
    
    public void deleteUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        deleteUnitOfMeasureKindUseByUnitOfMeasureKind(unitOfMeasureKind, deletedBy);
        deleteUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(unitOfMeasureKind, deletedBy);
        deleteUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind, deletedBy);
        
        UnitOfMeasureKindDetail unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetailForUpdate();
        unitOfMeasureKindDetail.setThruTime(session.START_TIME_LONG);
        unitOfMeasureKindDetail.store();
        unitOfMeasureKind.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        UnitOfMeasureKind defaultUnitOfMeasureKind = getDefaultUnitOfMeasureKind();
        if(defaultUnitOfMeasureKind == null) {
            List<UnitOfMeasureKind> unitOfMeasureKinds = getUnitOfMeasureKindsForUpdate();
            
            if(!unitOfMeasureKinds.isEmpty()) {
                Iterator<UnitOfMeasureKind> iter = unitOfMeasureKinds.iterator();
                if(iter.hasNext()) {
                    defaultUnitOfMeasureKind = (UnitOfMeasureKind)iter.next();
                }
                UnitOfMeasureKindDetailValue unitOfMeasureKindDetailValue = defaultUnitOfMeasureKind.getLastDetailForUpdate().getUnitOfMeasureKindDetailValue().clone();
                
                unitOfMeasureKindDetailValue.setIsDefault(Boolean.TRUE);
                updateUnitOfMeasureKindFromValue(unitOfMeasureKindDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(unitOfMeasureKind.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Kind Descriptions
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureKindDescription createUnitOfMeasureKindDescription(UnitOfMeasureKind unitOfMeasureKind, Language language, String description, BasePK createdBy) {
        UnitOfMeasureKindDescription unitOfMeasureKindDescription = UnitOfMeasureKindDescriptionFactory.getInstance().create(unitOfMeasureKind, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(unitOfMeasureKind.getPrimaryKey(), EventTypes.MODIFY.name(), unitOfMeasureKindDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = UnitOfMeasureKindDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<UnitOfMeasureKindDescription> unitOfMeasureKindDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinddescriptions, languages " +
                        "WHERE uomkd_uomk_unitofmeasurekindid = ? AND uomkd_thrutime = ? AND uomkd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinddescriptions " +
                        "WHERE uomkd_uomk_unitofmeasurekindid = ? AND uomkd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UnitOfMeasureKindDescriptionFactory.getInstance().prepareStatement(query);
            
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
        UnitOfMeasureKindDescription unitOfMeasureKindDescription = getUnitOfMeasureKindDescription(unitOfMeasureKind, language);
        
        if(unitOfMeasureKindDescription == null && !language.getIsDefault()) {
            unitOfMeasureKindDescription = getUnitOfMeasureKindDescription(unitOfMeasureKind, getPartyControl().getDefaultLanguage());
        }
        
        if(unitOfMeasureKindDescription == null) {
            description = unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName();
        } else {
            description = unitOfMeasureKindDescription.getDescription();
        }
        
        return description;
    }
    
    public UnitOfMeasureKindDescriptionTransfer getUnitOfMeasureKindDescriptionTransfer(UserVisit userVisit, UnitOfMeasureKindDescription unitOfMeasureKindDescription) {
        return getUomTransferCaches(userVisit).getUnitOfMeasureKindDescriptionTransferCache().getUnitOfMeasureKindDescriptionTransfer(unitOfMeasureKindDescription);
    }
    
    public List<UnitOfMeasureKindDescriptionTransfer> getUnitOfMeasureKindDescriptionTransfers(UserVisit userVisit, UnitOfMeasureKind unitOfMeasureKind) {
        List<UnitOfMeasureKindDescription> unitOfMeasureKindDescriptions = getUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(unitOfMeasureKind);
        List<UnitOfMeasureKindDescriptionTransfer> unitOfMeasureKindDescriptionTransfers = new ArrayList<>(unitOfMeasureKindDescriptions.size());
        UnitOfMeasureKindDescriptionTransferCache unitOfMeasureKindDescriptionTransferCache = getUomTransferCaches(userVisit).getUnitOfMeasureKindDescriptionTransferCache();
        
        unitOfMeasureKindDescriptions.stream().forEach((unitOfMeasureKindDescription) -> {
            unitOfMeasureKindDescriptionTransfers.add(unitOfMeasureKindDescriptionTransferCache.getUnitOfMeasureKindDescriptionTransfer(unitOfMeasureKindDescription));
        });
        
        return unitOfMeasureKindDescriptionTransfers;
    }
    
    public void updateUnitOfMeasureKindDescriptionFromValue(UnitOfMeasureKindDescriptionValue unitOfMeasureKindDescriptionValue, BasePK updatedBy) {
        if(unitOfMeasureKindDescriptionValue.hasBeenModified()) {
            UnitOfMeasureKindDescription unitOfMeasureKindDescription = UnitOfMeasureKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureKindDescriptionValue.getPrimaryKey());
            
            unitOfMeasureKindDescription.setThruTime(session.START_TIME_LONG);
            unitOfMeasureKindDescription.store();
            
            UnitOfMeasureKind unitOfMeasureKind = unitOfMeasureKindDescription.getUnitOfMeasureKind();
            Language language = unitOfMeasureKindDescription.getLanguage();
            String description = unitOfMeasureKindDescriptionValue.getDescription();
            
            unitOfMeasureKindDescription = UnitOfMeasureKindDescriptionFactory.getInstance().create(unitOfMeasureKind,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(unitOfMeasureKind.getPrimaryKey(), EventTypes.MODIFY.name(), unitOfMeasureKindDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureKindDescription(UnitOfMeasureKindDescription unitOfMeasureKindDescription, BasePK deletedBy) {
        unitOfMeasureKindDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(unitOfMeasureKindDescription.getUnitOfMeasureKindPK(), EventTypes.MODIFY.name(), unitOfMeasureKindDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteUnitOfMeasureKindDescriptionsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        List<UnitOfMeasureKindDescription> unitOfMeasureKindDescriptions = getUnitOfMeasureKindDescriptionsByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
        
        unitOfMeasureKindDescriptions.stream().forEach((unitOfMeasureKindDescription) -> {
            deleteUnitOfMeasureKindDescription(unitOfMeasureKindDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Types
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureType createUnitOfMeasureType(UnitOfMeasureKind unitOfMeasureKind, String unitOfMeasureTypeName,
            SymbolPosition symbolPosition, Boolean suppressSymbolSeparator, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        UnitOfMeasureType defaultUnitOfMeasureType = getDefaultUnitOfMeasureType(unitOfMeasureKind);
        boolean defaultFound = defaultUnitOfMeasureType != null;
        
        if(defaultFound && isDefault) {
            UnitOfMeasureTypeDetailValue defaultUnitOfMeasureTypeDetailValue = getDefaultUnitOfMeasureTypeDetailValueForUpdate(unitOfMeasureKind);
            
            defaultUnitOfMeasureTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateUnitOfMeasureTypeFromValue(defaultUnitOfMeasureTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        UnitOfMeasureType unitOfMeasureType = UnitOfMeasureTypeFactory.getInstance().create();
        UnitOfMeasureTypeDetail unitOfMeasureTypeDetail = UnitOfMeasureTypeDetailFactory.getInstance().create(unitOfMeasureType,
                unitOfMeasureKind, unitOfMeasureTypeName, symbolPosition, suppressSymbolSeparator, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        unitOfMeasureType = UnitOfMeasureTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, unitOfMeasureType.getPrimaryKey());
        unitOfMeasureType.setActiveDetail(unitOfMeasureTypeDetail);
        unitOfMeasureType.setLastDetail(unitOfMeasureTypeDetail);
        unitOfMeasureType.store();
        
        sendEventUsingNames(unitOfMeasureType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return unitOfMeasureType;
    }
    
    public long countUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM unitofmeasuretypes, unitofmeasuretypedetails " +
                "WHERE uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_uomk_unitofmeasurekindid = ?",
                unitOfMeasureKind);
    }
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.UnitOfMeasureType */
    public UnitOfMeasureType getUnitOfMeasureTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        UnitOfMeasureTypePK pk = new UnitOfMeasureTypePK(entityInstance.getEntityUniqueId());
        UnitOfMeasureType unitOfMeasureType = UnitOfMeasureTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return unitOfMeasureType;
    }

    public UnitOfMeasureType getUnitOfMeasureTypeByEntityInstance(EntityInstance entityInstance) {
        return getUnitOfMeasureTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UnitOfMeasureType getUnitOfMeasureTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUnitOfMeasureTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<UnitOfMeasureType> getUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, EntityPermission entityPermission) {
        List<UnitOfMeasureType> unitOfMeasureTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_uomk_unitofmeasurekindid = ? " +
                        "AND uomtdt_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypes, unitofmeasuretypedetails " +
                        "WHERE uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_uomk_unitofmeasurekindid = ? " +
                        "AND uomtdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = UnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = UnitOfMeasureTypeFactory.getInstance().prepareStatement(query);
            
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
        return getUomTransferCaches(userVisit).getUnitOfMeasureTypeTransferCache().getUnitOfMeasureTypeTransfer(unitOfMeasureType);
    }
    
    public List<UnitOfMeasureTypeTransfer> getUnitOfMeasureTypeTransfers(UserVisit userVisit, Collection<UnitOfMeasureType> unitOfMeasureTypes) {
        List<UnitOfMeasureTypeTransfer> unitOfMeasureTypeTransfers = new ArrayList<>(unitOfMeasureTypes.size());
        UnitOfMeasureTypeTransferCache unitOfMeasureTypeTransferCache = getUomTransferCaches(userVisit).getUnitOfMeasureTypeTransferCache();
        
        unitOfMeasureTypes.stream().forEach((unitOfMeasureType) -> {
            unitOfMeasureTypeTransfers.add(unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(unitOfMeasureType));
        });
        
        return unitOfMeasureTypeTransfers;
    }
    
    public List<UnitOfMeasureTypeTransfer> getUnitOfMeasureTypeTransfersByUnitOfMeasureKind(UserVisit userVisit, UnitOfMeasureKind unitOfMeasureKind) {
        return getUnitOfMeasureTypeTransfers(userVisit, getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind));
    }
    
    public UnitOfMeasureTypeChoicesBean getUnitOfMeasureTypeChoices(String defaultUnitOfMeasureTypeChoice, Language language,
            boolean allowNullChoice, UnitOfMeasureKind unitOfMeasureKind) {
        List<UnitOfMeasureType> unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKind(unitOfMeasureKind);
        int size = unitOfMeasureTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUnitOfMeasureTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(UnitOfMeasureType unitOfMeasureType: unitOfMeasureTypes) {
            UnitOfMeasureTypeDetail unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetail();
            
            String label = getBestSingularUnitOfMeasureTypeDescription(unitOfMeasureType, language);
            String value = unitOfMeasureTypeDetail.getUnitOfMeasureTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultUnitOfMeasureTypeChoice == null? false: defaultUnitOfMeasureTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && unitOfMeasureTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UnitOfMeasureTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUnitOfMeasureTypeFromValue(UnitOfMeasureTypeDetailValue unitOfMeasureTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(unitOfMeasureTypeDetailValue.hasBeenModified()) {
            UnitOfMeasureType unitOfMeasureType = UnitOfMeasureTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeDetailValue.getUnitOfMeasureTypePK());
            UnitOfMeasureTypeDetail unitOfMeasureTypeDetail = unitOfMeasureType.getActiveDetailForUpdate();
            
            unitOfMeasureTypeDetail.setThruTime(session.START_TIME_LONG);
            unitOfMeasureTypeDetail.store();
            
            UnitOfMeasureTypePK unitOfMeasureTypePK = unitOfMeasureTypeDetail.getUnitOfMeasureTypePK();
            UnitOfMeasureKindPK unitOfMeasureKindPK = unitOfMeasureTypeDetail.getUnitOfMeasureKindPK();
            UnitOfMeasureKind unitOfMeasureKind = unitOfMeasureTypeDetail.getUnitOfMeasureKind();
            String unitOfMeasureTypeName = unitOfMeasureTypeDetailValue.getUnitOfMeasureTypeName();
            SymbolPositionPK symbolPositionPK = unitOfMeasureTypeDetailValue.getSymbolPositionPK();
            Boolean suppressSymbolSeparator = unitOfMeasureTypeDetailValue.getSuppressSymbolSeparator();
            Boolean isDefault = unitOfMeasureTypeDetailValue.getIsDefault();
            Integer sortOrder = unitOfMeasureTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                UnitOfMeasureType defaultUnitOfMeasureType = getDefaultUnitOfMeasureType(unitOfMeasureKind);
                boolean defaultFound = defaultUnitOfMeasureType != null && !defaultUnitOfMeasureType.equals(unitOfMeasureType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    UnitOfMeasureTypeDetailValue defaultUnitOfMeasureTypeDetailValue = getDefaultUnitOfMeasureTypeDetailValueForUpdate(unitOfMeasureKind);
                    
                    defaultUnitOfMeasureTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateUnitOfMeasureTypeFromValue(defaultUnitOfMeasureTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            unitOfMeasureTypeDetail = UnitOfMeasureTypeDetailFactory.getInstance().create(unitOfMeasureTypePK, unitOfMeasureKindPK,
                    unitOfMeasureTypeName, symbolPositionPK, suppressSymbolSeparator, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            unitOfMeasureType.setActiveDetail(unitOfMeasureTypeDetail);
            unitOfMeasureType.setLastDetail(unitOfMeasureTypeDetail);
            
            sendEventUsingNames(unitOfMeasureTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateUnitOfMeasureTypeFromValue(UnitOfMeasureTypeDetailValue unitOfMeasureTypeDetailValue, BasePK updatedBy) {
        updateUnitOfMeasureTypeFromValue(unitOfMeasureTypeDetailValue, true, updatedBy);
    }
    
    public void deleteUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        var vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
        
        deleteUnitOfMeasureEquivalentsByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureTypeVolumeByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        deleteUnitOfMeasureTypeWeightByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        itemControl.deleteItemUnitOfMeasureTypesByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        vendorControl.deleteVendorItemCostsByUnitOfMeasureType(unitOfMeasureType, deletedBy);
        
        UnitOfMeasureTypeDetail unitOfMeasureTypeDetail = unitOfMeasureType.getLastDetailForUpdate();
        unitOfMeasureTypeDetail.setThruTime(session.START_TIME_LONG);
        unitOfMeasureTypeDetail.store();
        unitOfMeasureType.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        UnitOfMeasureKind unitOfMeasureKind = unitOfMeasureTypeDetail.getUnitOfMeasureKind();
        UnitOfMeasureType defaultUnitOfMeasureType = getDefaultUnitOfMeasureType(unitOfMeasureKind);
        if(defaultUnitOfMeasureType == null) {
            List<UnitOfMeasureType> unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
            
            if(!unitOfMeasureTypes.isEmpty()) {
                Iterator<UnitOfMeasureType> iter = unitOfMeasureTypes.iterator();
                if(iter.hasNext()) {
                    defaultUnitOfMeasureType = (UnitOfMeasureType)iter.next();
                }
                UnitOfMeasureTypeDetailValue unitOfMeasureTypeDetailValue = defaultUnitOfMeasureType.getLastDetailForUpdate().getUnitOfMeasureTypeDetailValue().clone();
                
                unitOfMeasureTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateUnitOfMeasureTypeFromValue(unitOfMeasureTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(unitOfMeasureType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteUnitOfMeasureTypesByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        List<UnitOfMeasureType> unitOfMeasureTypes = getUnitOfMeasureTypesByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
        
        unitOfMeasureTypes.stream().forEach((unitOfMeasureType) -> {
            deleteUnitOfMeasureType(unitOfMeasureType, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Descriptions
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureTypeDescription createUnitOfMeasureTypeDescription(UnitOfMeasureType unitOfMeasureType, Language language,
            String singularDescription, String pluralDescription, String symbol, BasePK createdBy) {
        UnitOfMeasureTypeDescription unitOfMeasureTypeDescription = UnitOfMeasureTypeDescriptionFactory.getInstance().create(session,
                unitOfMeasureType, language, singularDescription, pluralDescription, symbol, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(unitOfMeasureType.getLastDetail().getUnitOfMeasureTypePK(), EventTypes.MODIFY.name(), unitOfMeasureTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = UnitOfMeasureTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<UnitOfMeasureTypeDescription> unitOfMeasureTypeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypedescriptions, languages " +
                        "WHERE uomtd_uomt_unitofmeasuretypeid = ? AND uomtd_thrutime = ? AND uomtd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasuretypedescriptions " +
                        "WHERE uomtd_uomt_unitofmeasuretypeid = ? AND uomtd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UnitOfMeasureTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        UnitOfMeasureTypeDescription unitOfMeasureTypeDescription = getUnitOfMeasureTypeDescription(unitOfMeasureType, language);
        
        if(unitOfMeasureTypeDescription == null && !language.getIsDefault()) {
            unitOfMeasureTypeDescription = getUnitOfMeasureTypeDescription(unitOfMeasureType, getPartyControl().getDefaultLanguage());
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
        return getUomTransferCaches(userVisit).getUnitOfMeasureTypeDescriptionTransferCache().getUnitOfMeasureTypeDescriptionTransfer(unitOfMeasureTypeDescription);
    }
    
    public List<UnitOfMeasureTypeDescriptionTransfer> getUnitOfMeasureTypeDescriptionTransfers(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        List<UnitOfMeasureTypeDescription> unitOfMeasureTypeDescriptions = getUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(unitOfMeasureType);
        List<UnitOfMeasureTypeDescriptionTransfer> unitOfMeasureTypeDescriptionTransfers = new ArrayList<>(unitOfMeasureTypeDescriptions.size());
        UnitOfMeasureTypeDescriptionTransferCache unitOfMeasureTypeDescriptionTransferCache = getUomTransferCaches(userVisit).getUnitOfMeasureTypeDescriptionTransferCache();
        
        unitOfMeasureTypeDescriptions.stream().forEach((unitOfMeasureTypeDescription) -> {
            unitOfMeasureTypeDescriptionTransfers.add(unitOfMeasureTypeDescriptionTransferCache.getUnitOfMeasureTypeDescriptionTransfer(unitOfMeasureTypeDescription));
        });
        
        return unitOfMeasureTypeDescriptionTransfers;
    }
    
    public void updateUnitOfMeasureTypeDescriptionFromValue(UnitOfMeasureTypeDescriptionValue unitOfMeasureTypeDescriptionValue, BasePK updatedBy) {
        if(unitOfMeasureTypeDescriptionValue.hasBeenModified()) {
            UnitOfMeasureTypeDescription unitOfMeasureTypeDescription = UnitOfMeasureTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeDescriptionValue.getPrimaryKey());
            
            unitOfMeasureTypeDescription.setThruTime(session.START_TIME_LONG);
            unitOfMeasureTypeDescription.store();
            
            UnitOfMeasureType unitOfMeasureType = unitOfMeasureTypeDescription.getUnitOfMeasureType();
            Language language = unitOfMeasureTypeDescription.getLanguage();
            String singularDescription = unitOfMeasureTypeDescriptionValue.getSingularDescription();
            String pluralDescription = unitOfMeasureTypeDescriptionValue.getPluralDescription();
            String symbol = unitOfMeasureTypeDescriptionValue.getSymbol();
            
            unitOfMeasureTypeDescription = UnitOfMeasureTypeDescriptionFactory.getInstance().create(unitOfMeasureType, language,
                    singularDescription, pluralDescription, symbol, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(unitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY.name(), unitOfMeasureTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureTypeDescription(UnitOfMeasureTypeDescription unitOfMeasureTypeDescription, BasePK deletedBy) {
        unitOfMeasureTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(unitOfMeasureTypeDescription.getUnitOfMeasureTypePK(), EventTypes.MODIFY.name(), unitOfMeasureTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteUnitOfMeasureTypeDescriptionsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        List<UnitOfMeasureTypeDescription> unitOfMeasureTypeDescriptions = getUnitOfMeasureTypeDescriptionsByUnitOfMeasureTypeForUpdate(unitOfMeasureType);
        
        unitOfMeasureTypeDescriptions.stream().forEach((unitOfMeasureTypeDescription) -> {
            deleteUnitOfMeasureTypeDescription(unitOfMeasureTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Volumes
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureTypeVolume createUnitOfMeasureTypeVolume(UnitOfMeasureType unitOfMeasureType, Long height, Long width,
            Long depth, BasePK createdBy) {
        UnitOfMeasureTypeVolume unitOfMeasureTypeVolume = UnitOfMeasureTypeVolumeFactory.getInstance().create(session,
                unitOfMeasureType, height, width, depth, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(unitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY.name(), unitOfMeasureTypeVolume.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = UnitOfMeasureTypeVolumeFactory.getInstance().prepareStatement(query);
            
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
        UnitOfMeasureTypeVolume unitOfMeasureTypeVolume = getUnitOfMeasureTypeVolumeForUpdate(unitOfMeasureType);
        
        return unitOfMeasureTypeVolume == null? null: unitOfMeasureTypeVolume.getUnitOfMeasureTypeVolumeValue().clone();
    }
    
    public UnitOfMeasureTypeVolumeTransfer getUnitOfMeasureTypeVolumeTransfer(UserVisit userVisit, UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        return unitOfMeasureTypeVolume == null? null: getUomTransferCaches(userVisit).getUnitOfMeasureTypeVolumeTransferCache().getUnitOfMeasureTypeVolumeTransfer(unitOfMeasureTypeVolume);
    }
    
    public UnitOfMeasureTypeVolumeTransfer getUnitOfMeasureTypeVolumeTransfer(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeVolumeTransfer(userVisit, getUnitOfMeasureTypeVolume(unitOfMeasureType));
    }
    
    public void updateUnitOfMeasureTypeVolumeFromValue(UnitOfMeasureTypeVolumeValue unitOfMeasureTypeVolumeValue, BasePK updatedBy) {
        if(unitOfMeasureTypeVolumeValue.hasBeenModified()) {
            UnitOfMeasureTypeVolume unitOfMeasureTypeVolume = UnitOfMeasureTypeVolumeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeVolumeValue.getPrimaryKey());
            
            unitOfMeasureTypeVolume.setThruTime(session.START_TIME_LONG);
            unitOfMeasureTypeVolume.store();
            
            UnitOfMeasureTypePK unitOfMeasureTypePK = unitOfMeasureTypeVolume.getUnitOfMeasureTypePK(); // Not updated
            Long height = unitOfMeasureTypeVolumeValue.getHeight();
            Long width = unitOfMeasureTypeVolumeValue.getWidth();
            Long depth = unitOfMeasureTypeVolumeValue.getDepth();
            
            unitOfMeasureTypeVolume = UnitOfMeasureTypeVolumeFactory.getInstance().create(unitOfMeasureTypePK, height,
                    width, depth, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(unitOfMeasureTypePK, EventTypes.MODIFY.name(), unitOfMeasureTypeVolume.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureTypeVolume(UnitOfMeasureTypeVolume unitOfMeasureTypeVolume, BasePK deletedBy) {
        unitOfMeasureTypeVolume.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(unitOfMeasureTypeVolume.getUnitOfMeasureTypePK(), EventTypes.MODIFY.name(), unitOfMeasureTypeVolume.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteUnitOfMeasureTypeVolumeByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        UnitOfMeasureTypeVolume unitOfMeasureTypeVolume = getUnitOfMeasureTypeVolumeForUpdate(unitOfMeasureType);
        
        if(unitOfMeasureTypeVolume != null)
            deleteUnitOfMeasureTypeVolume(unitOfMeasureTypeVolume, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Type Weights
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureTypeWeight createUnitOfMeasureTypeWeight(UnitOfMeasureType unitOfMeasureType, Long weight, BasePK createdBy) {
        UnitOfMeasureTypeWeight unitOfMeasureTypeWeight = UnitOfMeasureTypeWeightFactory.getInstance().create(unitOfMeasureType, weight,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(unitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY.name(), unitOfMeasureTypeWeight.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = UnitOfMeasureTypeWeightFactory.getInstance().prepareStatement(query);
            
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
        UnitOfMeasureTypeWeight unitOfMeasureTypeWeight = getUnitOfMeasureTypeWeightForUpdate(unitOfMeasureType);
        
        return unitOfMeasureTypeWeight == null? null: unitOfMeasureTypeWeight.getUnitOfMeasureTypeWeightValue().clone();
    }
    
    public UnitOfMeasureTypeWeightTransfer getUnitOfMeasureTypeWeightTransfer(UserVisit userVisit, UnitOfMeasureTypeWeight unitOfMeasureTypeWeight) {
        return unitOfMeasureTypeWeight == null? null: getUomTransferCaches(userVisit).getUnitOfMeasureTypeWeightTransferCache().getUnitOfMeasureTypeWeightTransfer(unitOfMeasureTypeWeight);
    }
    
    public UnitOfMeasureTypeWeightTransfer getUnitOfMeasureTypeWeightTransfer(UserVisit userVisit, UnitOfMeasureType unitOfMeasureType) {
        return getUnitOfMeasureTypeWeightTransfer(userVisit, getUnitOfMeasureTypeWeight(unitOfMeasureType));
    }
    
    public void updateUnitOfMeasureTypeWeightFromValue(UnitOfMeasureTypeWeightValue unitOfMeasureTypeWeightValue, BasePK updatedBy) {
        if(unitOfMeasureTypeWeightValue.hasBeenModified()) {
            UnitOfMeasureTypeWeight unitOfMeasureTypeWeight = UnitOfMeasureTypeWeightFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureTypeWeightValue.getPrimaryKey());
            
            unitOfMeasureTypeWeight.setThruTime(session.START_TIME_LONG);
            unitOfMeasureTypeWeight.store();
            
            UnitOfMeasureTypePK unitOfMeasureTypePK = unitOfMeasureTypeWeight.getUnitOfMeasureTypePK(); // Not updated
            Long weight = unitOfMeasureTypeWeightValue.getWeight();
            
            unitOfMeasureTypeWeight = UnitOfMeasureTypeWeightFactory.getInstance().create(unitOfMeasureTypePK, weight,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(unitOfMeasureTypePK, EventTypes.MODIFY.name(), unitOfMeasureTypeWeight.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureTypeWeight(UnitOfMeasureTypeWeight unitOfMeasureTypeWeight, BasePK deletedBy) {
        unitOfMeasureTypeWeight.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(unitOfMeasureTypeWeight.getUnitOfMeasureTypePK(), EventTypes.MODIFY.name(), unitOfMeasureTypeWeight.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteUnitOfMeasureTypeWeightByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        UnitOfMeasureTypeWeight unitOfMeasureTypeWeight = getUnitOfMeasureTypeWeightForUpdate(unitOfMeasureType);
        
        if(unitOfMeasureTypeWeight != null)
            deleteUnitOfMeasureTypeWeight(unitOfMeasureTypeWeight, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Unit Of Measure Equivalents
    // --------------------------------------------------------------------------------
    
    public UnitOfMeasureEquivalent createUnitOfMeasureEquivalent(UnitOfMeasureType fromUnitOfMeasureType,
            UnitOfMeasureType toUnitOfMeasureType, Long toQuantity, BasePK createdBy) {
        UnitOfMeasureEquivalent unitOfMeasureEquivalent = UnitOfMeasureEquivalentFactory.getInstance().create(session,
                fromUnitOfMeasureType, toUnitOfMeasureType, toQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(fromUnitOfMeasureType.getPrimaryKey(), EventTypes.MODIFY.name(), unitOfMeasureEquivalent.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = UnitOfMeasureEquivalentFactory.getInstance().prepareStatement(query);
            
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
        UnitOfMeasureEquivalent unitOfMeasureEquivalent = getUnitOfMeasureEquivalentForUpdate(fromUnitOfMeasureType,
                toUnitOfMeasureType);
        
        return unitOfMeasureEquivalent == null? null: unitOfMeasureEquivalent.getUnitOfMeasureEquivalentValue().clone();
    }
    
    private List<UnitOfMeasureEquivalent> getUnitOfMeasureEquivalentsByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind,
            EntityPermission entityPermission) {
        List<UnitOfMeasureEquivalent> unitOfMeasureEquivalents = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents, unitofmeasuretypedetails, unitofmeasurekinddetails " +
                        "WHERE uomeq_thrutime = ? " +
                        "AND uomeq_fromunitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "AND uomkdt_uomk_unitofmeasurekindid = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents, unitofmeasuretypedetails, unitofmeasurekinddetails " +
                        "WHERE AND uomeq_thrutime = ? " +
                        "AND uomeq_fromunitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "AND uomkdt_uomk_unitofmeasurekindid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UnitOfMeasureEquivalentFactory.getInstance().prepareStatement(query);
            
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
        List<UnitOfMeasureEquivalent> unitOfMeasureEquivalents = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents, unitofmeasuretypedetails " +
                        "WHERE uomeq_fromunitofmeasuretypeid = ? AND uomeq_thrutime = ? " +
                        "AND uomeq_fromunitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents " +
                        "WHERE uomeq_fromunitofmeasuretypeid = ? AND uomeq_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UnitOfMeasureEquivalentFactory.getInstance().prepareStatement(query);
            
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
        List<UnitOfMeasureEquivalent> unitOfMeasureEquivalents = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents, unitofmeasuretypedetails " +
                        "WHERE uomeq_tounitofmeasuretypeid = ? AND uomeq_thrutime = ? " +
                        "AND uomeq_fromunitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasureequivalents " +
                        "WHERE uomeq_tounitofmeasuretypeid = ? AND uomeq_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UnitOfMeasureEquivalentFactory.getInstance().prepareStatement(query);
            
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
        return getUomTransferCaches(userVisit).getUnitOfMeasureEquivalentTransferCache().getUnitOfMeasureEquivalentTransfer(unitOfMeasureEquivalent);
    }
    
    private List<UnitOfMeasureEquivalentTransfer> getUnitOfMeasureEquivalentTransfers(final UserVisit userVisit,
            final List<UnitOfMeasureEquivalent> unitOfMeasureEquivalents) {
        List<UnitOfMeasureEquivalentTransfer> unitOfMeasureEquivalentTransfers = new ArrayList<>(unitOfMeasureEquivalents.size());
        UnitOfMeasureEquivalentTransferCache unitOfMeasureEquivalentTransferCache = getUomTransferCaches(userVisit).getUnitOfMeasureEquivalentTransferCache();
        
        unitOfMeasureEquivalents.stream().forEach((unitOfMeasureEquivalent) -> {
            unitOfMeasureEquivalentTransfers.add(unitOfMeasureEquivalentTransferCache.getUnitOfMeasureEquivalentTransfer(unitOfMeasureEquivalent));
        });
        
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
            UnitOfMeasureEquivalent unitOfMeasureEquivalent = UnitOfMeasureEquivalentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureEquivalentValue.getPrimaryKey());
            
            unitOfMeasureEquivalent.setThruTime(session.START_TIME_LONG);
            unitOfMeasureEquivalent.store();
            
            UnitOfMeasureTypePK fromUnitOfMeasureTypePK = unitOfMeasureEquivalent.getFromUnitOfMeasureTypePK(); // Not updated
            UnitOfMeasureTypePK toUnitOfMeasureTypePK = unitOfMeasureEquivalent.getToUnitOfMeasureTypePK(); // Not updated
            Long toQuantity = unitOfMeasureEquivalentValue.getToQuantity();
            
            unitOfMeasureEquivalent = UnitOfMeasureEquivalentFactory.getInstance().create(fromUnitOfMeasureTypePK,
                    toUnitOfMeasureTypePK, toQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(fromUnitOfMeasureTypePK, EventTypes.MODIFY.name(), unitOfMeasureEquivalent.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteUnitOfMeasureEquivalent(UnitOfMeasureEquivalent unitOfMeasureEquivalent, BasePK deletedBy) {
        unitOfMeasureEquivalent.setThruTime(session.START_TIME_LONG);
        unitOfMeasureEquivalent.store();
        
        sendEventUsingNames(unitOfMeasureEquivalent.getFromUnitOfMeasureTypePK(), EventTypes.MODIFY.name(), unitOfMeasureEquivalent.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteUnitOfMeasureEquivalentsByFromUnitOfMeasureType(UnitOfMeasureType fromUnitOfMeasureType, BasePK deletedBy) {
        List<UnitOfMeasureEquivalent> unitOfMeasureEquivalents = getUnitOfMeasureEquivalentsByFromUnitOfMeasureTypeForUpdate(fromUnitOfMeasureType);
        
        unitOfMeasureEquivalents.stream().forEach((unitOfMeasureEquivalent) -> {
            deleteUnitOfMeasureEquivalent(unitOfMeasureEquivalent, deletedBy);
        });
    }
    
    public void deleteUnitOfMeasureEquivalentsByToUnitOfMeasureType(UnitOfMeasureType toUnitOfMeasureType, BasePK deletedBy) {
        List<UnitOfMeasureEquivalent> unitOfMeasureEquivalents = getUnitOfMeasureEquivalentsByToUnitOfMeasureTypeForUpdate(toUnitOfMeasureType);
        
        unitOfMeasureEquivalents.stream().forEach((unitOfMeasureEquivalent) -> {
            deleteUnitOfMeasureEquivalent(unitOfMeasureEquivalent, deletedBy);
        });
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
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.UnitOfMeasureKindUseType */
    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        UnitOfMeasureKindUseTypePK pk = new UnitOfMeasureKindUseTypePK(entityInstance.getEntityUniqueId());
        UnitOfMeasureKindUseType unitOfMeasureKindUseType = UnitOfMeasureKindUseTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return unitOfMeasureKindUseType;
    }

    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getUnitOfMeasureKindUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUnitOfMeasureKindUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public List<UnitOfMeasureKindUseType> getUnitOfMeasureKindUseTypes() {
        PreparedStatement ps = UnitOfMeasureKindUseTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM unitofmeasurekindusetypes " +
                "ORDER BY uomkut_sortorder, uomkut_unitofmeasurekindusetypename");
        
        return UnitOfMeasureKindUseTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByName(String unitOfMeasureKindUseTypeName) {
        UnitOfMeasureKindUseType unitOfMeasureKindUseType;
        
        try {
            PreparedStatement ps = UnitOfMeasureKindUseTypeFactory.getInstance().prepareStatement(
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
        List<UnitOfMeasureKindUseType> unitOfMeasureKindUseTypes = getUnitOfMeasureKindUseTypes();
        int size = unitOfMeasureKindUseTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUnitOfMeasureKindUseTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(UnitOfMeasureKindUseType unitOfMeasureKindUseType: unitOfMeasureKindUseTypes) {
            String label = getBestUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, language);
            String value = unitOfMeasureKindUseType.getUnitOfMeasureKindUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultUnitOfMeasureKindUseTypeChoice == null? false: defaultUnitOfMeasureKindUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && unitOfMeasureKindUseType.getIsDefault()))
                defaultValue = value;
        }
        
        return new UnitOfMeasureKindUseTypeChoicesBean(labels, values, defaultValue);
    }
    
    public UnitOfMeasureKindUseTypeTransfer getUnitOfMeasureKindUseTypeTransfer(UserVisit userVisit,
            UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        return getUomTransferCaches(userVisit).getUnitOfMeasureKindUseTypeTransferCache().getUnitOfMeasureKindUseTypeTransfer(unitOfMeasureKindUseType);
    }
    
    public List<UnitOfMeasureKindUseTypeTransfer> getUnitOfMeasureKindUseTypeTransfers(UserVisit userVisit, Collection<UnitOfMeasureKindUseType> unitOfMeasureKindUseTypes) {
        List<UnitOfMeasureKindUseTypeTransfer> unitOfMeasureKindUseTypeTransfers = new ArrayList<>(unitOfMeasureKindUseTypes.size());
        UnitOfMeasureKindUseTypeTransferCache unitOfMeasureKindUseTypeTransferCache = getUomTransferCaches(userVisit).getUnitOfMeasureKindUseTypeTransferCache();
        
        unitOfMeasureKindUseTypes.stream().forEach((unitOfMeasureKindUseType) -> {
            unitOfMeasureKindUseTypeTransfers.add(unitOfMeasureKindUseTypeTransferCache.getUnitOfMeasureKindUseTypeTransfer(unitOfMeasureKindUseType));
        });
        
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
            PreparedStatement ps = UnitOfMeasureKindUseTypeDescriptionFactory.getInstance().prepareStatement(
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
        UnitOfMeasureKindUseTypeDescription unitOfMeasureKindUseTypeDescription = getUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, language);
        
        if(unitOfMeasureKindUseTypeDescription == null && !language.getIsDefault()) {
            unitOfMeasureKindUseTypeDescription = getUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, getPartyControl().getDefaultLanguage());
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
        UnitOfMeasureKindUse defaultUnitOfMeasureKindUse = getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType);
        boolean defaultFound = defaultUnitOfMeasureKindUse != null;
        
        if(defaultFound && isDefault) {
            UnitOfMeasureKindUseValue defaultUnitOfMeasureKindUseValue = getDefaultUnitOfMeasureKindUseValueForUpdate(unitOfMeasureKindUseType);
            
            defaultUnitOfMeasureKindUseValue.setIsDefault(Boolean.FALSE);
            updateUnitOfMeasureKindUseFromValue(defaultUnitOfMeasureKindUseValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        UnitOfMeasureKindUse unitOfMeasureKindUse = UnitOfMeasureKindUseFactory.getInstance().create(session,
                unitOfMeasureKindUseType, unitOfMeasureKind, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(unitOfMeasureKind.getPrimaryKey(), EventTypes.MODIFY.name(), unitOfMeasureKindUse.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
    
    /** Assume that the entityInstance passed to this function is a ECHOTHREE.UnitOfMeasureKindUse */
    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        UnitOfMeasureKindUsePK pk = new UnitOfMeasureKindUsePK(entityInstance.getEntityUniqueId());
        UnitOfMeasureKindUse unitOfMeasureKindUse = UnitOfMeasureKindUseFactory.getInstance().getEntityFromPK(entityPermission, pk);
        
        return unitOfMeasureKindUse;
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
            PreparedStatement ps = UnitOfMeasureKindUseFactory.getInstance().prepareStatement(query);
            
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
            PreparedStatement ps = UnitOfMeasureKindUseFactory.getInstance().prepareStatement(query);
            
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
        List<UnitOfMeasureKindUse> unitOfMeasureKindUses = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses, unitofmeasurekindusetypes " +
                        "WHERE uomku_uomk_unitofmeasurekindid = ? AND uomku_thrutime = ? " +
                        "AND uomku_uomkut_unitofmeasurekindusetypeid = uomkut_unitofmeasurekindusetypeid " +
                        "ORDER BY uomku_sortorder, uomkut_sortorder, uomkut_unitofmeasurekindusetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses " +
                        "WHERE uomku_uomk_unitofmeasurekindid = ? AND uomku_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UnitOfMeasureKindUseFactory.getInstance().prepareStatement(query);
            
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
        List<UnitOfMeasureKindUse> unitOfMeasureKindUses = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses, unitofmeasurekinddetails " +
                        "WHERE uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_thrutime = ? " +
                        "AND uomku_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "ORDER BY uomku_sortorder, uomkdt_sortorder, uomkdt_unitofmeasurekindname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM unitofmeasurekinduses, unitofmeasurekinddetails " +
                        "WHERE uomku_uomkut_unitofmeasurekindusetypeid = ? AND uomku_thrutime = ? " +
                        "AND uomku_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UnitOfMeasureKindUseFactory.getInstance().prepareStatement(query);
            
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
        UnitOfMeasureKindUse unitOfMeasureKindUse = null;
        
        if(!unitOfMeasureKindUseType.getAllowMultiple()) {
            try {
                PreparedStatement ps = UnitOfMeasureKindUseFactory.getInstance().prepareStatement(
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
        UnitOfMeasureKindUse unitOfMeasureKindUse = getUnitOfMeasureKindUseByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        
        return unitOfMeasureKindUse == null? null: unitOfMeasureKindUse.getUnitOfMeasureKind();
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(String unitOfMeasureKindUseTypeName) {
        UnitOfMeasureKindUse unitOfMeasureKindUse = null;
        UnitOfMeasureKindUseType unitOfMeasureKindUseType = getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);
        
        if(unitOfMeasureKindUseType != null) {
            unitOfMeasureKindUse = getUnitOfMeasureKindUseByUnitOfMeasureKindUseType(unitOfMeasureKindUseType);
        }
        
        return unitOfMeasureKindUse == null? null: unitOfMeasureKindUse.getUnitOfMeasureKind();
    }
    
    public UnitOfMeasureKindUseTransfer getUnitOfMeasureKindUseTransfer(UserVisit userVisit, UnitOfMeasureKindUse unitOfMeasureKindUse) {
        return unitOfMeasureKindUse == null? null: getUomTransferCaches(userVisit).getUnitOfMeasureKindUseTransferCache().getUnitOfMeasureKindUseTransfer(unitOfMeasureKindUse);
    }
    
    public List<UnitOfMeasureKindUseTransfer> getUnitOfMeasureKindUseTransfers(final UserVisit userVisit,
            final Collection<UnitOfMeasureKindUse> unitOfMeasureKindUses) {
        List<UnitOfMeasureKindUseTransfer> unitOfMeasureKindUseTransfers = new ArrayList<>(unitOfMeasureKindUses.size());
        UnitOfMeasureKindUseTransferCache unitOfMeasureKindUseTransferCache = getUomTransferCaches(userVisit).getUnitOfMeasureKindUseTransferCache();
        
        unitOfMeasureKindUses.stream().forEach((unitOfMeasureKindUse) -> {
            unitOfMeasureKindUseTransfers.add(unitOfMeasureKindUseTransferCache.getUnitOfMeasureKindUseTransfer(unitOfMeasureKindUse));
        });
        
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
            UnitOfMeasureKindUse unitOfMeasureKindUse = UnitOfMeasureKindUseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     unitOfMeasureKindUseValue.getPrimaryKey());
            
            unitOfMeasureKindUse.setThruTime(session.START_TIME_LONG);
            unitOfMeasureKindUse.store();
            
            UnitOfMeasureKindUseTypePK unitOfMeasureKindUseTypePK = unitOfMeasureKindUse.getUnitOfMeasureKindUseTypePK();
            UnitOfMeasureKindUseType unitOfMeasureKindUseType = unitOfMeasureKindUse.getUnitOfMeasureKindUseType();
            UnitOfMeasureKindPK unitOfMeasureKindPK = unitOfMeasureKindUse.getUnitOfMeasureKindPK();
            Boolean isDefault = unitOfMeasureKindUseValue.getIsDefault();
            Integer sortOrder = unitOfMeasureKindUseValue.getSortOrder();
            
            if(checkDefault) {
                UnitOfMeasureKindUse defaultUnitOfMeasureKindUse = getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType);
                boolean defaultFound = defaultUnitOfMeasureKindUse != null && !defaultUnitOfMeasureKindUse.equals(unitOfMeasureKindUse);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    UnitOfMeasureKindUseValue defaultUnitOfMeasureKindUseValue = getDefaultUnitOfMeasureKindUseValueForUpdate(unitOfMeasureKindUseType);
                    
                    defaultUnitOfMeasureKindUseValue.setIsDefault(Boolean.FALSE);
                    updateUnitOfMeasureKindUseFromValue(defaultUnitOfMeasureKindUseValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            unitOfMeasureKindUse = UnitOfMeasureKindUseFactory.getInstance().create(unitOfMeasureKindUseTypePK,
                    unitOfMeasureKindPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(unitOfMeasureKindPK, EventTypes.MODIFY.name(), unitOfMeasureKindUse.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
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
        UnitOfMeasureKindUseType unitOfMeasureKindUseType = unitOfMeasureKindUse.getUnitOfMeasureKindUseType();
        UnitOfMeasureKindUse defaultUnitOfMeasureKindUse = getDefaultUnitOfMeasureKindUse(unitOfMeasureKindUseType);
        if(defaultUnitOfMeasureKindUse == null) {
            List<UnitOfMeasureKindUse> unitOfMeasureKindUses = getUnitOfMeasureKindUsesByUnitOfMeasureKindUseTypeForUpdate(unitOfMeasureKindUseType);
            
            if(!unitOfMeasureKindUses.isEmpty()) {
                Iterator<UnitOfMeasureKindUse> iter = unitOfMeasureKindUses.iterator();
                if(iter.hasNext()) {
                    defaultUnitOfMeasureKindUse = (UnitOfMeasureKindUse)iter.next();
                }
                UnitOfMeasureKindUseValue unitOfMeasureKindUseValue = defaultUnitOfMeasureKindUse.getUnitOfMeasureKindUseValue().clone();
                
                unitOfMeasureKindUseValue.setIsDefault(Boolean.TRUE);
                updateUnitOfMeasureKindUseFromValue(unitOfMeasureKindUseValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(unitOfMeasureKindUse.getUnitOfMeasureKindPK(), EventTypes.MODIFY.name(), unitOfMeasureKindUse.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteUnitOfMeasureKindUseByUnitOfMeasureKind(UnitOfMeasureKind unitOfMeasureKind, BasePK deletedBy) {
        List<UnitOfMeasureKindUse> unitOfMeasureKindUses = getUnitOfMeasureKindUsesByUnitOfMeasureKindForUpdate(unitOfMeasureKind);
        
        unitOfMeasureKindUses.stream().forEach((unitOfMeasureKindUse) -> {
            deleteUnitOfMeasureKindUse(unitOfMeasureKindUse, deletedBy);
        });
    }
    
}

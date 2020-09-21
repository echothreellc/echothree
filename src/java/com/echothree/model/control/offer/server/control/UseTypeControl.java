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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.common.choice.OfferChoicesBean;
import com.echothree.model.control.offer.common.choice.SourceChoicesBean;
import com.echothree.model.control.offer.common.choice.UseChoicesBean;
import com.echothree.model.control.offer.common.choice.UseTypeChoicesBean;
import com.echothree.model.control.offer.common.transfer.OfferChainTypeTransfer;
import com.echothree.model.control.offer.common.transfer.OfferCustomerTypeTransfer;
import com.echothree.model.control.offer.common.transfer.OfferDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.OfferItemPriceTransfer;
import com.echothree.model.control.offer.common.transfer.OfferItemTransfer;
import com.echothree.model.control.offer.common.transfer.OfferNameElementDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.OfferNameElementTransfer;
import com.echothree.model.control.offer.common.transfer.OfferTransfer;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.common.transfer.SourceTransfer;
import com.echothree.model.control.offer.common.transfer.UseDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseNameElementDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseNameElementTransfer;
import com.echothree.model.control.offer.common.transfer.UseTransfer;
import com.echothree.model.control.offer.common.transfer.UseTypeDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseTypeTransfer;
import com.echothree.model.control.offer.server.logic.OfferLogic;
import com.echothree.model.control.offer.server.transfer.OfferChainTypeTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferCustomerTypeTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferItemPriceTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferItemTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferNameElementDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferNameElementTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferUseTransferCache;
import com.echothree.model.control.offer.server.transfer.UseNameElementDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.UseNameElementTransferCache;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.chain.common.pk.ChainPK;
import com.echothree.model.data.chain.common.pk.ChainTypePK;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.common.pk.CustomerTypePK;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.filter.common.pk.FilterPK;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.model.data.offer.common.pk.OfferItemPricePK;
import com.echothree.model.data.offer.common.pk.OfferNameElementPK;
import com.echothree.model.data.offer.common.pk.OfferPK;
import com.echothree.model.data.offer.common.pk.OfferUsePK;
import com.echothree.model.data.offer.common.pk.SourcePK;
import com.echothree.model.data.offer.common.pk.UseNameElementPK;
import com.echothree.model.data.offer.common.pk.UsePK;
import com.echothree.model.data.offer.common.pk.UseTypePK;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferChainType;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
import com.echothree.model.data.offer.server.entity.OfferDescription;
import com.echothree.model.data.offer.server.entity.OfferDetail;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemFixedPrice;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferItemVariablePrice;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferNameElementDescription;
import com.echothree.model.data.offer.server.entity.OfferNameElementDetail;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.OfferUseDetail;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.SourceDetail;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseDescription;
import com.echothree.model.data.offer.server.entity.UseDetail;
import com.echothree.model.data.offer.server.entity.UseNameElement;
import com.echothree.model.data.offer.server.entity.UseNameElementDescription;
import com.echothree.model.data.offer.server.entity.UseNameElementDetail;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDescription;
import com.echothree.model.data.offer.server.entity.UseTypeDetail;
import com.echothree.model.data.offer.server.factory.OfferChainTypeFactory;
import com.echothree.model.data.offer.server.factory.OfferCustomerTypeFactory;
import com.echothree.model.data.offer.server.factory.OfferDescriptionFactory;
import com.echothree.model.data.offer.server.factory.OfferDetailFactory;
import com.echothree.model.data.offer.server.factory.OfferFactory;
import com.echothree.model.data.offer.server.factory.OfferItemFactory;
import com.echothree.model.data.offer.server.factory.OfferItemFixedPriceFactory;
import com.echothree.model.data.offer.server.factory.OfferItemPriceFactory;
import com.echothree.model.data.offer.server.factory.OfferItemVariablePriceFactory;
import com.echothree.model.data.offer.server.factory.OfferNameElementDescriptionFactory;
import com.echothree.model.data.offer.server.factory.OfferNameElementDetailFactory;
import com.echothree.model.data.offer.server.factory.OfferNameElementFactory;
import com.echothree.model.data.offer.server.factory.OfferUseDetailFactory;
import com.echothree.model.data.offer.server.factory.OfferUseFactory;
import com.echothree.model.data.offer.server.factory.SourceDetailFactory;
import com.echothree.model.data.offer.server.factory.SourceFactory;
import com.echothree.model.data.offer.server.factory.UseDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseDetailFactory;
import com.echothree.model.data.offer.server.factory.UseFactory;
import com.echothree.model.data.offer.server.factory.UseNameElementDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseNameElementDetailFactory;
import com.echothree.model.data.offer.server.factory.UseNameElementFactory;
import com.echothree.model.data.offer.server.factory.UseTypeDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseTypeDetailFactory;
import com.echothree.model.data.offer.server.factory.UseTypeFactory;
import com.echothree.model.data.offer.server.value.OfferChainTypeValue;
import com.echothree.model.data.offer.server.value.OfferCustomerTypeValue;
import com.echothree.model.data.offer.server.value.OfferDescriptionValue;
import com.echothree.model.data.offer.server.value.OfferDetailValue;
import com.echothree.model.data.offer.server.value.OfferItemFixedPriceValue;
import com.echothree.model.data.offer.server.value.OfferItemVariablePriceValue;
import com.echothree.model.data.offer.server.value.OfferNameElementDescriptionValue;
import com.echothree.model.data.offer.server.value.OfferNameElementDetailValue;
import com.echothree.model.data.offer.server.value.OfferUseDetailValue;
import com.echothree.model.data.offer.server.value.SourceDetailValue;
import com.echothree.model.data.offer.server.value.UseDescriptionValue;
import com.echothree.model.data.offer.server.value.UseDetailValue;
import com.echothree.model.data.offer.server.value.UseNameElementDescriptionValue;
import com.echothree.model.data.offer.server.value.UseNameElementDetailValue;
import com.echothree.model.data.offer.server.value.UseTypeDescriptionValue;
import com.echothree.model.data.offer.server.value.UseTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.factory.SelectorFactory;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.transfer.HistoryTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UseTypeControl
        extends BaseOfferControl {

    /** Creates a new instance of OfferControl */
    public UseTypeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Use Types
    // --------------------------------------------------------------------------------
    
    public UseType createUseType(String useTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        UseType defaultUseType = getDefaultUseType();
        boolean defaultFound = defaultUseType != null;
        
        if(defaultFound && isDefault) {
            UseTypeDetailValue defaultUseTypeDetailValue = getDefaultUseTypeDetailValueForUpdate();
            
            defaultUseTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateUseTypeFromValue(defaultUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        UseType useType = UseTypeFactory.getInstance().create();
        UseTypeDetail useTypeDetail = UseTypeDetailFactory.getInstance().create(useType, useTypeName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        useType = UseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, useType.getPrimaryKey());
        useType.setActiveDetail(useTypeDetail);
        useType.setLastDetail(useTypeDetail);
        useType.store();
        
        sendEventUsingNames(useType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return useType;
    }
    
    public long countUseTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM usetypes, usetypedetails " +
                "WHERE usetyp_activedetailid = usetypdt_usetypedetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.UseType */
    public UseType getUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UseTypePK(entityInstance.getEntityUniqueId());
        var useType = UseTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return useType;
    }

    public UseType getUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UseType getUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<UseType> getUseTypes(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ "
                    + "FROM usetypes, usetypedetails "
                    + "WHERE usetyp_activedetailid = usetypdt_usetypedetailid "
                    + "ORDER BY usetypdt_sortorder, usetypdt_usetypename "
                    + "_LIMIT_";
        } else if (entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ "
                    + "FROM usetypes, usetypedetails "
                    + "WHERE usetyp_activedetailid = usetypdt_usetypedetailid "
                    + "FOR UPDATE";
        }
        
        PreparedStatement ps = UseTypeFactory.getInstance().prepareStatement(query);
        
        return UseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<UseType> getUseTypes() {
        return getUseTypes(EntityPermission.READ_ONLY);
    }
    
    public List<UseType> getUseTypesForUpdate() {
        return getUseTypes(EntityPermission.READ_WRITE);
    }

    public UseType getUseTypeByName(String useTypeName, EntityPermission entityPermission) {
        UseType useType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypes, usetypedetails " +
                        "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_usetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypes, usetypedetails " +
                        "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_usetypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, useTypeName);
            
            useType = UseTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useType;
    }
    
    public UseType getUseTypeByName(String useTypeName) {
        return getUseTypeByName(useTypeName, EntityPermission.READ_ONLY);
    }
    
    public UseType getUseTypeByNameForUpdate(String useTypeName) {
        return getUseTypeByName(useTypeName, EntityPermission.READ_WRITE);
    }
    
    public UseTypeDetailValue getUseTypeDetailValueForUpdate(UseType useType) {
        return useType == null? null: useType.getLastDetailForUpdate().getUseTypeDetailValue().clone();
    }
    
    public UseTypeDetailValue getUseTypeDetailValueByNameForUpdate(String useTypeName) {
        return getUseTypeDetailValueForUpdate(getUseTypeByNameForUpdate(useTypeName));
    }
    
    public UseType getDefaultUseType(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM usetypes, usetypedetails " +
                    "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM usetypes, usetypedetails " +
                    "WHERE usetyp_activedetailid = usetypdt_usetypedetailid AND usetypdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = UseTypeFactory.getInstance().prepareStatement(query);
        
        return UseTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public UseType getDefaultUseType() {
        return getDefaultUseType(EntityPermission.READ_ONLY);
    }
    
    public UseType getDefaultUseTypeForUpdate() {
        return getDefaultUseType(EntityPermission.READ_WRITE);
    }
    
    public UseTypeDetailValue getDefaultUseTypeDetailValueForUpdate() {
        return getDefaultUseTypeForUpdate().getLastDetailForUpdate().getUseTypeDetailValue().clone();
    }
    
    public List<UseTypeTransfer> getUseTypeTransfers(UserVisit userVisit, Collection<UseType> useTypes) {
        List<UseTypeTransfer> useTypeTransfers = new ArrayList<>(useTypes.size());
        
        useTypes.stream().forEach((useType) -> {
            useTypeTransfers.add(getOfferTransferCaches(userVisit).getUseTypeTransferCache().getUseTypeTransfer(useType));
        });
        
        return useTypeTransfers;
    }
    
    public List<UseTypeTransfer> getUseTypeTransfers(UserVisit userVisit) {
        return getUseTypeTransfers(userVisit, getUseTypes());
    }
    
    public UseTypeTransfer getUseTypeTransfer(UserVisit userVisit, UseType useType) {
        return getOfferTransferCaches(userVisit).getUseTypeTransferCache().getUseTypeTransfer(useType);
    }
    
    public UseTypeChoicesBean getUseTypeChoices(String defaultUseTypeChoice, Language language, boolean allowNullChoice) {
        List<UseType> useTypes = getUseTypes();
        int size = useTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUseTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(UseType useType : useTypes) {
            UseTypeDetail useTypeDetail = useType.getLastDetail();
            
            String label = getBestUseTypeDescription(useType, language);
            String value = useTypeDetail.getUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultUseTypeChoice == null? false: defaultUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && useTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UseTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUseTypeFromValue(UseTypeDetailValue useTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(useTypeDetailValue.hasBeenModified()) {
            UseType useType = UseTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     useTypeDetailValue.getUseTypePK());
            UseTypeDetail useTypeDetail = useType.getActiveDetailForUpdate();
            
            useTypeDetail.setThruTime(session.START_TIME_LONG);
            useTypeDetail.store();
            
            UseTypePK useTypePK = useTypeDetail.getUseTypePK();
            String useTypeName = useTypeDetailValue.getUseTypeName();
            Boolean isDefault = useTypeDetailValue.getIsDefault();
            Integer sortOrder = useTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                UseType defaultUseType = getDefaultUseType();
                boolean defaultFound = defaultUseType != null && !defaultUseType.equals(useType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    UseTypeDetailValue defaultUseTypeDetailValue = getDefaultUseTypeDetailValueForUpdate();
                    
                    defaultUseTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateUseTypeFromValue(defaultUseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            useTypeDetail = UseTypeDetailFactory.getInstance().create(useTypePK, useTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            useType.setActiveDetail(useTypeDetail);
            useType.setLastDetail(useTypeDetail);
            
            sendEventUsingNames(useTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateUseTypeFromValue(UseTypeDetailValue useTypeDetailValue, BasePK updatedBy) {
        updateUseTypeFromValue(useTypeDetailValue, true, updatedBy);
    }
    
    public void deleteUseType(UseType useType, BasePK deletedBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        deleteUseTypeDescriptionsByUseType(useType, deletedBy);
        offerControl.deleteUsesByUseType(useType, deletedBy);
        
        UseTypeDetail useTypeDetail = useType.getLastDetailForUpdate();
        useTypeDetail.setThruTime(session.START_TIME_LONG);
        useType.setActiveDetail(null);
        useType.store();
        
        // Check for default, and pick one if necessary
        UseType defaultUseType = getDefaultUseType();
        if(defaultUseType == null) {
            List<UseType> useTypes = getUseTypesForUpdate();
            
            if(!useTypes.isEmpty()) {
                Iterator<UseType> iter = useTypes.iterator();
                if(iter.hasNext()) {
                    defaultUseType = iter.next();
                }
                UseTypeDetailValue useTypeDetailValue = defaultUseType.getLastDetailForUpdate().getUseTypeDetailValue().clone();
                
                useTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateUseTypeFromValue(useTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(useType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    public UseTypeDescription createUseTypeDescription(UseType useType, Language language, String description, BasePK createdBy) {
        UseTypeDescription useTypeDescription = UseTypeDescriptionFactory.getInstance().create(useType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(useType.getPrimaryKey(), EventTypes.MODIFY.name(), useTypeDescription.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
        return useTypeDescription;
    }
    
    private UseTypeDescription getUseTypeDescription(UseType useType, Language language, EntityPermission entityPermission) {
        UseTypeDescription useTypeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_lang_languageid = ? AND usetypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_lang_languageid = ? AND usetypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, useType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            useTypeDescription = UseTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useTypeDescription;
    }
    
    public UseTypeDescription getUseTypeDescription(UseType useType, Language language) {
        return getUseTypeDescription(useType, language, EntityPermission.READ_ONLY);
    }
    
    public UseTypeDescription getUseTypeDescriptionForUpdate(UseType useType, Language language) {
        return getUseTypeDescription(useType, language, EntityPermission.READ_WRITE);
    }
    
    public UseTypeDescriptionValue getUseTypeDescriptionValue(UseTypeDescription useTypeDescription) {
        return useTypeDescription == null? null: useTypeDescription.getUseTypeDescriptionValue().clone();
    }
    
    public UseTypeDescriptionValue getUseTypeDescriptionValueForUpdate(UseType useType, Language language) {
        return getUseTypeDescriptionValue(getUseTypeDescriptionForUpdate(useType, language));
    }
    
    private List<UseTypeDescription> getUseTypeDescriptionsByUseType(UseType useType, EntityPermission entityPermission) {
        List<UseTypeDescription> useTypeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions, languages " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_thrutime = ? AND usetypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM usetypedescriptions " +
                        "WHERE usetypd_usetyp_usetypeid = ? AND usetypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = UseTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, useType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            useTypeDescriptions = UseTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return useTypeDescriptions;
    }
    
    public List<UseTypeDescription> getUseTypeDescriptionsByUseType(UseType useType) {
        return getUseTypeDescriptionsByUseType(useType, EntityPermission.READ_ONLY);
    }
    
    public List<UseTypeDescription> getUseTypeDescriptionsByUseTypeForUpdate(UseType useType) {
        return getUseTypeDescriptionsByUseType(useType, EntityPermission.READ_WRITE);
    }
    
    public String getBestUseTypeDescription(UseType useType, Language language) {
        String description;
        UseTypeDescription useTypeDescription = getUseTypeDescription(useType, language);
        
        if(useTypeDescription == null && !language.getIsDefault()) {
            useTypeDescription = getUseTypeDescription(useType, getPartyControl().getDefaultLanguage());
        }
        
        if(useTypeDescription == null) {
            description = useType.getLastDetail().getUseTypeName();
        } else {
            description = useTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public UseTypeDescriptionTransfer getUseTypeDescriptionTransfer(UserVisit userVisit, UseTypeDescription useTypeDescription) {
        return getOfferTransferCaches(userVisit).getUseTypeDescriptionTransferCache().getUseTypeDescriptionTransfer(useTypeDescription);
    }
    
    public List<UseTypeDescriptionTransfer> getUseTypeDescriptionTransfersByUseType(UserVisit userVisit, UseType useType) {
        List<UseTypeDescription> useTypeDescriptions = getUseTypeDescriptionsByUseType(useType);
        List<UseTypeDescriptionTransfer> useTypeDescriptionTransfers = new ArrayList<>(useTypeDescriptions.size());
        
        useTypeDescriptions.stream().forEach((useTypeDescription) -> {
            useTypeDescriptionTransfers.add(getOfferTransferCaches(userVisit).getUseTypeDescriptionTransferCache().getUseTypeDescriptionTransfer(useTypeDescription));
        });
        
        return useTypeDescriptionTransfers;
    }
    
    public void updateUseTypeDescriptionFromValue(UseTypeDescriptionValue useTypeDescriptionValue, BasePK updatedBy) {
        if(useTypeDescriptionValue.hasBeenModified()) {
            UseTypeDescription useTypeDescription = UseTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     useTypeDescriptionValue.getPrimaryKey());
            
            useTypeDescription.setThruTime(session.START_TIME_LONG);
            useTypeDescription.store();
            
            UseType useType = useTypeDescription.getUseType();
            Language language = useTypeDescription.getLanguage();
            String description = useTypeDescriptionValue.getDescription();
            
            useTypeDescription = UseTypeDescriptionFactory.getInstance().create(useType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(useType.getPrimaryKey(), EventTypes.MODIFY.name(), useTypeDescription.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteUseTypeDescription(UseTypeDescription useTypeDescription, BasePK deletedBy) {
        useTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(useTypeDescription.getUseTypePK(), EventTypes.MODIFY.name(),
                useTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteUseTypeDescriptionsByUseType(UseType useType, BasePK deletedBy) {
        List<UseTypeDescription> useTypeDescriptions = getUseTypeDescriptionsByUseTypeForUpdate(useType);
        
        useTypeDescriptions.stream().forEach((useTypeDescription) -> {
            deleteUseTypeDescription(useTypeDescription, deletedBy);
        });
    }

}

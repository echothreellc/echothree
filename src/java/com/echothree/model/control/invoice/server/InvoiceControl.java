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

package com.echothree.model.control.invoice.server;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.invoice.common.InvoiceConstants;
import com.echothree.model.control.invoice.common.choice.InvoiceAliasTypeChoicesBean;
import com.echothree.model.control.invoice.common.choice.InvoiceLineTypeChoicesBean;
import com.echothree.model.control.invoice.common.choice.InvoiceTimeTypeChoicesBean;
import com.echothree.model.control.invoice.common.choice.InvoiceTypeChoicesBean;
import com.echothree.model.control.invoice.common.transfer.InvoiceAliasTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceAliasTypeDescriptionTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceAliasTypeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineGlAccountTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineItemTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineTypeDescriptionTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineTypeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceLineUseTypeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceRoleTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceRoleTypeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTimeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTimeTypeDescriptionTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTimeTypeTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTypeDescriptionTransfer;
import com.echothree.model.control.invoice.common.transfer.InvoiceTypeTransfer;
import com.echothree.model.control.invoice.server.transfer.InvoiceAliasTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceAliasTypeDescriptionTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceAliasTypeTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceLineTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceLineTypeDescriptionTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceLineTypeTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceLineUseTypeTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceRoleTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceRoleTypeTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceTimeTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceTimeTypeDescriptionTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceTimeTypeTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceTransferCaches;
import com.echothree.model.control.invoice.server.transfer.InvoiceTypeDescriptionTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceTypeTransferCache;
import com.echothree.model.data.accounting.common.pk.GlAccountPK;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.common.pk.PartyContactMechanismPK;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.inventory.common.pk.InventoryConditionPK;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.invoice.common.pk.InvoiceAliasTypePK;
import com.echothree.model.data.invoice.common.pk.InvoiceLinePK;
import com.echothree.model.data.invoice.common.pk.InvoiceLineTypePK;
import com.echothree.model.data.invoice.common.pk.InvoicePK;
import com.echothree.model.data.invoice.common.pk.InvoiceRoleTypePK;
import com.echothree.model.data.invoice.common.pk.InvoiceTimeTypePK;
import com.echothree.model.data.invoice.common.pk.InvoiceTypePK;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.invoice.server.entity.InvoiceAlias;
import com.echothree.model.data.invoice.server.entity.InvoiceAliasType;
import com.echothree.model.data.invoice.server.entity.InvoiceAliasTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceAliasTypeDetail;
import com.echothree.model.data.invoice.server.entity.InvoiceDetail;
import com.echothree.model.data.invoice.server.entity.InvoiceLine;
import com.echothree.model.data.invoice.server.entity.InvoiceLineDetail;
import com.echothree.model.data.invoice.server.entity.InvoiceLineGlAccount;
import com.echothree.model.data.invoice.server.entity.InvoiceLineItem;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.invoice.server.entity.InvoiceLineTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceLineTypeDetail;
import com.echothree.model.data.invoice.server.entity.InvoiceLineUseType;
import com.echothree.model.data.invoice.server.entity.InvoiceLineUseTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceRole;
import com.echothree.model.data.invoice.server.entity.InvoiceRoleType;
import com.echothree.model.data.invoice.server.entity.InvoiceRoleTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceStatus;
import com.echothree.model.data.invoice.server.entity.InvoiceTime;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeType;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeTypeDetail;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.invoice.server.entity.InvoiceTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceTypeDetail;
import com.echothree.model.data.invoice.server.factory.InvoiceAliasFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceAliasTypeDescriptionFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceAliasTypeDetailFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceAliasTypeFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceDetailFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceLineDetailFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceLineFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceLineGlAccountFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceLineItemFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceLineTypeDescriptionFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceLineTypeDetailFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceLineTypeFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceLineUseTypeDescriptionFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceLineUseTypeFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceRoleFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceRoleTypeDescriptionFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceRoleTypeFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceStatusFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceTimeFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceTimeTypeDescriptionFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceTimeTypeDetailFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceTimeTypeFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceTypeDescriptionFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceTypeDetailFactory;
import com.echothree.model.data.invoice.server.factory.InvoiceTypeFactory;
import com.echothree.model.data.invoice.server.value.InvoiceAliasTypeDescriptionValue;
import com.echothree.model.data.invoice.server.value.InvoiceAliasTypeDetailValue;
import com.echothree.model.data.invoice.server.value.InvoiceAliasValue;
import com.echothree.model.data.invoice.server.value.InvoiceLineGlAccountValue;
import com.echothree.model.data.invoice.server.value.InvoiceLineItemValue;
import com.echothree.model.data.invoice.server.value.InvoiceLineTypeDescriptionValue;
import com.echothree.model.data.invoice.server.value.InvoiceLineTypeDetailValue;
import com.echothree.model.data.invoice.server.value.InvoiceRoleValue;
import com.echothree.model.data.invoice.server.value.InvoiceTimeTypeDescriptionValue;
import com.echothree.model.data.invoice.server.value.InvoiceTimeTypeDetailValue;
import com.echothree.model.data.invoice.server.value.InvoiceTimeValue;
import com.echothree.model.data.invoice.server.value.InvoiceTypeDescriptionValue;
import com.echothree.model.data.invoice.server.value.InvoiceTypeDetailValue;
import com.echothree.model.data.item.common.pk.ItemPK;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.server.entity.BillingAccount;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureTypePK;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvoiceControl
        extends BaseModelControl {
    
    /** Creates a new instance of InvoiceControl */
    public InvoiceControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Transfer Caches
    // --------------------------------------------------------------------------------
    
    private InvoiceTransferCaches invoiceTransferCaches = null;
    
    public InvoiceTransferCaches getInvoiceTransferCaches(UserVisit userVisit) {
        if(invoiceTransferCaches == null) {
            invoiceTransferCaches = new InvoiceTransferCaches(userVisit, this);
        }
        
        return invoiceTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Use Types
    // --------------------------------------------------------------------------------
    
    public InvoiceLineUseType createInvoiceLineUseType(String invoiceLineUseTypeName) {
        return InvoiceLineUseTypeFactory.getInstance().create(invoiceLineUseTypeName);
    }
    
    public List<InvoiceLineUseType> getInvoiceLineUseTypes() {
        PreparedStatement ps = InvoiceLineUseTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM invoicelineusetypes " +
                "ORDER BY invclut_invoicelineusetypename");
        
        return InvoiceLineUseTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public InvoiceLineUseType getInvoiceLineUseTypeByName(String invoiceLineUseTypeName) {
        InvoiceLineUseType invoiceLineUseType = null;
        
        try {
            PreparedStatement ps = InvoiceLineUseTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM invoicelineusetypes " +
                    "WHERE invclut_invoicelineusetypename = ?");
            
            ps.setString(1, invoiceLineUseTypeName);
            
            
            invoiceLineUseType = InvoiceLineUseTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceLineUseType;
    }
    
    public InvoiceLineUseTypeTransfer getInvoiceLineUseTypeTransfer(UserVisit userVisit,
            InvoiceLineUseType invoiceLineUseType) {
        return getInvoiceTransferCaches(userVisit).getInvoiceLineUseTypeTransferCache().getInvoiceLineUseTypeTransfer(invoiceLineUseType);
    }
    
    private List<InvoiceLineUseTypeTransfer> getInvoiceLineUseTypeTransfers(final UserVisit userVisit, final List<InvoiceLineUseType> invoiceLineUseTypes) {
        List<InvoiceLineUseTypeTransfer> invoiceLineUseTypeTransfers = new ArrayList<>(invoiceLineUseTypes.size());
        InvoiceLineUseTypeTransferCache invoiceLineUseTypeTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceLineUseTypeTransferCache();
        
        invoiceLineUseTypes.stream().forEach((invoiceLineUseType) -> {
            invoiceLineUseTypeTransfers.add(invoiceLineUseTypeTransferCache.getInvoiceLineUseTypeTransfer(invoiceLineUseType));
        });
        
        return invoiceLineUseTypeTransfers;
    }
    
    public List<InvoiceLineUseTypeTransfer> getInvoiceLineUseTypeTransfers(UserVisit userVisit) {
        return getInvoiceLineUseTypeTransfers(userVisit, getInvoiceLineUseTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Use Type Description
    // --------------------------------------------------------------------------------
    
    public InvoiceLineUseTypeDescription createInvoiceLineUseTypeDescription(InvoiceLineUseType invoiceLineUseType, Language language, String description) {
        return InvoiceLineUseTypeDescriptionFactory.getInstance().create(invoiceLineUseType, language, description);
    }
    
    public InvoiceLineUseTypeDescription getInvoiceLineUseTypeDescription(InvoiceLineUseType invoiceLineUseType, Language language) {
        InvoiceLineUseTypeDescription invoiceLineUseTypeDescription = null;
        
        try {
            PreparedStatement ps = InvoiceLineUseTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM invoicelineusetypedescriptions " +
                    "WHERE invclutd_invclut_invoicelineusetypeid = ? AND invclutd_lang_languageid = ?");
            
            ps.setLong(1, invoiceLineUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            invoiceLineUseTypeDescription = InvoiceLineUseTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceLineUseTypeDescription;
    }
    
    public String getBestInvoiceLineUseTypeDescription(InvoiceLineUseType invoiceLineUseType, Language language) {
        String description;
        InvoiceLineUseTypeDescription invoiceLineUseTypeDescription = getInvoiceLineUseTypeDescription(invoiceLineUseType, language);
        
        if(invoiceLineUseTypeDescription == null && !language.getIsDefault()) {
            invoiceLineUseTypeDescription = getInvoiceLineUseTypeDescription(invoiceLineUseType, getPartyControl().getDefaultLanguage());
        }
        
        if(invoiceLineUseTypeDescription == null) {
            description = invoiceLineUseType.getInvoiceLineUseTypeName();
        } else {
            description = invoiceLineUseTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Role Types
    // --------------------------------------------------------------------------------
    
    public InvoiceRoleType createInvoiceRoleType(String invoiceRoleTypeName, Integer sortOrder) {
        return InvoiceRoleTypeFactory.getInstance().create(invoiceRoleTypeName, sortOrder);
    }
    
    public List<InvoiceRoleType> getInvoiceRoleTypes() {
        PreparedStatement ps = InvoiceRoleTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM invoiceroletypes " +
                "ORDER BY invcrtyp_sortorder, invcrtyp_invoiceroletypename");
        
        return InvoiceRoleTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public InvoiceRoleType getInvoiceRoleTypeByName(String invoiceRoleTypeName) {
        InvoiceRoleType invoiceRoleType = null;
        
        try {
            PreparedStatement ps = InvoiceRoleTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM invoiceroletypes " +
                    "WHERE invcrtyp_invoiceroletypename = ?");
            
            ps.setString(1, invoiceRoleTypeName);
            
            
            invoiceRoleType = InvoiceRoleTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceRoleType;
    }
    
    public InvoiceRoleTypeTransfer getInvoiceRoleTypeTransfer(UserVisit userVisit,
            InvoiceRoleType invoiceRoleType) {
        return getInvoiceTransferCaches(userVisit).getInvoiceRoleTypeTransferCache().getInvoiceRoleTypeTransfer(invoiceRoleType);
    }
    
    private List<InvoiceRoleTypeTransfer> getInvoiceRoleTypeTransfers(final UserVisit userVisit, final List<InvoiceRoleType> invoiceRoleTypes) {
        List<InvoiceRoleTypeTransfer> invoiceRoleTypeTransfers = new ArrayList<>(invoiceRoleTypes.size());
        InvoiceRoleTypeTransferCache invoiceRoleTypeTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceRoleTypeTransferCache();
        
        invoiceRoleTypes.stream().forEach((invoiceRoleType) -> {
            invoiceRoleTypeTransfers.add(invoiceRoleTypeTransferCache.getInvoiceRoleTypeTransfer(invoiceRoleType));
        });

            return invoiceRoleTypeTransfers;
    }
    
    public List<InvoiceRoleTypeTransfer> getInvoiceRoleTypeTransfers(UserVisit userVisit) {
        return getInvoiceRoleTypeTransfers(userVisit, getInvoiceRoleTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Role Type Description
    // --------------------------------------------------------------------------------
    
    public InvoiceRoleTypeDescription createInvoiceRoleTypeDescription(InvoiceRoleType invoiceRoleType, Language language, String description) {
        return InvoiceRoleTypeDescriptionFactory.getInstance().create(invoiceRoleType, language, description);
    }
    
    public InvoiceRoleTypeDescription getInvoiceRoleTypeDescription(InvoiceRoleType invoiceRoleType, Language language) {
        InvoiceRoleTypeDescription invoiceRoleTypeDescription = null;
        
        try {
            PreparedStatement ps = InvoiceRoleTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM invoiceroletypedescriptions " +
                    "WHERE invcrtypd_invcrtyp_invoiceroletypeid = ? AND invcrtypd_lang_languageid = ?");
            
            ps.setLong(1, invoiceRoleType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            invoiceRoleTypeDescription = InvoiceRoleTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceRoleTypeDescription;
    }
    
    public String getBestInvoiceRoleTypeDescription(InvoiceRoleType invoiceRoleType, Language language) {
        String description;
        InvoiceRoleTypeDescription invoiceRoleTypeDescription = getInvoiceRoleTypeDescription(invoiceRoleType, language);
        
        if(invoiceRoleTypeDescription == null && !language.getIsDefault()) {
            invoiceRoleTypeDescription = getInvoiceRoleTypeDescription(invoiceRoleType, getPartyControl().getDefaultLanguage());
        }
        
        if(invoiceRoleTypeDescription == null) {
            description = invoiceRoleType.getInvoiceRoleTypeName();
        } else {
            description = invoiceRoleTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Types
    // --------------------------------------------------------------------------------
    
    public InvoiceType createInvoiceType(String invoiceTypeName, InvoiceType parentInvoiceType, SequenceType invoiceSequenceType,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        InvoiceType defaultInvoiceType = getDefaultInvoiceType();
        boolean defaultFound = defaultInvoiceType != null;
        
        if(defaultFound && isDefault) {
            InvoiceTypeDetailValue defaultInvoiceTypeDetailValue = getDefaultInvoiceTypeDetailValueForUpdate();
            
            defaultInvoiceTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateInvoiceTypeFromValue(defaultInvoiceTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        InvoiceType invoiceType = InvoiceTypeFactory.getInstance().create();
        InvoiceTypeDetail invoiceTypeDetail = InvoiceTypeDetailFactory.getInstance().create(invoiceType, invoiceTypeName,
                parentInvoiceType, invoiceSequenceType, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        invoiceType = InvoiceTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                invoiceType.getPrimaryKey());
        invoiceType.setActiveDetail(invoiceTypeDetail);
        invoiceType.setLastDetail(invoiceTypeDetail);
        invoiceType.store();
        
        sendEventUsingNames(invoiceType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return invoiceType;
    }
    
    private static final Map<EntityPermission, String> getInvoiceTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetypes, invoicetypedetails " +
                "WHERE invctyp_activedetailid = invctypdt_invoicetypedetailid " +
                "AND invctypdt_invoicetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetypes, invoicetypedetails " +
                "WHERE invctyp_activedetailid = invctypdt_invoicetypedetailid " +
                "AND invctypdt_invoicetypename = ? " +
                "FOR UPDATE");
        getInvoiceTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private InvoiceType getInvoiceTypeByName(String invoiceTypeName, EntityPermission entityPermission) {
        return InvoiceTypeFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceTypeByNameQueries, invoiceTypeName);
    }

    public InvoiceType getInvoiceTypeByName(String invoiceTypeName) {
        return getInvoiceTypeByName(invoiceTypeName, EntityPermission.READ_ONLY);
    }

    public InvoiceType getInvoiceTypeByNameForUpdate(String invoiceTypeName) {
        return getInvoiceTypeByName(invoiceTypeName, EntityPermission.READ_WRITE);
    }

    public InvoiceTypeDetailValue getInvoiceTypeDetailValueForUpdate(InvoiceType invoiceType) {
        return invoiceType == null? null: invoiceType.getLastDetailForUpdate().getInvoiceTypeDetailValue().clone();
    }

    public InvoiceTypeDetailValue getInvoiceTypeDetailValueByNameForUpdate(String invoiceTypeName) {
        return getInvoiceTypeDetailValueForUpdate(getInvoiceTypeByNameForUpdate(invoiceTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultInvoiceTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetypes, invoicetypedetails " +
                "WHERE invctyp_activedetailid = invctypdt_invoicetypedetailid " +
                "AND invctypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetypes, invoicetypedetails " +
                "WHERE invctyp_activedetailid = invctypdt_invoicetypedetailid " +
                "AND invctypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultInvoiceTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private InvoiceType getDefaultInvoiceType(EntityPermission entityPermission) {
        return InvoiceTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultInvoiceTypeQueries);
    }

    public InvoiceType getDefaultInvoiceType() {
        return getDefaultInvoiceType(EntityPermission.READ_ONLY);
    }

    public InvoiceType getDefaultInvoiceTypeForUpdate() {
        return getDefaultInvoiceType(EntityPermission.READ_WRITE);
    }

    public InvoiceTypeDetailValue getDefaultInvoiceTypeDetailValueForUpdate() {
        return getDefaultInvoiceTypeForUpdate().getLastDetailForUpdate().getInvoiceTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getInvoiceTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetypes, invoicetypedetails " +
                "WHERE invctyp_activedetailid = invctypdt_invoicetypedetailid " +
                "ORDER BY invctypdt_sortorder, invctypdt_invoicetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetypes, invoicetypedetails " +
                "WHERE invctyp_activedetailid = invctypdt_invoicetypedetailid " +
                "FOR UPDATE");
        getInvoiceTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InvoiceType> getInvoiceTypes(EntityPermission entityPermission) {
        return InvoiceTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceTypesQueries);
    }

    public List<InvoiceType> getInvoiceTypes() {
        return getInvoiceTypes(EntityPermission.READ_ONLY);
    }

    public List<InvoiceType> getInvoiceTypesForUpdate() {
        return getInvoiceTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getInvoiceTypesByParentInvoiceTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetypes, invoicetypedetails " +
                "WHERE invctyp_activedetailid = invctypdt_invoicetypedetailid AND invctypdt_parentinvoicetypeid = ? " +
                "ORDER BY invctypdt_sortorder, invctypdt_invoicetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetypes, invoicetypedetails " +
                "WHERE invctyp_activedetailid = invctypdt_invoicetypedetailid AND invctypdt_parentinvoicetypeid = ? " +
                "FOR UPDATE");
        getInvoiceTypesByParentInvoiceTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InvoiceType> getInvoiceTypesByParentInvoiceType(InvoiceType parentInvoiceType,
            EntityPermission entityPermission) {
        return InvoiceTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceTypesByParentInvoiceTypeQueries,
                parentInvoiceType);
    }

    public List<InvoiceType> getInvoiceTypesByParentInvoiceType(InvoiceType parentInvoiceType) {
        return getInvoiceTypesByParentInvoiceType(parentInvoiceType, EntityPermission.READ_ONLY);
    }

    public List<InvoiceType> getInvoiceTypesByParentInvoiceTypeForUpdate(InvoiceType parentInvoiceType) {
        return getInvoiceTypesByParentInvoiceType(parentInvoiceType, EntityPermission.READ_WRITE);
    }

    public InvoiceTypeTransfer getInvoiceTypeTransfer(UserVisit userVisit, InvoiceType invoiceType) {
        return getInvoiceTransferCaches(userVisit).getInvoiceTypeTransferCache().getInvoiceTypeTransfer(invoiceType);
    }
    
    public List<InvoiceTypeTransfer> getInvoiceTypeTransfers(UserVisit userVisit) {
        List<InvoiceType> invoiceTypes = getInvoiceTypes();
        List<InvoiceTypeTransfer> invoiceTypeTransfers = new ArrayList<>(invoiceTypes.size());
        InvoiceTypeTransferCache invoiceTypeTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceTypeTransferCache();
        
        invoiceTypes.stream().forEach((invoiceType) -> {
            invoiceTypeTransfers.add(invoiceTypeTransferCache.getInvoiceTypeTransfer(invoiceType));
        });
        
        return invoiceTypeTransfers;
    }
    
    public InvoiceTypeChoicesBean getInvoiceTypeChoices(String defaultInvoiceTypeChoice, Language language,
            boolean allowNullChoice) {
        List<InvoiceType> invoiceTypes = getInvoiceTypes();
        int size = invoiceTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInvoiceTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(InvoiceType invoiceType: invoiceTypes) {
            InvoiceTypeDetail invoiceTypeDetail = invoiceType.getLastDetail();
            
            String label = getBestInvoiceTypeDescription(invoiceType, language);
            String value = invoiceTypeDetail.getInvoiceTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultInvoiceTypeChoice == null? false: defaultInvoiceTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && invoiceTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new InvoiceTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentInvoiceTypeSafe(InvoiceType invoiceType, InvoiceType parentInvoiceType) {
        boolean safe = true;
        
        if(parentInvoiceType != null) {
            Set<InvoiceType> parentItemPurchasingCategorys = new HashSet<>();
            
            parentItemPurchasingCategorys.add(invoiceType);
            do {
                if(parentItemPurchasingCategorys.contains(parentInvoiceType)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategorys.add(parentInvoiceType);
                parentInvoiceType = parentInvoiceType.getLastDetail().getParentInvoiceType();
            } while(parentInvoiceType != null);
        }
        
        return safe;
    }
    
    private void updateInvoiceTypeFromValue(InvoiceTypeDetailValue invoiceTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(invoiceTypeDetailValue.hasBeenModified()) {
            InvoiceType invoiceType = InvoiceTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceTypeDetailValue.getInvoiceTypePK());
            InvoiceTypeDetail invoiceTypeDetail = invoiceType.getActiveDetailForUpdate();
            
            invoiceTypeDetail.setThruTime(session.START_TIME_LONG);
            invoiceTypeDetail.store();
            
            InvoiceTypePK invoiceTypePK = invoiceTypeDetail.getInvoiceTypePK();
            String invoiceTypeName = invoiceTypeDetailValue.getInvoiceTypeName();
            InvoiceTypePK parentInvoiceTypePK = invoiceTypeDetailValue.getParentInvoiceTypePK();
            SequenceTypePK invoiceSequenceTypePK = invoiceTypeDetailValue.getInvoiceSequenceTypePK();
            Boolean isDefault = invoiceTypeDetailValue.getIsDefault();
            Integer sortOrder = invoiceTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                InvoiceType defaultInvoiceType = getDefaultInvoiceType();
                boolean defaultFound = defaultInvoiceType != null && !defaultInvoiceType.equals(invoiceType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    InvoiceTypeDetailValue defaultInvoiceTypeDetailValue = getDefaultInvoiceTypeDetailValueForUpdate();
                    
                    defaultInvoiceTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateInvoiceTypeFromValue(defaultInvoiceTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            invoiceTypeDetail = InvoiceTypeDetailFactory.getInstance().create(invoiceTypePK, invoiceTypeName,
                    parentInvoiceTypePK, invoiceSequenceTypePK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            invoiceType.setActiveDetail(invoiceTypeDetail);
            invoiceType.setLastDetail(invoiceTypeDetail);
            
            sendEventUsingNames(invoiceTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateInvoiceTypeFromValue(InvoiceTypeDetailValue invoiceTypeDetailValue, BasePK updatedBy) {
        updateInvoiceTypeFromValue(invoiceTypeDetailValue, true, updatedBy);
    }
    
    private void deleteInvoiceType(InvoiceType invoiceType, boolean checkDefault, BasePK deletedBy) {
        InvoiceTypeDetail invoiceTypeDetail = invoiceType.getLastDetailForUpdate();

        deleteInvoiceTypesByParentInvoiceType(invoiceType, deletedBy);
        deleteInvoiceLineTypesByInvoiceType(invoiceType, deletedBy);
        deleteInvoiceTypeDescriptionsByInvoiceType(invoiceType, deletedBy);
        
        invoiceTypeDetail.setThruTime(session.START_TIME_LONG);
        invoiceType.setActiveDetail(null);
        invoiceType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            InvoiceType defaultInvoiceType = getDefaultInvoiceType();

            if(defaultInvoiceType == null) {
                List<InvoiceType> invoiceTypes = getInvoiceTypesForUpdate();

                if(!invoiceTypes.isEmpty()) {
                    Iterator<InvoiceType> iter = invoiceTypes.iterator();
                    if(iter.hasNext()) {
                        defaultInvoiceType = iter.next();
                    }
                    InvoiceTypeDetailValue invoiceTypeDetailValue = defaultInvoiceType.getLastDetailForUpdate().getInvoiceTypeDetailValue().clone();

                    invoiceTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateInvoiceTypeFromValue(invoiceTypeDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEventUsingNames(invoiceType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteInvoiceType(InvoiceType invoiceType, BasePK deletedBy) {
        deleteInvoiceType(invoiceType, true, deletedBy);
    }

    private void deleteInvoiceTypes(List<InvoiceType> invoiceTypes, boolean checkDefault, BasePK deletedBy) {
        invoiceTypes.stream().forEach((invoiceType) -> {
            deleteInvoiceType(invoiceType, checkDefault, deletedBy);
        });
    }

    public void deleteInvoiceTypes(List<InvoiceType> invoiceTypes, BasePK deletedBy) {
        deleteInvoiceTypes(invoiceTypes, true, deletedBy);
    }

    private void deleteInvoiceTypesByParentInvoiceType(InvoiceType parentInvoiceType, BasePK deletedBy) {
        deleteInvoiceTypes(getInvoiceTypesByParentInvoiceTypeForUpdate(parentInvoiceType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Invoice Type Descriptions
    // --------------------------------------------------------------------------------
    
    public InvoiceTypeDescription createInvoiceTypeDescription(InvoiceType invoiceType, Language language, String description, BasePK createdBy) {
        InvoiceTypeDescription invoiceTypeDescription = InvoiceTypeDescriptionFactory.getInstance().create(invoiceType, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(invoiceType.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return invoiceTypeDescription;
    }
    
    private InvoiceTypeDescription getInvoiceTypeDescription(InvoiceType invoiceType, Language language, EntityPermission entityPermission) {
        InvoiceTypeDescription invoiceTypeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicetypedescriptions " +
                        "WHERE invctypd_invctyp_invoicetypeid = ? AND invctypd_lang_languageid = ? AND invctypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicetypedescriptions " +
                        "WHERE invctypd_invctyp_invoicetypeid = ? AND invctypd_lang_languageid = ? AND invctypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, invoiceType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            invoiceTypeDescription = InvoiceTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceTypeDescription;
    }
    
    public InvoiceTypeDescription getInvoiceTypeDescription(InvoiceType invoiceType, Language language) {
        return getInvoiceTypeDescription(invoiceType, language, EntityPermission.READ_ONLY);
    }
    
    public InvoiceTypeDescription getInvoiceTypeDescriptionForUpdate(InvoiceType invoiceType, Language language) {
        return getInvoiceTypeDescription(invoiceType, language, EntityPermission.READ_WRITE);
    }
    
    public InvoiceTypeDescriptionValue getInvoiceTypeDescriptionValue(InvoiceTypeDescription invoiceTypeDescription) {
        return invoiceTypeDescription == null? null: invoiceTypeDescription.getInvoiceTypeDescriptionValue().clone();
    }
    
    public InvoiceTypeDescriptionValue getInvoiceTypeDescriptionValueForUpdate(InvoiceType invoiceType, Language language) {
        return getInvoiceTypeDescriptionValue(getInvoiceTypeDescriptionForUpdate(invoiceType, language));
    }
    
    private List<InvoiceTypeDescription> getInvoiceTypeDescriptionsByInvoiceType(InvoiceType invoiceType, EntityPermission entityPermission) {
        List<InvoiceTypeDescription> invoiceTypeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicetypedescriptions, languages " +
                        "WHERE invctypd_invctyp_invoicetypeid = ? AND invctypd_thrutime = ? AND invctypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicetypedescriptions " +
                        "WHERE invctypd_invctyp_invoicetypeid = ? AND invctypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, invoiceType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            invoiceTypeDescriptions = InvoiceTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceTypeDescriptions;
    }
    
    public List<InvoiceTypeDescription> getInvoiceTypeDescriptionsByInvoiceType(InvoiceType invoiceType) {
        return getInvoiceTypeDescriptionsByInvoiceType(invoiceType, EntityPermission.READ_ONLY);
    }
    
    public List<InvoiceTypeDescription> getInvoiceTypeDescriptionsByInvoiceTypeForUpdate(InvoiceType invoiceType) {
        return getInvoiceTypeDescriptionsByInvoiceType(invoiceType, EntityPermission.READ_WRITE);
    }
    
    public String getBestInvoiceTypeDescription(InvoiceType invoiceType, Language language) {
        String description;
        InvoiceTypeDescription invoiceTypeDescription = getInvoiceTypeDescription(invoiceType, language);
        
        if(invoiceTypeDescription == null && !language.getIsDefault()) {
            invoiceTypeDescription = getInvoiceTypeDescription(invoiceType, getPartyControl().getDefaultLanguage());
        }
        
        if(invoiceTypeDescription == null) {
            description = invoiceType.getLastDetail().getInvoiceTypeName();
        } else {
            description = invoiceTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public InvoiceTypeDescriptionTransfer getInvoiceTypeDescriptionTransfer(UserVisit userVisit, InvoiceTypeDescription invoiceTypeDescription) {
        return getInvoiceTransferCaches(userVisit).getInvoiceTypeDescriptionTransferCache().getInvoiceTypeDescriptionTransfer(invoiceTypeDescription);
    }
    
    public List<InvoiceTypeDescriptionTransfer> getInvoiceTypeDescriptionTransfersByInvoiceType(UserVisit userVisit, InvoiceType invoiceType) {
        List<InvoiceTypeDescription> invoiceTypeDescriptions = getInvoiceTypeDescriptionsByInvoiceType(invoiceType);
        List<InvoiceTypeDescriptionTransfer> invoiceTypeDescriptionTransfers = new ArrayList<>(invoiceTypeDescriptions.size());
        InvoiceTypeDescriptionTransferCache invoiceTypeDescriptionTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceTypeDescriptionTransferCache();
        
        invoiceTypeDescriptions.stream().forEach((invoiceTypeDescription) -> {
            invoiceTypeDescriptionTransfers.add(invoiceTypeDescriptionTransferCache.getInvoiceTypeDescriptionTransfer(invoiceTypeDescription));
        });
        
        return invoiceTypeDescriptionTransfers;
    }
    
    public void updateInvoiceTypeDescriptionFromValue(InvoiceTypeDescriptionValue invoiceTypeDescriptionValue, BasePK updatedBy) {
        if(invoiceTypeDescriptionValue.hasBeenModified()) {
            InvoiceTypeDescription invoiceTypeDescription = InvoiceTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceTypeDescriptionValue.getPrimaryKey());
            
            invoiceTypeDescription.setThruTime(session.START_TIME_LONG);
            invoiceTypeDescription.store();
            
            InvoiceType invoiceType = invoiceTypeDescription.getInvoiceType();
            Language language = invoiceTypeDescription.getLanguage();
            String description = invoiceTypeDescriptionValue.getDescription();
            
            invoiceTypeDescription = InvoiceTypeDescriptionFactory.getInstance().create(invoiceType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(invoiceType.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteInvoiceTypeDescription(InvoiceTypeDescription invoiceTypeDescription, BasePK deletedBy) {
        invoiceTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(invoiceTypeDescription.getInvoiceTypePK(), EventTypes.MODIFY.name(), invoiceTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteInvoiceTypeDescriptionsByInvoiceType(InvoiceType invoiceType, BasePK deletedBy) {
        List<InvoiceTypeDescription> invoiceTypeDescriptions = getInvoiceTypeDescriptionsByInvoiceTypeForUpdate(invoiceType);
        
        invoiceTypeDescriptions.stream().forEach((invoiceTypeDescription) -> {
            deleteInvoiceTypeDescription(invoiceTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Alias Types
    // --------------------------------------------------------------------------------
    
    public InvoiceAliasType createInvoiceAliasType(InvoiceType invoiceType, String invoiceAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        InvoiceAliasType defaultInvoiceAliasType = getDefaultInvoiceAliasType(invoiceType);
        boolean defaultFound = defaultInvoiceAliasType != null;
        
        if(defaultFound && isDefault) {
            InvoiceAliasTypeDetailValue defaultInvoiceAliasTypeDetailValue = getDefaultInvoiceAliasTypeDetailValueForUpdate(invoiceType);
            
            defaultInvoiceAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateInvoiceAliasTypeFromValue(defaultInvoiceAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        InvoiceAliasType invoiceAliasType = InvoiceAliasTypeFactory.getInstance().create();
        InvoiceAliasTypeDetail invoiceAliasTypeDetail = InvoiceAliasTypeDetailFactory.getInstance().create(invoiceAliasType, invoiceType, invoiceAliasTypeName,
                validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        invoiceAliasType = InvoiceAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceAliasType.getPrimaryKey());
        invoiceAliasType.setActiveDetail(invoiceAliasTypeDetail);
        invoiceAliasType.setLastDetail(invoiceAliasTypeDetail);
        invoiceAliasType.store();
        
        sendEventUsingNames(invoiceAliasType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return invoiceAliasType;
    }
    
    private static final Map<EntityPermission, String> getInvoiceAliasTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicealiastypes, invoicealiastypedetails " +
                "WHERE invcat_activedetailid = invcatdt_invoicealiastypedetailid AND invcatdt_geot_invoicetypeid = ? " +
                "AND invcatdt_invoicealiastypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicealiastypes, invoicealiastypedetails " +
                "WHERE invcat_activedetailid = invcatdt_invoicealiastypedetailid AND invcatdt_geot_invoicetypeid = ? " +
                "AND invcatdt_invoicealiastypename = ? " +
                "FOR UPDATE");
        getInvoiceAliasTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private InvoiceAliasType getInvoiceAliasTypeByName(InvoiceType invoiceType, String invoiceAliasTypeName, EntityPermission entityPermission) {
        return InvoiceAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceAliasTypeByNameQueries,
                invoiceType, invoiceAliasTypeName);
    }
    
    public InvoiceAliasType getInvoiceAliasTypeByName(InvoiceType invoiceType, String invoiceAliasTypeName) {
        return getInvoiceAliasTypeByName(invoiceType, invoiceAliasTypeName, EntityPermission.READ_ONLY);
    }
    
    public InvoiceAliasType getInvoiceAliasTypeByNameForUpdate(InvoiceType invoiceType, String invoiceAliasTypeName) {
        return getInvoiceAliasTypeByName(invoiceType, invoiceAliasTypeName, EntityPermission.READ_WRITE);
    }
    
    public InvoiceAliasTypeDetailValue getInvoiceAliasTypeDetailValueForUpdate(InvoiceAliasType invoiceAliasType) {
        return invoiceAliasType == null? null: invoiceAliasType.getLastDetailForUpdate().getInvoiceAliasTypeDetailValue().clone();
    }
    
    public InvoiceAliasTypeDetailValue getInvoiceAliasTypeDetailValueByNameForUpdate(InvoiceType invoiceType,
            String invoiceAliasTypeName) {
        return getInvoiceAliasTypeDetailValueForUpdate(getInvoiceAliasTypeByNameForUpdate(invoiceType, invoiceAliasTypeName));
    }
    
    private static final Map<EntityPermission, String> getDefaultInvoiceAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicealiastypes, invoicealiastypedetails " +
                "WHERE invcat_activedetailid = invcatdt_invoicealiastypedetailid AND invcatdt_geot_invoicetypeid = ? " +
                "AND invcatdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicealiastypes, invoicealiastypedetails " +
                "WHERE invcat_activedetailid = invcatdt_invoicealiastypedetailid AND invcatdt_geot_invoicetypeid = ? " +
                "AND invcatdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultInvoiceAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private InvoiceAliasType getDefaultInvoiceAliasType(InvoiceType invoiceType, EntityPermission entityPermission) {
        return InvoiceAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultInvoiceAliasTypeQueries, invoiceType);
    }
    
    public InvoiceAliasType getDefaultInvoiceAliasType(InvoiceType invoiceType) {
        return getDefaultInvoiceAliasType(invoiceType, EntityPermission.READ_ONLY);
    }
    
    public InvoiceAliasType getDefaultInvoiceAliasTypeForUpdate(InvoiceType invoiceType) {
        return getDefaultInvoiceAliasType(invoiceType, EntityPermission.READ_WRITE);
    }
    
    public InvoiceAliasTypeDetailValue getDefaultInvoiceAliasTypeDetailValueForUpdate(InvoiceType invoiceType) {
        return getDefaultInvoiceAliasTypeForUpdate(invoiceType).getLastDetailForUpdate().getInvoiceAliasTypeDetailValue().clone();
    }
    
    private static final Map<EntityPermission, String> getInvoiceAliasTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicealiastypes, invoicealiastypedetails " +
                "WHERE invcat_activedetailid = invcatdt_invoicealiastypedetailid AND invcatdt_geot_invoicetypeid = ? " +
                "ORDER BY invcatdt_sortorder, invcatdt_invoicealiastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicealiastypes, invoicealiastypedetails " +
                "WHERE invcat_activedetailid = invcatdt_invoicealiastypedetailid AND invcatdt_geot_invoicetypeid = ? " +
                "FOR UPDATE");
        getInvoiceAliasTypesQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<InvoiceAliasType> getInvoiceAliasTypes(InvoiceType invoiceType, EntityPermission entityPermission) {
        return InvoiceAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceAliasTypesQueries, invoiceType);
    }
    
    public List<InvoiceAliasType> getInvoiceAliasTypes(InvoiceType invoiceType) {
        return getInvoiceAliasTypes(invoiceType, EntityPermission.READ_ONLY);
    }
    
    public List<InvoiceAliasType> getInvoiceAliasTypesForUpdate(InvoiceType invoiceType) {
        return getInvoiceAliasTypes(invoiceType, EntityPermission.READ_WRITE);
    }
    
    public InvoiceAliasTypeTransfer getInvoiceAliasTypeTransfer(UserVisit userVisit, InvoiceAliasType invoiceAliasType) {
        return getInvoiceTransferCaches(userVisit).getInvoiceAliasTypeTransferCache().getInvoiceAliasTypeTransfer(invoiceAliasType);
    }
    
    public List<InvoiceAliasTypeTransfer> getInvoiceAliasTypeTransfers(UserVisit userVisit, InvoiceType invoiceType) {
        List<InvoiceAliasType> invoiceAliasTypes = getInvoiceAliasTypes(invoiceType);
        List<InvoiceAliasTypeTransfer> invoiceAliasTypeTransfers = new ArrayList<>(invoiceAliasTypes.size());
        InvoiceAliasTypeTransferCache invoiceAliasTypeTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceAliasTypeTransferCache();
        
        invoiceAliasTypes.stream().forEach((invoiceAliasType) -> {
            invoiceAliasTypeTransfers.add(invoiceAliasTypeTransferCache.getInvoiceAliasTypeTransfer(invoiceAliasType));
        });
        
        return invoiceAliasTypeTransfers;
    }
    
    public InvoiceAliasTypeChoicesBean getInvoiceAliasTypeChoices(String defaultInvoiceAliasTypeChoice, Language language,
            boolean allowNullChoice, InvoiceType invoiceType) {
        List<InvoiceAliasType> invoiceAliasTypes = getInvoiceAliasTypes(invoiceType);
        int size = invoiceAliasTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInvoiceAliasTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(InvoiceAliasType invoiceAliasType: invoiceAliasTypes) {
            InvoiceAliasTypeDetail invoiceAliasTypeDetail = invoiceAliasType.getLastDetail();
            
            String label = getBestInvoiceAliasTypeDescription(invoiceAliasType, language);
            String value = invoiceAliasTypeDetail.getInvoiceAliasTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultInvoiceAliasTypeChoice == null? false: defaultInvoiceAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && invoiceAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new InvoiceAliasTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateInvoiceAliasTypeFromValue(InvoiceAliasTypeDetailValue invoiceAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(invoiceAliasTypeDetailValue.hasBeenModified()) {
            InvoiceAliasType invoiceAliasType = InvoiceAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    invoiceAliasTypeDetailValue.getInvoiceAliasTypePK());
            InvoiceAliasTypeDetail invoiceAliasTypeDetail = invoiceAliasType.getActiveDetailForUpdate();
            
            invoiceAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            invoiceAliasTypeDetail.store();
            
            InvoiceAliasTypePK invoiceAliasTypePK = invoiceAliasTypeDetail.getInvoiceAliasTypePK();
            InvoiceType invoiceType = invoiceAliasTypeDetail.getInvoiceType();
            InvoiceTypePK invoiceTypePK = invoiceType.getPrimaryKey();
            String invoiceAliasTypeName = invoiceAliasTypeDetailValue.getInvoiceAliasTypeName();
            String validationPattern = invoiceAliasTypeDetailValue.getValidationPattern();
            Boolean isDefault = invoiceAliasTypeDetailValue.getIsDefault();
            Integer sortOrder = invoiceAliasTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                InvoiceAliasType defaultInvoiceAliasType = getDefaultInvoiceAliasType(invoiceType);
                boolean defaultFound = defaultInvoiceAliasType != null && !defaultInvoiceAliasType.equals(invoiceAliasType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    InvoiceAliasTypeDetailValue defaultInvoiceAliasTypeDetailValue = getDefaultInvoiceAliasTypeDetailValueForUpdate(invoiceType);
                    
                    defaultInvoiceAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateInvoiceAliasTypeFromValue(defaultInvoiceAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            invoiceAliasTypeDetail = InvoiceAliasTypeDetailFactory.getInstance().create(invoiceAliasTypePK, invoiceTypePK, invoiceAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            invoiceAliasType.setActiveDetail(invoiceAliasTypeDetail);
            invoiceAliasType.setLastDetail(invoiceAliasTypeDetail);
            
            sendEventUsingNames(invoiceAliasTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateInvoiceAliasTypeFromValue(InvoiceAliasTypeDetailValue invoiceAliasTypeDetailValue, BasePK updatedBy) {
        updateInvoiceAliasTypeFromValue(invoiceAliasTypeDetailValue, true, updatedBy);
    }
    
    public void deleteInvoiceAliasType(InvoiceAliasType invoiceAliasType, BasePK deletedBy) {
        deleteInvoiceAliasesByInvoiceAliasType(invoiceAliasType, deletedBy);
        deleteInvoiceAliasTypeDescriptionsByInvoiceAliasType(invoiceAliasType, deletedBy);
        
        InvoiceAliasTypeDetail invoiceAliasTypeDetail = invoiceAliasType.getLastDetailForUpdate();
        invoiceAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        invoiceAliasType.setActiveDetail(null);
        invoiceAliasType.store();
        
        // Check for default, and pick one if necessary
        InvoiceType invoiceType = invoiceAliasTypeDetail.getInvoiceType();
        InvoiceAliasType defaultInvoiceAliasType = getDefaultInvoiceAliasType(invoiceType);
        if(defaultInvoiceAliasType == null) {
            List<InvoiceAliasType> invoiceAliasTypes = getInvoiceAliasTypesForUpdate(invoiceType);
            
            if(!invoiceAliasTypes.isEmpty()) {
                Iterator<InvoiceAliasType> iter = invoiceAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultInvoiceAliasType = iter.next();
                }
                InvoiceAliasTypeDetailValue invoiceAliasTypeDetailValue = defaultInvoiceAliasType.getLastDetailForUpdate().getInvoiceAliasTypeDetailValue().clone();
                
                invoiceAliasTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateInvoiceAliasTypeFromValue(invoiceAliasTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(invoiceAliasType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteInvoiceAliasTypes(List<InvoiceAliasType> invoiceAliasTypes, BasePK deletedBy) {
        invoiceAliasTypes.stream().forEach((invoiceAliasType) -> {
            deleteInvoiceAliasType(invoiceAliasType, deletedBy);
        });
    }
    
    public void deleteInvoiceAliasTypesByInvoiceType(InvoiceType invoiceType, BasePK deletedBy) {
        deleteInvoiceAliasTypes(getInvoiceAliasTypesForUpdate(invoiceType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Time Types
    // --------------------------------------------------------------------------------

    public InvoiceTimeType createInvoiceTimeType(InvoiceType invoiceType, String invoiceTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        InvoiceTimeType defaultInvoiceTimeType = getDefaultInvoiceTimeType(invoiceType);
        boolean defaultFound = defaultInvoiceTimeType != null;

        if(defaultFound && isDefault) {
            InvoiceTimeTypeDetailValue defaultInvoiceTimeTypeDetailValue = getDefaultInvoiceTimeTypeDetailValueForUpdate(invoiceType);

            defaultInvoiceTimeTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateInvoiceTimeTypeFromValue(defaultInvoiceTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        InvoiceTimeType invoiceTimeType = InvoiceTimeTypeFactory.getInstance().create();
        InvoiceTimeTypeDetail invoiceTimeTypeDetail = InvoiceTimeTypeDetailFactory.getInstance().create(invoiceTimeType, invoiceType, invoiceTimeTypeName, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        invoiceTimeType = InvoiceTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                invoiceTimeType.getPrimaryKey());
        invoiceTimeType.setActiveDetail(invoiceTimeTypeDetail);
        invoiceTimeType.setLastDetail(invoiceTimeTypeDetail);
        invoiceTimeType.store();

        sendEventUsingNames(invoiceTimeType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return invoiceTimeType;
    }

    private static final Map<EntityPermission, String> getInvoiceTimeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetimetypes, invoicetimetypedetails " +
                "WHERE invctimtyp_activedetailid = invctimtypdt_invoicetimetypedetailid " +
                "AND invctimtypdt_invctyp_invoicetypeid = ? AND invctimtypdt_invoicetimetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetimetypes, invoicetimetypedetails " +
                "WHERE invctimtyp_activedetailid = invctimtypdt_invoicetimetypedetailid " +
                "AND invctimtypdt_invctyp_invoicetypeid = ? AND invctimtypdt_invoicetimetypename = ? " +
                "FOR UPDATE");
        getInvoiceTimeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private InvoiceTimeType getInvoiceTimeTypeByName(InvoiceType invoiceType, String invoiceTimeTypeName, EntityPermission entityPermission) {
        return InvoiceTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceTimeTypeByNameQueries,
                invoiceType, invoiceTimeTypeName);
    }

    public InvoiceTimeType getInvoiceTimeTypeByName(InvoiceType invoiceType, String invoiceTimeTypeName) {
        return getInvoiceTimeTypeByName(invoiceType, invoiceTimeTypeName, EntityPermission.READ_ONLY);
    }

    public InvoiceTimeType getInvoiceTimeTypeByNameForUpdate(InvoiceType invoiceType, String invoiceTimeTypeName) {
        return getInvoiceTimeTypeByName(invoiceType, invoiceTimeTypeName, EntityPermission.READ_WRITE);
    }

    public InvoiceTimeTypeDetailValue getInvoiceTimeTypeDetailValueForUpdate(InvoiceTimeType invoiceTimeType) {
        return invoiceTimeType == null? null: invoiceTimeType.getLastDetailForUpdate().getInvoiceTimeTypeDetailValue().clone();
    }

    public InvoiceTimeTypeDetailValue getInvoiceTimeTypeDetailValueByNameForUpdate(InvoiceType invoiceType, String invoiceTimeTypeName) {
        return getInvoiceTimeTypeDetailValueForUpdate(getInvoiceTimeTypeByNameForUpdate(invoiceType, invoiceTimeTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultInvoiceTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetimetypes, invoicetimetypedetails " +
                "WHERE invctimtyp_activedetailid = invctimtypdt_invoicetimetypedetailid " +
                "AND invctimtypdt_invctyp_invoicetypeid = ? AND invctimtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetimetypes, invoicetimetypedetails " +
                "WHERE invctimtyp_activedetailid = invctimtypdt_invoicetimetypedetailid " +
                "AND invctimtypdt_invctyp_invoicetypeid = ? AND invctimtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultInvoiceTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private InvoiceTimeType getDefaultInvoiceTimeType(InvoiceType invoiceType, EntityPermission entityPermission) {
        return InvoiceTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultInvoiceTimeTypeQueries,
                invoiceType);
    }

    public InvoiceTimeType getDefaultInvoiceTimeType(InvoiceType invoiceType) {
        return getDefaultInvoiceTimeType(invoiceType, EntityPermission.READ_ONLY);
    }

    public InvoiceTimeType getDefaultInvoiceTimeTypeForUpdate(InvoiceType invoiceType) {
        return getDefaultInvoiceTimeType(invoiceType, EntityPermission.READ_WRITE);
    }

    public InvoiceTimeTypeDetailValue getDefaultInvoiceTimeTypeDetailValueForUpdate(InvoiceType invoiceType) {
        return getDefaultInvoiceTimeTypeForUpdate(invoiceType).getLastDetailForUpdate().getInvoiceTimeTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getInvoiceTimeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetimetypes, invoicetimetypedetails " +
                "WHERE invctimtyp_activedetailid = invctimtypdt_invoicetimetypedetailid " +
                "AND invctimtypdt_invctyp_invoicetypeid = ? " +
                "ORDER BY invctimtypdt_sortorder, invctimtypdt_invoicetimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetimetypes, invoicetimetypedetails " +
                "WHERE invctimtyp_activedetailid = invctimtypdt_invoicetimetypedetailid " +
                "AND invctimtypdt_invctyp_invoicetypeid = ? " +
                "FOR UPDATE");
        getInvoiceTimeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InvoiceTimeType> getInvoiceTimeTypes(InvoiceType invoiceType, EntityPermission entityPermission) {
        return InvoiceTimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceTimeTypesQueries,
                invoiceType);
    }

    public List<InvoiceTimeType> getInvoiceTimeTypes(InvoiceType invoiceType) {
        return getInvoiceTimeTypes(invoiceType, EntityPermission.READ_ONLY);
    }

    public List<InvoiceTimeType> getInvoiceTimeTypesForUpdate(InvoiceType invoiceType) {
        return getInvoiceTimeTypes(invoiceType, EntityPermission.READ_WRITE);
    }

    public InvoiceTimeTypeTransfer getInvoiceTimeTypeTransfer(UserVisit userVisit, InvoiceTimeType invoiceTimeType) {
        return getInvoiceTransferCaches(userVisit).getInvoiceTimeTypeTransferCache().getInvoiceTimeTypeTransfer(invoiceTimeType);
    }

    public List<InvoiceTimeTypeTransfer> getInvoiceTimeTypeTransfers(UserVisit userVisit, InvoiceType invoiceType) {
        List<InvoiceTimeType> invoiceTimeTypes = getInvoiceTimeTypes(invoiceType);
        List<InvoiceTimeTypeTransfer> invoiceTimeTypeTransfers = new ArrayList<>(invoiceTimeTypes.size());
        InvoiceTimeTypeTransferCache invoiceTimeTypeTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceTimeTypeTransferCache();

        invoiceTimeTypes.stream().forEach((invoiceTimeType) -> {
            invoiceTimeTypeTransfers.add(invoiceTimeTypeTransferCache.getInvoiceTimeTypeTransfer(invoiceTimeType));
        });

        return invoiceTimeTypeTransfers;
    }

    public InvoiceTimeTypeChoicesBean getInvoiceTimeTypeChoices(String defaultInvoiceTimeTypeChoice, Language language, boolean allowNullChoice,
            InvoiceType invoiceType) {
        List<InvoiceTimeType> invoiceTimeTypes = getInvoiceTimeTypes(invoiceType);
        int size = invoiceTimeTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInvoiceTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(InvoiceTimeType invoiceTimeType: invoiceTimeTypes) {
            InvoiceTimeTypeDetail invoiceTimeTypeDetail = invoiceTimeType.getLastDetail();

            String label = getBestInvoiceTimeTypeDescription(invoiceTimeType, language);
            String value = invoiceTimeTypeDetail.getInvoiceTimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultInvoiceTimeTypeChoice == null? false: defaultInvoiceTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && invoiceTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InvoiceTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateInvoiceTimeTypeFromValue(InvoiceTimeTypeDetailValue invoiceTimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(invoiceTimeTypeDetailValue.hasBeenModified()) {
            InvoiceTimeType invoiceTimeType = InvoiceTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceTimeTypeDetailValue.getInvoiceTimeTypePK());
            InvoiceTimeTypeDetail invoiceTimeTypeDetail = invoiceTimeType.getActiveDetailForUpdate();

            invoiceTimeTypeDetail.setThruTime(session.START_TIME_LONG);
            invoiceTimeTypeDetail.store();

            InvoiceType invoiceType = invoiceTimeTypeDetail.getInvoiceType(); // Not updated
            InvoiceTypePK invoiceTypePK = invoiceType.getPrimaryKey(); // Not updated
            InvoiceTimeTypePK invoiceTimeTypePK = invoiceTimeTypeDetail.getInvoiceTimeTypePK(); // Not updated
            String invoiceTimeTypeName = invoiceTimeTypeDetailValue.getInvoiceTimeTypeName();
            Boolean isDefault = invoiceTimeTypeDetailValue.getIsDefault();
            Integer sortOrder = invoiceTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                InvoiceTimeType defaultInvoiceTimeType = getDefaultInvoiceTimeType(invoiceType);
                boolean defaultFound = defaultInvoiceTimeType != null && !defaultInvoiceTimeType.equals(invoiceTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    InvoiceTimeTypeDetailValue defaultInvoiceTimeTypeDetailValue = getDefaultInvoiceTimeTypeDetailValueForUpdate(invoiceType);

                    defaultInvoiceTimeTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateInvoiceTimeTypeFromValue(defaultInvoiceTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            invoiceTimeTypeDetail = InvoiceTimeTypeDetailFactory.getInstance().create(invoiceTimeTypePK, invoiceTypePK, invoiceTimeTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            invoiceTimeType.setActiveDetail(invoiceTimeTypeDetail);
            invoiceTimeType.setLastDetail(invoiceTimeTypeDetail);

            sendEventUsingNames(invoiceTimeTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateInvoiceTimeTypeFromValue(InvoiceTimeTypeDetailValue invoiceTimeTypeDetailValue, BasePK updatedBy) {
        updateInvoiceTimeTypeFromValue(invoiceTimeTypeDetailValue, true, updatedBy);
    }

    public void deleteInvoiceTimeType(InvoiceTimeType invoiceTimeType, BasePK deletedBy) {
        deleteInvoiceTimesByInvoiceTimeType(invoiceTimeType, deletedBy);
        deleteInvoiceTimeTypeDescriptionsByInvoiceTimeType(invoiceTimeType, deletedBy);

        InvoiceTimeTypeDetail invoiceTimeTypeDetail = invoiceTimeType.getLastDetailForUpdate();
        invoiceTimeTypeDetail.setThruTime(session.START_TIME_LONG);
        invoiceTimeType.setActiveDetail(null);
        invoiceTimeType.store();

        // Check for default, and pick one if necessary
        InvoiceType invoiceType = invoiceTimeTypeDetail.getInvoiceType();
        InvoiceTimeType defaultInvoiceTimeType = getDefaultInvoiceTimeType(invoiceType);
        if(defaultInvoiceTimeType == null) {
            List<InvoiceTimeType> invoiceTimeTypes = getInvoiceTimeTypesForUpdate(invoiceType);

            if(!invoiceTimeTypes.isEmpty()) {
                Iterator<InvoiceTimeType> iter = invoiceTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultInvoiceTimeType = iter.next();
                }
                InvoiceTimeTypeDetailValue invoiceTimeTypeDetailValue = defaultInvoiceTimeType.getLastDetailForUpdate().getInvoiceTimeTypeDetailValue().clone();

                invoiceTimeTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateInvoiceTimeTypeFromValue(invoiceTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(invoiceTimeType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Invoice Time Type Descriptions
    // --------------------------------------------------------------------------------

    public InvoiceTimeTypeDescription createInvoiceTimeTypeDescription(InvoiceTimeType invoiceTimeType, Language language, String description, BasePK createdBy) {
        InvoiceTimeTypeDescription invoiceTimeTypeDescription = InvoiceTimeTypeDescriptionFactory.getInstance().create(invoiceTimeType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(invoiceTimeType.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return invoiceTimeTypeDescription;
    }

    private static final Map<EntityPermission, String> getInvoiceTimeTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetimetypedescriptions " +
                "WHERE invctimtypd_invctimtyp_invoicetimetypeid = ? AND invctimtypd_lang_languageid = ? AND invctimtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetimetypedescriptions " +
                "WHERE invctimtypd_invctimtyp_invoicetimetypeid = ? AND invctimtypd_lang_languageid = ? AND invctimtypd_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceTimeTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private InvoiceTimeTypeDescription getInvoiceTimeTypeDescription(InvoiceTimeType invoiceTimeType, Language language, EntityPermission entityPermission) {
        return InvoiceTimeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceTimeTypeDescriptionQueries,
                invoiceTimeType, language, Session.MAX_TIME);
    }

    public InvoiceTimeTypeDescription getInvoiceTimeTypeDescription(InvoiceTimeType invoiceTimeType, Language language) {
        return getInvoiceTimeTypeDescription(invoiceTimeType, language, EntityPermission.READ_ONLY);
    }

    public InvoiceTimeTypeDescription getInvoiceTimeTypeDescriptionForUpdate(InvoiceTimeType invoiceTimeType, Language language) {
        return getInvoiceTimeTypeDescription(invoiceTimeType, language, EntityPermission.READ_WRITE);
    }

    public InvoiceTimeTypeDescriptionValue getInvoiceTimeTypeDescriptionValue(InvoiceTimeTypeDescription invoiceTimeTypeDescription) {
        return invoiceTimeTypeDescription == null? null: invoiceTimeTypeDescription.getInvoiceTimeTypeDescriptionValue().clone();
    }

    public InvoiceTimeTypeDescriptionValue getInvoiceTimeTypeDescriptionValueForUpdate(InvoiceTimeType invoiceTimeType, Language language) {
        return getInvoiceTimeTypeDescriptionValue(getInvoiceTimeTypeDescriptionForUpdate(invoiceTimeType, language));
    }

    private static final Map<EntityPermission, String> getInvoiceTimeTypeDescriptionsByInvoiceTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetimetypedescriptions, languages " +
                "WHERE invctimtypd_invctimtyp_invoicetimetypeid = ? AND invctimtypd_thrutime = ? AND invctimtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetimetypedescriptions " +
                "WHERE invctimtypd_invctimtyp_invoicetimetypeid = ? AND invctimtypd_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceTimeTypeDescriptionsByInvoiceTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InvoiceTimeTypeDescription> getInvoiceTimeTypeDescriptionsByInvoiceTimeType(InvoiceTimeType invoiceTimeType, EntityPermission entityPermission) {
        return InvoiceTimeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceTimeTypeDescriptionsByInvoiceTimeTypeQueries,
                invoiceTimeType, Session.MAX_TIME);
    }

    public List<InvoiceTimeTypeDescription> getInvoiceTimeTypeDescriptionsByInvoiceTimeType(InvoiceTimeType invoiceTimeType) {
        return getInvoiceTimeTypeDescriptionsByInvoiceTimeType(invoiceTimeType, EntityPermission.READ_ONLY);
    }

    public List<InvoiceTimeTypeDescription> getInvoiceTimeTypeDescriptionsByInvoiceTimeTypeForUpdate(InvoiceTimeType invoiceTimeType) {
        return getInvoiceTimeTypeDescriptionsByInvoiceTimeType(invoiceTimeType, EntityPermission.READ_WRITE);
    }

    public String getBestInvoiceTimeTypeDescription(InvoiceTimeType invoiceTimeType, Language language) {
        String description;
        InvoiceTimeTypeDescription invoiceTimeTypeDescription = getInvoiceTimeTypeDescription(invoiceTimeType, language);

        if(invoiceTimeTypeDescription == null && !language.getIsDefault()) {
            invoiceTimeTypeDescription = getInvoiceTimeTypeDescription(invoiceTimeType, getPartyControl().getDefaultLanguage());
        }

        if(invoiceTimeTypeDescription == null) {
            description = invoiceTimeType.getLastDetail().getInvoiceTimeTypeName();
        } else {
            description = invoiceTimeTypeDescription.getDescription();
        }

        return description;
    }

    public InvoiceTimeTypeDescriptionTransfer getInvoiceTimeTypeDescriptionTransfer(UserVisit userVisit, InvoiceTimeTypeDescription invoiceTimeTypeDescription) {
        return getInvoiceTransferCaches(userVisit).getInvoiceTimeTypeDescriptionTransferCache().getInvoiceTimeTypeDescriptionTransfer(invoiceTimeTypeDescription);
    }

    public List<InvoiceTimeTypeDescriptionTransfer> getInvoiceTimeTypeDescriptionTransfersByInvoiceTimeType(UserVisit userVisit, InvoiceTimeType invoiceTimeType) {
        List<InvoiceTimeTypeDescription> invoiceTimeTypeDescriptions = getInvoiceTimeTypeDescriptionsByInvoiceTimeType(invoiceTimeType);
        List<InvoiceTimeTypeDescriptionTransfer> invoiceTimeTypeDescriptionTransfers = new ArrayList<>(invoiceTimeTypeDescriptions.size());
        InvoiceTimeTypeDescriptionTransferCache invoiceTimeTypeDescriptionTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceTimeTypeDescriptionTransferCache();

        invoiceTimeTypeDescriptions.stream().forEach((invoiceTimeTypeDescription) -> {
            invoiceTimeTypeDescriptionTransfers.add(invoiceTimeTypeDescriptionTransferCache.getInvoiceTimeTypeDescriptionTransfer(invoiceTimeTypeDescription));
        });

        return invoiceTimeTypeDescriptionTransfers;
    }

    public void updateInvoiceTimeTypeDescriptionFromValue(InvoiceTimeTypeDescriptionValue invoiceTimeTypeDescriptionValue, BasePK updatedBy) {
        if(invoiceTimeTypeDescriptionValue.hasBeenModified()) {
            InvoiceTimeTypeDescription invoiceTimeTypeDescription = InvoiceTimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    invoiceTimeTypeDescriptionValue.getPrimaryKey());

            invoiceTimeTypeDescription.setThruTime(session.START_TIME_LONG);
            invoiceTimeTypeDescription.store();

            InvoiceTimeType invoiceTimeType = invoiceTimeTypeDescription.getInvoiceTimeType();
            Language language = invoiceTimeTypeDescription.getLanguage();
            String description = invoiceTimeTypeDescriptionValue.getDescription();

            invoiceTimeTypeDescription = InvoiceTimeTypeDescriptionFactory.getInstance().create(invoiceTimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(invoiceTimeType.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteInvoiceTimeTypeDescription(InvoiceTimeTypeDescription invoiceTimeTypeDescription, BasePK deletedBy) {
        invoiceTimeTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(invoiceTimeTypeDescription.getInvoiceTimeTypePK(), EventTypes.MODIFY.name(), invoiceTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteInvoiceTimeTypeDescriptionsByInvoiceTimeType(InvoiceTimeType invoiceTimeType, BasePK deletedBy) {
        List<InvoiceTimeTypeDescription> invoiceTimeTypeDescriptions = getInvoiceTimeTypeDescriptionsByInvoiceTimeTypeForUpdate(invoiceTimeType);

        invoiceTimeTypeDescriptions.stream().forEach((invoiceTimeTypeDescription) -> {
            deleteInvoiceTimeTypeDescription(invoiceTimeTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Invoice Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    public InvoiceAliasTypeDescription createInvoiceAliasTypeDescription(InvoiceAliasType invoiceAliasType, Language language, String description, BasePK createdBy) {
        InvoiceAliasTypeDescription invoiceAliasTypeDescription = InvoiceAliasTypeDescriptionFactory.getInstance().create(invoiceAliasType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(invoiceAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return invoiceAliasTypeDescription;
    }
    
    private static final Map<EntityPermission, String> getInvoiceAliasTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicealiastypedescriptions " +
                "WHERE invcatd_invcat_invoicealiastypeid = ? AND invcatd_lang_languageid = ? AND invcatd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicealiastypedescriptions " +
                "WHERE invcatd_invcat_invoicealiastypeid = ? AND invcatd_lang_languageid = ? AND invcatd_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceAliasTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private InvoiceAliasTypeDescription getInvoiceAliasTypeDescription(InvoiceAliasType invoiceAliasType, Language language, EntityPermission entityPermission) {
        return InvoiceAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceAliasTypeDescriptionQueries,
                invoiceAliasType, language, Session.MAX_TIME);
    }
    
    public InvoiceAliasTypeDescription getInvoiceAliasTypeDescription(InvoiceAliasType invoiceAliasType, Language language) {
        return getInvoiceAliasTypeDescription(invoiceAliasType, language, EntityPermission.READ_ONLY);
    }
    
    public InvoiceAliasTypeDescription getInvoiceAliasTypeDescriptionForUpdate(InvoiceAliasType invoiceAliasType, Language language) {
        return getInvoiceAliasTypeDescription(invoiceAliasType, language, EntityPermission.READ_WRITE);
    }
    
    public InvoiceAliasTypeDescriptionValue getInvoiceAliasTypeDescriptionValue(InvoiceAliasTypeDescription invoiceAliasTypeDescription) {
        return invoiceAliasTypeDescription == null? null: invoiceAliasTypeDescription.getInvoiceAliasTypeDescriptionValue().clone();
    }
    
    public InvoiceAliasTypeDescriptionValue getInvoiceAliasTypeDescriptionValueForUpdate(InvoiceAliasType invoiceAliasType, Language language) {
        return getInvoiceAliasTypeDescriptionValue(getInvoiceAliasTypeDescriptionForUpdate(invoiceAliasType, language));
    }
    
    private static final Map<EntityPermission, String> getInvoiceAliasTypeDescriptionsByInvoiceAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicealiastypedescriptions, languages " +
                "WHERE invcatd_invcat_invoicealiastypeid = ? AND invcatd_thrutime = ? AND invcatd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicealiastypedescriptions " +
                "WHERE invcatd_invcat_invoicealiastypeid = ? AND invcatd_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceAliasTypeDescriptionsByInvoiceAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<InvoiceAliasTypeDescription> getInvoiceAliasTypeDescriptionsByInvoiceAliasType(InvoiceAliasType invoiceAliasType, EntityPermission entityPermission) {
        return InvoiceAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceAliasTypeDescriptionsByInvoiceAliasTypeQueries,
                invoiceAliasType, Session.MAX_TIME);
    }
    
    public List<InvoiceAliasTypeDescription> getInvoiceAliasTypeDescriptionsByInvoiceAliasType(InvoiceAliasType invoiceAliasType) {
        return getInvoiceAliasTypeDescriptionsByInvoiceAliasType(invoiceAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<InvoiceAliasTypeDescription> getInvoiceAliasTypeDescriptionsByInvoiceAliasTypeForUpdate(InvoiceAliasType invoiceAliasType) {
        return getInvoiceAliasTypeDescriptionsByInvoiceAliasType(invoiceAliasType, EntityPermission.READ_WRITE);
    }
    
    public String getBestInvoiceAliasTypeDescription(InvoiceAliasType invoiceAliasType, Language language) {
        String description;
        InvoiceAliasTypeDescription invoiceAliasTypeDescription = getInvoiceAliasTypeDescription(invoiceAliasType, language);
        
        if(invoiceAliasTypeDescription == null && !language.getIsDefault()) {
            invoiceAliasTypeDescription = getInvoiceAliasTypeDescription(invoiceAliasType, getPartyControl().getDefaultLanguage());
        }
        
        if(invoiceAliasTypeDescription == null) {
            description = invoiceAliasType.getLastDetail().getInvoiceAliasTypeName();
        } else {
            description = invoiceAliasTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public InvoiceAliasTypeDescriptionTransfer getInvoiceAliasTypeDescriptionTransfer(UserVisit userVisit, InvoiceAliasTypeDescription invoiceAliasTypeDescription) {
        return getInvoiceTransferCaches(userVisit).getInvoiceAliasTypeDescriptionTransferCache().getInvoiceAliasTypeDescriptionTransfer(invoiceAliasTypeDescription);
    }
    
    public List<InvoiceAliasTypeDescriptionTransfer> getInvoiceAliasTypeDescriptionTransfersByInvoiceAliasType(UserVisit userVisit, InvoiceAliasType invoiceAliasType) {
        List<InvoiceAliasTypeDescription> invoiceAliasTypeDescriptions = getInvoiceAliasTypeDescriptionsByInvoiceAliasType(invoiceAliasType);
        List<InvoiceAliasTypeDescriptionTransfer> invoiceAliasTypeDescriptionTransfers = new ArrayList<>(invoiceAliasTypeDescriptions.size());
        InvoiceAliasTypeDescriptionTransferCache invoiceAliasTypeDescriptionTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceAliasTypeDescriptionTransferCache();
        
        invoiceAliasTypeDescriptions.stream().forEach((invoiceAliasTypeDescription) -> {
            invoiceAliasTypeDescriptionTransfers.add(invoiceAliasTypeDescriptionTransferCache.getInvoiceAliasTypeDescriptionTransfer(invoiceAliasTypeDescription));
        });
        
        return invoiceAliasTypeDescriptionTransfers;
    }
    
    public void updateInvoiceAliasTypeDescriptionFromValue(InvoiceAliasTypeDescriptionValue invoiceAliasTypeDescriptionValue, BasePK updatedBy) {
        if(invoiceAliasTypeDescriptionValue.hasBeenModified()) {
            InvoiceAliasTypeDescription invoiceAliasTypeDescription = InvoiceAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceAliasTypeDescriptionValue.getPrimaryKey());
            
            invoiceAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            invoiceAliasTypeDescription.store();
            
            InvoiceAliasType invoiceAliasType = invoiceAliasTypeDescription.getInvoiceAliasType();
            Language language = invoiceAliasTypeDescription.getLanguage();
            String description = invoiceAliasTypeDescriptionValue.getDescription();
            
            invoiceAliasTypeDescription = InvoiceAliasTypeDescriptionFactory.getInstance().create(invoiceAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(invoiceAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteInvoiceAliasTypeDescription(InvoiceAliasTypeDescription invoiceAliasTypeDescription, BasePK deletedBy) {
        invoiceAliasTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(invoiceAliasTypeDescription.getInvoiceAliasTypePK(), EventTypes.MODIFY.name(), invoiceAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteInvoiceAliasTypeDescriptionsByInvoiceAliasType(InvoiceAliasType invoiceAliasType, BasePK deletedBy) {
        List<InvoiceAliasTypeDescription> invoiceAliasTypeDescriptions = getInvoiceAliasTypeDescriptionsByInvoiceAliasTypeForUpdate(invoiceAliasType);
        
        invoiceAliasTypeDescriptions.stream().forEach((invoiceAliasTypeDescription) -> {
            deleteInvoiceAliasTypeDescription(invoiceAliasTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Types
    // --------------------------------------------------------------------------------
    
    public InvoiceLineType createInvoiceLineType(InvoiceType invoiceType, String invoiceLineTypeName,
            InvoiceLineType parentInvoiceLineType, GlAccount defaultGlAccount, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        InvoiceLineType defaultInvoiceLineType = getDefaultInvoiceLineType(invoiceType);
        boolean defaultFound = defaultInvoiceLineType != null;
        
        if(defaultFound && isDefault) {
            InvoiceLineTypeDetailValue defaultInvoiceLineTypeDetailValue = getDefaultInvoiceLineTypeDetailValueForUpdate(invoiceType);
            
            defaultInvoiceLineTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateInvoiceLineTypeFromValue(defaultInvoiceLineTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        InvoiceLineType invoiceLineType = InvoiceLineTypeFactory.getInstance().create();
        InvoiceLineTypeDetail invoiceLineTypeDetail = InvoiceLineTypeDetailFactory.getInstance().create(invoiceLineType,
                invoiceType, invoiceLineTypeName, parentInvoiceLineType, defaultGlAccount, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        invoiceLineType = InvoiceLineTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                invoiceLineType.getPrimaryKey());
        invoiceLineType.setActiveDetail(invoiceLineTypeDetail);
        invoiceLineType.setLastDetail(invoiceLineTypeDetail);
        invoiceLineType.store();
        
        sendEventUsingNames(invoiceLineType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return invoiceLineType;
    }
    
    private InvoiceLineType getInvoiceLineTypeByName(InvoiceType invoiceType, String invoiceLineTypeName,
            EntityPermission entityPermission) {
        InvoiceLineType invoiceLineType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicelinetypes, invoicelinetypedetails " +
                        "WHERE invcltyp_activedetailid = invcltypdt_invoicelinetypedetailid " +
                        "AND invcltypdt_invctyp_invoicetypeid = ? AND invcltypdt_invoicelinetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicelinetypes, invoicelinetypedetails " +
                        "WHERE invcltyp_activedetailid = invcltypdt_invoicelinetypedetailid " +
                        "AND invcltypdt_invctyp_invoicetypeid = ? AND invcltypdt_invoicelinetypename = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceLineTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, invoiceType.getPrimaryKey().getEntityId());
            ps.setString(2, invoiceLineTypeName);
            
            invoiceLineType = InvoiceLineTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceLineType;
    }
    
    public InvoiceLineType getInvoiceLineTypeByName(InvoiceType invoiceType, String invoiceLineTypeName) {
        return getInvoiceLineTypeByName(invoiceType, invoiceLineTypeName, EntityPermission.READ_ONLY);
    }
    
    public InvoiceLineType getInvoiceLineTypeByNameForUpdate(InvoiceType invoiceType, String invoiceLineTypeName) {
        return getInvoiceLineTypeByName(invoiceType, invoiceLineTypeName, EntityPermission.READ_WRITE);
    }
    
    public InvoiceLineTypeDetailValue getInvoiceLineTypeDetailValueForUpdate(InvoiceLineType invoiceLineType) {
        return invoiceLineType == null? null: invoiceLineType.getLastDetailForUpdate().getInvoiceLineTypeDetailValue().clone();
    }
    
    public InvoiceLineTypeDetailValue getInvoiceLineTypeDetailValueByNameForUpdate(InvoiceType invoiceType,
            String invoiceLineTypeName) {
        return getInvoiceLineTypeDetailValueForUpdate(getInvoiceLineTypeByNameForUpdate(invoiceType, invoiceLineTypeName));
    }
    
    private InvoiceLineType getDefaultInvoiceLineType(InvoiceType invoiceType, EntityPermission entityPermission) {
        InvoiceLineType invoiceLineType = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicelinetypes, invoicelinetypedetails " +
                        "WHERE invcltyp_activedetailid = invcltypdt_invoicelinetypedetailid " +
                        "AND invcltypdt_invctyp_invoicetypeid = ? AND invcltypdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicelinetypes, invoicelinetypedetails " +
                        "WHERE invcltyp_activedetailid = invcltypdt_invoicelinetypedetailid " +
                        "AND invcltypdt_invctyp_invoicetypeid = ? AND invcltypdt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceLineTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, invoiceType.getPrimaryKey().getEntityId());
            
            invoiceLineType = InvoiceLineTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceLineType;
    }
    
    public InvoiceLineType getDefaultInvoiceLineType(InvoiceType invoiceType) {
        return getDefaultInvoiceLineType(invoiceType, EntityPermission.READ_ONLY);
    }
    
    public InvoiceLineType getDefaultInvoiceLineTypeForUpdate(InvoiceType invoiceType) {
        return getDefaultInvoiceLineType(invoiceType, EntityPermission.READ_WRITE);
    }
    
    public InvoiceLineTypeDetailValue getDefaultInvoiceLineTypeDetailValueForUpdate(InvoiceType invoiceType) {
        return getDefaultInvoiceLineTypeForUpdate(invoiceType).getLastDetailForUpdate().getInvoiceLineTypeDetailValue().clone();
    }
    
    private List<InvoiceLineType> getInvoiceLineTypes(InvoiceType invoiceType, EntityPermission entityPermission) {
        List<InvoiceLineType> invoiceLineTypes = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM invoicelinetypes, invoicelinetypedetails " +
                    "WHERE invcltyp_activedetailid = invcltypdt_invoicelinetypedetailid " +
                    "AND invcltypdt_invctyp_invoicetypeid = ? " +
                    "ORDER BY invcltypdt_sortorder, invcltypdt_invoicelinetypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM invoicelinetypes, invoicelinetypedetails " +
                    "WHERE invcltyp_activedetailid = invcltypdt_invoicelinetypedetailid " +
                    "AND invcltypdt_invctyp_invoicetypeid = ? " +
                    "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceLineTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, invoiceType.getPrimaryKey().getEntityId());
            
            invoiceLineTypes = InvoiceLineTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceLineTypes;
    }
    
    public List<InvoiceLineType> getInvoiceLineTypes(InvoiceType invoiceType) {
        return getInvoiceLineTypes(invoiceType, EntityPermission.READ_ONLY);
    }
    
    public List<InvoiceLineType> getInvoiceLineTypesForUpdate(InvoiceType invoiceType) {
        return getInvoiceLineTypes(invoiceType, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getInvoiceLineTypesByParentInvoiceLineTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicelinetypes, invoicelinetypedetails " +
                "WHERE invcltyp_activedetailid = invcltypdt_invoicelinetypedetailid AND invcltypdt_parentinvoicelinetypeid = ? " +
                "ORDER BY invcltypdt_sortorder, invcltypdt_invoicelinetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicelinetypes, invoicelinetypedetails " +
                "WHERE invcltyp_activedetailid = invcltypdt_invoicelinetypedetailid AND invcltypdt_parentinvoicelinetypeid = ? " +
                "FOR UPDATE");
        getInvoiceLineTypesByParentInvoiceLineTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InvoiceLineType> getInvoiceLineTypesByParentInvoiceLineType(InvoiceLineType parentInvoiceLineType,
            EntityPermission entityPermission) {
        return InvoiceLineTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceLineTypesByParentInvoiceLineTypeQueries,
                parentInvoiceLineType);
    }

    public List<InvoiceLineType> getInvoiceLineTypesByParentInvoiceLineType(InvoiceLineType parentInvoiceLineType) {
        return getInvoiceLineTypesByParentInvoiceLineType(parentInvoiceLineType, EntityPermission.READ_ONLY);
    }

    public List<InvoiceLineType> getInvoiceLineTypesByParentInvoiceLineTypeForUpdate(InvoiceLineType parentInvoiceLineType) {
        return getInvoiceLineTypesByParentInvoiceLineType(parentInvoiceLineType, EntityPermission.READ_WRITE);
    }

    public InvoiceLineTypeTransfer getInvoiceLineTypeTransfer(UserVisit userVisit, InvoiceLineType invoiceLineType) {
        return getInvoiceTransferCaches(userVisit).getInvoiceLineTypeTransferCache().getInvoiceLineTypeTransfer(invoiceLineType);
    }
    
    public List<InvoiceLineTypeTransfer> getInvoiceLineTypeTransfers(UserVisit userVisit, List<InvoiceLineType> invoiceLineTypes) {
        List<InvoiceLineTypeTransfer> invoiceLineTypeTransfers = new ArrayList<>(invoiceLineTypes.size());
        InvoiceLineTypeTransferCache invoiceLineTypeTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceLineTypeTransferCache();
        
        invoiceLineTypes.stream().forEach((invoiceLineType) -> {
            invoiceLineTypeTransfers.add(invoiceLineTypeTransferCache.getInvoiceLineTypeTransfer(invoiceLineType));
        });
        
        return invoiceLineTypeTransfers;
    }
    
    public List<InvoiceLineTypeTransfer> getInvoiceLineTypeTransfersByInvoiceType(UserVisit userVisit, InvoiceType invoiceType) {
        return getInvoiceLineTypeTransfers(userVisit, getInvoiceLineTypes(invoiceType));
    }
    
    public InvoiceLineTypeChoicesBean getInvoiceLineTypeChoices(InvoiceType invoiceType, String defaultInvoiceLineTypeChoice,
            Language language, boolean allowNullChoice) {
        List<InvoiceLineType> invoiceLineTypes = getInvoiceLineTypes(invoiceType);
        int size = invoiceLineTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInvoiceLineTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(InvoiceLineType invoiceLineType: invoiceLineTypes) {
            InvoiceLineTypeDetail invoiceLineTypeDetail = invoiceLineType.getLastDetail();
            
            String label = getBestInvoiceLineTypeDescription(invoiceLineType, language);
            String value = invoiceLineTypeDetail.getInvoiceLineTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultInvoiceLineTypeChoice == null? false: defaultInvoiceLineTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && invoiceLineTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new InvoiceLineTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentInvoiceLineTypeSafe(InvoiceLineType invoiceLineType, InvoiceLineType parentInvoiceLineType) {
        boolean safe = true;
        
        if(parentInvoiceLineType != null) {
            Set<InvoiceLineType> parentItemPurchasingCategorys = new HashSet<>();
            
            parentItemPurchasingCategorys.add(invoiceLineType);
            do {
                if(parentItemPurchasingCategorys.contains(parentInvoiceLineType)) {
                    safe = false;
                    break;
                }
                
                parentItemPurchasingCategorys.add(parentInvoiceLineType);
                parentInvoiceLineType = parentInvoiceLineType.getLastDetail().getParentInvoiceLineType();
            } while(parentInvoiceLineType != null);
        }
        
        return safe;
    }
    
    private void updateInvoiceLineTypeFromValue(InvoiceLineTypeDetailValue invoiceLineTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(invoiceLineTypeDetailValue.hasBeenModified()) {
            InvoiceLineType invoiceLineType = InvoiceLineTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceLineTypeDetailValue.getInvoiceLineTypePK());
            InvoiceLineTypeDetail invoiceLineTypeDetail = invoiceLineType.getActiveDetailForUpdate();
            
            invoiceLineTypeDetail.setThruTime(session.START_TIME_LONG);
            invoiceLineTypeDetail.store();
            
            InvoiceLineTypePK invoiceLineTypePK = invoiceLineTypeDetail.getInvoiceLineTypePK(); // Not updated
            InvoiceType invoiceType = invoiceLineTypeDetail.getInvoiceType(); // Not updated
            InvoiceTypePK invoiceTypePK = invoiceType.getPrimaryKey(); // Not updated
            String invoiceLineTypeName = invoiceLineTypeDetailValue.getInvoiceLineTypeName();
            InvoiceLineTypePK parentInvoiceLineTypePK = invoiceLineTypeDetailValue.getParentInvoiceLineTypePK();
            GlAccountPK defaultGlAccountPK = invoiceLineTypeDetailValue.getDefaultGlAccountPK();
            Boolean isDefault = invoiceLineTypeDetailValue.getIsDefault();
            Integer sortOrder = invoiceLineTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                InvoiceLineType defaultInvoiceLineType = getDefaultInvoiceLineType(invoiceType);
                boolean defaultFound = defaultInvoiceLineType != null && !defaultInvoiceLineType.equals(invoiceLineType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    InvoiceLineTypeDetailValue defaultInvoiceLineTypeDetailValue = getDefaultInvoiceLineTypeDetailValueForUpdate(invoiceType);
                    
                    defaultInvoiceLineTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateInvoiceLineTypeFromValue(defaultInvoiceLineTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            invoiceLineTypeDetail = InvoiceLineTypeDetailFactory.getInstance().create(invoiceLineTypePK, invoiceTypePK,
                    invoiceLineTypeName, parentInvoiceLineTypePK, defaultGlAccountPK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            invoiceLineType.setActiveDetail(invoiceLineTypeDetail);
            invoiceLineType.setLastDetail(invoiceLineTypeDetail);
            
            sendEventUsingNames(invoiceLineTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateInvoiceLineTypeFromValue(InvoiceLineTypeDetailValue invoiceLineTypeDetailValue, BasePK updatedBy) {
        updateInvoiceLineTypeFromValue(invoiceLineTypeDetailValue, true, updatedBy);
    }
    
    private void deleteInvoiceLineType(InvoiceLineType invoiceLineType, boolean checkDefault, BasePK deletedBy) {
        InvoiceLineTypeDetail invoiceLineTypeDetail = invoiceLineType.getLastDetailForUpdate();

        deleteInvoiceLineTypesByParentInvoiceLineType(invoiceLineType, deletedBy);
        deleteInvoiceLineTypeDescriptionsByInvoiceLineType(invoiceLineType, deletedBy);
        
        invoiceLineTypeDetail.setThruTime(session.START_TIME_LONG);
        invoiceLineType.setActiveDetail(null);
        invoiceLineType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            InvoiceType invoiceType = invoiceLineTypeDetail.getInvoiceType();
            InvoiceLineType defaultInvoiceLineType = getDefaultInvoiceLineType(invoiceType);

            if(defaultInvoiceLineType == null) {
                List<InvoiceLineType> invoiceLineTypes = getInvoiceLineTypesForUpdate(invoiceType);

                if(!invoiceLineTypes.isEmpty()) {
                    Iterator<InvoiceLineType> iter = invoiceLineTypes.iterator();
                    if(iter.hasNext()) {
                        defaultInvoiceLineType = iter.next();
                    }
                    InvoiceLineTypeDetailValue invoiceLineTypeDetailValue = defaultInvoiceLineType.getLastDetailForUpdate().getInvoiceLineTypeDetailValue().clone();

                    invoiceLineTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateInvoiceLineTypeFromValue(invoiceLineTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(invoiceLineType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteInvoiceLineType(InvoiceLineType invoiceLineType, BasePK deletedBy) {
        deleteInvoiceLineType(invoiceLineType, true, deletedBy);
    }

    private void deleteInvoiceLineTypes(List<InvoiceLineType> invoiceLineTypes, boolean checkDefault, BasePK deletedBy) {
        invoiceLineTypes.stream().forEach((invoiceLineType) -> {
            deleteInvoiceLineType(invoiceLineType, checkDefault, deletedBy);
        });
    }

    public void deleteInvoiceLineTypes(List<InvoiceLineType> invoiceLineTypes, BasePK deletedBy) {
        deleteInvoiceLineTypes(invoiceLineTypes, true, deletedBy);
    }

    private void deleteInvoiceLineTypesByParentInvoiceLineType(InvoiceLineType parentInvoiceLineType, BasePK deletedBy) {
        deleteInvoiceLineTypes(getInvoiceLineTypesByParentInvoiceLineTypeForUpdate(parentInvoiceLineType), false, deletedBy);
    }

    public void deleteInvoiceLineTypesByInvoiceType(InvoiceType invoiceType, BasePK deletedBy) {
        deleteInvoiceLineTypes(getInvoiceLineTypesForUpdate(invoiceType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Type Descriptions
    // --------------------------------------------------------------------------------
    
    public InvoiceLineTypeDescription createInvoiceLineTypeDescription(InvoiceLineType invoiceLineType, Language language,
            String description, BasePK createdBy) {
        InvoiceLineTypeDescription invoiceLineTypeDescription = InvoiceLineTypeDescriptionFactory.getInstance().create(session,
                invoiceLineType, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(invoiceLineType.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceLineTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return invoiceLineTypeDescription;
    }
    
    private InvoiceLineTypeDescription getInvoiceLineTypeDescription(InvoiceLineType invoiceLineType, Language language,
            EntityPermission entityPermission) {
        InvoiceLineTypeDescription invoiceLineTypeDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicelinetypedescriptions " +
                        "WHERE invcltypd_invcltyp_invoicelinetypeid = ? AND invcltypd_lang_languageid = ? AND invcltypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicelinetypedescriptions " +
                        "WHERE invcltypd_invcltyp_invoicelinetypeid = ? AND invcltypd_lang_languageid = ? AND invcltypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceLineTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, invoiceLineType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            invoiceLineTypeDescription = InvoiceLineTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceLineTypeDescription;
    }
    
    public InvoiceLineTypeDescription getInvoiceLineTypeDescription(InvoiceLineType invoiceLineType, Language language) {
        return getInvoiceLineTypeDescription(invoiceLineType, language, EntityPermission.READ_ONLY);
    }
    
    public InvoiceLineTypeDescription getInvoiceLineTypeDescriptionForUpdate(InvoiceLineType invoiceLineType, Language language) {
        return getInvoiceLineTypeDescription(invoiceLineType, language, EntityPermission.READ_WRITE);
    }
    
    public InvoiceLineTypeDescriptionValue getInvoiceLineTypeDescriptionValue(InvoiceLineTypeDescription invoiceLineTypeDescription) {
        return invoiceLineTypeDescription == null? null: invoiceLineTypeDescription.getInvoiceLineTypeDescriptionValue().clone();
    }
    
    public InvoiceLineTypeDescriptionValue getInvoiceLineTypeDescriptionValueForUpdate(InvoiceLineType invoiceLineType, Language language) {
        return getInvoiceLineTypeDescriptionValue(getInvoiceLineTypeDescriptionForUpdate(invoiceLineType, language));
    }
    
    private List<InvoiceLineTypeDescription> getInvoiceLineTypeDescriptionsByInvoiceLineType(InvoiceLineType invoiceLineType,
            EntityPermission entityPermission) {
        List<InvoiceLineTypeDescription> invoiceLineTypeDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicelinetypedescriptions, languages " +
                        "WHERE invcltypd_invcltyp_invoicelinetypeid = ? AND invcltypd_thrutime = ? AND invcltypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicelinetypedescriptions " +
                        "WHERE invcltypd_invcltyp_invoicelinetypeid = ? AND invcltypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceLineTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, invoiceLineType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            invoiceLineTypeDescriptions = InvoiceLineTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceLineTypeDescriptions;
    }
    
    public List<InvoiceLineTypeDescription> getInvoiceLineTypeDescriptionsByInvoiceLineType(InvoiceLineType invoiceLineType) {
        return getInvoiceLineTypeDescriptionsByInvoiceLineType(invoiceLineType, EntityPermission.READ_ONLY);
    }
    
    public List<InvoiceLineTypeDescription> getInvoiceLineTypeDescriptionsByInvoiceLineTypeForUpdate(InvoiceLineType invoiceLineType) {
        return getInvoiceLineTypeDescriptionsByInvoiceLineType(invoiceLineType, EntityPermission.READ_WRITE);
    }
    
    public String getBestInvoiceLineTypeDescription(InvoiceLineType invoiceLineType, Language language) {
        String description;
        InvoiceLineTypeDescription invoiceLineTypeDescription = getInvoiceLineTypeDescription(invoiceLineType, language);
        
        if(invoiceLineTypeDescription == null && !language.getIsDefault()) {
            invoiceLineTypeDescription = getInvoiceLineTypeDescription(invoiceLineType, getPartyControl().getDefaultLanguage());
        }
        
        if(invoiceLineTypeDescription == null) {
            description = invoiceLineType.getLastDetail().getInvoiceLineTypeName();
        } else {
            description = invoiceLineTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public InvoiceLineTypeDescriptionTransfer getInvoiceLineTypeDescriptionTransfer(UserVisit userVisit, InvoiceLineTypeDescription invoiceLineTypeDescription) {
        return getInvoiceTransferCaches(userVisit).getInvoiceLineTypeDescriptionTransferCache().getInvoiceLineTypeDescriptionTransfer(invoiceLineTypeDescription);
    }
    
    public List<InvoiceLineTypeDescriptionTransfer> getInvoiceLineTypeDescriptionTransfersByInvoiceLineType(UserVisit userVisit, InvoiceLineType invoiceLineType) {
        List<InvoiceLineTypeDescription> invoiceLineTypeDescriptions = getInvoiceLineTypeDescriptionsByInvoiceLineType(invoiceLineType);
        List<InvoiceLineTypeDescriptionTransfer> invoiceLineTypeDescriptionTransfers = new ArrayList<>(invoiceLineTypeDescriptions.size());
        InvoiceLineTypeDescriptionTransferCache invoiceLineTypeDescriptionTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceLineTypeDescriptionTransferCache();
        
        invoiceLineTypeDescriptions.stream().forEach((invoiceLineTypeDescription) -> {
            invoiceLineTypeDescriptionTransfers.add(invoiceLineTypeDescriptionTransferCache.getInvoiceLineTypeDescriptionTransfer(invoiceLineTypeDescription));
        });
        
        return invoiceLineTypeDescriptionTransfers;
    }
    
    public void updateInvoiceLineTypeDescriptionFromValue(InvoiceLineTypeDescriptionValue invoiceLineTypeDescriptionValue, BasePK updatedBy) {
        if(invoiceLineTypeDescriptionValue.hasBeenModified()) {
            InvoiceLineTypeDescription invoiceLineTypeDescription = InvoiceLineTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceLineTypeDescriptionValue.getPrimaryKey());
            
            invoiceLineTypeDescription.setThruTime(session.START_TIME_LONG);
            invoiceLineTypeDescription.store();
            
            InvoiceLineType invoiceLineType = invoiceLineTypeDescription.getInvoiceLineType();
            Language language = invoiceLineTypeDescription.getLanguage();
            String description = invoiceLineTypeDescriptionValue.getDescription();
            
            invoiceLineTypeDescription = InvoiceLineTypeDescriptionFactory.getInstance().create(invoiceLineType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(invoiceLineType.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceLineTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteInvoiceLineTypeDescription(InvoiceLineTypeDescription invoiceLineTypeDescription, BasePK deletedBy) {
        invoiceLineTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(invoiceLineTypeDescription.getInvoiceLineTypePK(), EventTypes.MODIFY.name(), invoiceLineTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteInvoiceLineTypeDescriptionsByInvoiceLineType(InvoiceLineType invoiceLineType, BasePK deletedBy) {
        List<InvoiceLineTypeDescription> invoiceLineTypeDescriptions = getInvoiceLineTypeDescriptionsByInvoiceLineTypeForUpdate(invoiceLineType);
        
        invoiceLineTypeDescriptions.stream().forEach((invoiceLineTypeDescription) -> {
            deleteInvoiceLineTypeDescription(invoiceLineTypeDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Roles
    // --------------------------------------------------------------------------------
    
    public InvoiceRole createInvoiceRoleUsingNames(Invoice invoice, Party party, PartyContactMechanism partyContactMechanism, String invoiceRoleTypeName, BasePK createdBy) {
        InvoiceRoleType invoiceRoleType = getInvoiceRoleTypeByName(invoiceRoleTypeName);
        
        return createInvoiceRole(invoice, party, partyContactMechanism, invoiceRoleType, createdBy);
    }
    
    public InvoiceRole createInvoiceRole(Invoice invoice, Party party, PartyContactMechanism partyContactMechanism, InvoiceRoleType invoiceRoleType, BasePK createdBy) {
        InvoiceRole invoiceRole = InvoiceRoleFactory.getInstance().create(invoice, party, partyContactMechanism, invoiceRoleType, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(invoice.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceRole.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return invoiceRole;
    }
    
    private InvoiceRole getInvoiceRole(Invoice invoice, InvoiceRoleType invoiceRoleType, EntityPermission entityPermission) {
        InvoiceRole invoiceRole = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM invoiceroles " +
                        "WHERE invcr_invc_invoiceid = ? AND invcr_invcrtyp_invoiceroletypeid = ? AND invcr_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM invoiceroles " +
                        "WHERE invcr_invc_invoiceid = ? AND invcr_invcrtyp_invoiceroletypeid = ? AND invcr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, invoice.getPrimaryKey().getEntityId());
            ps.setLong(2, invoiceRoleType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            invoiceRole = InvoiceRoleFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceRole;
    }
    
    public InvoiceRole getInvoiceRole(Invoice invoice, InvoiceRoleType invoiceRoleType) {
        return getInvoiceRole(invoice, invoiceRoleType, EntityPermission.READ_ONLY);
    }
    
    public InvoiceRole getInvoiceRoleUsingNames(Invoice invoice, String invoiceRoleTypeName) {
        InvoiceRoleType invoiceRoleType = getInvoiceRoleTypeByName(invoiceRoleTypeName);
        
        return getInvoiceRole(invoice, invoiceRoleType);
    }
    
    public InvoiceRole getInvoiceRoleForUpdate(Invoice invoice, Party party, InvoiceRoleType invoiceRoleType) {
        return getInvoiceRole(invoice, invoiceRoleType, EntityPermission.READ_WRITE);
    }
    
    public InvoiceRoleValue getInvoiceRoleValue(InvoiceRole invoiceRole) {
        return invoiceRole == null? null: invoiceRole.getInvoiceRoleValue().clone();
    }
    
    public InvoiceRoleValue getInvoiceRoleValueForUpdate(Invoice invoice, Party party, InvoiceRoleType invoiceRoleType) {
        return getInvoiceRoleValue(getInvoiceRoleForUpdate(invoice, party, invoiceRoleType));
    }
    
    private List<InvoiceRole> getInvoiceRolesByInvoice(Invoice invoice, EntityPermission entityPermission) {
        List<InvoiceRole> invoiceRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM invoiceroles, invoiceroletypes, parties, partydetails " +
                        "WHERE invcr_invc_invoiceid = ? AND invcr_thrutime = ? " +
                        "AND invcr_invcrtyp_invoiceroletypeid = invcrtyp_invoiceroletypeid " +
                        "AND invcr_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "ORDER BY invcrtyp_sortorder, pardt_partyname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM invoiceroles " +
                        "WHERE invcr_invc_invoiceid = ? AND invcr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, invoice.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            invoiceRoles = InvoiceRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceRoles;
    }
    
    public List<InvoiceRole> getInvoiceRolesByInvoice(Invoice invoice) {
        return getInvoiceRolesByInvoice(invoice, EntityPermission.READ_ONLY);
    }
    
    public List<InvoiceRole> getInvoiceRolesByInvoiceForUpdate(Invoice invoice) {
        return getInvoiceRolesByInvoice(invoice, EntityPermission.READ_WRITE);
    }
    
    private List<InvoiceRole> getInvoiceRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism, EntityPermission entityPermission) {
        List<InvoiceRole> invoiceRoles = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM invoiceroles, invoices, invoicedetails, parties, partydetails, invoiceroletypes " +
                        "WHERE invcr_pcm_partycontactmechanismid = ? AND invcr_thrutime = ? " +
                        "AND invcr_invc_invoiceid = invc_invoiceid AND invc_lastdetailid = invcdt_invoicedetailid " +
                        "AND invcr_par_partyid = par_partyid AND par_lastdetailid = pardt_partydetailid " +
                        "AND invcr_invcrtyp_invoiceroletypeid = invcrtyp_invoiceroletypeid " +
                        "ORDER BY invcdt_invoicename, pardt_partyname, invcrtyp_sortorder, invcrtyp_invoiceroletypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM invoiceroles " +
                        "WHERE invcr_pcm_partycontactmechanismid = ? AND invcr_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceRoleFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyContactMechanism.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            invoiceRoles = InvoiceRoleFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceRoles;
    }
    
    public List<InvoiceRole> getInvoiceRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism) {
        return getInvoiceRolesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<InvoiceRole> getInvoiceRolesByPartyContactMechanismForUpdate(PartyContactMechanism partyContactMechanism) {
        return getInvoiceRolesByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public List<InvoiceTransfer> getInvoiceTransfers(UserVisit userVisit, List<Invoice> invoices) {
        List<InvoiceTransfer> invoiceTransfers = new ArrayList<>(invoices.size());
        InvoiceTransferCache invoiceTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceTransferCache();
        
        invoices.stream().forEach((invoice) -> {
            invoiceTransfers.add(invoiceTransferCache.getInvoiceTransfer(invoice));
        });
        
        return invoiceTransfers;
    }
    
    public List<InvoiceTransfer> getInvoiceTransfersByInvoiceFrom(UserVisit userVisit, Party invoiceFrom) {
        return getInvoiceTransfers(userVisit, getInvoicesByInvoiceFrom(invoiceFrom));
    }
    
    public void updateInvoiceRoleFromValue(InvoiceRoleValue invoiceRoleValue, BasePK updatedBy) {
        if(invoiceRoleValue.hasBeenModified()) {
            InvoiceRole invoiceRole = InvoiceRoleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceRoleValue.getPrimaryKey());
            
            invoiceRole.setThruTime(session.START_TIME_LONG);
            invoiceRole.store();
            
            InvoicePK invoicePK = invoiceRole.getInvoicePK(); // Not updated
            PartyPK partyPK = invoiceRole.getPartyPK(); // Not updated
            PartyContactMechanismPK partyContactMechanismPK = invoiceRoleValue.getPartyContactMechanismPK();
            InvoiceRoleTypePK invoiceRoleTypePK = invoiceRole.getInvoiceRoleTypePK(); // Not updated
            
            invoiceRole = InvoiceRoleFactory.getInstance().create(invoicePK, partyPK, partyContactMechanismPK, invoiceRoleTypePK,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(invoicePK, EventTypes.MODIFY.name(), invoiceRole.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteInvoiceRole(InvoiceRole invoiceRole, BasePK deletedBy) {
        invoiceRole.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(invoiceRole.getInvoicePK(), EventTypes.MODIFY.name(), invoiceRole.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteInvoiceRolesByInvoice(Invoice invoice, BasePK deletedBy) {
        getInvoiceRolesByInvoiceForUpdate(invoice).stream().forEach((invoiceRole) -> {
            deleteInvoiceRole(invoiceRole, deletedBy);
        });
    }
    
    public void deleteInvoiceRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        getInvoiceRolesByPartyContactMechanismForUpdate(partyContactMechanism).stream().forEach((invoiceRole) -> {
            deleteInvoiceRole(invoiceRole, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Invoices
    // --------------------------------------------------------------------------------
    
    public Invoice createInvoice(InvoiceType invoiceType, String invoiceName, BillingAccount billingAccount, GlAccount glAccount, Term term, String reference,
            String description, BasePK createdBy) {
        Invoice invoice = InvoiceFactory.getInstance().create();
        InvoiceDetail invoiceDetail = InvoiceDetailFactory.getInstance().create(invoice, invoiceType, invoiceName, billingAccount, glAccount, term,
                reference, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        invoice = InvoiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoice.getPrimaryKey());
        invoice.setActiveDetail(invoiceDetail);
        invoice.setLastDetail(invoiceDetail);
        invoice.store();
        
        sendEventUsingNames(invoice.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        createInvoiceStatus(invoice);
        
        return invoice;
    }
    
    private static final Map<EntityPermission, String> getInvoicesByInvoiceFromQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoices, invoicedetails, invoiceroletypes, invoiceroles " +
                "WHERE invc_activedetailid = invcdt_invoicedetailid " +
                "AND invc_invoiceid = invcr_invc_invoiceid AND invcr_par_partyid = ? AND invcrtyp_invoiceroletypename = ? " +
                "AND invcrtyp_invoiceroletypeid = invcr_invcrtyp_invoiceroletypeid AND invcr_thrutime = ? " +
                "ORDER BY invcdt_invoicename DESC " + // TODO: should use the entity instance's created time for ordering
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoices, invoicedetails, invoiceroletypes, invoiceroles " +
                "WHERE invc_activedetailid = invcdt_invoicedetailid " +
                "AND invc_invoiceid = invcr_invc_invoiceid AND invcr_par_partyid = ? AND invcrtyp_invoiceroletypename = ? " +
                "AND invcrtyp_invoiceroletypeid = invcr_invcrtyp_invoiceroletypeid AND invcr_thrutime = ? " +
                "FOR UPDATE");
        getInvoicesByInvoiceFromQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<Invoice> getInvoicesByInvoiceFrom(Party invoiceFrom, EntityPermission entityPermission) {
        return InvoiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoicesByInvoiceFromQueries,
                invoiceFrom, InvoiceConstants.InvoiceRoleType_INVOICE_FROM, Session.MAX_TIME);
    }
    
    public List<Invoice> getInvoicesByInvoiceFrom(Party invoiceFrom) {
        return getInvoicesByInvoiceFrom(invoiceFrom, EntityPermission.READ_ONLY);
    }
    
    public List<Invoice> getInvoicesByInvoiceFromForUpdate(Party invoiceFrom) {
        return getInvoicesByInvoiceFrom(invoiceFrom, EntityPermission.READ_WRITE);
    }
    
    public long countInvoicesByInvoiceFrom(Party invoiceFrom) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM invoices, invoicedetails, invoiceroletypes, invoiceroles " +
                "WHERE invc_activedetailid = invcdt_invoicedetailid " +
                "AND invc_invoiceid = invcr_invc_invoiceid AND invcr_par_partyid = ? AND invcrtyp_invoiceroletypename = ? " +
                "AND invcrtyp_invoiceroletypeid = invcr_invcrtyp_invoiceroletypeid AND invcr_thrutime = ?",
                invoiceFrom, InvoiceConstants.InvoiceRoleType_INVOICE_FROM, Session.MAX_TIME);
    }
    
    private static final Map<EntityPermission, String> getInvoicesByInvoiceToQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoices, invoicedetails, invoiceroletypes, invoiceroles " +
                "WHERE invc_activedetailid = invcdt_invoicedetailid " +
                "AND invc_invoiceid = invcr_invc_invoiceid AND invcr_par_partyid = ? AND invcrtyp_invoiceroletypename = ? " +
                "AND invcrtyp_invoiceroletypeid = invcr_invcrtyp_invoiceroletypeid AND invcr_thrutime = ? " +
                "ORDER BY invcdt_invoicename DESC " + // TODO: should use the entity instance's created time for ordering
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoices, invoicedetails, invoiceroletypes, invoiceroles " +
                "WHERE invc_activedetailid = invcdt_invoicedetailid " +
                "AND invc_invoiceid = invcr_invc_invoiceid AND invcr_par_partyid = ? AND invcrtyp_invoiceroletypename = ? " +
                "AND invcrtyp_invoiceroletypeid = invcr_invcrtyp_invoiceroletypeid AND invcr_thrutime = ? " +
                "FOR UPDATE");
        getInvoicesByInvoiceToQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<Invoice> getInvoicesByInvoiceTo(Party invoiceTo, EntityPermission entityPermission) {
        return InvoiceFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoicesByInvoiceToQueries,
                invoiceTo, InvoiceConstants.InvoiceRoleType_INVOICE_TO, Session.MAX_TIME);
    }
    
    public List<Invoice> getInvoicesByInvoiceTo(Party invoiceTo) {
        return getInvoicesByInvoiceTo(invoiceTo, EntityPermission.READ_ONLY);
    }
    
    public List<Invoice> getInvoicesByInvoiceToForUpdate(Party invoiceTo) {
        return getInvoicesByInvoiceTo(invoiceTo, EntityPermission.READ_WRITE);
    }
    
    public long countInvoicesByInvoiceTo(Party invoiceTo) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM invoices, invoicedetails, invoiceroletypes, invoiceroles " +
                "WHERE invc_activedetailid = invcdt_invoicedetailid " +
                "AND invc_invoiceid = invcr_invc_invoiceid AND invcr_par_partyid = ? AND invcrtyp_invoiceroletypename = ? " +
                "AND invcrtyp_invoiceroletypeid = invcr_invcrtyp_invoiceroletypeid AND invcr_thrutime = ?",
                invoiceTo, InvoiceConstants.InvoiceRoleType_INVOICE_TO, Session.MAX_TIME);
    }
    
    private static final Map<EntityPermission, String> getInvoiceByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoices, invoicedetails " +
                "WHERE invc_activedetailid = invcdt_invoicedetailid AND invcdt_invctyp_invoicetypeid = ? AND invcdt_invoicename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoices, invoicedetails " +
                "WHERE invc_activedetailid = invcdt_invoicedetailid AND invcdt_invctyp_invoicetypeid = ? AND invcdt_invoicename = ? " +
                "FOR UPDATE");
        getInvoiceByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private Invoice getInvoiceByName(InvoiceType invoiceType, String invoiceName, EntityPermission entityPermission) {
        return InvoiceFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceByNameQueries,
                invoiceType, invoiceName);
    }
    
    private Invoice getInvoiceByNameUsingNames(String invoiceTypeName, String invoiceName, EntityPermission entityPermission) {
        InvoiceType invoiceType = getInvoiceTypeByName(invoiceTypeName);
        
        return getInvoiceByName(invoiceType, invoiceName, entityPermission);
    }
    
    public Invoice getInvoiceByName(InvoiceType invoiceType, String invoiceName) {
        return getInvoiceByName(invoiceType, invoiceName, EntityPermission.READ_ONLY);
    }
    
    public Invoice getInvoiceByNameForUpdate(InvoiceType invoiceType, String invoiceName) {
        return getInvoiceByName(invoiceType, invoiceName, EntityPermission.READ_WRITE);
    }
    
    public Invoice getInvoiceByNameUsingNames(String invoiceTypeName, String invoiceName) {
        return getInvoiceByNameUsingNames(invoiceTypeName, invoiceName, EntityPermission.READ_ONLY);
    }
    
    public Invoice getInvoiceByNameForUpdateUsingNames(String invoiceTypeName, String invoiceName) {
        return getInvoiceByNameUsingNames(invoiceTypeName, invoiceName, EntityPermission.READ_WRITE);
    }
    
    public long countInvoicesByInvoiceFromAndReference(Party invoiceFrom, String reference) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM invoices, invoicedetails, invoiceroles, invoiceroletypes " +
                "WHERE invc_activedetailid = invcdt_invoicedetailid AND invcdt_reference = ? " +
                "AND invc_invoiceid = invcr_invc_invoiceid AND invcr_thrutime = ? AND invcr_par_partyid = ? " +
                "AND invcr_invcrtyp_invoiceroletypeid = invcrtyp_invoiceroletypeid AND invcrtyp_invoiceroletypename = ?",
                reference, Session.MAX_TIME_LONG, invoiceFrom, InvoiceConstants.InvoiceRoleType_INVOICE_FROM);
    }
    
    public InvoiceTransfer getInvoiceTransfer(UserVisit userVisit, Invoice invoice) {
        return getInvoiceTransferCaches(userVisit).getInvoiceTransferCache().getInvoiceTransfer(invoice);
    }
    
    public List<InvoiceRoleTransfer> getInvoiceRoleTransfers(UserVisit userVisit, List<InvoiceRole> invoiceRoles) {
        List<InvoiceRoleTransfer> invoiceRoleTransfers = new ArrayList<>(invoiceRoles.size());
        InvoiceRoleTransferCache invoiceRoleTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceRoleTransferCache();
        
        invoiceRoles.stream().forEach((invoiceRole) -> {
            invoiceRoleTransfers.add(invoiceRoleTransferCache.getInvoiceRoleTransfer(invoiceRole));
        });
        
        return invoiceRoleTransfers;
    }
    
    public List<InvoiceRoleTransfer> getInvoiceRoleTransfersByInvoice(UserVisit userVisit, Invoice invoice) {
        return getInvoiceRoleTransfers(userVisit, getInvoiceRolesByInvoice(invoice));
    }
    
    public List<InvoiceTransfer> getInvoiceTransfersByInvoiceTo(UserVisit userVisit, Party invoiceTo) {
        return getInvoiceTransfers(userVisit, getInvoicesByInvoiceTo(invoiceTo));
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Statuses
    // --------------------------------------------------------------------------------
    
    public InvoiceStatus createInvoiceStatus(Invoice invoice) {
        return InvoiceStatusFactory.getInstance().create(invoice, 0);
    }
    
    private InvoiceStatus getInvoiceStatus(Invoice invoice, EntityPermission entityPermission) {
        InvoiceStatus invoiceStatus = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicestatuses " +
                        "WHERE invcst_invc_invoiceid = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM invoicestatuses " +
                        "WHERE invcst_invc_invoiceid = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InvoiceStatusFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, invoice.getPrimaryKey().getEntityId());
            
            invoiceStatus = InvoiceStatusFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return invoiceStatus;
    }
    
    public InvoiceStatus getInvoiceStatus(Invoice invoice) {
        return getInvoiceStatus(invoice, EntityPermission.READ_ONLY);
    }
    
    public InvoiceStatus getInvoiceStatusForUpdate(Invoice invoice) {
        return getInvoiceStatus(invoice, EntityPermission.READ_WRITE);
    }
    
    public void removeInvoiceStatusByInvoice(Invoice invoice) {
        InvoiceStatus invoiceStatus = getInvoiceStatusForUpdate(invoice);
        
        if(invoiceStatus != null) {
            invoiceStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Times
    // --------------------------------------------------------------------------------

    public InvoiceTime createInvoiceTime(Invoice invoice, InvoiceTimeType invoiceTimeType, Long time, BasePK createdBy) {
        InvoiceTime invoiceTime = InvoiceTimeFactory.getInstance().create(invoice, invoiceTimeType, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(invoice.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceTime.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return invoiceTime;
    }

    public long countInvoiceTimesByInvoice(Invoice invoice) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM invoicetimes " +
                "WHERE invctim_invc_invoiceid = ? AND invctim_thrutime = ?",
                invoice, Session.MAX_TIME_LONG);
    }

    public long countInvoiceTimesByInvoiceTimeType(InvoiceTimeType invoiceTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM invoicetimes " +
                "WHERE invctim_invctimtyp_invoicetimetypeid = ? AND invctim_thrutime = ?",
                invoiceTimeType, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getInvoiceTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetimes " +
                "WHERE invctim_invc_invoiceid = ? AND invctim_invctimtyp_invoicetimetypeid = ? AND invctim_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetimes " +
                "WHERE invctim_invc_invoiceid = ? AND invctim_invctimtyp_invoicetimetypeid = ? AND invctim_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    private InvoiceTime getInvoiceTime(Invoice invoice, InvoiceTimeType invoiceTimeType, EntityPermission entityPermission) {
        return InvoiceTimeFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceTimeQueries, invoice, invoiceTimeType, Session.MAX_TIME);
    }

    public InvoiceTime getInvoiceTime(Invoice invoice, InvoiceTimeType invoiceTimeType) {
        return getInvoiceTime(invoice, invoiceTimeType, EntityPermission.READ_ONLY);
    }

    public InvoiceTime getInvoiceTimeForUpdate(Invoice invoice, InvoiceTimeType invoiceTimeType) {
        return getInvoiceTime(invoice, invoiceTimeType, EntityPermission.READ_WRITE);
    }

    public InvoiceTimeValue getInvoiceTimeValue(InvoiceTime invoiceTime) {
        return invoiceTime == null? null: invoiceTime.getInvoiceTimeValue().clone();
    }

    public InvoiceTimeValue getInvoiceTimeValueForUpdate(Invoice invoice, InvoiceTimeType invoiceTimeType) {
        return getInvoiceTimeValue(getInvoiceTimeForUpdate(invoice, invoiceTimeType));
    }

    private static final Map<EntityPermission, String> getInvoiceTimesByInvoiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetimes, invoicetimetypes, invoicetimetypedetails " +
                "WHERE invctim_invc_invoiceid = ? AND invctim_thrutime = ? " +
                "AND invctim_invctimtyp_invoicetimetypeid = invctimtyp_invoicetimetypeid AND invctimtyp_activedetailid = invctimtypdt_invoicetimetypedetailid " +
                "ORDER BY invctimtypdt_sortorder, invctimtypdt_invoicetimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetimes " +
                "WHERE invctim_invc_invoiceid = ? AND invctim_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceTimesByInvoiceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InvoiceTime> getInvoiceTimesByInvoice(Invoice invoice, EntityPermission entityPermission) {
        return InvoiceTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceTimesByInvoiceQueries, invoice, Session.MAX_TIME);
    }

    public List<InvoiceTime> getInvoiceTimesByInvoice(Invoice invoice) {
        return getInvoiceTimesByInvoice(invoice, EntityPermission.READ_ONLY);
    }

    public List<InvoiceTime> getInvoiceTimesByInvoiceForUpdate(Invoice invoice) {
        return getInvoiceTimesByInvoice(invoice, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getInvoiceTimesByInvoiceTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicetimes, invoices, invoicedetails " +
                "WHERE invctim_invctimtyp_invoicetimetypeid = ? AND invctim_thrutime = ? " +
                "AND invctim_invc_invoiceid = invctim_invc_invoiceid AND inv_activedetailid = invdt_invoicedetailid " +
                "ORDER BY invdt_invoicename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicetimes " +
                "WHERE invctim_invctimtyp_invoicetimetypeid = ? AND invctim_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceTimesByInvoiceTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InvoiceTime> getInvoiceTimesByInvoiceTimeType(InvoiceTimeType invoiceTimeType, EntityPermission entityPermission) {
        return InvoiceTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceTimesByInvoiceTimeTypeQueries, invoiceTimeType, Session.MAX_TIME);
    }

    public List<InvoiceTime> getInvoiceTimesByInvoiceTimeType(InvoiceTimeType invoiceTimeType) {
        return getInvoiceTimesByInvoiceTimeType(invoiceTimeType, EntityPermission.READ_ONLY);
    }

    public List<InvoiceTime> getInvoiceTimesByInvoiceTimeTypeForUpdate(InvoiceTimeType invoiceTimeType) {
        return getInvoiceTimesByInvoiceTimeType(invoiceTimeType, EntityPermission.READ_WRITE);
    }

    public InvoiceTimeTransfer getInvoiceTimeTransfer(UserVisit userVisit, InvoiceTime invoiceTime) {
        return getInvoiceTransferCaches(userVisit).getInvoiceTimeTransferCache().getInvoiceTimeTransfer(invoiceTime);
    }

    public List<InvoiceTimeTransfer> getInvoiceTimeTransfers(UserVisit userVisit, List<InvoiceTime> invoiceTimes) {
        List<InvoiceTimeTransfer> invoiceTimeTransfers = new ArrayList<>(invoiceTimes.size());
        InvoiceTimeTransferCache invoiceTimeTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceTimeTransferCache();

        invoiceTimes.stream().forEach((invoiceTime) -> {
            invoiceTimeTransfers.add(invoiceTimeTransferCache.getInvoiceTimeTransfer(invoiceTime));
        });

        return invoiceTimeTransfers;
    }

    public List<InvoiceTimeTransfer> getInvoiceTimeTransfersByInvoice(UserVisit userVisit, Invoice invoice) {
        return getInvoiceTimeTransfers(userVisit, getInvoiceTimesByInvoice(invoice));
    }

    public List<InvoiceTimeTransfer> getInvoiceTimeTransfersByInvoiceTimeType(UserVisit userVisit, InvoiceTimeType invoiceTimeType) {
        return getInvoiceTimeTransfers(userVisit, getInvoiceTimesByInvoiceTimeType(invoiceTimeType));
    }

    public void updateInvoiceTimeFromValue(InvoiceTimeValue invoiceTimeValue, BasePK updatedBy) {
        if(invoiceTimeValue.hasBeenModified()) {
            InvoiceTime invoiceTime = InvoiceTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    invoiceTimeValue.getPrimaryKey());

            invoiceTime.setThruTime(session.START_TIME_LONG);
            invoiceTime.store();

            InvoicePK invoicePK = invoiceTime.getInvoicePK(); // Not updated
            InvoiceTimeTypePK invoiceTimeTypePK = invoiceTime.getInvoiceTimeTypePK(); // Not updated
            Long time = invoiceTimeValue.getTime();

            invoiceTime = InvoiceTimeFactory.getInstance().create(invoicePK, invoiceTimeTypePK, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(invoicePK, EventTypes.MODIFY.name(), invoiceTime.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteInvoiceTime(InvoiceTime invoiceTime, BasePK deletedBy) {
        invoiceTime.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(invoiceTime.getInvoiceTimeTypePK(), EventTypes.MODIFY.name(), invoiceTime.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteInvoiceTimes(List<InvoiceTime> invoiceTimes, BasePK deletedBy) {
        invoiceTimes.stream().forEach((invoiceTime) -> {
            deleteInvoiceTime(invoiceTime, deletedBy);
        });
    }

    public void deleteInvoiceTimesByInvoice(Invoice invoice, BasePK deletedBy) {
        deleteInvoiceTimes(getInvoiceTimesByInvoiceForUpdate(invoice), deletedBy);
    }

    public void deleteInvoiceTimesByInvoiceTimeType(InvoiceTimeType invoiceTimeType, BasePK deletedBy) {
        deleteInvoiceTimes(getInvoiceTimesByInvoiceTimeTypeForUpdate(invoiceTimeType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Invoice Aliases
    // --------------------------------------------------------------------------------
    
    public InvoiceAlias createInvoiceAlias(Invoice invoice, InvoiceAliasType invoiceAliasType, String alias, BasePK createdBy) {
        InvoiceAlias invoiceAlias = InvoiceAliasFactory.getInstance().create(invoice, invoiceAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(invoice.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceAlias.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return invoiceAlias;
    }
    
    private static final Map<EntityPermission, String> getInvoiceAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicealiases " +
                "WHERE invcal_invc_invoiceid = ? AND invcal_invcat_invoicealiastypeid = ? AND invcal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicealiases " +
                "WHERE invcal_invc_invoiceid = ? AND invcal_invcat_invoicealiastypeid = ? AND invcal_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceAliasQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private InvoiceAlias getInvoiceAlias(Invoice invoice, InvoiceAliasType invoiceAliasType, EntityPermission entityPermission) {
        return InvoiceAliasFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceAliasQueries,
                invoice, invoiceAliasType, Session.MAX_TIME);
    }
    
    public InvoiceAlias getInvoiceAlias(Invoice invoice, InvoiceAliasType invoiceAliasType) {
        return getInvoiceAlias(invoice, invoiceAliasType, EntityPermission.READ_ONLY);
    }
    
    public InvoiceAlias getInvoiceAliasForUpdate(Invoice invoice, InvoiceAliasType invoiceAliasType) {
        return getInvoiceAlias(invoice, invoiceAliasType, EntityPermission.READ_WRITE);
    }
    
    public InvoiceAliasValue getInvoiceAliasValue(InvoiceAlias invoiceAlias) {
        return invoiceAlias == null? null: invoiceAlias.getInvoiceAliasValue().clone();
    }
    
    public InvoiceAliasValue getInvoiceAliasValueForUpdate(Invoice invoice, InvoiceAliasType invoiceAliasType) {
        return getInvoiceAliasValue(getInvoiceAliasForUpdate(invoice, invoiceAliasType));
    }
    
    private static final Map<EntityPermission, String> getInvoiceAliasesByInvoiceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicealiases, invoicealiastypes, invoicealiastypedetails " +
                "WHERE invcal_invc_invoiceid = ? AND invcal_thrutime = ? " +
                "AND invcal_invcat_invoicealiastypeid = invcat_invoicealiastypeid AND invcat_lastdetailid = invcatdt_invoicealiastypedetailid" +
                "ORDER BY invcatdt_sortorder, invcatdt_invoicealiastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicealiases " +
                "WHERE invcal_invc_invoiceid = ? AND invcal_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceAliasesByInvoiceQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<InvoiceAlias> getInvoiceAliasesByInvoice(Invoice invoice, EntityPermission entityPermission) {
        return InvoiceAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceAliasesByInvoiceQueries,
                invoice, Session.MAX_TIME);
    }
    
    public List<InvoiceAlias> getInvoiceAliasesByInvoice(Invoice invoice) {
        return getInvoiceAliasesByInvoice(invoice, EntityPermission.READ_ONLY);
    }
    
    public List<InvoiceAlias> getInvoiceAliasesByInvoiceForUpdate(Invoice invoice) {
        return getInvoiceAliasesByInvoice(invoice, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getInvoiceAliasesByInvoiceAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicealiases, invoicees, invoicedetails " +
                "WHERE invcal_invcat_invoicealiastypeid = ? AND invcal_thrutime = ? " +
                "AND invcal_invc_invoiceid = invc_invoiceid AND invc_lastdetailid = invcdt_invoicedetailid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicealiases " +
                "WHERE invcal_invcat_invoicealiastypeid = ? AND invcal_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceAliasesByInvoiceAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<InvoiceAlias> getInvoiceAliasesByInvoiceAliasType(InvoiceAliasType invoiceAliasType, EntityPermission entityPermission) {
        return InvoiceAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceAliasesByInvoiceAliasTypeQueries,
                invoiceAliasType, Session.MAX_TIME);
    }
    
    public List<InvoiceAlias> getInvoiceAliasesByInvoiceAliasType(InvoiceAliasType invoiceAliasType) {
        return getInvoiceAliasesByInvoiceAliasType(invoiceAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<InvoiceAlias> getInvoiceAliasesByInvoiceAliasTypeForUpdate(InvoiceAliasType invoiceAliasType) {
        return getInvoiceAliasesByInvoiceAliasType(invoiceAliasType, EntityPermission.READ_WRITE);
    }
    
    public InvoiceAliasTransfer getInvoiceAliasTransfer(UserVisit userVisit, InvoiceAlias invoiceAlias) {
        return getInvoiceTransferCaches(userVisit).getInvoiceAliasTransferCache().getInvoiceAliasTransfer(invoiceAlias);
    }
    
    public List<InvoiceAliasTransfer> getInvoiceAliasTransfersByInvoice(UserVisit userVisit, Invoice invoice) {
        List<InvoiceAlias> invoicealiases = getInvoiceAliasesByInvoice(invoice);
        List<InvoiceAliasTransfer> invoiceAliasTransfers = new ArrayList<>(invoicealiases.size());
        InvoiceAliasTransferCache invoiceAliasTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceAliasTransferCache();
        
        invoicealiases.stream().forEach((invoiceAlias) -> {
            invoiceAliasTransfers.add(invoiceAliasTransferCache.getInvoiceAliasTransfer(invoiceAlias));
        });
        
        return invoiceAliasTransfers;
    }
    
    public void updateInvoiceAliasFromValue(InvoiceAliasValue invoiceAliasValue, BasePK updatedBy) {
        if(invoiceAliasValue.hasBeenModified()) {
            InvoiceAlias invoiceAlias = InvoiceAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceAliasValue.getPrimaryKey());
            
            invoiceAlias.setThruTime(session.START_TIME_LONG);
            invoiceAlias.store();
            
            InvoicePK invoicePK = invoiceAlias.getInvoicePK();
            InvoiceAliasTypePK invoiceAliasTypePK = invoiceAlias.getInvoiceAliasTypePK();
            String alias  = invoiceAliasValue.getAlias();
            
            invoiceAlias = InvoiceAliasFactory.getInstance().create(invoicePK, invoiceAliasTypePK, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(invoicePK, EventTypes.MODIFY.name(), invoiceAlias.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteInvoiceAlias(InvoiceAlias invoiceAlias, BasePK deletedBy) {
        invoiceAlias.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(invoiceAlias.getInvoicePK(), EventTypes.MODIFY.name(), invoiceAlias.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteInvoiceAliasesByInvoiceAliasType(InvoiceAliasType invoiceAliasType, BasePK deletedBy) {
        List<InvoiceAlias> invoicealiases = getInvoiceAliasesByInvoiceAliasTypeForUpdate(invoiceAliasType);
        
        invoicealiases.stream().forEach((invoiceAlias) -> {
            deleteInvoiceAlias(invoiceAlias, deletedBy);
        });
    }
    
    public void deleteInvoiceAliasesByInvoice(Invoice invoice, BasePK deletedBy) {
        List<InvoiceAlias> invoicealiases = getInvoiceAliasesByInvoiceForUpdate(invoice);
        
        invoicealiases.stream().forEach((invoiceAlias) -> {
            deleteInvoiceAlias(invoiceAlias, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Lines
    // --------------------------------------------------------------------------------
    
    public InvoiceLine createInvoiceLine(Invoice invoice, Integer invoiceLineSequence, InvoiceLine parentInvoiceLine, InvoiceLineType invoiceLineType,
            InvoiceLineUseType invoiceLineUseType, Long amount, String description, BasePK createdBy) {
        if(invoiceLineSequence == null) {
            InvoiceStatus invoiceStatus = getInvoiceStatusForUpdate(invoice);
            
            do {
                invoiceLineSequence = invoiceStatus.getInvoiceLineSequence() + 1;
                invoiceStatus.setInvoiceLineSequence(invoiceLineSequence);
            } while(invoiceLineExists(invoice, invoiceLineSequence));
        }
        
        InvoiceLine invoiceLine = InvoiceLineFactory.getInstance().create();
        InvoiceLineDetail invoiceLineDetail = InvoiceLineDetailFactory.getInstance().create(invoiceLine, invoice, invoiceLineSequence, parentInvoiceLine,
                invoiceLineType, invoiceLineUseType, amount, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        invoiceLine = InvoiceLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceLine.getPrimaryKey());
        invoiceLine.setActiveDetail(invoiceLineDetail);
        invoiceLine.setLastDetail(invoiceLineDetail);
        invoiceLine.store();
        
        sendEventUsingNames(invoiceLine.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return invoiceLine;
    }
    
    public boolean invoiceLineExists(Invoice invoice, Integer invoiceLineSequence) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM invoicelines, invoicelinedetails " +
                "WHERE invcl_activedetailid = invcldt_invoicelinedetailid AND invcldt_invc_invoiceid = ? AND invcldt_invoicelinesequence = ?",
                invoice, invoiceLineSequence) == 1;
    }
    
    private static final Map<EntityPermission, String> getInvoiceLineQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicelines, invoicelinedetails " +
                "WHERE invcl_activedetailid = invcldt_invoicelinedetailid AND invcldt_invc_invoiceid = ? AND invcldt_invoicelinesequence = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicelines, invoicelinedetails " +
                "WHERE invcl_activedetailid = invcldt_invoicelinedetailid AND invcldt_invc_invoiceid = ? AND invcldt_invoicelinesequence = ? " +
                "FOR UPDATE");
        getInvoiceLineQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private InvoiceLine getInvoiceLine(Invoice invoice, Integer invoiceLineSequence, EntityPermission entityPermission) {
        return InvoiceLineFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceLineQueries,
                invoice, invoiceLineSequence);
    }
    
    public InvoiceLine getInvoiceLine(Invoice invoice, Integer invoiceLineSequence) {
        return getInvoiceLine(invoice, invoiceLineSequence, EntityPermission.READ_ONLY);
    }
    
    public InvoiceLine getInvoiceLineForUpdate(Invoice invoice, Integer invoiceLineSequence) {
        return getInvoiceLine(invoice, invoiceLineSequence, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getInvoiceLinesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicelines, invoicelinedetails " +
                "WHERE invcl_activedetailid = invcldt_invoicelinedetailid AND invcldt_invc_invoiceid = ? " +
                "ORDER BY invcldt_invoicelinesequence");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicelines, invoicelinedetails " +
                "WHERE invcl_activedetailid = invcldt_invoicelinedetailid AND invcldt_invc_invoiceid = ? " +
                "FOR UPDATE");
        getInvoiceLinesQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<InvoiceLine> getInvoiceLines(Invoice invoice, EntityPermission entityPermission) {
        return InvoiceLineFactory.getInstance().getEntitiesFromQuery(entityPermission, getInvoiceLinesQueries,
                invoice);
    }
    
    public List<InvoiceLine> getInvoiceLines(Invoice invoice) {
        return getInvoiceLines(invoice, EntityPermission.READ_ONLY);
    }
    
    public List<InvoiceLine> getInvoiceLinesForUpdate(Invoice invoice) {
        return getInvoiceLines(invoice, EntityPermission.READ_WRITE);
    }
    
    public InvoiceLineTransfer getInvoiceLineTransfer(UserVisit userVisit, InvoiceLine invoiceLine) {
        return getInvoiceTransferCaches(userVisit).getInvoiceLineTransferCache().getInvoiceLineTransfer(invoiceLine);
    }
    
    public List<InvoiceLineTransfer> getInvoiceLineTransfers(final UserVisit userVisit, final List<InvoiceLine> invoiceLines) {
        List<InvoiceLineTransfer> invoiceLineTransfers = new ArrayList<>(invoiceLines.size());
        InvoiceLineTransferCache invoiceLineTransferCache = getInvoiceTransferCaches(userVisit).getInvoiceLineTransferCache();
        
        invoiceLines.stream().forEach((invoiceLine) -> {
            invoiceLineTransfers.add(invoiceLineTransferCache.getInvoiceLineTransfer(invoiceLine));
        });
        
        return invoiceLineTransfers;
    }
    
    public List<InvoiceLineTransfer> getInvoiceLineTransfersByInvoice(final UserVisit userVisit, final Invoice invoice) {
        return getInvoiceLineTransfers(userVisit, getInvoiceLines(invoice));
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Items
    // --------------------------------------------------------------------------------
    
    public InvoiceLineItem createInvoiceLineItem(InvoiceLine invoiceLine, Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType, Integer quantity, BasePK createdBy) {
        InvoiceLineItem invoiceLineItem = InvoiceLineItemFactory.getInstance().create(invoiceLine, item, inventoryCondition, unitOfMeasureType, quantity, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(invoiceLine.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceLineItem.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return invoiceLineItem;
    }
    
    private static final Map<EntityPermission, String> getInvoiceLineItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicelineitems " +
                "WHERE invclitm_invcl_invoicelineid = ? AND invclitm_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicelineitems " +
                "WHERE invclitm_invcl_invoicelineid = ? AND invclitm_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceLineItemQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private InvoiceLineItem getInvoiceLineItem(InvoiceLine invoiceLine, EntityPermission entityPermission) {
        return InvoiceLineItemFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceLineItemQueries,
                invoiceLine, Session.MAX_TIME_LONG);
    }
    
    public InvoiceLineItem getInvoiceLineItem(InvoiceLine invoiceLine) {
        return getInvoiceLineItem(invoiceLine, EntityPermission.READ_ONLY);
    }
    
    public InvoiceLineItem getInvoiceLineItemForUpdate(InvoiceLine invoiceLine) {
        return getInvoiceLineItem(invoiceLine, EntityPermission.READ_WRITE);
    }
    
    public InvoiceLineItemValue getInvoiceLineItemValue(InvoiceLineItem invoiceLineItem) {
        return invoiceLineItem == null? null: invoiceLineItem.getInvoiceLineItemValue().clone();
    }
    
    public InvoiceLineItemValue getInvoiceLineItemValueForUpdate(InvoiceLine invoiceLine) {
        return getInvoiceLineItemValue(getInvoiceLineItemForUpdate(invoiceLine));
    }
    
    public InvoiceLineItemTransfer getInvoiceLineItemTransfer(UserVisit userVisit, InvoiceLineItem invoiceLineItem) {
        return getInvoiceTransferCaches(userVisit).getInvoiceLineItemTransferCache().getInvoiceLineItemTransfer(invoiceLineItem);
    }
    
    public void updateInvoiceLineItemFromValue(InvoiceLineItemValue invoiceLineItemValue, BasePK updatedBy) {
        if(invoiceLineItemValue.hasBeenModified()) {
            InvoiceLineItem invoiceLineItem = InvoiceLineItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceLineItemValue.getPrimaryKey());
            
            invoiceLineItem.setThruTime(session.START_TIME_LONG);
            invoiceLineItem.store();
            
            InvoiceLinePK invoiceLinePK = invoiceLineItem.getInvoiceLinePK(); // Not updated
            ItemPK itemPK = invoiceLineItem.getItemPK();
            InventoryConditionPK inventoryConditionPK = invoiceLineItem.getInventoryConditionPK();
            UnitOfMeasureTypePK unitOfMeasureTypePK = invoiceLineItem.getUnitOfMeasureTypePK();
            Integer quantity = invoiceLineItem.getQuantity();
            
            invoiceLineItem = InvoiceLineItemFactory.getInstance().create(invoiceLinePK, itemPK, inventoryConditionPK, unitOfMeasureTypePK, quantity, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(invoiceLinePK, EventTypes.MODIFY.name(), invoiceLineItem.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteInvoiceLineItem(InvoiceLineItem invoiceLineItem, BasePK deletedBy) {
        invoiceLineItem.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(invoiceLineItem.getInvoiceLinePK(), EventTypes.MODIFY.name(), invoiceLineItem.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteInvoiceLineItemsByInvoiceLine(InvoiceLine invoiceLine, BasePK deletedBy) {
        InvoiceLineItem invoiceLineItem = getInvoiceLineItemForUpdate(invoiceLine);
        
        if(invoiceLineItem != null) {
            deleteInvoiceLineItem(invoiceLineItem, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Gl Accounts
    // --------------------------------------------------------------------------------
    
    public InvoiceLineGlAccount createInvoiceLineGlAccount(InvoiceLine invoiceLine, GlAccount glAccount, BasePK createdBy) {
        InvoiceLineGlAccount invoiceLineGlAccount = InvoiceLineGlAccountFactory.getInstance().create(invoiceLine, glAccount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(invoiceLine.getPrimaryKey(), EventTypes.MODIFY.name(), invoiceLineGlAccount.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return invoiceLineGlAccount;
    }
    
    private static final Map<EntityPermission, String> getInvoiceLineGlAccountQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM invoicelineglaccounts " +
                "WHERE invclgla_invcl_invoicelineid = ? AND invclgla_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM invoicelineglaccounts " +
                "WHERE invclgla_invcl_invoicelineid = ? AND invclgla_thrutime = ? " +
                "FOR UPDATE");
        getInvoiceLineGlAccountQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private InvoiceLineGlAccount getInvoiceLineGlAccount(InvoiceLine invoiceLine, EntityPermission entityPermission) {
        return InvoiceLineGlAccountFactory.getInstance().getEntityFromQuery(entityPermission, getInvoiceLineGlAccountQueries,
                invoiceLine, Session.MAX_TIME_LONG);
    }
    
    public InvoiceLineGlAccount getInvoiceLineGlAccount(InvoiceLine invoiceLine) {
        return getInvoiceLineGlAccount(invoiceLine, EntityPermission.READ_ONLY);
    }
    
    public InvoiceLineGlAccount getInvoiceLineGlAccountForUpdate(InvoiceLine invoiceLine) {
        return getInvoiceLineGlAccount(invoiceLine, EntityPermission.READ_WRITE);
    }
    
    public InvoiceLineGlAccountValue getInvoiceLineGlAccountValue(InvoiceLineGlAccount invoiceLineGlAccount) {
        return invoiceLineGlAccount == null? null: invoiceLineGlAccount.getInvoiceLineGlAccountValue().clone();
    }
    
    public InvoiceLineGlAccountValue getInvoiceLineGlAccountValueForUpdate(InvoiceLine invoiceLine) {
        return getInvoiceLineGlAccountValue(getInvoiceLineGlAccountForUpdate(invoiceLine));
    }
    
    public InvoiceLineGlAccountTransfer getInvoiceLineGlAccountTransfer(UserVisit userVisit, InvoiceLineGlAccount invoiceLineGlAccount) {
        return getInvoiceTransferCaches(userVisit).getInvoiceLineGlAccountTransferCache().getInvoiceLineGlAccountTransfer(invoiceLineGlAccount);
    }
    
    public void updateInvoiceLineGlAccountFromValue(InvoiceLineGlAccountValue invoiceLineGlAccountValue, BasePK updatedBy) {
        if(invoiceLineGlAccountValue.hasBeenModified()) {
            InvoiceLineGlAccount invoiceLineGlAccount = InvoiceLineGlAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceLineGlAccountValue.getPrimaryKey());
            
            invoiceLineGlAccount.setThruTime(session.START_TIME_LONG);
            invoiceLineGlAccount.store();
            
            InvoiceLinePK invoiceLinePK = invoiceLineGlAccount.getInvoiceLinePK(); // Not updated
            GlAccountPK glAccountPK = invoiceLineGlAccountValue.getGlAccountPK();
            
            invoiceLineGlAccount = InvoiceLineGlAccountFactory.getInstance().create(invoiceLinePK, glAccountPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(invoiceLinePK, EventTypes.MODIFY.name(), invoiceLineGlAccount.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteInvoiceLineGlAccount(InvoiceLineGlAccount invoiceLineGlAccount, BasePK deletedBy) {
        invoiceLineGlAccount.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(invoiceLineGlAccount.getInvoiceLinePK(), EventTypes.MODIFY.name(), invoiceLineGlAccount.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteInvoiceLineGlAccountsByInvoiceLine(InvoiceLine invoiceLine, BasePK deletedBy) {
        InvoiceLineGlAccount invoiceLineGlAccount = getInvoiceLineGlAccountForUpdate(invoiceLine);
        
        if(invoiceLineGlAccount != null) {
            deleteInvoiceLineGlAccount(invoiceLineGlAccount, deletedBy);
        }
    }
    
}

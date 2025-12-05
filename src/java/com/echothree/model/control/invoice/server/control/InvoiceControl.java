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

package com.echothree.model.control.invoice.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.invoice.common.InvoiceRoleTypes;
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
import com.echothree.model.control.invoice.server.transfer.InvoiceLineGlAccountTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceLineItemTransferCache;
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
import com.echothree.model.control.invoice.server.transfer.InvoiceTypeDescriptionTransferCache;
import com.echothree.model.control.invoice.server.transfer.InvoiceTypeTransferCache;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.invoice.server.entity.Invoice;
import com.echothree.model.data.invoice.server.entity.InvoiceAlias;
import com.echothree.model.data.invoice.server.entity.InvoiceAliasType;
import com.echothree.model.data.invoice.server.entity.InvoiceAliasTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceLine;
import com.echothree.model.data.invoice.server.entity.InvoiceLineGlAccount;
import com.echothree.model.data.invoice.server.entity.InvoiceLineItem;
import com.echothree.model.data.invoice.server.entity.InvoiceLineType;
import com.echothree.model.data.invoice.server.entity.InvoiceLineTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceLineUseType;
import com.echothree.model.data.invoice.server.entity.InvoiceLineUseTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceRole;
import com.echothree.model.data.invoice.server.entity.InvoiceRoleType;
import com.echothree.model.data.invoice.server.entity.InvoiceRoleTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceStatus;
import com.echothree.model.data.invoice.server.entity.InvoiceTime;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeType;
import com.echothree.model.data.invoice.server.entity.InvoiceTimeTypeDescription;
import com.echothree.model.data.invoice.server.entity.InvoiceType;
import com.echothree.model.data.invoice.server.entity.InvoiceTypeDescription;
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
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.payment.server.entity.BillingAccount;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class InvoiceControl
        extends BaseModelControl {
    
    /** Creates a new instance of InvoiceControl */
    protected InvoiceControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    InvoiceLineUseTypeTransferCache invoiceLineUseTypeTransferCache;

    @Inject
    InvoiceRoleTypeTransferCache invoiceRoleTypeTransferCache;

    @Inject
    InvoiceRoleTransferCache invoiceRoleTransferCache;

    @Inject
    InvoiceTypeTransferCache invoiceTypeTransferCache;

    @Inject
    InvoiceTypeDescriptionTransferCache invoiceTypeDescriptionTransferCache;

    @Inject
    InvoiceAliasTypeTransferCache invoiceAliasTypeTransferCache;

    @Inject
    InvoiceAliasTypeDescriptionTransferCache invoiceAliasTypeDescriptionTransferCache;

    @Inject
    InvoiceLineTypeTransferCache invoiceLineTypeTransferCache;

    @Inject
    InvoiceLineTypeDescriptionTransferCache invoiceLineTypeDescriptionTransferCache;

    @Inject
    InvoiceTransferCache invoiceTransferCache;

    @Inject
    InvoiceAliasTransferCache invoiceAliasTransferCache;

    @Inject
    InvoiceLineTransferCache invoiceLineTransferCache;

    @Inject
    InvoiceLineItemTransferCache invoiceLineItemTransferCache;

    @Inject
    InvoiceLineGlAccountTransferCache invoiceLineGlAccountTransferCache;

    @Inject
    InvoiceTimeTypeTransferCache invoiceTimeTypeTransferCache;

    @Inject
    InvoiceTimeTypeDescriptionTransferCache invoiceTimeTypeDescriptionTransferCache;

    @Inject
    InvoiceTimeTransferCache invoiceTimeTransferCache;

    // --------------------------------------------------------------------------------
    //   Invoice Line Use Types
    // --------------------------------------------------------------------------------
    
    public InvoiceLineUseType createInvoiceLineUseType(String invoiceLineUseTypeName) {
        return InvoiceLineUseTypeFactory.getInstance().create(invoiceLineUseTypeName);
    }
    
    public List<InvoiceLineUseType> getInvoiceLineUseTypes() {
        var ps = InvoiceLineUseTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM invoicelineusetypes " +
                "ORDER BY invclut_invoicelineusetypename");
        
        return InvoiceLineUseTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public InvoiceLineUseType getInvoiceLineUseTypeByName(String invoiceLineUseTypeName) {
        InvoiceLineUseType invoiceLineUseType;
        
        try {
            var ps = InvoiceLineUseTypeFactory.getInstance().prepareStatement(
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
        return invoiceLineUseTypeTransferCache.getInvoiceLineUseTypeTransfer(userVisit, invoiceLineUseType);
    }
    
    private List<InvoiceLineUseTypeTransfer> getInvoiceLineUseTypeTransfers(final UserVisit userVisit, final List<InvoiceLineUseType> invoiceLineUseTypes) {
        List<InvoiceLineUseTypeTransfer> invoiceLineUseTypeTransfers = new ArrayList<>(invoiceLineUseTypes.size());
        
        invoiceLineUseTypes.forEach((invoiceLineUseType) ->
                invoiceLineUseTypeTransfers.add(invoiceLineUseTypeTransferCache.getInvoiceLineUseTypeTransfer(userVisit, invoiceLineUseType))
        );
        
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
        InvoiceLineUseTypeDescription invoiceLineUseTypeDescription;
        
        try {
            var ps = InvoiceLineUseTypeDescriptionFactory.getInstance().prepareStatement(
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
        var invoiceLineUseTypeDescription = getInvoiceLineUseTypeDescription(invoiceLineUseType, language);
        
        if(invoiceLineUseTypeDescription == null && !language.getIsDefault()) {
            invoiceLineUseTypeDescription = getInvoiceLineUseTypeDescription(invoiceLineUseType, partyControl.getDefaultLanguage());
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
        var ps = InvoiceRoleTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM invoiceroletypes " +
                "ORDER BY invcrtyp_sortorder, invcrtyp_invoiceroletypename");
        
        return InvoiceRoleTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public InvoiceRoleType getInvoiceRoleTypeByName(String invoiceRoleTypeName) {
        InvoiceRoleType invoiceRoleType;
        
        try {
            var ps = InvoiceRoleTypeFactory.getInstance().prepareStatement(
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
        return invoiceRoleTypeTransferCache.getInvoiceRoleTypeTransfer(userVisit, invoiceRoleType);
    }
    
    private List<InvoiceRoleTypeTransfer> getInvoiceRoleTypeTransfers(final UserVisit userVisit, final List<InvoiceRoleType> invoiceRoleTypes) {
        List<InvoiceRoleTypeTransfer> invoiceRoleTypeTransfers = new ArrayList<>(invoiceRoleTypes.size());
        
        invoiceRoleTypes.forEach((invoiceRoleType) ->
                invoiceRoleTypeTransfers.add(invoiceRoleTypeTransferCache.getInvoiceRoleTypeTransfer(userVisit, invoiceRoleType))
        );

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
        InvoiceRoleTypeDescription invoiceRoleTypeDescription;
        
        try {
            var ps = InvoiceRoleTypeDescriptionFactory.getInstance().prepareStatement(
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
        var invoiceRoleTypeDescription = getInvoiceRoleTypeDescription(invoiceRoleType, language);
        
        if(invoiceRoleTypeDescription == null && !language.getIsDefault()) {
            invoiceRoleTypeDescription = getInvoiceRoleTypeDescription(invoiceRoleType, partyControl.getDefaultLanguage());
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
        var defaultInvoiceType = getDefaultInvoiceType();
        var defaultFound = defaultInvoiceType != null;
        
        if(defaultFound && isDefault) {
            var defaultInvoiceTypeDetailValue = getDefaultInvoiceTypeDetailValueForUpdate();
            
            defaultInvoiceTypeDetailValue.setIsDefault(false);
            updateInvoiceTypeFromValue(defaultInvoiceTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var invoiceType = InvoiceTypeFactory.getInstance().create();
        var invoiceTypeDetail = InvoiceTypeDetailFactory.getInstance().create(invoiceType, invoiceTypeName,
                parentInvoiceType, invoiceSequenceType, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        invoiceType = InvoiceTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                invoiceType.getPrimaryKey());
        invoiceType.setActiveDetail(invoiceTypeDetail);
        invoiceType.setLastDetail(invoiceTypeDetail);
        invoiceType.store();
        
        sendEvent(invoiceType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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
        return invoiceTypeTransferCache.getInvoiceTypeTransfer(userVisit, invoiceType);
    }
    
    public List<InvoiceTypeTransfer> getInvoiceTypeTransfers(UserVisit userVisit) {
        var invoiceTypes = getInvoiceTypes();
        List<InvoiceTypeTransfer> invoiceTypeTransfers = new ArrayList<>(invoiceTypes.size());
        
        invoiceTypes.forEach((invoiceType) ->
                invoiceTypeTransfers.add(invoiceTypeTransferCache.getInvoiceTypeTransfer(userVisit, invoiceType))
        );
        
        return invoiceTypeTransfers;
    }
    
    public InvoiceTypeChoicesBean getInvoiceTypeChoices(String defaultInvoiceTypeChoice, Language language,
            boolean allowNullChoice) {
        var invoiceTypes = getInvoiceTypes();
        var size = invoiceTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInvoiceTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var invoiceType : invoiceTypes) {
            var invoiceTypeDetail = invoiceType.getLastDetail();
            
            var label = getBestInvoiceTypeDescription(invoiceType, language);
            var value = invoiceTypeDetail.getInvoiceTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultInvoiceTypeChoice != null && defaultInvoiceTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && invoiceTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new InvoiceTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentInvoiceTypeSafe(InvoiceType invoiceType, InvoiceType parentInvoiceType) {
        var safe = true;
        
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
            var invoiceType = InvoiceTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceTypeDetailValue.getInvoiceTypePK());
            var invoiceTypeDetail = invoiceType.getActiveDetailForUpdate();
            
            invoiceTypeDetail.setThruTime(session.getStartTime());
            invoiceTypeDetail.store();

            var invoiceTypePK = invoiceTypeDetail.getInvoiceTypePK();
            var invoiceTypeName = invoiceTypeDetailValue.getInvoiceTypeName();
            var parentInvoiceTypePK = invoiceTypeDetailValue.getParentInvoiceTypePK();
            var invoiceSequenceTypePK = invoiceTypeDetailValue.getInvoiceSequenceTypePK();
            var isDefault = invoiceTypeDetailValue.getIsDefault();
            var sortOrder = invoiceTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultInvoiceType = getDefaultInvoiceType();
                var defaultFound = defaultInvoiceType != null && !defaultInvoiceType.equals(invoiceType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInvoiceTypeDetailValue = getDefaultInvoiceTypeDetailValueForUpdate();
                    
                    defaultInvoiceTypeDetailValue.setIsDefault(false);
                    updateInvoiceTypeFromValue(defaultInvoiceTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            invoiceTypeDetail = InvoiceTypeDetailFactory.getInstance().create(invoiceTypePK, invoiceTypeName,
                    parentInvoiceTypePK, invoiceSequenceTypePK, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            invoiceType.setActiveDetail(invoiceTypeDetail);
            invoiceType.setLastDetail(invoiceTypeDetail);
            
            sendEvent(invoiceTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateInvoiceTypeFromValue(InvoiceTypeDetailValue invoiceTypeDetailValue, BasePK updatedBy) {
        updateInvoiceTypeFromValue(invoiceTypeDetailValue, true, updatedBy);
    }
    
    private void deleteInvoiceType(InvoiceType invoiceType, boolean checkDefault, BasePK deletedBy) {
        var invoiceTypeDetail = invoiceType.getLastDetailForUpdate();

        deleteInvoiceTypesByParentInvoiceType(invoiceType, deletedBy);
        deleteInvoiceLineTypesByInvoiceType(invoiceType, deletedBy);
        deleteInvoiceTypeDescriptionsByInvoiceType(invoiceType, deletedBy);
        
        invoiceTypeDetail.setThruTime(session.getStartTime());
        invoiceType.setActiveDetail(null);
        invoiceType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultInvoiceType = getDefaultInvoiceType();

            if(defaultInvoiceType == null) {
                var invoiceTypes = getInvoiceTypesForUpdate();

                if(!invoiceTypes.isEmpty()) {
                    var iter = invoiceTypes.iterator();
                    if(iter.hasNext()) {
                        defaultInvoiceType = iter.next();
                    }
                    var invoiceTypeDetailValue = Objects.requireNonNull(defaultInvoiceType).getLastDetailForUpdate().getInvoiceTypeDetailValue().clone();

                    invoiceTypeDetailValue.setIsDefault(true);
                    updateInvoiceTypeFromValue(invoiceTypeDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEvent(invoiceType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteInvoiceType(InvoiceType invoiceType, BasePK deletedBy) {
        deleteInvoiceType(invoiceType, true, deletedBy);
    }

    private void deleteInvoiceTypes(List<InvoiceType> invoiceTypes, boolean checkDefault, BasePK deletedBy) {
        invoiceTypes.forEach((invoiceType) -> deleteInvoiceType(invoiceType, checkDefault, deletedBy));
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
        var invoiceTypeDescription = InvoiceTypeDescriptionFactory.getInstance().create(invoiceType, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(invoiceType.getPrimaryKey(), EventTypes.MODIFY, invoiceTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return invoiceTypeDescription;
    }
    
    private InvoiceTypeDescription getInvoiceTypeDescription(InvoiceType invoiceType, Language language, EntityPermission entityPermission) {
        InvoiceTypeDescription invoiceTypeDescription;
        
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

            var ps = InvoiceTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<InvoiceTypeDescription> invoiceTypeDescriptions;
        
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

            var ps = InvoiceTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var invoiceTypeDescription = getInvoiceTypeDescription(invoiceType, language);
        
        if(invoiceTypeDescription == null && !language.getIsDefault()) {
            invoiceTypeDescription = getInvoiceTypeDescription(invoiceType, partyControl.getDefaultLanguage());
        }
        
        if(invoiceTypeDescription == null) {
            description = invoiceType.getLastDetail().getInvoiceTypeName();
        } else {
            description = invoiceTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public InvoiceTypeDescriptionTransfer getInvoiceTypeDescriptionTransfer(UserVisit userVisit, InvoiceTypeDescription invoiceTypeDescription) {
        return invoiceTypeDescriptionTransferCache.getInvoiceTypeDescriptionTransfer(userVisit, invoiceTypeDescription);
    }
    
    public List<InvoiceTypeDescriptionTransfer> getInvoiceTypeDescriptionTransfersByInvoiceType(UserVisit userVisit, InvoiceType invoiceType) {
        var invoiceTypeDescriptions = getInvoiceTypeDescriptionsByInvoiceType(invoiceType);
        List<InvoiceTypeDescriptionTransfer> invoiceTypeDescriptionTransfers = new ArrayList<>(invoiceTypeDescriptions.size());
        
        invoiceTypeDescriptions.forEach((invoiceTypeDescription) ->
                invoiceTypeDescriptionTransfers.add(invoiceTypeDescriptionTransferCache.getInvoiceTypeDescriptionTransfer(userVisit, invoiceTypeDescription))
        );
        
        return invoiceTypeDescriptionTransfers;
    }
    
    public void updateInvoiceTypeDescriptionFromValue(InvoiceTypeDescriptionValue invoiceTypeDescriptionValue, BasePK updatedBy) {
        if(invoiceTypeDescriptionValue.hasBeenModified()) {
            var invoiceTypeDescription = InvoiceTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceTypeDescriptionValue.getPrimaryKey());
            
            invoiceTypeDescription.setThruTime(session.getStartTime());
            invoiceTypeDescription.store();

            var invoiceType = invoiceTypeDescription.getInvoiceType();
            var language = invoiceTypeDescription.getLanguage();
            var description = invoiceTypeDescriptionValue.getDescription();
            
            invoiceTypeDescription = InvoiceTypeDescriptionFactory.getInstance().create(invoiceType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(invoiceType.getPrimaryKey(), EventTypes.MODIFY, invoiceTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteInvoiceTypeDescription(InvoiceTypeDescription invoiceTypeDescription, BasePK deletedBy) {
        invoiceTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(invoiceTypeDescription.getInvoiceTypePK(), EventTypes.MODIFY, invoiceTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteInvoiceTypeDescriptionsByInvoiceType(InvoiceType invoiceType, BasePK deletedBy) {
        var invoiceTypeDescriptions = getInvoiceTypeDescriptionsByInvoiceTypeForUpdate(invoiceType);
        
        invoiceTypeDescriptions.forEach((invoiceTypeDescription) -> 
                deleteInvoiceTypeDescription(invoiceTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Alias Types
    // --------------------------------------------------------------------------------
    
    public InvoiceAliasType createInvoiceAliasType(InvoiceType invoiceType, String invoiceAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultInvoiceAliasType = getDefaultInvoiceAliasType(invoiceType);
        var defaultFound = defaultInvoiceAliasType != null;
        
        if(defaultFound && isDefault) {
            var defaultInvoiceAliasTypeDetailValue = getDefaultInvoiceAliasTypeDetailValueForUpdate(invoiceType);
            
            defaultInvoiceAliasTypeDetailValue.setIsDefault(false);
            updateInvoiceAliasTypeFromValue(defaultInvoiceAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var invoiceAliasType = InvoiceAliasTypeFactory.getInstance().create();
        var invoiceAliasTypeDetail = InvoiceAliasTypeDetailFactory.getInstance().create(invoiceAliasType, invoiceType, invoiceAliasTypeName,
                validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        invoiceAliasType = InvoiceAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceAliasType.getPrimaryKey());
        invoiceAliasType.setActiveDetail(invoiceAliasTypeDetail);
        invoiceAliasType.setLastDetail(invoiceAliasTypeDetail);
        invoiceAliasType.store();
        
        sendEvent(invoiceAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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
        return invoiceAliasTypeTransferCache.getInvoiceAliasTypeTransfer(userVisit, invoiceAliasType);
    }
    
    public List<InvoiceAliasTypeTransfer> getInvoiceAliasTypeTransfers(UserVisit userVisit, InvoiceType invoiceType) {
        var invoiceAliasTypes = getInvoiceAliasTypes(invoiceType);
        List<InvoiceAliasTypeTransfer> invoiceAliasTypeTransfers = new ArrayList<>(invoiceAliasTypes.size());
        
        invoiceAliasTypes.forEach((invoiceAliasType) ->
                invoiceAliasTypeTransfers.add(invoiceAliasTypeTransferCache.getInvoiceAliasTypeTransfer(userVisit, invoiceAliasType))
        );
        
        return invoiceAliasTypeTransfers;
    }
    
    public InvoiceAliasTypeChoicesBean getInvoiceAliasTypeChoices(String defaultInvoiceAliasTypeChoice, Language language,
            boolean allowNullChoice, InvoiceType invoiceType) {
        var invoiceAliasTypes = getInvoiceAliasTypes(invoiceType);
        var size = invoiceAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInvoiceAliasTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var invoiceAliasType : invoiceAliasTypes) {
            var invoiceAliasTypeDetail = invoiceAliasType.getLastDetail();
            
            var label = getBestInvoiceAliasTypeDescription(invoiceAliasType, language);
            var value = invoiceAliasTypeDetail.getInvoiceAliasTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultInvoiceAliasTypeChoice != null && defaultInvoiceAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && invoiceAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new InvoiceAliasTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateInvoiceAliasTypeFromValue(InvoiceAliasTypeDetailValue invoiceAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(invoiceAliasTypeDetailValue.hasBeenModified()) {
            var invoiceAliasType = InvoiceAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    invoiceAliasTypeDetailValue.getInvoiceAliasTypePK());
            var invoiceAliasTypeDetail = invoiceAliasType.getActiveDetailForUpdate();
            
            invoiceAliasTypeDetail.setThruTime(session.getStartTime());
            invoiceAliasTypeDetail.store();

            var invoiceAliasTypePK = invoiceAliasTypeDetail.getInvoiceAliasTypePK();
            var invoiceType = invoiceAliasTypeDetail.getInvoiceType();
            var invoiceTypePK = invoiceType.getPrimaryKey();
            var invoiceAliasTypeName = invoiceAliasTypeDetailValue.getInvoiceAliasTypeName();
            var validationPattern = invoiceAliasTypeDetailValue.getValidationPattern();
            var isDefault = invoiceAliasTypeDetailValue.getIsDefault();
            var sortOrder = invoiceAliasTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultInvoiceAliasType = getDefaultInvoiceAliasType(invoiceType);
                var defaultFound = defaultInvoiceAliasType != null && !defaultInvoiceAliasType.equals(invoiceAliasType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInvoiceAliasTypeDetailValue = getDefaultInvoiceAliasTypeDetailValueForUpdate(invoiceType);
                    
                    defaultInvoiceAliasTypeDetailValue.setIsDefault(false);
                    updateInvoiceAliasTypeFromValue(defaultInvoiceAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            invoiceAliasTypeDetail = InvoiceAliasTypeDetailFactory.getInstance().create(invoiceAliasTypePK, invoiceTypePK, invoiceAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            invoiceAliasType.setActiveDetail(invoiceAliasTypeDetail);
            invoiceAliasType.setLastDetail(invoiceAliasTypeDetail);
            
            sendEvent(invoiceAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateInvoiceAliasTypeFromValue(InvoiceAliasTypeDetailValue invoiceAliasTypeDetailValue, BasePK updatedBy) {
        updateInvoiceAliasTypeFromValue(invoiceAliasTypeDetailValue, true, updatedBy);
    }
    
    public void deleteInvoiceAliasType(InvoiceAliasType invoiceAliasType, BasePK deletedBy) {
        deleteInvoiceAliasesByInvoiceAliasType(invoiceAliasType, deletedBy);
        deleteInvoiceAliasTypeDescriptionsByInvoiceAliasType(invoiceAliasType, deletedBy);

        var invoiceAliasTypeDetail = invoiceAliasType.getLastDetailForUpdate();
        invoiceAliasTypeDetail.setThruTime(session.getStartTime());
        invoiceAliasType.setActiveDetail(null);
        invoiceAliasType.store();
        
        // Check for default, and pick one if necessary
        var invoiceType = invoiceAliasTypeDetail.getInvoiceType();
        var defaultInvoiceAliasType = getDefaultInvoiceAliasType(invoiceType);
        if(defaultInvoiceAliasType == null) {
            var invoiceAliasTypes = getInvoiceAliasTypesForUpdate(invoiceType);
            
            if(!invoiceAliasTypes.isEmpty()) {
                var iter = invoiceAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultInvoiceAliasType = iter.next();
                }
                var invoiceAliasTypeDetailValue = Objects.requireNonNull(defaultInvoiceAliasType).getLastDetailForUpdate().getInvoiceAliasTypeDetailValue().clone();
                
                invoiceAliasTypeDetailValue.setIsDefault(true);
                updateInvoiceAliasTypeFromValue(invoiceAliasTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(invoiceAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteInvoiceAliasTypes(List<InvoiceAliasType> invoiceAliasTypes, BasePK deletedBy) {
        invoiceAliasTypes.forEach((invoiceAliasType) -> 
                deleteInvoiceAliasType(invoiceAliasType, deletedBy)
        );
    }
    
    public void deleteInvoiceAliasTypesByInvoiceType(InvoiceType invoiceType, BasePK deletedBy) {
        deleteInvoiceAliasTypes(getInvoiceAliasTypesForUpdate(invoiceType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Time Types
    // --------------------------------------------------------------------------------

    public InvoiceTimeType createInvoiceTimeType(InvoiceType invoiceType, String invoiceTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInvoiceTimeType = getDefaultInvoiceTimeType(invoiceType);
        var defaultFound = defaultInvoiceTimeType != null;

        if(defaultFound && isDefault) {
            var defaultInvoiceTimeTypeDetailValue = getDefaultInvoiceTimeTypeDetailValueForUpdate(invoiceType);

            defaultInvoiceTimeTypeDetailValue.setIsDefault(false);
            updateInvoiceTimeTypeFromValue(defaultInvoiceTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var invoiceTimeType = InvoiceTimeTypeFactory.getInstance().create();
        var invoiceTimeTypeDetail = InvoiceTimeTypeDetailFactory.getInstance().create(invoiceTimeType, invoiceType, invoiceTimeTypeName, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        invoiceTimeType = InvoiceTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                invoiceTimeType.getPrimaryKey());
        invoiceTimeType.setActiveDetail(invoiceTimeTypeDetail);
        invoiceTimeType.setLastDetail(invoiceTimeTypeDetail);
        invoiceTimeType.store();

        sendEvent(invoiceTimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

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
        return invoiceTimeTypeTransferCache.getInvoiceTimeTypeTransfer(userVisit, invoiceTimeType);
    }

    public List<InvoiceTimeTypeTransfer> getInvoiceTimeTypeTransfers(UserVisit userVisit, InvoiceType invoiceType) {
        var invoiceTimeTypes = getInvoiceTimeTypes(invoiceType);
        List<InvoiceTimeTypeTransfer> invoiceTimeTypeTransfers = new ArrayList<>(invoiceTimeTypes.size());

        invoiceTimeTypes.forEach((invoiceTimeType) ->
                invoiceTimeTypeTransfers.add(invoiceTimeTypeTransferCache.getInvoiceTimeTypeTransfer(userVisit, invoiceTimeType))
        );

        return invoiceTimeTypeTransfers;
    }

    public InvoiceTimeTypeChoicesBean getInvoiceTimeTypeChoices(String defaultInvoiceTimeTypeChoice, Language language, boolean allowNullChoice,
            InvoiceType invoiceType) {
        var invoiceTimeTypes = getInvoiceTimeTypes(invoiceType);
        var size = invoiceTimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInvoiceTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var invoiceTimeType : invoiceTimeTypes) {
            var invoiceTimeTypeDetail = invoiceTimeType.getLastDetail();

            var label = getBestInvoiceTimeTypeDescription(invoiceTimeType, language);
            var value = invoiceTimeTypeDetail.getInvoiceTimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultInvoiceTimeTypeChoice != null && defaultInvoiceTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && invoiceTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InvoiceTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateInvoiceTimeTypeFromValue(InvoiceTimeTypeDetailValue invoiceTimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(invoiceTimeTypeDetailValue.hasBeenModified()) {
            var invoiceTimeType = InvoiceTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceTimeTypeDetailValue.getInvoiceTimeTypePK());
            var invoiceTimeTypeDetail = invoiceTimeType.getActiveDetailForUpdate();

            invoiceTimeTypeDetail.setThruTime(session.getStartTime());
            invoiceTimeTypeDetail.store();

            var invoiceType = invoiceTimeTypeDetail.getInvoiceType(); // Not updated
            var invoiceTypePK = invoiceType.getPrimaryKey(); // Not updated
            var invoiceTimeTypePK = invoiceTimeTypeDetail.getInvoiceTimeTypePK(); // Not updated
            var invoiceTimeTypeName = invoiceTimeTypeDetailValue.getInvoiceTimeTypeName();
            var isDefault = invoiceTimeTypeDetailValue.getIsDefault();
            var sortOrder = invoiceTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInvoiceTimeType = getDefaultInvoiceTimeType(invoiceType);
                var defaultFound = defaultInvoiceTimeType != null && !defaultInvoiceTimeType.equals(invoiceTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInvoiceTimeTypeDetailValue = getDefaultInvoiceTimeTypeDetailValueForUpdate(invoiceType);

                    defaultInvoiceTimeTypeDetailValue.setIsDefault(false);
                    updateInvoiceTimeTypeFromValue(defaultInvoiceTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            invoiceTimeTypeDetail = InvoiceTimeTypeDetailFactory.getInstance().create(invoiceTimeTypePK, invoiceTypePK, invoiceTimeTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            invoiceTimeType.setActiveDetail(invoiceTimeTypeDetail);
            invoiceTimeType.setLastDetail(invoiceTimeTypeDetail);

            sendEvent(invoiceTimeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInvoiceTimeTypeFromValue(InvoiceTimeTypeDetailValue invoiceTimeTypeDetailValue, BasePK updatedBy) {
        updateInvoiceTimeTypeFromValue(invoiceTimeTypeDetailValue, true, updatedBy);
    }

    public void deleteInvoiceTimeType(InvoiceTimeType invoiceTimeType, BasePK deletedBy) {
        deleteInvoiceTimesByInvoiceTimeType(invoiceTimeType, deletedBy);
        deleteInvoiceTimeTypeDescriptionsByInvoiceTimeType(invoiceTimeType, deletedBy);

        var invoiceTimeTypeDetail = invoiceTimeType.getLastDetailForUpdate();
        invoiceTimeTypeDetail.setThruTime(session.getStartTime());
        invoiceTimeType.setActiveDetail(null);
        invoiceTimeType.store();

        // Check for default, and pick one if necessary
        var invoiceType = invoiceTimeTypeDetail.getInvoiceType();
        var defaultInvoiceTimeType = getDefaultInvoiceTimeType(invoiceType);
        if(defaultInvoiceTimeType == null) {
            var invoiceTimeTypes = getInvoiceTimeTypesForUpdate(invoiceType);

            if(!invoiceTimeTypes.isEmpty()) {
                var iter = invoiceTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultInvoiceTimeType = iter.next();
                }
                var invoiceTimeTypeDetailValue = Objects.requireNonNull(defaultInvoiceTimeType).getLastDetailForUpdate().getInvoiceTimeTypeDetailValue().clone();

                invoiceTimeTypeDetailValue.setIsDefault(true);
                updateInvoiceTimeTypeFromValue(invoiceTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(invoiceTimeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Invoice Time Type Descriptions
    // --------------------------------------------------------------------------------

    public InvoiceTimeTypeDescription createInvoiceTimeTypeDescription(InvoiceTimeType invoiceTimeType, Language language, String description, BasePK createdBy) {
        var invoiceTimeTypeDescription = InvoiceTimeTypeDescriptionFactory.getInstance().create(invoiceTimeType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(invoiceTimeType.getPrimaryKey(), EventTypes.MODIFY, invoiceTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

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
        var invoiceTimeTypeDescription = getInvoiceTimeTypeDescription(invoiceTimeType, language);

        if(invoiceTimeTypeDescription == null && !language.getIsDefault()) {
            invoiceTimeTypeDescription = getInvoiceTimeTypeDescription(invoiceTimeType, partyControl.getDefaultLanguage());
        }

        if(invoiceTimeTypeDescription == null) {
            description = invoiceTimeType.getLastDetail().getInvoiceTimeTypeName();
        } else {
            description = invoiceTimeTypeDescription.getDescription();
        }

        return description;
    }

    public InvoiceTimeTypeDescriptionTransfer getInvoiceTimeTypeDescriptionTransfer(UserVisit userVisit, InvoiceTimeTypeDescription invoiceTimeTypeDescription) {
        return invoiceTimeTypeDescriptionTransferCache.getInvoiceTimeTypeDescriptionTransfer(userVisit, invoiceTimeTypeDescription);
    }

    public List<InvoiceTimeTypeDescriptionTransfer> getInvoiceTimeTypeDescriptionTransfersByInvoiceTimeType(UserVisit userVisit, InvoiceTimeType invoiceTimeType) {
        var invoiceTimeTypeDescriptions = getInvoiceTimeTypeDescriptionsByInvoiceTimeType(invoiceTimeType);
        List<InvoiceTimeTypeDescriptionTransfer> invoiceTimeTypeDescriptionTransfers = new ArrayList<>(invoiceTimeTypeDescriptions.size());

        invoiceTimeTypeDescriptions.forEach((invoiceTimeTypeDescription) ->
                invoiceTimeTypeDescriptionTransfers.add(invoiceTimeTypeDescriptionTransferCache.getInvoiceTimeTypeDescriptionTransfer(userVisit, invoiceTimeTypeDescription))
        );

        return invoiceTimeTypeDescriptionTransfers;
    }

    public void updateInvoiceTimeTypeDescriptionFromValue(InvoiceTimeTypeDescriptionValue invoiceTimeTypeDescriptionValue, BasePK updatedBy) {
        if(invoiceTimeTypeDescriptionValue.hasBeenModified()) {
            var invoiceTimeTypeDescription = InvoiceTimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    invoiceTimeTypeDescriptionValue.getPrimaryKey());

            invoiceTimeTypeDescription.setThruTime(session.getStartTime());
            invoiceTimeTypeDescription.store();

            var invoiceTimeType = invoiceTimeTypeDescription.getInvoiceTimeType();
            var language = invoiceTimeTypeDescription.getLanguage();
            var description = invoiceTimeTypeDescriptionValue.getDescription();

            invoiceTimeTypeDescription = InvoiceTimeTypeDescriptionFactory.getInstance().create(invoiceTimeType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(invoiceTimeType.getPrimaryKey(), EventTypes.MODIFY, invoiceTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInvoiceTimeTypeDescription(InvoiceTimeTypeDescription invoiceTimeTypeDescription, BasePK deletedBy) {
        invoiceTimeTypeDescription.setThruTime(session.getStartTime());

        sendEvent(invoiceTimeTypeDescription.getInvoiceTimeTypePK(), EventTypes.MODIFY, invoiceTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInvoiceTimeTypeDescriptionsByInvoiceTimeType(InvoiceTimeType invoiceTimeType, BasePK deletedBy) {
        var invoiceTimeTypeDescriptions = getInvoiceTimeTypeDescriptionsByInvoiceTimeTypeForUpdate(invoiceTimeType);

        invoiceTimeTypeDescriptions.forEach((invoiceTimeTypeDescription) -> 
                deleteInvoiceTimeTypeDescription(invoiceTimeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Invoice Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    public InvoiceAliasTypeDescription createInvoiceAliasTypeDescription(InvoiceAliasType invoiceAliasType, Language language, String description, BasePK createdBy) {
        var invoiceAliasTypeDescription = InvoiceAliasTypeDescriptionFactory.getInstance().create(invoiceAliasType, language,
                description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(invoiceAliasType.getPrimaryKey(), EventTypes.MODIFY, invoiceAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
        var invoiceAliasTypeDescription = getInvoiceAliasTypeDescription(invoiceAliasType, language);
        
        if(invoiceAliasTypeDescription == null && !language.getIsDefault()) {
            invoiceAliasTypeDescription = getInvoiceAliasTypeDescription(invoiceAliasType, partyControl.getDefaultLanguage());
        }
        
        if(invoiceAliasTypeDescription == null) {
            description = invoiceAliasType.getLastDetail().getInvoiceAliasTypeName();
        } else {
            description = invoiceAliasTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public InvoiceAliasTypeDescriptionTransfer getInvoiceAliasTypeDescriptionTransfer(UserVisit userVisit, InvoiceAliasTypeDescription invoiceAliasTypeDescription) {
        return invoiceAliasTypeDescriptionTransferCache.getInvoiceAliasTypeDescriptionTransfer(userVisit, invoiceAliasTypeDescription);
    }
    
    public List<InvoiceAliasTypeDescriptionTransfer> getInvoiceAliasTypeDescriptionTransfersByInvoiceAliasType(UserVisit userVisit, InvoiceAliasType invoiceAliasType) {
        var invoiceAliasTypeDescriptions = getInvoiceAliasTypeDescriptionsByInvoiceAliasType(invoiceAliasType);
        List<InvoiceAliasTypeDescriptionTransfer> invoiceAliasTypeDescriptionTransfers = new ArrayList<>(invoiceAliasTypeDescriptions.size());
        
        invoiceAliasTypeDescriptions.forEach((invoiceAliasTypeDescription) ->
                invoiceAliasTypeDescriptionTransfers.add(invoiceAliasTypeDescriptionTransferCache.getInvoiceAliasTypeDescriptionTransfer(userVisit, invoiceAliasTypeDescription))
        );
        
        return invoiceAliasTypeDescriptionTransfers;
    }
    
    public void updateInvoiceAliasTypeDescriptionFromValue(InvoiceAliasTypeDescriptionValue invoiceAliasTypeDescriptionValue, BasePK updatedBy) {
        if(invoiceAliasTypeDescriptionValue.hasBeenModified()) {
            var invoiceAliasTypeDescription = InvoiceAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceAliasTypeDescriptionValue.getPrimaryKey());
            
            invoiceAliasTypeDescription.setThruTime(session.getStartTime());
            invoiceAliasTypeDescription.store();

            var invoiceAliasType = invoiceAliasTypeDescription.getInvoiceAliasType();
            var language = invoiceAliasTypeDescription.getLanguage();
            var description = invoiceAliasTypeDescriptionValue.getDescription();
            
            invoiceAliasTypeDescription = InvoiceAliasTypeDescriptionFactory.getInstance().create(invoiceAliasType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(invoiceAliasType.getPrimaryKey(), EventTypes.MODIFY, invoiceAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteInvoiceAliasTypeDescription(InvoiceAliasTypeDescription invoiceAliasTypeDescription, BasePK deletedBy) {
        invoiceAliasTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(invoiceAliasTypeDescription.getInvoiceAliasTypePK(), EventTypes.MODIFY, invoiceAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteInvoiceAliasTypeDescriptionsByInvoiceAliasType(InvoiceAliasType invoiceAliasType, BasePK deletedBy) {
        var invoiceAliasTypeDescriptions = getInvoiceAliasTypeDescriptionsByInvoiceAliasTypeForUpdate(invoiceAliasType);
        
        invoiceAliasTypeDescriptions.forEach((invoiceAliasTypeDescription) -> 
                deleteInvoiceAliasTypeDescription(invoiceAliasTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Types
    // --------------------------------------------------------------------------------
    
    public InvoiceLineType createInvoiceLineType(InvoiceType invoiceType, String invoiceLineTypeName,
            InvoiceLineType parentInvoiceLineType, GlAccount defaultGlAccount, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInvoiceLineType = getDefaultInvoiceLineType(invoiceType);
        var defaultFound = defaultInvoiceLineType != null;
        
        if(defaultFound && isDefault) {
            var defaultInvoiceLineTypeDetailValue = getDefaultInvoiceLineTypeDetailValueForUpdate(invoiceType);
            
            defaultInvoiceLineTypeDetailValue.setIsDefault(false);
            updateInvoiceLineTypeFromValue(defaultInvoiceLineTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var invoiceLineType = InvoiceLineTypeFactory.getInstance().create();
        var invoiceLineTypeDetail = InvoiceLineTypeDetailFactory.getInstance().create(invoiceLineType,
                invoiceType, invoiceLineTypeName, parentInvoiceLineType, defaultGlAccount, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        invoiceLineType = InvoiceLineTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                invoiceLineType.getPrimaryKey());
        invoiceLineType.setActiveDetail(invoiceLineTypeDetail);
        invoiceLineType.setLastDetail(invoiceLineTypeDetail);
        invoiceLineType.store();
        
        sendEvent(invoiceLineType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return invoiceLineType;
    }
    
    private InvoiceLineType getInvoiceLineTypeByName(InvoiceType invoiceType, String invoiceLineTypeName,
            EntityPermission entityPermission) {
        InvoiceLineType invoiceLineType;
        
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

            var ps = InvoiceLineTypeFactory.getInstance().prepareStatement(query);
            
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
        InvoiceLineType invoiceLineType;
        
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

            var ps = InvoiceLineTypeFactory.getInstance().prepareStatement(query);
            
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
        List<InvoiceLineType> invoiceLineTypes;
        
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

            var ps = InvoiceLineTypeFactory.getInstance().prepareStatement(query);
            
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
        return invoiceLineTypeTransferCache.getInvoiceLineTypeTransfer(userVisit, invoiceLineType);
    }
    
    public List<InvoiceLineTypeTransfer> getInvoiceLineTypeTransfers(UserVisit userVisit, Collection<InvoiceLineType> invoiceLineTypes) {
        List<InvoiceLineTypeTransfer> invoiceLineTypeTransfers = new ArrayList<>(invoiceLineTypes.size());
        
        invoiceLineTypes.forEach((invoiceLineType) ->
                invoiceLineTypeTransfers.add(invoiceLineTypeTransferCache.getInvoiceLineTypeTransfer(userVisit, invoiceLineType))
        );
        
        return invoiceLineTypeTransfers;
    }
    
    public List<InvoiceLineTypeTransfer> getInvoiceLineTypeTransfersByInvoiceType(UserVisit userVisit, InvoiceType invoiceType) {
        return getInvoiceLineTypeTransfers(userVisit, getInvoiceLineTypes(invoiceType));
    }
    
    public InvoiceLineTypeChoicesBean getInvoiceLineTypeChoices(InvoiceType invoiceType, String defaultInvoiceLineTypeChoice,
            Language language, boolean allowNullChoice) {
        var invoiceLineTypes = getInvoiceLineTypes(invoiceType);
        var size = invoiceLineTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInvoiceLineTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var invoiceLineType : invoiceLineTypes) {
            var invoiceLineTypeDetail = invoiceLineType.getLastDetail();
            
            var label = getBestInvoiceLineTypeDescription(invoiceLineType, language);
            var value = invoiceLineTypeDetail.getInvoiceLineTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultInvoiceLineTypeChoice != null && defaultInvoiceLineTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && invoiceLineTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new InvoiceLineTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentInvoiceLineTypeSafe(InvoiceLineType invoiceLineType, InvoiceLineType parentInvoiceLineType) {
        var safe = true;
        
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
            var invoiceLineType = InvoiceLineTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceLineTypeDetailValue.getInvoiceLineTypePK());
            var invoiceLineTypeDetail = invoiceLineType.getActiveDetailForUpdate();
            
            invoiceLineTypeDetail.setThruTime(session.getStartTime());
            invoiceLineTypeDetail.store();

            var invoiceLineTypePK = invoiceLineTypeDetail.getInvoiceLineTypePK(); // Not updated
            var invoiceType = invoiceLineTypeDetail.getInvoiceType(); // Not updated
            var invoiceTypePK = invoiceType.getPrimaryKey(); // Not updated
            var invoiceLineTypeName = invoiceLineTypeDetailValue.getInvoiceLineTypeName();
            var parentInvoiceLineTypePK = invoiceLineTypeDetailValue.getParentInvoiceLineTypePK();
            var defaultGlAccountPK = invoiceLineTypeDetailValue.getDefaultGlAccountPK();
            var isDefault = invoiceLineTypeDetailValue.getIsDefault();
            var sortOrder = invoiceLineTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultInvoiceLineType = getDefaultInvoiceLineType(invoiceType);
                var defaultFound = defaultInvoiceLineType != null && !defaultInvoiceLineType.equals(invoiceLineType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInvoiceLineTypeDetailValue = getDefaultInvoiceLineTypeDetailValueForUpdate(invoiceType);
                    
                    defaultInvoiceLineTypeDetailValue.setIsDefault(false);
                    updateInvoiceLineTypeFromValue(defaultInvoiceLineTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            invoiceLineTypeDetail = InvoiceLineTypeDetailFactory.getInstance().create(invoiceLineTypePK, invoiceTypePK,
                    invoiceLineTypeName, parentInvoiceLineTypePK, defaultGlAccountPK, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);
            
            invoiceLineType.setActiveDetail(invoiceLineTypeDetail);
            invoiceLineType.setLastDetail(invoiceLineTypeDetail);
            
            sendEvent(invoiceLineTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateInvoiceLineTypeFromValue(InvoiceLineTypeDetailValue invoiceLineTypeDetailValue, BasePK updatedBy) {
        updateInvoiceLineTypeFromValue(invoiceLineTypeDetailValue, true, updatedBy);
    }
    
    private void deleteInvoiceLineType(InvoiceLineType invoiceLineType, boolean checkDefault, BasePK deletedBy) {
        var invoiceLineTypeDetail = invoiceLineType.getLastDetailForUpdate();

        deleteInvoiceLineTypesByParentInvoiceLineType(invoiceLineType, deletedBy);
        deleteInvoiceLineTypeDescriptionsByInvoiceLineType(invoiceLineType, deletedBy);
        
        invoiceLineTypeDetail.setThruTime(session.getStartTime());
        invoiceLineType.setActiveDetail(null);
        invoiceLineType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var invoiceType = invoiceLineTypeDetail.getInvoiceType();
            var defaultInvoiceLineType = getDefaultInvoiceLineType(invoiceType);

            if(defaultInvoiceLineType == null) {
                var invoiceLineTypes = getInvoiceLineTypesForUpdate(invoiceType);

                if(!invoiceLineTypes.isEmpty()) {
                    var iter = invoiceLineTypes.iterator();
                    if(iter.hasNext()) {
                        defaultInvoiceLineType = iter.next();
                    }
                    var invoiceLineTypeDetailValue = Objects.requireNonNull(defaultInvoiceLineType).getLastDetailForUpdate().getInvoiceLineTypeDetailValue().clone();

                    invoiceLineTypeDetailValue.setIsDefault(true);
                    updateInvoiceLineTypeFromValue(invoiceLineTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(invoiceLineType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteInvoiceLineType(InvoiceLineType invoiceLineType, BasePK deletedBy) {
        deleteInvoiceLineType(invoiceLineType, true, deletedBy);
    }

    private void deleteInvoiceLineTypes(List<InvoiceLineType> invoiceLineTypes, boolean checkDefault, BasePK deletedBy) {
        invoiceLineTypes.forEach((invoiceLineType) -> deleteInvoiceLineType(invoiceLineType, checkDefault, deletedBy));
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
        var invoiceLineTypeDescription = InvoiceLineTypeDescriptionFactory.getInstance().create(session,
                invoiceLineType, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(invoiceLineType.getPrimaryKey(), EventTypes.MODIFY, invoiceLineTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return invoiceLineTypeDescription;
    }
    
    private InvoiceLineTypeDescription getInvoiceLineTypeDescription(InvoiceLineType invoiceLineType, Language language,
            EntityPermission entityPermission) {
        InvoiceLineTypeDescription invoiceLineTypeDescription;
        
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

            var ps = InvoiceLineTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<InvoiceLineTypeDescription> invoiceLineTypeDescriptions;
        
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

            var ps = InvoiceLineTypeDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var invoiceLineTypeDescription = getInvoiceLineTypeDescription(invoiceLineType, language);
        
        if(invoiceLineTypeDescription == null && !language.getIsDefault()) {
            invoiceLineTypeDescription = getInvoiceLineTypeDescription(invoiceLineType, partyControl.getDefaultLanguage());
        }
        
        if(invoiceLineTypeDescription == null) {
            description = invoiceLineType.getLastDetail().getInvoiceLineTypeName();
        } else {
            description = invoiceLineTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public InvoiceLineTypeDescriptionTransfer getInvoiceLineTypeDescriptionTransfer(UserVisit userVisit, InvoiceLineTypeDescription invoiceLineTypeDescription) {
        return invoiceLineTypeDescriptionTransferCache.getInvoiceLineTypeDescriptionTransfer(userVisit, invoiceLineTypeDescription);
    }
    
    public List<InvoiceLineTypeDescriptionTransfer> getInvoiceLineTypeDescriptionTransfersByInvoiceLineType(UserVisit userVisit, InvoiceLineType invoiceLineType) {
        var invoiceLineTypeDescriptions = getInvoiceLineTypeDescriptionsByInvoiceLineType(invoiceLineType);
        List<InvoiceLineTypeDescriptionTransfer> invoiceLineTypeDescriptionTransfers = new ArrayList<>(invoiceLineTypeDescriptions.size());
        
        invoiceLineTypeDescriptions.forEach((invoiceLineTypeDescription) ->
                invoiceLineTypeDescriptionTransfers.add(invoiceLineTypeDescriptionTransferCache.getInvoiceLineTypeDescriptionTransfer(userVisit, invoiceLineTypeDescription))
        );
        
        return invoiceLineTypeDescriptionTransfers;
    }
    
    public void updateInvoiceLineTypeDescriptionFromValue(InvoiceLineTypeDescriptionValue invoiceLineTypeDescriptionValue, BasePK updatedBy) {
        if(invoiceLineTypeDescriptionValue.hasBeenModified()) {
            var invoiceLineTypeDescription = InvoiceLineTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceLineTypeDescriptionValue.getPrimaryKey());
            
            invoiceLineTypeDescription.setThruTime(session.getStartTime());
            invoiceLineTypeDescription.store();

            var invoiceLineType = invoiceLineTypeDescription.getInvoiceLineType();
            var language = invoiceLineTypeDescription.getLanguage();
            var description = invoiceLineTypeDescriptionValue.getDescription();
            
            invoiceLineTypeDescription = InvoiceLineTypeDescriptionFactory.getInstance().create(invoiceLineType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(invoiceLineType.getPrimaryKey(), EventTypes.MODIFY, invoiceLineTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteInvoiceLineTypeDescription(InvoiceLineTypeDescription invoiceLineTypeDescription, BasePK deletedBy) {
        invoiceLineTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(invoiceLineTypeDescription.getInvoiceLineTypePK(), EventTypes.MODIFY, invoiceLineTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteInvoiceLineTypeDescriptionsByInvoiceLineType(InvoiceLineType invoiceLineType, BasePK deletedBy) {
        var invoiceLineTypeDescriptions = getInvoiceLineTypeDescriptionsByInvoiceLineTypeForUpdate(invoiceLineType);
        
        invoiceLineTypeDescriptions.forEach((invoiceLineTypeDescription) -> 
                deleteInvoiceLineTypeDescription(invoiceLineTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Roles
    // --------------------------------------------------------------------------------
    
    public InvoiceRole createInvoiceRoleUsingNames(Invoice invoice, Party party, PartyContactMechanism partyContactMechanism, String invoiceRoleTypeName, BasePK createdBy) {
        var invoiceRoleType = getInvoiceRoleTypeByName(invoiceRoleTypeName);
        
        return createInvoiceRole(invoice, party, partyContactMechanism, invoiceRoleType, createdBy);
    }
    
    public InvoiceRole createInvoiceRole(Invoice invoice, Party party, PartyContactMechanism partyContactMechanism, InvoiceRoleType invoiceRoleType, BasePK createdBy) {
        var invoiceRole = InvoiceRoleFactory.getInstance().create(invoice, party, partyContactMechanism, invoiceRoleType, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(invoice.getPrimaryKey(), EventTypes.MODIFY, invoiceRole.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return invoiceRole;
    }
    
    private InvoiceRole getInvoiceRole(Invoice invoice, InvoiceRoleType invoiceRoleType, EntityPermission entityPermission) {
        InvoiceRole invoiceRole;
        
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

            var ps = InvoiceRoleFactory.getInstance().prepareStatement(query);
            
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
        var invoiceRoleType = getInvoiceRoleTypeByName(invoiceRoleTypeName);
        
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
        List<InvoiceRole> invoiceRoles;
        
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

            var ps = InvoiceRoleFactory.getInstance().prepareStatement(query);
            
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
        List<InvoiceRole> invoiceRoles;
        
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

            var ps = InvoiceRoleFactory.getInstance().prepareStatement(query);
            
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
    
    public List<InvoiceTransfer> getInvoiceTransfers(UserVisit userVisit, Collection<Invoice> invoices) {
        List<InvoiceTransfer> invoiceTransfers = new ArrayList<>(invoices.size());
        
        invoices.forEach((invoice) ->
                invoiceTransfers.add(invoiceTransferCache.getInvoiceTransfer(userVisit, invoice))
        );
        
        return invoiceTransfers;
    }
    
    public List<InvoiceTransfer> getInvoiceTransfersByInvoiceFrom(UserVisit userVisit, Party invoiceFrom) {
        return getInvoiceTransfers(userVisit, getInvoicesByInvoiceFrom(invoiceFrom));
    }
    
    public void updateInvoiceRoleFromValue(InvoiceRoleValue invoiceRoleValue, BasePK updatedBy) {
        if(invoiceRoleValue.hasBeenModified()) {
            var invoiceRole = InvoiceRoleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     invoiceRoleValue.getPrimaryKey());
            
            invoiceRole.setThruTime(session.getStartTime());
            invoiceRole.store();

            var invoicePK = invoiceRole.getInvoicePK(); // Not updated
            var partyPK = invoiceRole.getPartyPK(); // Not updated
            var partyContactMechanismPK = invoiceRoleValue.getPartyContactMechanismPK();
            var invoiceRoleTypePK = invoiceRole.getInvoiceRoleTypePK(); // Not updated
            
            invoiceRole = InvoiceRoleFactory.getInstance().create(invoicePK, partyPK, partyContactMechanismPK, invoiceRoleTypePK,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(invoicePK, EventTypes.MODIFY, invoiceRole.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteInvoiceRole(InvoiceRole invoiceRole, BasePK deletedBy) {
        invoiceRole.setThruTime(session.getStartTime());
        
        sendEvent(invoiceRole.getInvoicePK(), EventTypes.MODIFY, invoiceRole.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteInvoiceRolesByInvoice(Invoice invoice, BasePK deletedBy) {
        getInvoiceRolesByInvoiceForUpdate(invoice).forEach((invoiceRole) -> {
            deleteInvoiceRole(invoiceRole, deletedBy);
        });
    }
    
    public void deleteInvoiceRolesByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        getInvoiceRolesByPartyContactMechanismForUpdate(partyContactMechanism).forEach((invoiceRole) -> {
            deleteInvoiceRole(invoiceRole, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Invoices
    // --------------------------------------------------------------------------------
    
    public Invoice createInvoice(InvoiceType invoiceType, String invoiceName, BillingAccount billingAccount, GlAccount glAccount,
            Term term, FreeOnBoard freeOnBoard, String reference, String description, BasePK createdBy) {
        var invoice = InvoiceFactory.getInstance().create();
        var invoiceDetail = InvoiceDetailFactory.getInstance().create(invoice, invoiceType, invoiceName, billingAccount,
                glAccount, term, freeOnBoard, reference, description, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        invoice = InvoiceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoice.getPrimaryKey());
        invoice.setActiveDetail(invoiceDetail);
        invoice.setLastDetail(invoiceDetail);
        invoice.store();
        
        sendEvent(invoice.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
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
                invoiceFrom, InvoiceRoleTypes.INVOICE_FROM.name(), Session.MAX_TIME);
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
                invoiceFrom, InvoiceRoleTypes.INVOICE_FROM.name(), Session.MAX_TIME);
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
                invoiceTo, InvoiceRoleTypes.INVOICE_TO.name(), Session.MAX_TIME);
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
                invoiceTo, InvoiceRoleTypes.INVOICE_TO.name(), Session.MAX_TIME);
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
        var invoiceType = getInvoiceTypeByName(invoiceTypeName);
        
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
                reference, Session.MAX_TIME, invoiceFrom, InvoiceRoleTypes.INVOICE_FROM.name());
    }
    
    public InvoiceTransfer getInvoiceTransfer(UserVisit userVisit, Invoice invoice) {
        return invoiceTransferCache.getInvoiceTransfer(userVisit, invoice);
    }
    
    public List<InvoiceRoleTransfer> getInvoiceRoleTransfers(UserVisit userVisit, Collection<InvoiceRole> invoiceRoles) {
        List<InvoiceRoleTransfer> invoiceRoleTransfers = new ArrayList<>(invoiceRoles.size());
        
        invoiceRoles.forEach((invoiceRole) ->
                invoiceRoleTransfers.add(invoiceRoleTransferCache.getInvoiceRoleTransfer(userVisit, invoiceRole))
        );
        
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
        InvoiceStatus invoiceStatus;
        
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

            var ps = InvoiceStatusFactory.getInstance().prepareStatement(query);
            
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
        var invoiceStatus = getInvoiceStatusForUpdate(invoice);
        
        if(invoiceStatus != null) {
            invoiceStatus.remove();
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Times
    // --------------------------------------------------------------------------------

    public InvoiceTime createInvoiceTime(Invoice invoice, InvoiceTimeType invoiceTimeType, Long time, BasePK createdBy) {
        var invoiceTime = InvoiceTimeFactory.getInstance().create(invoice, invoiceTimeType, time, session.getStartTime(), Session.MAX_TIME);

        sendEvent(invoice.getPrimaryKey(), EventTypes.MODIFY, invoiceTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return invoiceTime;
    }

    public long countInvoiceTimesByInvoice(Invoice invoice) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM invoicetimes " +
                "WHERE invctim_invc_invoiceid = ? AND invctim_thrutime = ?",
                invoice, Session.MAX_TIME);
    }

    public long countInvoiceTimesByInvoiceTimeType(InvoiceTimeType invoiceTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM invoicetimes " +
                "WHERE invctim_invctimtyp_invoicetimetypeid = ? AND invctim_thrutime = ?",
                invoiceTimeType, Session.MAX_TIME);
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
        return invoiceTimeTransferCache.getInvoiceTimeTransfer(userVisit, invoiceTime);
    }

    public List<InvoiceTimeTransfer> getInvoiceTimeTransfers(UserVisit userVisit, Collection<InvoiceTime> invoiceTimes) {
        List<InvoiceTimeTransfer> invoiceTimeTransfers = new ArrayList<>(invoiceTimes.size());

        invoiceTimes.forEach((invoiceTime) ->
                invoiceTimeTransfers.add(invoiceTimeTransferCache.getInvoiceTimeTransfer(userVisit, invoiceTime))
        );

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
            var invoiceTime = InvoiceTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    invoiceTimeValue.getPrimaryKey());

            invoiceTime.setThruTime(session.getStartTime());
            invoiceTime.store();

            var invoicePK = invoiceTime.getInvoicePK(); // Not updated
            var invoiceTimeTypePK = invoiceTime.getInvoiceTimeTypePK(); // Not updated
            var time = invoiceTimeValue.getTime();

            invoiceTime = InvoiceTimeFactory.getInstance().create(invoicePK, invoiceTimeTypePK, time, session.getStartTime(), Session.MAX_TIME);

            sendEvent(invoicePK, EventTypes.MODIFY, invoiceTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInvoiceTime(InvoiceTime invoiceTime, BasePK deletedBy) {
        invoiceTime.setThruTime(session.getStartTime());

        sendEvent(invoiceTime.getInvoiceTimeTypePK(), EventTypes.MODIFY, invoiceTime.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInvoiceTimes(List<InvoiceTime> invoiceTimes, BasePK deletedBy) {
        invoiceTimes.forEach((invoiceTime) -> 
                deleteInvoiceTime(invoiceTime, deletedBy)
        );
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
        var invoiceAlias = InvoiceAliasFactory.getInstance().create(invoice, invoiceAliasType, alias, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(invoice.getPrimaryKey(), EventTypes.MODIFY, invoiceAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
        return invoiceAliasTransferCache.getInvoiceAliasTransfer(userVisit, invoiceAlias);
    }
    
    public List<InvoiceAliasTransfer> getInvoiceAliasTransfersByInvoice(UserVisit userVisit, Invoice invoice) {
        var invoicealiases = getInvoiceAliasesByInvoice(invoice);
        List<InvoiceAliasTransfer> invoiceAliasTransfers = new ArrayList<>(invoicealiases.size());
        
        invoicealiases.forEach((invoiceAlias) ->
                invoiceAliasTransfers.add(invoiceAliasTransferCache.getInvoiceAliasTransfer(userVisit, invoiceAlias))
        );
        
        return invoiceAliasTransfers;
    }
    
    public void updateInvoiceAliasFromValue(InvoiceAliasValue invoiceAliasValue, BasePK updatedBy) {
        if(invoiceAliasValue.hasBeenModified()) {
            var invoiceAlias = InvoiceAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceAliasValue.getPrimaryKey());
            
            invoiceAlias.setThruTime(session.getStartTime());
            invoiceAlias.store();

            var invoicePK = invoiceAlias.getInvoicePK();
            var invoiceAliasTypePK = invoiceAlias.getInvoiceAliasTypePK();
            var alias  = invoiceAliasValue.getAlias();
            
            invoiceAlias = InvoiceAliasFactory.getInstance().create(invoicePK, invoiceAliasTypePK, alias, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(invoicePK, EventTypes.MODIFY, invoiceAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteInvoiceAlias(InvoiceAlias invoiceAlias, BasePK deletedBy) {
        invoiceAlias.setThruTime(session.getStartTime());
        
        sendEvent(invoiceAlias.getInvoicePK(), EventTypes.MODIFY, invoiceAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteInvoiceAliasesByInvoiceAliasType(InvoiceAliasType invoiceAliasType, BasePK deletedBy) {
        var invoicealiases = getInvoiceAliasesByInvoiceAliasTypeForUpdate(invoiceAliasType);
        
        invoicealiases.forEach((invoiceAlias) -> 
                deleteInvoiceAlias(invoiceAlias, deletedBy)
        );
    }
    
    public void deleteInvoiceAliasesByInvoice(Invoice invoice, BasePK deletedBy) {
        var invoicealiases = getInvoiceAliasesByInvoiceForUpdate(invoice);
        
        invoicealiases.forEach((invoiceAlias) -> 
                deleteInvoiceAlias(invoiceAlias, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Lines
    // --------------------------------------------------------------------------------
    
    public InvoiceLine createInvoiceLine(Invoice invoice, Integer invoiceLineSequence, InvoiceLine parentInvoiceLine, InvoiceLineType invoiceLineType,
            InvoiceLineUseType invoiceLineUseType, Long amount, String description, BasePK createdBy) {
        if(invoiceLineSequence == null) {
            var invoiceStatus = getInvoiceStatusForUpdate(invoice);
            
            do {
                invoiceLineSequence = invoiceStatus.getInvoiceLineSequence() + 1;
                invoiceStatus.setInvoiceLineSequence(invoiceLineSequence);
            } while(invoiceLineExists(invoice, invoiceLineSequence));
        }

        var invoiceLine = InvoiceLineFactory.getInstance().create();
        var invoiceLineDetail = InvoiceLineDetailFactory.getInstance().create(invoiceLine, invoice, invoiceLineSequence, parentInvoiceLine,
                invoiceLineType, invoiceLineUseType, amount, description, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        invoiceLine = InvoiceLineFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceLine.getPrimaryKey());
        invoiceLine.setActiveDetail(invoiceLineDetail);
        invoiceLine.setLastDetail(invoiceLineDetail);
        invoiceLine.store();
        
        sendEvent(invoiceLine.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return invoiceLine;
    }
    
    public boolean invoiceLineExists(Invoice invoice, Integer invoiceLineSequence) {
        return session.queryForLong(
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
        return invoiceLineTransferCache.getInvoiceLineTransfer(userVisit, invoiceLine);
    }
    
    public List<InvoiceLineTransfer> getInvoiceLineTransfers(final UserVisit userVisit, final List<InvoiceLine> invoiceLines) {
        List<InvoiceLineTransfer> invoiceLineTransfers = new ArrayList<>(invoiceLines.size());
        
        invoiceLines.forEach((invoiceLine) ->
                invoiceLineTransfers.add(invoiceLineTransferCache.getInvoiceLineTransfer(userVisit, invoiceLine))
        );
        
        return invoiceLineTransfers;
    }
    
    public List<InvoiceLineTransfer> getInvoiceLineTransfersByInvoice(final UserVisit userVisit, final Invoice invoice) {
        return getInvoiceLineTransfers(userVisit, getInvoiceLines(invoice));
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Items
    // --------------------------------------------------------------------------------
    
    public InvoiceLineItem createInvoiceLineItem(InvoiceLine invoiceLine, Item item, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType, Integer quantity, BasePK createdBy) {
        var invoiceLineItem = InvoiceLineItemFactory.getInstance().create(invoiceLine, item, inventoryCondition, unitOfMeasureType, quantity, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(invoiceLine.getPrimaryKey(), EventTypes.MODIFY, invoiceLineItem.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
                invoiceLine, Session.MAX_TIME);
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
        return invoiceLineItemTransferCache.getInvoiceLineItemTransfer(userVisit, invoiceLineItem);
    }
    
    public void updateInvoiceLineItemFromValue(InvoiceLineItemValue invoiceLineItemValue, BasePK updatedBy) {
        if(invoiceLineItemValue.hasBeenModified()) {
            var invoiceLineItem = InvoiceLineItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceLineItemValue.getPrimaryKey());
            
            invoiceLineItem.setThruTime(session.getStartTime());
            invoiceLineItem.store();

            var invoiceLinePK = invoiceLineItem.getInvoiceLinePK(); // Not updated
            var itemPK = invoiceLineItem.getItemPK();
            var inventoryConditionPK = invoiceLineItem.getInventoryConditionPK();
            var unitOfMeasureTypePK = invoiceLineItem.getUnitOfMeasureTypePK();
            var quantity = invoiceLineItem.getQuantity();
            
            invoiceLineItem = InvoiceLineItemFactory.getInstance().create(invoiceLinePK, itemPK, inventoryConditionPK, unitOfMeasureTypePK, quantity, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(invoiceLinePK, EventTypes.MODIFY, invoiceLineItem.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteInvoiceLineItem(InvoiceLineItem invoiceLineItem, BasePK deletedBy) {
        invoiceLineItem.setThruTime(session.getStartTime());
        
        sendEvent(invoiceLineItem.getInvoiceLinePK(), EventTypes.MODIFY, invoiceLineItem.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteInvoiceLineItemsByInvoiceLine(InvoiceLine invoiceLine, BasePK deletedBy) {
        var invoiceLineItem = getInvoiceLineItemForUpdate(invoiceLine);
        
        if(invoiceLineItem != null) {
            deleteInvoiceLineItem(invoiceLineItem, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Gl Accounts
    // --------------------------------------------------------------------------------
    
    public InvoiceLineGlAccount createInvoiceLineGlAccount(InvoiceLine invoiceLine, GlAccount glAccount, BasePK createdBy) {
        var invoiceLineGlAccount = InvoiceLineGlAccountFactory.getInstance().create(invoiceLine, glAccount, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(invoiceLine.getPrimaryKey(), EventTypes.MODIFY, invoiceLineGlAccount.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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
                invoiceLine, Session.MAX_TIME);
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
        return invoiceLineGlAccountTransferCache.getInvoiceLineGlAccountTransfer(userVisit, invoiceLineGlAccount);
    }
    
    public void updateInvoiceLineGlAccountFromValue(InvoiceLineGlAccountValue invoiceLineGlAccountValue, BasePK updatedBy) {
        if(invoiceLineGlAccountValue.hasBeenModified()) {
            var invoiceLineGlAccount = InvoiceLineGlAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, invoiceLineGlAccountValue.getPrimaryKey());
            
            invoiceLineGlAccount.setThruTime(session.getStartTime());
            invoiceLineGlAccount.store();

            var invoiceLinePK = invoiceLineGlAccount.getInvoiceLinePK(); // Not updated
            var glAccountPK = invoiceLineGlAccountValue.getGlAccountPK();
            
            invoiceLineGlAccount = InvoiceLineGlAccountFactory.getInstance().create(invoiceLinePK, glAccountPK, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(invoiceLinePK, EventTypes.MODIFY, invoiceLineGlAccount.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteInvoiceLineGlAccount(InvoiceLineGlAccount invoiceLineGlAccount, BasePK deletedBy) {
        invoiceLineGlAccount.setThruTime(session.getStartTime());
        
        sendEvent(invoiceLineGlAccount.getInvoiceLinePK(), EventTypes.MODIFY, invoiceLineGlAccount.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteInvoiceLineGlAccountsByInvoiceLine(InvoiceLine invoiceLine, BasePK deletedBy) {
        var invoiceLineGlAccount = getInvoiceLineGlAccountForUpdate(invoiceLine);
        
        if(invoiceLineGlAccount != null) {
            deleteInvoiceLineGlAccount(invoiceLineGlAccount, deletedBy);
        }
    }
    
}

// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.term.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.term.common.TermTypes;
import com.echothree.model.control.term.common.choice.TermChoicesBean;
import com.echothree.model.control.term.common.choice.TermTypeChoicesBean;
import com.echothree.model.control.term.common.transfer.CustomerTypeCreditLimitTransfer;
import com.echothree.model.control.term.common.transfer.PartyCreditLimitTransfer;
import com.echothree.model.control.term.common.transfer.PartyTermTransfer;
import com.echothree.model.control.term.common.transfer.TermDescriptionTransfer;
import com.echothree.model.control.term.common.transfer.TermTransfer;
import com.echothree.model.control.term.common.transfer.TermTypeTransfer;
import com.echothree.model.control.term.server.transfer.CustomerTypeCreditLimitTransferCache;
import com.echothree.model.control.term.server.transfer.PartyCreditLimitTransferCache;
import com.echothree.model.control.term.server.transfer.TermTransferCache;
import com.echothree.model.control.term.server.transfer.TermTransferCaches;
import com.echothree.model.control.term.server.transfer.TermTypeTransferCache;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.customer.common.pk.CustomerTypePK;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.term.common.pk.TermPK;
import com.echothree.model.data.term.common.pk.TermTypePK;
import com.echothree.model.data.term.server.entity.CustomerTypeCreditLimit;
import com.echothree.model.data.term.server.entity.DateDrivenTerm;
import com.echothree.model.data.term.server.entity.PartyCreditLimit;
import com.echothree.model.data.term.server.entity.PartyTerm;
import com.echothree.model.data.term.server.entity.StandardTerm;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.term.server.entity.TermDescription;
import com.echothree.model.data.term.server.entity.TermDetail;
import com.echothree.model.data.term.server.entity.TermType;
import com.echothree.model.data.term.server.entity.TermTypeDescription;
import com.echothree.model.data.term.server.factory.CustomerTypeCreditLimitFactory;
import com.echothree.model.data.term.server.factory.DateDrivenTermFactory;
import com.echothree.model.data.term.server.factory.PartyCreditLimitFactory;
import com.echothree.model.data.term.server.factory.PartyTermFactory;
import com.echothree.model.data.term.server.factory.StandardTermFactory;
import com.echothree.model.data.term.server.factory.TermDescriptionFactory;
import com.echothree.model.data.term.server.factory.TermDetailFactory;
import com.echothree.model.data.term.server.factory.TermFactory;
import com.echothree.model.data.term.server.factory.TermTypeDescriptionFactory;
import com.echothree.model.data.term.server.factory.TermTypeFactory;
import com.echothree.model.data.term.server.value.CustomerTypeCreditLimitValue;
import com.echothree.model.data.term.server.value.DateDrivenTermValue;
import com.echothree.model.data.term.server.value.PartyCreditLimitValue;
import com.echothree.model.data.term.server.value.PartyTermValue;
import com.echothree.model.data.term.server.value.StandardTermValue;
import com.echothree.model.data.term.server.value.TermDescriptionValue;
import com.echothree.model.data.term.server.value.TermDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
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

public class TermControl
        extends BaseModelControl {
    
    /** Creates a new instance of TermControl */
    public TermControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Term Transfer Caches
    // --------------------------------------------------------------------------------
    
    private TermTransferCaches termTransferCaches;
    
    public TermTransferCaches getTermTransferCaches(UserVisit userVisit) {
        if(termTransferCaches == null) {
            termTransferCaches = new TermTransferCaches(userVisit, this);
        }
        
        return termTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Term Types
    // --------------------------------------------------------------------------------
    
    public TermType createTermType(String termTypeName, Boolean isDefault, Integer sortOrder) {
        return TermTypeFactory.getInstance().create(termTypeName, isDefault, sortOrder);
    }
    
    public TermType getTermTypeByName(String termTypeName) {
        TermType termType;
        
        try {
            PreparedStatement ps = TermTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM termtypes " +
                    "WHERE trmtyp_termtypename = ?");
            
            ps.setString(1, termTypeName);
            
            termType = TermTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return termType;
    }
    
    public List<TermType> getTermTypes() {
        PreparedStatement ps = TermTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM termtypes " +
                "ORDER BY trmtyp_sortorder, trmtyp_termtypename");
        
        return TermTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public TermTypeChoicesBean getTermTypeChoices(String defaultTermTypeChoice, Language language, boolean allowNullChoice) {
        List<TermType> termTypes = getTermTypes();
        var size = termTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultTermTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var termType : termTypes) {
            var label = getBestTermTypeDescription(termType, language);
            var value = termType.getTermTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultTermTypeChoice != null && defaultTermTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && termType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new TermTypeChoicesBean(labels, values, defaultValue);
    }
    
    public TermTypeTransfer getTermTypeTransfer(UserVisit userVisit, TermType termType) {
        return getTermTransferCaches(userVisit).getTermTypeTransferCache().getTermTypeTransfer(termType);
    }
    
    public List<TermTypeTransfer> getTermTypeTransfers(UserVisit userVisit) {
        List<TermType> termTypes = getTermTypes();
        List<TermTypeTransfer> termTypeTransfers = new ArrayList<>(termTypes.size());
        TermTypeTransferCache termTypeTransferCache = getTermTransferCaches(userVisit).getTermTypeTransferCache();
        
        termTypes.forEach((termType) ->
                termTypeTransfers.add(termTypeTransferCache.getTermTypeTransfer(termType))
        );
        
        return termTypeTransfers;
    }
    
    // --------------------------------------------------------------------------------
    //   Term Type Descriptions
    // --------------------------------------------------------------------------------
    
    public TermTypeDescription createTermTypeDescription(TermType termType, Language language, String description) {
        return TermTypeDescriptionFactory.getInstance().create(termType, language, description);
    }
    
    public TermTypeDescription getTermTypeDescription(TermType termType, Language language) {
        TermTypeDescription termTypeDescription;
        
        try {
            PreparedStatement ps = TermTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM termtypedescriptions " +
                    "WHERE trmtypd_trmtyp_termtypeid = ? AND trmtypd_lang_languageid = ?");
            
            ps.setLong(1, termType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            termTypeDescription = TermTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return termTypeDescription;
    }
    
    public String getBestTermTypeDescription(TermType termType, Language language) {
        String description;
        TermTypeDescription termTypeDescription = getTermTypeDescription(termType, language);
        
        if(termTypeDescription == null && !language.getIsDefault()) {
            termTypeDescription = getTermTypeDescription(termType, getPartyControl().getDefaultLanguage());
        }
        
        if(termTypeDescription == null) {
            description = termType.getTermTypeName();
        } else {
            description = termTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Terms
    // --------------------------------------------------------------------------------
    
    public Term createTerm(String termName, TermType termType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Term defaultTerm = getDefaultTerm();
        boolean defaultFound = defaultTerm != null;
        
        if(defaultFound && isDefault) {
            TermDetailValue defaultTermDetailValue = getDefaultTermDetailValueForUpdate();
            
            defaultTermDetailValue.setIsDefault(Boolean.FALSE);
            updateTermFromValue(defaultTermDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        Term term = TermFactory.getInstance().create();
        TermDetail termDetail = TermDetailFactory.getInstance().create(term, termName, termType, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        term = TermFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                term.getPrimaryKey());
        term.setActiveDetail(termDetail);
        term.setLastDetail(termDetail);
        term.store();
        
        sendEventUsingNames(term.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return term;
    }
    
    private Term getTermByName(String termName, EntityPermission entityPermission) {
        Term term;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM terms, termdetails " +
                        "WHERE trm_activedetailid = trmdt_termdetailid AND trmdt_termname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM terms, termdetails " +
                        "WHERE trm_activedetailid = trmdt_termdetailid AND trmdt_termname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TermFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, termName);
            
            term = TermFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return term;
    }
    
    public Term getTermByName(String termName) {
        return getTermByName(termName, EntityPermission.READ_ONLY);
    }
    
    public Term getTermByNameForUpdate(String termName) {
        return getTermByName(termName, EntityPermission.READ_WRITE);
    }
    
    public TermDetailValue getTermDetailValueByNameForUpdate(String termName) {
        return getTermByName(termName, EntityPermission.READ_WRITE).getLastDetailForUpdate().getTermDetailValue();
    }
    
    private Term getDefaultTerm(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM terms, termdetails " +
                    "WHERE trm_activedetailid = trmdt_termdetailid AND trmdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM terms, termdetails " +
                    "WHERE trm_activedetailid = trmdt_termdetailid AND trmdt_isdefault = 1 " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TermFactory.getInstance().prepareStatement(query);
        
        return TermFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public Term getDefaultTerm() {
        return getDefaultTerm(EntityPermission.READ_ONLY);
    }
    
    public Term getDefaultTermForUpdate() {
        return getDefaultTerm(EntityPermission.READ_WRITE);
    }
    
    public TermDetailValue getDefaultTermDetailValueForUpdate() {
        return getDefaultTerm(EntityPermission.READ_WRITE).getLastDetailForUpdate().getTermDetailValue();
    }
    
    private List<Term> getTerms(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM terms, termdetails " +
                    "WHERE trm_activedetailid = trmdt_termdetailid " +
                    "ORDER BY trmdt_sortorder, trmdt_termname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM terms, termdetails " +
                    "WHERE trm_activedetailid = trmdt_termdetailid " +
                    "FOR UPDATE";
        }
        
        PreparedStatement ps = TermFactory.getInstance().prepareStatement(query);
        
        return TermFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Term> getTerms() {
        return getTerms(EntityPermission.READ_ONLY);
    }
    
    public List<Term> getTermsForUpdate() {
        return getTerms(EntityPermission.READ_WRITE);
    }
    
    public TermChoicesBean getTermChoices(String defaultTermChoice, Language language, boolean allowNullChoice) {
        List<Term> terms = getTerms();
        var size = terms.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultTermChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var term : terms) {
            TermDetail termDetail = term.getLastDetail();
            
            var label = getBestTermDescription(term, language);
            var value = termDetail.getTermName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultTermChoice != null && defaultTermChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && termDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new TermChoicesBean(labels, values, defaultValue);
    }
    
    public TermTransfer getTermTransfer(UserVisit userVisit, Term term) {
        return getTermTransferCaches(userVisit).getTermTransferCache().getTermTransfer(term);
    }
    
    public List<TermTransfer> getTermTransfers(UserVisit userVisit) {
        List<Term> terms = getTerms();
        List<TermTransfer> termTransfers = new ArrayList<>(terms.size());
        TermTransferCache termTransferCache = getTermTransferCaches(userVisit).getTermTransferCache();
        
        terms.forEach((term) ->
                termTransfers.add(termTransferCache.getTermTransfer(term))
        );
        
        return termTransfers;
    }
    
    private void updateTermFromValue(TermDetailValue termDetailValue, boolean checkDefault, BasePK updatedBy) {
        Term term = TermFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, termDetailValue.getTermPK());
        TermDetail termDetail = term.getActiveDetailForUpdate();
        
        termDetail.setThruTime(session.START_TIME_LONG);
        termDetail.store();
        
        TermPK termPK = termDetail.getTermPK();
        String termName = termDetailValue.getTermName();
        TermTypePK termTypePK = termDetail.getTermTypePK(); // Not updated
        Boolean isDefault = termDetailValue.getIsDefault();
        Integer sortOrder = termDetailValue.getSortOrder();
        
        if(checkDefault) {
            Term defaultTerm = getDefaultTerm();
            boolean defaultFound = defaultTerm != null && !defaultTerm.equals(term);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                TermDetailValue defaultTermDetailValue = getDefaultTermDetailValueForUpdate();
                
                defaultTermDetailValue.setIsDefault(Boolean.FALSE);
                updateTermFromValue(defaultTermDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }
        
        termDetail = TermDetailFactory.getInstance().create(termPK, termName, termTypePK, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        term.setActiveDetail(termDetail);
        term.setLastDetail(termDetail);
        term.store();
        
        sendEventUsingNames(termPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }
    
    public void updateTermFromValue(TermDetailValue termDetailValue, BasePK updatedBy) {
        updateTermFromValue(termDetailValue, true, updatedBy);
    }
    
    public void deleteTerm(Term term, BasePK deletedBy) {
        deletePartyTermsByTerm(term, deletedBy);
        deleteTermDescriptionsByTerm(term, deletedBy);
        
        TermDetail termDetail = term.getLastDetailForUpdate();
        termDetail.setThruTime(session.START_TIME_LONG);
        term.setActiveDetail(null);
        term.store();
        
        String termTypeName = termDetail.getTermType().getTermTypeName();
        if(termTypeName.equals(TermTypes.STANDARD.name())) {
            deleteStandardTermByTerm(term, deletedBy);
        } else if(termTypeName.equals(TermTypes.DATE_DRIVEN.name())) {
            deleteDateDrivenTermByTerm(term, deletedBy);
        }
        
        // Check for default, and pick one if necessary
        Term defaultTerm = getDefaultTerm();
        if(defaultTerm == null) {
            List<Term> terms = getTermsForUpdate();
            
            if(!terms.isEmpty()) {
                Iterator<Term> iter = terms.iterator();
                if(iter.hasNext()) {
                    defaultTerm = iter.next();
                }
                TermDetailValue termDetailValue = Objects.requireNonNull(defaultTerm).getLastDetailForUpdate().getTermDetailValue().clone();
                
                termDetailValue.setIsDefault(Boolean.TRUE);
                updateTermFromValue(termDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(term.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Term Descriptions
    // --------------------------------------------------------------------------------
    
    public TermDescription createTermDescription(Term term, Language language, String description, BasePK createdBy) {
        TermDescription termDescription = TermDescriptionFactory.getInstance().create(term, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(term.getPrimaryKey(), EventTypes.MODIFY.name(), termDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return termDescription;
    }
    
    private TermDescription getTermDescription(Term term, Language language, EntityPermission entityPermission) {
        TermDescription termDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM termdescriptions " +
                        "WHERE trmd_trm_termid = ? AND trmd_lang_languageid = ? AND trmd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM termdescriptions " +
                        "WHERE trmd_trm_termid = ? AND trmd_lang_languageid = ? AND trmd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TermDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, term.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            termDescription = TermDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return termDescription;
    }
    
    public TermDescription getTermDescription(Term term, Language language) {
        return getTermDescription(term, language, EntityPermission.READ_ONLY);
    }
    
    public TermDescription getTermDescriptionForUpdate(Term term, Language language) {
        return getTermDescription(term, language, EntityPermission.READ_WRITE);
    }
    
    public TermDescriptionValue getTermDescriptionValue(TermDescription termDescription) {
        return termDescription == null? null: termDescription.getTermDescriptionValue().clone();
    }
    
    public TermDescriptionValue getTermDescriptionValueForUpdate(Term term, Language language) {
        return getTermDescriptionValue(getTermDescriptionForUpdate(term, language));
    }
    
    private List<TermDescription> getTermDescriptionsByTerm(Term term, EntityPermission entityPermission) {
        List<TermDescription> termDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM termdescriptions, languages " +
                        "WHERE trmd_trm_termid = ? AND trmd_thrutime = ? AND trmd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM termdescriptions " +
                        "WHERE trmd_trm_termid = ? AND trmd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = TermDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, term.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            termDescriptions = TermDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return termDescriptions;
    }
    
    public List<TermDescription> getTermDescriptionsByTerm(Term term) {
        return getTermDescriptionsByTerm(term, EntityPermission.READ_ONLY);
    }
    
    public List<TermDescription> getTermDescriptionsByTermForUpdate(Term term) {
        return getTermDescriptionsByTerm(term, EntityPermission.READ_WRITE);
    }
    
    public String getBestTermDescription(Term term, Language language) {
        String description;
        TermDescription termDescription = getTermDescription(term, language);
        
        if(termDescription == null && !language.getIsDefault()) {
            termDescription = getTermDescription(term, getPartyControl().getDefaultLanguage());
        }
        
        if(termDescription == null) {
            description = term.getLastDetail().getTermName();
        } else {
            description = termDescription.getDescription();
        }
        
        return description;
    }
    
    public TermDescriptionTransfer getTermDescriptionTransfer(UserVisit userVisit, TermDescription termDescription) {
        return getTermTransferCaches(userVisit).getTermDescriptionTransferCache().getTermDescriptionTransfer(termDescription);
    }
    
    public List<TermDescriptionTransfer> getTermDescriptionTransfersByTerm(UserVisit userVisit, Term term) {
        List<TermDescription> termDescriptions = getTermDescriptionsByTerm(term);
        List<TermDescriptionTransfer> termDescriptionTransfers = new ArrayList<>(termDescriptions.size());
        
        termDescriptions.forEach((termDescription) -> {
            termDescriptionTransfers.add(getTermTransferCaches(userVisit).getTermDescriptionTransferCache().getTermDescriptionTransfer(termDescription));
        });
        
        return termDescriptionTransfers;
    }
    
    public void updateTermDescriptionFromValue(TermDescriptionValue termDescriptionValue, BasePK updatedBy) {
        if(termDescriptionValue.hasBeenModified()) {
            TermDescription termDescription = TermDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, termDescriptionValue.getPrimaryKey());
            
            termDescription.setThruTime(session.START_TIME_LONG);
            termDescription.store();
            
            Term term = termDescription.getTerm();
            Language language = termDescription.getLanguage();
            String description = termDescriptionValue.getDescription();
            
            termDescription = TermDescriptionFactory.getInstance().create(term, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(term.getPrimaryKey(), EventTypes.MODIFY.name(), termDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteTermDescription(TermDescription termDescription, BasePK deletedBy) {
        termDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(termDescription.getTermPK(), EventTypes.MODIFY.name(), termDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteTermDescriptionsByTerm(Term term, BasePK deletedBy) {
        List<TermDescription> termDescriptions = getTermDescriptionsByTermForUpdate(term);
        
        termDescriptions.forEach((termDescription) -> 
                deleteTermDescription(termDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Standard Terms
    // --------------------------------------------------------------------------------
    
    public StandardTerm createStandardTerm(Term term, Integer netDueDays, Integer discountPercentage, Integer discountDays,
            BasePK createdBy) {
        StandardTerm standardTerm = StandardTermFactory.getInstance().create(term, netDueDays, discountPercentage,
                discountDays, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(term.getPrimaryKey(), EventTypes.MODIFY.name(), standardTerm.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return standardTerm;
    }
    
    private StandardTerm getStandardTerm(Term term, EntityPermission entityPermission) {
        StandardTerm standardTerm;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM standardterms " +
                        "WHERE stdtrm_trm_termid = ? AND stdtrm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM standardterms " +
                        "WHERE stdtrm_trm_termid = ? AND stdtrm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = StandardTermFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, term.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            standardTerm = StandardTermFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return standardTerm;
    }
    
    public StandardTerm getStandardTerm(Term term) {
        return getStandardTerm(term, EntityPermission.READ_ONLY);
    }
    
    public StandardTerm getStandardTermForUpdate(Term term) {
        return getStandardTerm(term, EntityPermission.READ_WRITE);
    }
    
    public StandardTermValue getStandardTermValue(StandardTerm standardTerm) {
        return standardTerm == null? null: standardTerm.getStandardTermValue().clone();
    }
    
    public StandardTermValue getStandardTermValueForUpdate(Term term) {
        return getStandardTermValue(getStandardTermForUpdate(term));
    }
    
    public void updateStandardTermFromValue(StandardTermValue standardTermValue, BasePK updatedBy) {
        if(standardTermValue.hasBeenModified()) {
            StandardTerm standardTerm = StandardTermFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    standardTermValue.getPrimaryKey());
            
            standardTerm.setThruTime(session.START_TIME_LONG);
            standardTerm.store();
            
            TermPK termPK = standardTerm.getTermPK(); // Not updated
            Integer netDueDays = standardTermValue.getNetDueDays();
            Integer discountPercentage = standardTermValue.getDiscountPercentage();
            Integer discountDays = standardTermValue.getDiscountDays();
            
            standardTerm = StandardTermFactory.getInstance().create(termPK, netDueDays, discountPercentage, discountDays,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(termPK, EventTypes.MODIFY.name(), standardTerm.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteStandardTerm(StandardTerm standardTerm, BasePK deletedBy) {
        standardTerm.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(standardTerm.getTermPK(), EventTypes.MODIFY.name(), standardTerm.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteStandardTermByTerm(Term term, BasePK deletedBy) {
        StandardTerm standardTerm = getStandardTermForUpdate(term);
        
        if(standardTerm != null) {
            deleteStandardTerm(standardTerm, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Date Driven Terms
    // --------------------------------------------------------------------------------
    
    public DateDrivenTerm createDateDrivenTerm(Term term, Integer netDueDayOfMonth, Integer dueNextMonthDays,
            Integer discountPercentage, Integer discountBeforeDayOfMonth, BasePK createdBy) {
        DateDrivenTerm dateDrivenTerm = DateDrivenTermFactory.getInstance().create(term, netDueDayOfMonth,
                dueNextMonthDays, discountPercentage, discountBeforeDayOfMonth, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(term.getPrimaryKey(), EventTypes.MODIFY.name(), dateDrivenTerm.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return dateDrivenTerm;
    }
    
    private DateDrivenTerm getDateDrivenTerm(Term term, EntityPermission entityPermission) {
        DateDrivenTerm dateDrivenTerm;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM datedriventerms " +
                        "WHERE ddtrm_trm_termid = ? AND ddtrm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM datedriventerms " +
                        "WHERE ddtrm_trm_termid = ? AND ddtrm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = DateDrivenTermFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, term.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            dateDrivenTerm = DateDrivenTermFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return dateDrivenTerm;
    }
    
    public DateDrivenTerm getDateDrivenTerm(Term term) {
        return getDateDrivenTerm(term, EntityPermission.READ_ONLY);
    }
    
    public DateDrivenTerm getDateDrivenTermForUpdate(Term term) {
        return getDateDrivenTerm(term, EntityPermission.READ_WRITE);
    }
    
    public DateDrivenTermValue getDateDrivenTermValue(DateDrivenTerm dateDrivenTerm) {
        return dateDrivenTerm == null? null: dateDrivenTerm.getDateDrivenTermValue().clone();
    }
    
    public DateDrivenTermValue getDateDrivenTermValueForUpdate(Term term) {
        return getDateDrivenTermValue(getDateDrivenTermForUpdate(term));
    }
    
    public void updateDateDrivenTermFromValue(DateDrivenTermValue dateDrivenTermValue, BasePK updatedBy) {
        if(dateDrivenTermValue.hasBeenModified()) {
            DateDrivenTerm dateDrivenTerm = DateDrivenTermFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    dateDrivenTermValue.getPrimaryKey());
            
            dateDrivenTerm.setThruTime(session.START_TIME_LONG);
            dateDrivenTerm.store();
            
            TermPK termPK = dateDrivenTerm.getTermPK(); // Not updated
            Integer netDueDayOfMonth = dateDrivenTermValue.getNetDueDayOfMonth();
            Integer dueNextMonthDays = dateDrivenTermValue.getDueNextMonthDays();
            Integer discountPercentage = dateDrivenTermValue.getDiscountPercentage();
            Integer discountBeforeDayOfMonth = dateDrivenTermValue.getDiscountBeforeDayOfMonth();
            
            dateDrivenTerm = DateDrivenTermFactory.getInstance().create(termPK, netDueDayOfMonth, dueNextMonthDays,
                    discountPercentage, discountBeforeDayOfMonth, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(termPK, EventTypes.MODIFY.name(), dateDrivenTerm.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteDateDrivenTerm(DateDrivenTerm dateDrivenTerm, BasePK deletedBy) {
        dateDrivenTerm.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(dateDrivenTerm.getTermPK(), EventTypes.MODIFY.name(), dateDrivenTerm.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteDateDrivenTermByTerm(Term term, BasePK deletedBy) {
        DateDrivenTerm dateDrivenTerm = getDateDrivenTermForUpdate(term);
        
        if(dateDrivenTerm != null) {
            deleteDateDrivenTerm(dateDrivenTerm, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Credit Limits
    // --------------------------------------------------------------------------------
    
    public CustomerTypeCreditLimit createCustomerTypeCreditLimit(CustomerType customerType, Currency currency, Long creditLimit,
            Long potentialCreditLimit, BasePK createdBy) {
        CustomerTypeCreditLimit customerTypeCreditLimit = CustomerTypeCreditLimitFactory.getInstance().create(customerType,
                currency, creditLimit, potentialCreditLimit, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(customerType.getPrimaryKey(), EventTypes.MODIFY.name(), customerTypeCreditLimit.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return customerTypeCreditLimit;
    }
    
    private CustomerTypeCreditLimit getCustomerTypeCreditLimit(CustomerType customerType, Currency currency, EntityPermission entityPermission) {
        CustomerTypeCreditLimit customerTypeCreditLimit;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM customertypecreditlimits " +
                        "WHERE cutyclim_cuty_customertypeid = ? AND cutyclim_cur_currencyid = ? AND cutyclim_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM customertypecreditlimits " +
                        "WHERE cutyclim_cuty_customertypeid = ? AND cutyclim_cur_currencyid = ? AND cutyclim_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CustomerTypeCreditLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, customerType.getPrimaryKey().getEntityId());
            ps.setLong(2, currency.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            customerTypeCreditLimit = CustomerTypeCreditLimitFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return customerTypeCreditLimit;
    }
    
    public CustomerTypeCreditLimit getCustomerTypeCreditLimit(CustomerType customerType, Currency currency) {
        return getCustomerTypeCreditLimit(customerType, currency, EntityPermission.READ_ONLY);
    }
    
    public CustomerTypeCreditLimit getCustomerTypeCreditLimitForUpdate(CustomerType customerType, Currency currency) {
        return getCustomerTypeCreditLimit(customerType, currency, EntityPermission.READ_WRITE);
    }
    
    public CustomerTypeCreditLimitValue getCustomerTypeCreditLimitValue(CustomerTypeCreditLimit customerTypeCreditLimit) {
        return customerTypeCreditLimit == null? null: customerTypeCreditLimit.getCustomerTypeCreditLimitValue().clone();
    }
    
    public CustomerTypeCreditLimitValue getCustomerTypeCreditLimitValueForUpdate(CustomerType customerType, Currency currency) {
        return getCustomerTypeCreditLimitValue(getCustomerTypeCreditLimitForUpdate(customerType, currency));
    }
    
    private List<CustomerTypeCreditLimit> getCustomerTypeCreditLimitsByCustomerType(CustomerType customerType,
            EntityPermission entityPermission) {
        List<CustomerTypeCreditLimit> customerTypeCreditLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM customertypecreditlimits, currencies " +
                        "WHERE cutyclim_cuty_customertypeid = ? AND cutyclim_thrutime = ? " +
                        "AND cutyclim_cur_currencyid = cur_currencyid " +
                        "ORDER BY cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM customertypecreditlimits " +
                        "WHERE cutyclim_cuty_customertypeid = ? AND cutyclim_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = CustomerTypeCreditLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, customerType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            customerTypeCreditLimits = CustomerTypeCreditLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return customerTypeCreditLimits;
    }
    
    public List<CustomerTypeCreditLimit> getCustomerTypeCreditLimitsByCustomerType(CustomerType customerType) {
        return getCustomerTypeCreditLimitsByCustomerType(customerType, EntityPermission.READ_ONLY);
    }
    
    public List<CustomerTypeCreditLimit> getCustomerTypeCreditLimitsByCustomerTypeForUpdate(CustomerType customerType) {
        return getCustomerTypeCreditLimitsByCustomerType(customerType, EntityPermission.READ_WRITE);
    }
    
    public CustomerTypeCreditLimitTransfer getCustomerTypeCreditLimitTransfer(UserVisit userVisit, CustomerTypeCreditLimit customerTypeCreditLimit) {
        return getTermTransferCaches(userVisit).getCustomerTypeCreditLimitTransferCache().getCustomerTypeCreditLimitTransfer(customerTypeCreditLimit);
    }
    
    public List<CustomerTypeCreditLimitTransfer> getCustomerTypeCreditLimitTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        List<CustomerTypeCreditLimit> customerTypeCreditLimits = getCustomerTypeCreditLimitsByCustomerType(customerType);
        List<CustomerTypeCreditLimitTransfer> customerTypeCreditLimitTransfers = new ArrayList<>(customerTypeCreditLimits.size());
        CustomerTypeCreditLimitTransferCache customerTypeCreditLimitTransferCache = getTermTransferCaches(userVisit).getCustomerTypeCreditLimitTransferCache();
        
        customerTypeCreditLimits.forEach((customerTypeCreditLimit) ->
                customerTypeCreditLimitTransfers.add(customerTypeCreditLimitTransferCache.getCustomerTypeCreditLimitTransfer(customerTypeCreditLimit))
        );
        
        return customerTypeCreditLimitTransfers;
    }
    
    public void updateCustomerTypeCreditLimitFromValue(CustomerTypeCreditLimitValue customerTypeCreditLimitValue, BasePK updatedBy) {
        if(customerTypeCreditLimitValue.hasBeenModified()) {
            CustomerTypeCreditLimit customerTypeCreditLimit = CustomerTypeCreditLimitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeCreditLimitValue.getPrimaryKey());
            
            customerTypeCreditLimit.setThruTime(session.START_TIME_LONG);
            customerTypeCreditLimit.store();
            
            CustomerTypePK customerTypePK = customerTypeCreditLimit.getCustomerTypePK(); // Not updated
            CurrencyPK currencyPK = customerTypeCreditLimit.getCurrencyPK(); // Not updated
            Long creditLimit = customerTypeCreditLimitValue.getCreditLimit();
            Long potentialCreditLimit = customerTypeCreditLimitValue.getPotentialCreditLimit();
            
            customerTypeCreditLimit = CustomerTypeCreditLimitFactory.getInstance().create(customerTypePK, currencyPK, creditLimit,
                    potentialCreditLimit, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(customerTypePK, EventTypes.MODIFY.name(), customerTypeCreditLimit.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteCustomerTypeCreditLimit(CustomerTypeCreditLimit customerTypeCreditLimit, BasePK deletedBy) {
        customerTypeCreditLimit.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(customerTypeCreditLimit.getCustomerTypePK(), EventTypes.MODIFY.name(), customerTypeCreditLimit.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteCustomerTypeCreditLimitsByCustomerType(CustomerType customerType, BasePK deletedBy) {
        List<CustomerTypeCreditLimit> customerTypeCreditLimits = getCustomerTypeCreditLimitsByCustomerTypeForUpdate(customerType);
        
        customerTypeCreditLimits.forEach((customerTypeCreditLimit) -> 
                deleteCustomerTypeCreditLimit(customerTypeCreditLimit, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Credit Limits
    // --------------------------------------------------------------------------------
    
    public PartyCreditLimit createPartyCreditLimit(Party party, Currency currency, Long creditLimit, Long potentialCreditLimit,
            BasePK createdBy) {
        PartyCreditLimit partyCreditLimit = PartyCreditLimitFactory.getInstance().create(party, currency, creditLimit,
                potentialCreditLimit, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyCreditLimit.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyCreditLimit;
    }
    
    private PartyCreditLimit getPartyCreditLimit(Party party, Currency currency, EntityPermission entityPermission) {
        PartyCreditLimit partyCreditLimit;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycreditlimits " +
                        "WHERE pclim_par_partyid = ? AND pclim_cur_currencyid = ? AND pclim_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycreditlimits " +
                        "WHERE pclim_par_partyid = ? AND pclim_cur_currencyid = ? AND pclim_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyCreditLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, currency.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            partyCreditLimit = PartyCreditLimitFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyCreditLimit;
    }
    
    public PartyCreditLimit getPartyCreditLimit(Party party, Currency currency) {
        return getPartyCreditLimit(party, currency, EntityPermission.READ_ONLY);
    }
    
    public PartyCreditLimit getPartyCreditLimitForUpdate(Party party, Currency currency) {
        return getPartyCreditLimit(party, currency, EntityPermission.READ_WRITE);
    }
    
    public PartyCreditLimitValue getPartyCreditLimitValue(PartyCreditLimit partyCreditLimit) {
        return partyCreditLimit == null? null: partyCreditLimit.getPartyCreditLimitValue().clone();
    }
    
    public PartyCreditLimitValue getPartyCreditLimitValueForUpdate(Party party, Currency currency) {
        return getPartyCreditLimitValue(getPartyCreditLimitForUpdate(party, currency));
    }
    
    private List<PartyCreditLimit> getPartyCreditLimitsByParty(Party party, EntityPermission entityPermission) {
        List<PartyCreditLimit> partyCreditLimits;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partycreditlimits, currencies " +
                        "WHERE pclim_par_partyid = ? AND pclim_thrutime = ? " +
                        "AND pclim_cur_currencyid = cur_currencyid " +
                        "ORDER BY cur_sortorder, cur_currencyisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partycreditlimits " +
                        "WHERE pclim_par_partyid = ? AND pclim_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyCreditLimitFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyCreditLimits = PartyCreditLimitFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyCreditLimits;
    }
    
    public List<PartyCreditLimit> getPartyCreditLimitsByParty(Party party) {
        return getPartyCreditLimitsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyCreditLimit> getPartyCreditLimitsByPartyForUpdate(Party party) {
        return getPartyCreditLimitsByParty(party, EntityPermission.READ_WRITE);
    }
    
    public PartyCreditLimitTransfer getPartyCreditLimitTransfer(UserVisit userVisit, PartyCreditLimit partyCreditLimit) {
        return getTermTransferCaches(userVisit).getPartyCreditLimitTransferCache().getPartyCreditLimitTransfer(partyCreditLimit);
    }
    
    public List<PartyCreditLimitTransfer> getPartyCreditLimitTransfersByParty(UserVisit userVisit, Party party) {
        List<PartyCreditLimit> partyCreditLimits = getPartyCreditLimitsByParty(party);
        List<PartyCreditLimitTransfer> partyCreditLimitTransfers = new ArrayList<>(partyCreditLimits.size());
        PartyCreditLimitTransferCache partyCreditLimitTransferCache = getTermTransferCaches(userVisit).getPartyCreditLimitTransferCache();
        
        partyCreditLimits.forEach((partyCreditLimit) ->
                partyCreditLimitTransfers.add(partyCreditLimitTransferCache.getPartyCreditLimitTransfer(partyCreditLimit))
        );
        
        return partyCreditLimitTransfers;
    }
    
    public void updatePartyCreditLimitFromValue(PartyCreditLimitValue partyCreditLimitValue, BasePK updatedBy) {
        if(partyCreditLimitValue.hasBeenModified()) {
            PartyCreditLimit partyCreditLimit = PartyCreditLimitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyCreditLimitValue.getPrimaryKey());
            
            partyCreditLimit.setThruTime(session.START_TIME_LONG);
            partyCreditLimit.store();
            
            PartyPK partyPK = partyCreditLimit.getPartyPK(); // Not updated
            CurrencyPK currencyPK = partyCreditLimit.getCurrencyPK(); // Not updated
            Long creditLimit = partyCreditLimitValue.getCreditLimit();
            Long potentialCreditLimit = partyCreditLimitValue.getPotentialCreditLimit();
            
            partyCreditLimit = PartyCreditLimitFactory.getInstance().create(partyPK, currencyPK, creditLimit,
                    potentialCreditLimit, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), partyCreditLimit.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePartyCreditLimit(PartyCreditLimit partyCreditLimit, BasePK deletedBy) {
        partyCreditLimit.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partyCreditLimit.getPartyPK(), EventTypes.MODIFY.name(), partyCreditLimit.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyCreditLimitsByParty(Party party, BasePK deletedBy) {
        List<PartyCreditLimit> partyCreditLimits = getPartyCreditLimitsByPartyForUpdate(party);
        
        partyCreditLimits.forEach((partyCreditLimit) -> 
                deletePartyCreditLimit(partyCreditLimit, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Terms
    // --------------------------------------------------------------------------------
    
    public PartyTerm createPartyTerm(Party party, Term term, Boolean taxable, BasePK createdBy) {
        PartyTerm partyTerm = PartyTermFactory.getInstance().create(party, term, taxable, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyTerm.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyTerm;
    }
    
    private PartyTerm getPartyTerm(Party party, EntityPermission entityPermission) {
        PartyTerm partyTerm;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyterms " +
                        "WHERE ptrm_par_partyid = ? AND ptrm_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyterms " +
                        "WHERE ptrm_par_partyid = ? AND ptrm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyTermFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyTerm = PartyTermFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyTerm;
    }
    
    public PartyTerm getPartyTerm(Party party) {
        return getPartyTerm(party, EntityPermission.READ_ONLY);
    }
    
    public PartyTerm getPartyTermForUpdate(Party party) {
        return getPartyTerm(party, EntityPermission.READ_WRITE);
    }
    
    public PartyTermValue getPartyTermValue(PartyTerm partyTerm) {
        return partyTerm == null? null: partyTerm.getPartyTermValue().clone();
    }
    
    public PartyTermValue getPartyTermValueForUpdate(Party party) {
        return getPartyTermValue(getPartyTermForUpdate(party));
    }
    
    private List<PartyTerm> getPartyTermsByTerm(Term term, EntityPermission entityPermission) {
        List<PartyTerm> partyTerms;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyterms, terms, termdetails " +
                        "WHERE ptrm_trm_termid = ? AND ptrm_thrutime = ? " +
                        "AND ptrm_trm_termid = trm_termid AND trm_lastdetailid = trmdt_termdetailid " +
                        "ORDER BY trmdt_sortorder, trmdt_termname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyterms " +
                        "WHERE ptrm_trm_termid = ? AND ptrm_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyTermFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, term.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyTerms = PartyTermFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyTerms;
    }
    
    public List<PartyTerm> getPartyTermsByTerm(Term term) {
        return getPartyTermsByTerm(term, EntityPermission.READ_ONLY);
    }
    
    public List<PartyTerm> getPartyTermsByTermForUpdate(Term term) {
        return getPartyTermsByTerm(term, EntityPermission.READ_WRITE);
    }
    
    public PartyTermTransfer getPartyTermTransfer(UserVisit userVisit, Party party) {
        PartyTerm partyTerm = getPartyTerm(party);
        
        return partyTerm == null? null: getTermTransferCaches(userVisit).getPartyTermTransferCache().getPartyTermTransfer(partyTerm);
    }
    
    public PartyTermTransfer getPartyTermTransfer(UserVisit userVisit, PartyTerm partyTerm) {
        return getTermTransferCaches(userVisit).getPartyTermTransferCache().getPartyTermTransfer(partyTerm);
    }
    
    public void updatePartyTermFromValue(PartyTermValue partyTermValue, BasePK updatedBy) {
        if(partyTermValue.hasBeenModified()) {
            PartyTerm partyTerm = PartyTermFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyTermValue.getPrimaryKey());
            
            partyTerm.setThruTime(session.START_TIME_LONG);
            partyTerm.store();
            
            PartyPK partyPK = partyTerm.getPartyPK(); // Not updated
            TermPK termPK = partyTermValue.getTermPK();
            Boolean taxable = partyTermValue.getTaxable();
            
            partyTerm = PartyTermFactory.getInstance().create(partyPK, termPK, taxable, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), partyTerm.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePartyTerm(PartyTerm partyTerm, BasePK deletedBy) {
        partyTerm.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partyTerm.getPartyPK(), EventTypes.MODIFY.name(), partyTerm.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyTermByParty(Party party, BasePK deletedBy) {
        PartyTerm partyTerm = getPartyTermForUpdate(party);
        
        if(partyTerm != null) {
            deletePartyTerm(partyTerm, deletedBy);
        }
    }
    
    public void deletePartyTermsByTerm(Term term, BasePK deletedBy) {
        List<PartyTerm> partyTerms = getPartyTermsByTermForUpdate(term);
        
        partyTerms.forEach((partyTerm) -> 
                deletePartyTerm(partyTerm, deletedBy)
        );
    }
    
}

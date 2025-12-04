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
import com.echothree.model.control.term.server.transfer.PartyTermTransferCache;
import com.echothree.model.control.term.server.transfer.TermDescriptionTransferCache;
import com.echothree.model.control.term.server.transfer.TermTransferCache;
import com.echothree.model.control.term.server.transfer.TermTypeTransferCache;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.customer.server.entity.CustomerType;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class TermControl
        extends BaseModelControl {
    
    /** Creates a new instance of TermControl */
    protected TermControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Term Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    TermTypeTransferCache termTypeTransferCache;

    @Inject
    TermTransferCache termTransferCache;

    @Inject
    TermDescriptionTransferCache termDescriptionTransferCache;

    @Inject
    PartyTermTransferCache partyTermTransferCache;

    @Inject
    CustomerTypeCreditLimitTransferCache customerTypeCreditLimitTransferCache;

    @Inject
    PartyCreditLimitTransferCache partyCreditLimitTransferCache;

    // --------------------------------------------------------------------------------
    //   Term Types
    // --------------------------------------------------------------------------------

    public TermType createTermType(String termTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var termType = TermTypeFactory.getInstance().create(termTypeName, isDefault, sortOrder);

        sendEvent(termType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return termType;
    }

    public long countTermTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                    "FROM termtypes");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TermType */
    public TermType getTermTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TermTypePK(entityInstance.getEntityUniqueId());

        return TermTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public TermType getTermTypeByEntityInstance(EntityInstance entityInstance) {
        return getTermTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TermType getTermTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTermTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getTermTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM termtypes " +
                "WHERE trmtyp_termtypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM termtypes " +
                "WHERE trmtyp_termtypename = ? " +
                "FOR UPDATE");
        getTermTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public TermType getTermTypeByName(String termTypeName, EntityPermission entityPermission) {
        return TermTypeFactory.getInstance().getEntityFromQuery(entityPermission, getTermTypeByNameQueries, termTypeName);
    }

    public TermType getTermTypeByName(String termTypeName) {
        return getTermTypeByName(termTypeName, EntityPermission.READ_ONLY);
    }

    public TermType getTermTypeByNameForUpdate(String termTypeName) {
        return getTermTypeByName(termTypeName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultTermTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM termtypes " +
                "WHERE trmtyp_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM termtypes " +
                "WHERE trmtyp_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultTermTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public TermType getDefaultTermType(EntityPermission entityPermission) {
        return TermTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultTermTypeQueries);
    }

    public TermType getDefaultTermType() {
        return getDefaultTermType(EntityPermission.READ_ONLY);
    }

    public TermType getDefaultTermTypeForUpdate() {
        return getDefaultTermType(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getTermTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM termtypes " +
                "ORDER BY trmtyp_sortorder, trmtyp_termtypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM termtypes " +
                "FOR UPDATE");
        getTermTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TermType> getTermTypes(EntityPermission entityPermission) {
        return TermTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getTermTypesQueries);
    }

    public List<TermType> getTermTypes() {
        return getTermTypes(EntityPermission.READ_ONLY);
    }

    public List<TermType> getTermTypesForUpdate() {
        return getTermTypes(EntityPermission.READ_WRITE);
    }

    public TermTypeTransfer getTermTypeTransfer(UserVisit userVisit, TermType termType) {
        return termTypeTransferCache.getTermTypeTransfer(userVisit, termType);
    }

    public List<TermTypeTransfer> getTermTypeTransfers(UserVisit userVisit, Collection<TermType> termTypes) {
        List<TermTypeTransfer> termTypeTransfers = new ArrayList<>(termTypes.size());

        termTypes.forEach((termType) ->
                termTypeTransfers.add(termTypeTransferCache.getTermTypeTransfer(userVisit, termType))
        );

        return termTypeTransfers;
    }

    public List<TermTypeTransfer> getTermTypeTransfers(UserVisit userVisit) {
        return getTermTypeTransfers(userVisit, getTermTypes());
    }

    public TermTypeChoicesBean getTermTypeChoices(String defaultTermTypeChoice,
            Language language, boolean allowNullChoice) {
        var termTypes = getTermTypes();
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

    // --------------------------------------------------------------------------------
    //   Term Type Descriptions
    // --------------------------------------------------------------------------------

    public TermTypeDescription createTermTypeDescription(TermType termType, Language language,
            String description, BasePK createdBy) {
        var termTypeDescription = TermTypeDescriptionFactory.getInstance().create(termType, language, description);

        sendEvent(termType.getPrimaryKey(), EventTypes.MODIFY, termTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return termTypeDescription;
    }

    public TermTypeDescription getTermTypeDescription(TermType termType, Language language) {
        TermTypeDescription termTypeDescription;

        try {
            var ps = TermTypeDescriptionFactory.getInstance().prepareStatement(
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
        var termTypeDescription = getTermTypeDescription(termType, language);

        if(termTypeDescription == null && !language.getIsDefault()) {
            termTypeDescription = getTermTypeDescription(termType, partyControl.getDefaultLanguage());
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
        var defaultTerm = getDefaultTerm();
        var defaultFound = defaultTerm != null;
        
        if(defaultFound && isDefault) {
            var defaultTermDetailValue = getDefaultTermDetailValueForUpdate();
            
            defaultTermDetailValue.setIsDefault(false);
            updateTermFromValue(defaultTermDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var term = TermFactory.getInstance().create();
        var termDetail = TermDetailFactory.getInstance().create(term, termName, termType, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        term = TermFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                term.getPrimaryKey());
        term.setActiveDetail(termDetail);
        term.setLastDetail(termDetail);
        term.store();
        
        sendEvent(term.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return term;
    }

    public long countTerms() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM terms, termtypes " +
                "WHERE trm_activedetailid = trmtyp_termtypeid");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Term */
    public Term getTermByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TermPK(entityInstance.getEntityUniqueId());

        return TermFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Term getTermByEntityInstance(EntityInstance entityInstance) {
        return getTermByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Term getTermByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTermByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public Term getTermByName(String termName, EntityPermission entityPermission) {
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

            var ps = TermFactory.getInstance().prepareStatement(query);
            
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
    
    public Term getDefaultTerm(EntityPermission entityPermission) {
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

        var ps = TermFactory.getInstance().prepareStatement(query);
        
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
                    "ORDER BY trmdt_sortorder, trmdt_termname " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM terms, termdetails " +
                    "WHERE trm_activedetailid = trmdt_termdetailid " +
                    "FOR UPDATE";
        }

        var ps = TermFactory.getInstance().prepareStatement(query);
        
        return TermFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Term> getTerms() {
        return getTerms(EntityPermission.READ_ONLY);
    }
    
    public List<Term> getTermsForUpdate() {
        return getTerms(EntityPermission.READ_WRITE);
    }
    
    public TermChoicesBean getTermChoices(String defaultTermChoice, Language language, boolean allowNullChoice) {
        var terms = getTerms();
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
            var termDetail = term.getLastDetail();
            
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
        return termTransferCache.getTermTransfer(userVisit, term);
    }

    public List<TermTransfer> getTermTransfers(UserVisit userVisit, Collection<Term> terms) {
        List<TermTransfer> termTransfers = new ArrayList<>(terms.size());

        terms.forEach((term) ->
                termTransfers.add(termTransferCache.getTermTransfer(userVisit, term))
        );

        return termTransfers;
    }

    public List<TermTransfer> getTermTransfers(UserVisit userVisit) {
        return getTermTransfers(userVisit, getTerms());
    }

    private void updateTermFromValue(TermDetailValue termDetailValue, boolean checkDefault, BasePK updatedBy) {
        var term = TermFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, termDetailValue.getTermPK());
        var termDetail = term.getActiveDetailForUpdate();
        
        termDetail.setThruTime(session.START_TIME_LONG);
        termDetail.store();

        var termPK = termDetail.getTermPK();
        var termName = termDetailValue.getTermName();
        var termTypePK = termDetail.getTermTypePK(); // Not updated
        var isDefault = termDetailValue.getIsDefault();
        var sortOrder = termDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultTerm = getDefaultTerm();
            var defaultFound = defaultTerm != null && !defaultTerm.equals(term);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultTermDetailValue = getDefaultTermDetailValueForUpdate();
                
                defaultTermDetailValue.setIsDefault(false);
                updateTermFromValue(defaultTermDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        termDetail = TermDetailFactory.getInstance().create(termPK, termName, termTypePK, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        term.setActiveDetail(termDetail);
        term.setLastDetail(termDetail);
        term.store();
        
        sendEvent(termPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateTermFromValue(TermDetailValue termDetailValue, BasePK updatedBy) {
        updateTermFromValue(termDetailValue, true, updatedBy);
    }
    
    public void deleteTerm(Term term, BasePK deletedBy) {
        deletePartyTermsByTerm(term, deletedBy);
        deleteTermDescriptionsByTerm(term, deletedBy);

        var termDetail = term.getLastDetailForUpdate();
        termDetail.setThruTime(session.START_TIME_LONG);
        term.setActiveDetail(null);
        term.store();

        var termTypeName = termDetail.getTermType().getTermTypeName();
        if(termTypeName.equals(TermTypes.STANDARD.name())) {
            deleteStandardTermByTerm(term, deletedBy);
        } else if(termTypeName.equals(TermTypes.DATE_DRIVEN.name())) {
            deleteDateDrivenTermByTerm(term, deletedBy);
        }
        
        // Check for default, and pick one if necessary
        var defaultTerm = getDefaultTerm();
        if(defaultTerm == null) {
            var terms = getTermsForUpdate();
            
            if(!terms.isEmpty()) {
                var iter = terms.iterator();
                if(iter.hasNext()) {
                    defaultTerm = iter.next();
                }
                var termDetailValue = Objects.requireNonNull(defaultTerm).getLastDetailForUpdate().getTermDetailValue().clone();
                
                termDetailValue.setIsDefault(true);
                updateTermFromValue(termDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(term.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Term Descriptions
    // --------------------------------------------------------------------------------
    
    public TermDescription createTermDescription(Term term, Language language, String description, BasePK createdBy) {
        var termDescription = TermDescriptionFactory.getInstance().create(term, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(term.getPrimaryKey(), EventTypes.MODIFY, termDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = TermDescriptionFactory.getInstance().prepareStatement(query);
            
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

            var ps = TermDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var termDescription = getTermDescription(term, language);
        
        if(termDescription == null && !language.getIsDefault()) {
            termDescription = getTermDescription(term, partyControl.getDefaultLanguage());
        }
        
        if(termDescription == null) {
            description = term.getLastDetail().getTermName();
        } else {
            description = termDescription.getDescription();
        }
        
        return description;
    }
    
    public TermDescriptionTransfer getTermDescriptionTransfer(UserVisit userVisit, TermDescription termDescription) {
        return termDescriptionTransferCache.getTermDescriptionTransfer(userVisit, termDescription);
    }
    
    public List<TermDescriptionTransfer> getTermDescriptionTransfersByTerm(UserVisit userVisit, Term term) {
        var termDescriptions = getTermDescriptionsByTerm(term);
        List<TermDescriptionTransfer> termDescriptionTransfers = new ArrayList<>(termDescriptions.size());
        
        termDescriptions.forEach((termDescription) -> {
            termDescriptionTransfers.add(termDescriptionTransferCache.getTermDescriptionTransfer(userVisit, termDescription));
        });
        
        return termDescriptionTransfers;
    }
    
    public void updateTermDescriptionFromValue(TermDescriptionValue termDescriptionValue, BasePK updatedBy) {
        if(termDescriptionValue.hasBeenModified()) {
            var termDescription = TermDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, termDescriptionValue.getPrimaryKey());
            
            termDescription.setThruTime(session.START_TIME_LONG);
            termDescription.store();

            var term = termDescription.getTerm();
            var language = termDescription.getLanguage();
            var description = termDescriptionValue.getDescription();
            
            termDescription = TermDescriptionFactory.getInstance().create(term, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(term.getPrimaryKey(), EventTypes.MODIFY, termDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTermDescription(TermDescription termDescription, BasePK deletedBy) {
        termDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(termDescription.getTermPK(), EventTypes.MODIFY, termDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteTermDescriptionsByTerm(Term term, BasePK deletedBy) {
        var termDescriptions = getTermDescriptionsByTermForUpdate(term);
        
        termDescriptions.forEach((termDescription) -> 
                deleteTermDescription(termDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Standard Terms
    // --------------------------------------------------------------------------------
    
    public StandardTerm createStandardTerm(Term term, Integer netDueDays, Integer discountPercentage, Integer discountDays,
            BasePK createdBy) {
        var standardTerm = StandardTermFactory.getInstance().create(term, netDueDays, discountPercentage,
                discountDays, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(term.getPrimaryKey(), EventTypes.MODIFY, standardTerm.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = StandardTermFactory.getInstance().prepareStatement(query);
            
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
            var standardTerm = StandardTermFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    standardTermValue.getPrimaryKey());
            
            standardTerm.setThruTime(session.START_TIME_LONG);
            standardTerm.store();

            var termPK = standardTerm.getTermPK(); // Not updated
            var netDueDays = standardTermValue.getNetDueDays();
            var discountPercentage = standardTermValue.getDiscountPercentage();
            var discountDays = standardTermValue.getDiscountDays();
            
            standardTerm = StandardTermFactory.getInstance().create(termPK, netDueDays, discountPercentage, discountDays,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(termPK, EventTypes.MODIFY, standardTerm.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteStandardTerm(StandardTerm standardTerm, BasePK deletedBy) {
        standardTerm.setThruTime(session.START_TIME_LONG);
        
        sendEvent(standardTerm.getTermPK(), EventTypes.MODIFY, standardTerm.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteStandardTermByTerm(Term term, BasePK deletedBy) {
        var standardTerm = getStandardTermForUpdate(term);
        
        if(standardTerm != null) {
            deleteStandardTerm(standardTerm, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Date Driven Terms
    // --------------------------------------------------------------------------------
    
    public DateDrivenTerm createDateDrivenTerm(Term term, Integer netDueDayOfMonth, Integer dueNextMonthDays,
            Integer discountPercentage, Integer discountBeforeDayOfMonth, BasePK createdBy) {
        var dateDrivenTerm = DateDrivenTermFactory.getInstance().create(term, netDueDayOfMonth,
                dueNextMonthDays, discountPercentage, discountBeforeDayOfMonth, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(term.getPrimaryKey(), EventTypes.MODIFY, dateDrivenTerm.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = DateDrivenTermFactory.getInstance().prepareStatement(query);
            
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
            var dateDrivenTerm = DateDrivenTermFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    dateDrivenTermValue.getPrimaryKey());
            
            dateDrivenTerm.setThruTime(session.START_TIME_LONG);
            dateDrivenTerm.store();

            var termPK = dateDrivenTerm.getTermPK(); // Not updated
            var netDueDayOfMonth = dateDrivenTermValue.getNetDueDayOfMonth();
            var dueNextMonthDays = dateDrivenTermValue.getDueNextMonthDays();
            var discountPercentage = dateDrivenTermValue.getDiscountPercentage();
            var discountBeforeDayOfMonth = dateDrivenTermValue.getDiscountBeforeDayOfMonth();
            
            dateDrivenTerm = DateDrivenTermFactory.getInstance().create(termPK, netDueDayOfMonth, dueNextMonthDays,
                    discountPercentage, discountBeforeDayOfMonth, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(termPK, EventTypes.MODIFY, dateDrivenTerm.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDateDrivenTerm(DateDrivenTerm dateDrivenTerm, BasePK deletedBy) {
        dateDrivenTerm.setThruTime(session.START_TIME_LONG);
        
        sendEvent(dateDrivenTerm.getTermPK(), EventTypes.MODIFY, dateDrivenTerm.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteDateDrivenTermByTerm(Term term, BasePK deletedBy) {
        var dateDrivenTerm = getDateDrivenTermForUpdate(term);
        
        if(dateDrivenTerm != null) {
            deleteDateDrivenTerm(dateDrivenTerm, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Credit Limits
    // --------------------------------------------------------------------------------
    
    public CustomerTypeCreditLimit createCustomerTypeCreditLimit(CustomerType customerType, Currency currency, Long creditLimit,
            Long potentialCreditLimit, BasePK createdBy) {
        var customerTypeCreditLimit = CustomerTypeCreditLimitFactory.getInstance().create(customerType,
                currency, creditLimit, potentialCreditLimit, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(customerType.getPrimaryKey(), EventTypes.MODIFY, customerTypeCreditLimit.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = CustomerTypeCreditLimitFactory.getInstance().prepareStatement(query);
            
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

            var ps = CustomerTypeCreditLimitFactory.getInstance().prepareStatement(query);
            
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
        return customerTypeCreditLimitTransferCache.getCustomerTypeCreditLimitTransfer(userVisit, customerTypeCreditLimit);
    }
    
    public List<CustomerTypeCreditLimitTransfer> getCustomerTypeCreditLimitTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        var customerTypeCreditLimits = getCustomerTypeCreditLimitsByCustomerType(customerType);
        List<CustomerTypeCreditLimitTransfer> customerTypeCreditLimitTransfers = new ArrayList<>(customerTypeCreditLimits.size());
        
        customerTypeCreditLimits.forEach((customerTypeCreditLimit) ->
                customerTypeCreditLimitTransfers.add(customerTypeCreditLimitTransferCache.getCustomerTypeCreditLimitTransfer(userVisit, customerTypeCreditLimit))
        );
        
        return customerTypeCreditLimitTransfers;
    }
    
    public void updateCustomerTypeCreditLimitFromValue(CustomerTypeCreditLimitValue customerTypeCreditLimitValue, BasePK updatedBy) {
        if(customerTypeCreditLimitValue.hasBeenModified()) {
            var customerTypeCreditLimit = CustomerTypeCreditLimitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeCreditLimitValue.getPrimaryKey());
            
            customerTypeCreditLimit.setThruTime(session.START_TIME_LONG);
            customerTypeCreditLimit.store();

            var customerTypePK = customerTypeCreditLimit.getCustomerTypePK(); // Not updated
            var currencyPK = customerTypeCreditLimit.getCurrencyPK(); // Not updated
            var creditLimit = customerTypeCreditLimitValue.getCreditLimit();
            var potentialCreditLimit = customerTypeCreditLimitValue.getPotentialCreditLimit();
            
            customerTypeCreditLimit = CustomerTypeCreditLimitFactory.getInstance().create(customerTypePK, currencyPK, creditLimit,
                    potentialCreditLimit, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(customerTypePK, EventTypes.MODIFY, customerTypeCreditLimit.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCustomerTypeCreditLimit(CustomerTypeCreditLimit customerTypeCreditLimit, BasePK deletedBy) {
        customerTypeCreditLimit.setThruTime(session.START_TIME_LONG);
        
        sendEvent(customerTypeCreditLimit.getCustomerTypePK(), EventTypes.MODIFY, customerTypeCreditLimit.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteCustomerTypeCreditLimitsByCustomerType(CustomerType customerType, BasePK deletedBy) {
        var customerTypeCreditLimits = getCustomerTypeCreditLimitsByCustomerTypeForUpdate(customerType);
        
        customerTypeCreditLimits.forEach((customerTypeCreditLimit) -> 
                deleteCustomerTypeCreditLimit(customerTypeCreditLimit, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Credit Limits
    // --------------------------------------------------------------------------------
    
    public PartyCreditLimit createPartyCreditLimit(Party party, Currency currency, Long creditLimit, Long potentialCreditLimit,
            BasePK createdBy) {
        var partyCreditLimit = PartyCreditLimitFactory.getInstance().create(party, currency, creditLimit,
                potentialCreditLimit, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyCreditLimit.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = PartyCreditLimitFactory.getInstance().prepareStatement(query);
            
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

            var ps = PartyCreditLimitFactory.getInstance().prepareStatement(query);
            
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
        return partyCreditLimitTransferCache.getPartyCreditLimitTransfer(userVisit, partyCreditLimit);
    }
    
    public List<PartyCreditLimitTransfer> getPartyCreditLimitTransfersByParty(UserVisit userVisit, Party party) {
        var partyCreditLimits = getPartyCreditLimitsByParty(party);
        List<PartyCreditLimitTransfer> partyCreditLimitTransfers = new ArrayList<>(partyCreditLimits.size());
        
        partyCreditLimits.forEach((partyCreditLimit) ->
                partyCreditLimitTransfers.add(partyCreditLimitTransferCache.getPartyCreditLimitTransfer(userVisit, partyCreditLimit))
        );
        
        return partyCreditLimitTransfers;
    }
    
    public void updatePartyCreditLimitFromValue(PartyCreditLimitValue partyCreditLimitValue, BasePK updatedBy) {
        if(partyCreditLimitValue.hasBeenModified()) {
            var partyCreditLimit = PartyCreditLimitFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyCreditLimitValue.getPrimaryKey());
            
            partyCreditLimit.setThruTime(session.START_TIME_LONG);
            partyCreditLimit.store();

            var partyPK = partyCreditLimit.getPartyPK(); // Not updated
            var currencyPK = partyCreditLimit.getCurrencyPK(); // Not updated
            var creditLimit = partyCreditLimitValue.getCreditLimit();
            var potentialCreditLimit = partyCreditLimitValue.getPotentialCreditLimit();
            
            partyCreditLimit = PartyCreditLimitFactory.getInstance().create(partyPK, currencyPK, creditLimit,
                    potentialCreditLimit, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyCreditLimit.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyCreditLimit(PartyCreditLimit partyCreditLimit, BasePK deletedBy) {
        partyCreditLimit.setThruTime(session.START_TIME_LONG);
        
        sendEvent(partyCreditLimit.getPartyPK(), EventTypes.MODIFY, partyCreditLimit.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyCreditLimitsByParty(Party party, BasePK deletedBy) {
        var partyCreditLimits = getPartyCreditLimitsByPartyForUpdate(party);
        
        partyCreditLimits.forEach((partyCreditLimit) -> 
                deletePartyCreditLimit(partyCreditLimit, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Terms
    // --------------------------------------------------------------------------------
    
    public PartyTerm createPartyTerm(Party party, Term term, Boolean taxable, BasePK createdBy) {
        var partyTerm = PartyTermFactory.getInstance().create(party, term, taxable, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyTerm.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
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

            var ps = PartyTermFactory.getInstance().prepareStatement(query);
            
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

            var ps = PartyTermFactory.getInstance().prepareStatement(query);
            
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
        var partyTerm = getPartyTerm(party);
        
        return partyTerm == null? null: partyTermTransferCache.getPartyTermTransfer(userVisit, partyTerm);
    }
    
    public PartyTermTransfer getPartyTermTransfer(UserVisit userVisit, PartyTerm partyTerm) {
        return partyTermTransferCache.getPartyTermTransfer(userVisit, partyTerm);
    }
    
    public void updatePartyTermFromValue(PartyTermValue partyTermValue, BasePK updatedBy) {
        if(partyTermValue.hasBeenModified()) {
            var partyTerm = PartyTermFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    partyTermValue.getPrimaryKey());
            
            partyTerm.setThruTime(session.START_TIME_LONG);
            partyTerm.store();

            var partyPK = partyTerm.getPartyPK(); // Not updated
            var termPK = partyTermValue.getTermPK();
            var taxable = partyTermValue.getTaxable();
            
            partyTerm = PartyTermFactory.getInstance().create(partyPK, termPK, taxable, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyTerm.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTerm(PartyTerm partyTerm, BasePK deletedBy) {
        partyTerm.setThruTime(session.START_TIME_LONG);
        
        sendEvent(partyTerm.getPartyPK(), EventTypes.MODIFY, partyTerm.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deletePartyTermByParty(Party party, BasePK deletedBy) {
        var partyTerm = getPartyTermForUpdate(party);
        
        if(partyTerm != null) {
            deletePartyTerm(partyTerm, deletedBy);
        }
    }
    
    public void deletePartyTermsByTerm(Term term, BasePK deletedBy) {
        var partyTerms = getPartyTermsByTermForUpdate(term);
        
        partyTerms.forEach((partyTerm) -> 
                deletePartyTerm(partyTerm, deletedBy)
        );
    }
    
}

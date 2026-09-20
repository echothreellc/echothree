// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.term.TermForeignKeys.CUSTOMER_TYPE_CREDIT_LIMITS_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.term.TermForeignKeys.PARTY_CREDIT_LIMITS_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.term.TermForeignKeys.PARTY_TERMS_PARTY_FK;
import static com.echothree.model.jooq.server.keys.term.TermForeignKeys.TERMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.term.TermForeignKeys.TERM_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.accounting.Currencies.Currencies;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.term.CustomerTypeCreditLimits.CustomerTypeCreditLimits;
import static com.echothree.model.jooq.server.tables.term.DateDrivenTerms.DateDrivenTerms;
import static com.echothree.model.jooq.server.tables.term.PartyCreditLimits.PartyCreditLimits;
import static com.echothree.model.jooq.server.tables.term.PartyTerms.PartyTerms;
import static com.echothree.model.jooq.server.tables.term.StandardTerms.StandardTerms;
import static com.echothree.model.jooq.server.tables.term.TermDescriptions.TermDescriptions;
import static com.echothree.model.jooq.server.tables.term.TermDetails.TermDetails;
import static com.echothree.model.jooq.server.tables.term.TermTypeDescriptions.TermTypeDescriptions;
import static com.echothree.model.jooq.server.tables.term.TermTypes.TermTypes;
import static com.echothree.model.jooq.server.tables.term.Terms.Terms;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class TermControl
        extends BaseModelControl {
    
    /** Creates a new instance of TermControl */
    protected TermControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Term Types
    // --------------------------------------------------------------------------------

    @Inject
    protected TermTypeFactory termTypeFactory;

    @Inject
    private TermTypeTransferCache termTypeTransferCache;

    public TermType createTermType(String termTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var termType = termTypeFactory.create(termTypeName, isDefault, sortOrder);

        sendEvent(termType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return termType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.TermType */
    public TermType getTermTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TermTypePK(entityInstance.getEntityUniqueId());

        return termTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public TermType getTermTypeByEntityInstance(EntityInstance entityInstance) {
        return getTermTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public TermType getTermTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTermTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTermTypes() {
        return session.getDslContext()
                .selectCount()
                .from(TermTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public TermType getTermTypeByName(String termTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TermTypes.fields())
                .from(TermTypes)
                .where(TermTypes.TERM_TYPE_NAME.eq(termTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return termTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public TermType getTermTypeByName(String termTypeName) {
        return getTermTypeByName(termTypeName, EntityPermission.READ_ONLY);
    }

    public TermType getTermTypeByNameForUpdate(String termTypeName) {
        return getTermTypeByName(termTypeName, EntityPermission.READ_WRITE);
    }

    public TermType getDefaultTermType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TermTypes.fields())
                .from(TermTypes)
                .where(TermTypes.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return termTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public TermType getDefaultTermType() {
        return getDefaultTermType(EntityPermission.READ_ONLY);
    }

    public TermType getDefaultTermTypeForUpdate() {
        return getDefaultTermType(EntityPermission.READ_WRITE);
    }

    private List<TermType> getTermTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TermTypes.fields())
                .from(TermTypes);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TermTypes.SORT_ORDER, TermTypes.TERM_TYPE_NAME),
                    TermTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return termTypeFactory.getEntitiesFromQuery(entityPermission, query);
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

    @Inject
    protected TermTypeDescriptionFactory termTypeDescriptionFactory;

    public TermTypeDescription createTermTypeDescription(TermType termType, Language language,
            String description, BasePK createdBy) {
        var termTypeDescription = termTypeDescriptionFactory.create(termType, language, description);

        sendEvent(termType.getPrimaryKey(), EventTypes.MODIFY, termTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return termTypeDescription;
    }

    public TermTypeDescription getTermTypeDescription(TermType termType, Language language) {
        var query = session.getDslContext()
                .select(TermTypeDescriptions.fields())
                .from(TermTypeDescriptions)
                .where(TermTypeDescriptions.TERM_TYPE.eq(termType.getPrimaryKey()),
                        TermTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return termTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected TermDetailFactory termDetailFactory;

    @Inject
    protected TermFactory termFactory;

    @Inject
    private TermTransferCache termTransferCache;

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

        var term = termFactory.create();
        var termDetail = termDetailFactory.create(term, termName, termType, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        term = termFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                term.getPrimaryKey());
        term.setActiveDetail(termDetail);
        term.setLastDetail(termDetail);
        term.store();
        
        sendEvent(term.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return term;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Term */
    public Term getTermByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TermPK(entityInstance.getEntityUniqueId());

        return termFactory.getEntityFromPK(entityPermission, pk);
    }

    public Term getTermByEntityInstance(EntityInstance entityInstance) {
        return getTermByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Term getTermByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTermByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTerms() {
        return session.getDslContext()
                .selectCount()
                .from(Terms)
                .join(TermDetails).onKey(TERMS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public Term getTermByName(String termName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Terms.fields())
                .from(Terms)
                .join(TermDetails).onKey(TERMS_ACTIVE_DETAIL_FK)
                .where(TermDetails.TERM_NAME.eq(termName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return termFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(Terms.fields())
                .from(Terms)
                .join(TermDetails).onKey(TERMS_ACTIVE_DETAIL_FK)
                .where(TermDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return termFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(Terms.fields())
                .from(Terms)
                .join(TermDetails).onKey(TERMS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TermDetails.SORT_ORDER, TermDetails.TERM_NAME),
                    TermFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return termFactory.getEntitiesFromQuery(entityPermission, query);
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
        var term = termFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, termDetailValue.getTermPK());
        var termDetail = term.getActiveDetailForUpdate();
        
        termDetail.setThruTime(session.getStartTime());
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
        
        termDetail = termDetailFactory.create(termPK, termName, termTypePK, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
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
        termDetail.setThruTime(session.getStartTime());
        term.setActiveDetail(null);
        term.store();

        var termTypeName = termDetail.getTermType().getTermTypeName();
        if(termTypeName.equals(com.echothree.model.control.term.common.TermTypes.STANDARD.name())) {
            deleteStandardTermByTerm(term, deletedBy);
        } else if(termTypeName.equals(com.echothree.model.control.term.common.TermTypes.DATE_DRIVEN.name())) {
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

    @Inject
    protected TermDescriptionFactory termDescriptionFactory;

    @Inject
    private TermDescriptionTransferCache termDescriptionTransferCache;

    public TermDescription createTermDescription(Term term, Language language, String description, BasePK createdBy) {
        var termDescription = termDescriptionFactory.create(term, language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(term.getPrimaryKey(), EventTypes.MODIFY, termDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return termDescription;
    }
    
    private TermDescription getTermDescription(Term term, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TermDescriptions.fields())
                .from(TermDescriptions)
                .where(TermDescriptions.TERM.eq(term.getPrimaryKey()),
                        TermDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        TermDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return termDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TermDescriptions.fields())
                    .from(TermDescriptions)
                    .join(Languages).onKey(TERM_DESCRIPTIONS_LANGUAGE_FK)
                    .where(TermDescriptions.TERM.eq(term.getPrimaryKey()),
                            TermDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TermDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TermDescriptions.fields())
                    .from(TermDescriptions)
                    .where(TermDescriptions.TERM.eq(term.getPrimaryKey()),
                            TermDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return termDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            var termDescription = termDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, termDescriptionValue.getPrimaryKey());
            
            termDescription.setThruTime(session.getStartTime());
            termDescription.store();

            var term = termDescription.getTerm();
            var language = termDescription.getLanguage();
            var description = termDescriptionValue.getDescription();
            
            termDescription = termDescriptionFactory.create(term, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(term.getPrimaryKey(), EventTypes.MODIFY, termDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteTermDescription(TermDescription termDescription, BasePK deletedBy) {
        termDescription.setThruTime(session.getStartTime());
        
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

    @Inject
    protected StandardTermFactory standardTermFactory;

    public StandardTerm createStandardTerm(Term term, Integer netDueDays, Integer discountPercentage, Integer discountDays,
            BasePK createdBy) {
        var standardTerm = standardTermFactory.create(term, netDueDays, discountPercentage,
                discountDays, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(term.getPrimaryKey(), EventTypes.MODIFY, standardTerm.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return standardTerm;
    }
    
    private StandardTerm getStandardTerm(Term term, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(StandardTerms.fields())
                .from(StandardTerms)
                .where(StandardTerms.TERM.eq(term.getPrimaryKey()),
                        StandardTerms.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return standardTermFactory.getEntityFromQuery(entityPermission, query);
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
            var standardTerm = standardTermFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    standardTermValue.getPrimaryKey());
            
            standardTerm.setThruTime(session.getStartTime());
            standardTerm.store();

            var termPK = standardTerm.getTermPK(); // Not updated
            var netDueDays = standardTermValue.getNetDueDays();
            var discountPercentage = standardTermValue.getDiscountPercentage();
            var discountDays = standardTermValue.getDiscountDays();
            
            standardTerm = standardTermFactory.create(termPK, netDueDays, discountPercentage, discountDays,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(termPK, EventTypes.MODIFY, standardTerm.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteStandardTerm(StandardTerm standardTerm, BasePK deletedBy) {
        standardTerm.setThruTime(session.getStartTime());
        
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

    @Inject
    protected DateDrivenTermFactory dateDrivenTermFactory;

    public DateDrivenTerm createDateDrivenTerm(Term term, Integer netDueDayOfMonth, Integer dueNextMonthDays,
            Integer discountPercentage, Integer discountBeforeDayOfMonth, BasePK createdBy) {
        var dateDrivenTerm = dateDrivenTermFactory.create(term, netDueDayOfMonth,
                dueNextMonthDays, discountPercentage, discountBeforeDayOfMonth, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(term.getPrimaryKey(), EventTypes.MODIFY, dateDrivenTerm.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return dateDrivenTerm;
    }
    
    private DateDrivenTerm getDateDrivenTerm(Term term, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DateDrivenTerms.fields())
                .from(DateDrivenTerms)
                .where(DateDrivenTerms.TERM.eq(term.getPrimaryKey()),
                        DateDrivenTerms.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return dateDrivenTermFactory.getEntityFromQuery(entityPermission, query);
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
            var dateDrivenTerm = dateDrivenTermFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    dateDrivenTermValue.getPrimaryKey());
            
            dateDrivenTerm.setThruTime(session.getStartTime());
            dateDrivenTerm.store();

            var termPK = dateDrivenTerm.getTermPK(); // Not updated
            var netDueDayOfMonth = dateDrivenTermValue.getNetDueDayOfMonth();
            var dueNextMonthDays = dateDrivenTermValue.getDueNextMonthDays();
            var discountPercentage = dateDrivenTermValue.getDiscountPercentage();
            var discountBeforeDayOfMonth = dateDrivenTermValue.getDiscountBeforeDayOfMonth();
            
            dateDrivenTerm = dateDrivenTermFactory.create(termPK, netDueDayOfMonth, dueNextMonthDays,
                    discountPercentage, discountBeforeDayOfMonth, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(termPK, EventTypes.MODIFY, dateDrivenTerm.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDateDrivenTerm(DateDrivenTerm dateDrivenTerm, BasePK deletedBy) {
        dateDrivenTerm.setThruTime(session.getStartTime());
        
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

    @Inject
    protected CustomerTypeCreditLimitFactory customerTypeCreditLimitFactory;

    @Inject
    private CustomerTypeCreditLimitTransferCache customerTypeCreditLimitTransferCache;

    public CustomerTypeCreditLimit createCustomerTypeCreditLimit(CustomerType customerType, Currency currency, Long creditLimit,
            Long potentialCreditLimit, BasePK createdBy) {
        var customerTypeCreditLimit = customerTypeCreditLimitFactory.create(customerType,
                currency, creditLimit, potentialCreditLimit, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(customerType.getPrimaryKey(), EventTypes.MODIFY, customerTypeCreditLimit.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return customerTypeCreditLimit;
    }

    public long countCustomerTypeCreditLimitsByCustomerType(final CustomerType customerType) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypeCreditLimits)
                .where(CustomerTypeCreditLimits.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeCreditLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCustomerTypeCreditLimitsByCurrency(final Currency currency) {
        return session.getDslContext()
                .selectCount()
                .from(CustomerTypeCreditLimits)
                .where(CustomerTypeCreditLimits.CURRENCY.eq(currency.getPrimaryKey()),
                        CustomerTypeCreditLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private CustomerTypeCreditLimit getCustomerTypeCreditLimit(CustomerType customerType, Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CustomerTypeCreditLimits.fields())
                .from(CustomerTypeCreditLimits)
                .where(CustomerTypeCreditLimits.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                        CustomerTypeCreditLimits.CURRENCY.eq(currency.getPrimaryKey()),
                        CustomerTypeCreditLimits.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return customerTypeCreditLimitFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CustomerTypeCreditLimits.fields())
                    .from(CustomerTypeCreditLimits)
                    .join(Currencies).onKey(CUSTOMER_TYPE_CREDIT_LIMITS_CURRENCY_FK)
                    .where(CustomerTypeCreditLimits.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypeCreditLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    CustomerTypeCreditLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CustomerTypeCreditLimits.fields())
                    .from(CustomerTypeCreditLimits)
                    .where(CustomerTypeCreditLimits.CUSTOMER_TYPE.eq(customerType.getPrimaryKey()),
                            CustomerTypeCreditLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return customerTypeCreditLimitFactory.getEntitiesFromQuery(entityPermission, query);
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

    public List<CustomerTypeCreditLimitTransfer> getCustomerTypeCreditLimitTransfers(UserVisit userVisit,
            Collection<CustomerTypeCreditLimit> customerTypeCreditLimits) {
        List<CustomerTypeCreditLimitTransfer> customerTypeCreditLimitTransfers = new ArrayList<>(customerTypeCreditLimits.size());

        customerTypeCreditLimits.forEach((customerTypeCreditLimit) ->
                customerTypeCreditLimitTransfers.add(customerTypeCreditLimitTransferCache.getCustomerTypeCreditLimitTransfer(userVisit, customerTypeCreditLimit))
        );

        return customerTypeCreditLimitTransfers;
    }

    public List<CustomerTypeCreditLimitTransfer> getCustomerTypeCreditLimitTransfersByCustomerType(UserVisit userVisit, CustomerType customerType) {
        return getCustomerTypeCreditLimitTransfers(userVisit, getCustomerTypeCreditLimitsByCustomerType(customerType));
    }
    
    public void updateCustomerTypeCreditLimitFromValue(CustomerTypeCreditLimitValue customerTypeCreditLimitValue, BasePK updatedBy) {
        if(customerTypeCreditLimitValue.hasBeenModified()) {
            var customerTypeCreditLimit = customerTypeCreditLimitFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     customerTypeCreditLimitValue.getPrimaryKey());
            
            customerTypeCreditLimit.setThruTime(session.getStartTime());
            customerTypeCreditLimit.store();

            var customerTypePK = customerTypeCreditLimit.getCustomerTypePK(); // Not updated
            var currencyPK = customerTypeCreditLimit.getCurrencyPK(); // Not updated
            var creditLimit = customerTypeCreditLimitValue.getCreditLimit();
            var potentialCreditLimit = customerTypeCreditLimitValue.getPotentialCreditLimit();
            
            customerTypeCreditLimit = customerTypeCreditLimitFactory.create(customerTypePK, currencyPK, creditLimit,
                    potentialCreditLimit, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(customerTypePK, EventTypes.MODIFY, customerTypeCreditLimit.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCustomerTypeCreditLimit(CustomerTypeCreditLimit customerTypeCreditLimit, BasePK deletedBy) {
        customerTypeCreditLimit.setThruTime(session.getStartTime());
        
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

    @Inject
    protected PartyCreditLimitFactory partyCreditLimitFactory;

    @Inject
    private PartyCreditLimitTransferCache partyCreditLimitTransferCache;

    public PartyCreditLimit createPartyCreditLimit(Party party, Currency currency, Long creditLimit, Long potentialCreditLimit,
            BasePK createdBy) {
        var partyCreditLimit = partyCreditLimitFactory.create(party, currency, creditLimit,
                potentialCreditLimit, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyCreditLimit.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyCreditLimit;
    }

    public long countPartyCreditLimitsByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyCreditLimits)
                .where(PartyCreditLimits.PARTY.eq(party.getPrimaryKey()),
                        PartyCreditLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyCreditLimitsByCurrency(final Currency currency) {
        return session.getDslContext()
                .selectCount()
                .from(PartyCreditLimits)
                .where(PartyCreditLimits.CURRENCY.eq(currency.getPrimaryKey()),
                        PartyCreditLimits.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyCreditLimit getPartyCreditLimit(Party party, Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyCreditLimits.fields())
                .from(PartyCreditLimits)
                .where(PartyCreditLimits.PARTY.eq(party.getPrimaryKey()),
                        PartyCreditLimits.CURRENCY.eq(currency.getPrimaryKey()),
                        PartyCreditLimits.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyCreditLimitFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyCreditLimits.fields())
                    .from(PartyCreditLimits)
                    .join(Currencies).onKey(PARTY_CREDIT_LIMITS_CURRENCY_FK)
                    .where(PartyCreditLimits.PARTY.eq(party.getPrimaryKey()),
                            PartyCreditLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    PartyCreditLimitFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyCreditLimits.fields())
                    .from(PartyCreditLimits)
                    .where(PartyCreditLimits.PARTY.eq(party.getPrimaryKey()),
                            PartyCreditLimits.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyCreditLimitFactory.getEntitiesFromQuery(entityPermission, query);
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

    public List<PartyCreditLimitTransfer> getPartyCreditLimitTransfers(UserVisit userVisit, Collection<PartyCreditLimit> partyCreditLimits) {
        List<PartyCreditLimitTransfer> partyCreditLimitTransfers = new ArrayList<>(partyCreditLimits.size());

        partyCreditLimits.forEach((partyCreditLimit) ->
                partyCreditLimitTransfers.add(partyCreditLimitTransferCache.getPartyCreditLimitTransfer(userVisit, partyCreditLimit))
        );

        return partyCreditLimitTransfers;
    }

    public List<PartyCreditLimitTransfer> getPartyCreditLimitTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyCreditLimitTransfers(userVisit, getPartyCreditLimitsByParty(party));
    }
    
    public void updatePartyCreditLimitFromValue(PartyCreditLimitValue partyCreditLimitValue, BasePK updatedBy) {
        if(partyCreditLimitValue.hasBeenModified()) {
            var partyCreditLimit = partyCreditLimitFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyCreditLimitValue.getPrimaryKey());
            
            partyCreditLimit.setThruTime(session.getStartTime());
            partyCreditLimit.store();

            var partyPK = partyCreditLimit.getPartyPK(); // Not updated
            var currencyPK = partyCreditLimit.getCurrencyPK(); // Not updated
            var creditLimit = partyCreditLimitValue.getCreditLimit();
            var potentialCreditLimit = partyCreditLimitValue.getPotentialCreditLimit();
            
            partyCreditLimit = partyCreditLimitFactory.create(partyPK, currencyPK, creditLimit,
                    potentialCreditLimit, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyCreditLimit.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyCreditLimit(PartyCreditLimit partyCreditLimit, BasePK deletedBy) {
        partyCreditLimit.setThruTime(session.getStartTime());
        
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

    @Inject
    protected PartyTermFactory partyTermFactory;

    @Inject
    private PartyTermTransferCache partyTermTransferCache;

    public PartyTerm createPartyTerm(Party party, Term term, Boolean taxable, BasePK createdBy) {
        var partyTerm = partyTermFactory.create(party, term, taxable, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyTerm.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyTerm;
    }

    public long countPartyTermByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTerms)
                .where(PartyTerms.PARTY.eq(party.getPrimaryKey()),
                        PartyTerms.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTermByTerm(final Term term) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTerms)
                .where(PartyTerms.TERM.eq(term.getPrimaryKey()),
                        PartyTerms.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyTerm getPartyTerm(Party party, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTerms.fields())
                .from(PartyTerms)
                .where(PartyTerms.PARTY.eq(party.getPrimaryKey()),
                        PartyTerms.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTermFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTerms.fields())
                    .from(PartyTerms)
                    .join(Parties).onKey(PARTY_TERMS_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(PartyTerms.TERM.eq(term.getPrimaryKey()),
                            PartyTerms.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyDetails.PARTY_NAME),
                    PartyTermFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTerms.fields())
                    .from(PartyTerms)
                    .where(PartyTerms.TERM.eq(term.getPrimaryKey()),
                            PartyTerms.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyTermFactory.getEntitiesFromQuery(entityPermission, query);
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
            var partyTerm = partyTermFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    partyTermValue.getPrimaryKey());
            
            partyTerm.setThruTime(session.getStartTime());
            partyTerm.store();

            var partyPK = partyTerm.getPartyPK(); // Not updated
            var termPK = partyTermValue.getTermPK();
            var taxable = partyTermValue.getTaxable();
            
            partyTerm = partyTermFactory.create(partyPK, termPK, taxable, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(partyPK, EventTypes.MODIFY, partyTerm.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deletePartyTerm(PartyTerm partyTerm, BasePK deletedBy) {
        partyTerm.setThruTime(session.getStartTime());
        
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

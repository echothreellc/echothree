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

package com.echothree.model.control.search.server.search;

import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.search.server.entity.CachedExecutedSearch;
import com.echothree.model.data.search.server.entity.CachedSearch;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.value.CachedExecutedSearchResultValue;
import com.echothree.model.data.search.server.value.SearchResultValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.string.StringUtils;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import com.echothree.util.server.persistence.ThreadSession;
import static java.lang.Math.toIntExact;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.codec.language.Soundex;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

public abstract class BaseSearchEvaluator
        extends BaseEvaluator {
    
    private Soundex soundex;
    
    protected Session session;
    protected SearchType searchType;
    protected SearchSortOrder searchSortOrder;
    protected SearchSortDirection searchSortDirection;
    protected SearchUseType searchUseType;
    
    private Party party;
    private PartyPK partyPK;
    private Boolean partyVerified;
    
    private void init(SearchType searchType, SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection, SearchUseType searchUseType) {
        var userSession = getUserControl().getUserSessionByUserVisit(userVisit);
        
        this.session = ThreadSession.currentSession();
        this.searchType = searchType;
        this.searchSortOrder = searchSortOrder;
        this.searchSortDirection = searchSortDirection;
        this.searchUseType = searchUseType;

        if(userSession != null) {
            party = userSession.getParty();
            partyPK = party == null ? null : party.getPrimaryKey(); // For convenience.
            partyVerified = userSession.getIdentityVerifiedTime() != null;
        }
    }
    
    protected BaseSearchEvaluator(UserVisit userVisit, SearchDefaultOperator searchDefaultOperator, SearchType searchType, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection, SearchUseType searchUseType, String componentVendorName, String entityTypeName, String indexTypeName,
            Language language, String indexName) {
        super(userVisit, searchDefaultOperator, componentVendorName, entityTypeName, indexTypeName, language, indexName);
        
        init(searchType, searchSortOrder, searchSortDirection, searchUseType);
    }
    
    protected String getSearchSortOrderName() {
        return searchSortOrder.getLastDetail().getSearchSortOrderName();
    }
    
    protected void removeUserVisitSearch() {
        var userVisitSearch = searchControl.getUserVisitSearchForUpdate(userVisit, searchType);
        
        if(userVisitSearch != null) {
            searchControl.removeUserVisitSearch(userVisitSearch);
        }
    }

    protected EntityInstancePKHolder getEntityInstancePKHolderFromQuery(PreparedStatement ps) {
        return convertPKListToEntityInstancePKHolder(EntityInstanceFactory.getInstance().getPKsFromQueryAsList(ps));
    }


    protected EntityInstancePKHolder getEntityInstancePKHolderFromQuery(PreparedStatement ps, final Object... params) {
        return convertPKListToEntityInstancePKHolder(EntityInstanceFactory.getInstance().getPKsFromQueryAsList(ps, params));
    }

    private EntityInstancePKHolder convertPKListToEntityInstancePKHolder(List<EntityInstancePK> entityInstancePKs) {
        var entityInstancePKHolder = new EntityInstancePKHolder(entityInstancePKs.size());
        var i = 0;

        for(var entityInstancePK : entityInstancePKs) {
            entityInstancePKHolder.add(entityInstancePK, i++);
        }

        return entityInstancePKHolder;
    }

    /** Counts the number of search parameters. */
    protected int countParameters() {
        return (createdSince == null ? 0 : 1) + (modifiedSince == null ? 0 : 1) + (q == null ? 0 : 1);
    }

    /** Determines if the result of the search may be cached. */
    protected boolean isResultCachable() {
        return false;
    }
    
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = null;
        
        if(createdSince != null && (resultSet == null || resultSet.size() > 0)) {
            var entityInstancePKHolder = getEntityInstancePKHolderByCreatedTime(createdSince);

            if(resultSet == null) {
                resultSet = entityInstancePKHolder;
            } else {
                resultSet.retainAll(entityInstancePKHolder);
            }
        }

        if(modifiedSince != null && (resultSet == null || resultSet.size() > 0)) {
            var entityInstancePKHolder = getEntityInstancePKHolderByModifiedTime(modifiedSince);

            if(resultSet == null) {
                resultSet = entityInstancePKHolder;
            } else {
                resultSet.retainAll(entityInstancePKHolder);
            }
        }
        
        return resultSet;
    }
    
    protected Long getSize(UserVisitSearch userVisitSearch) {
        Long size = null;
        
        if(userVisitSearch != null) {
            var search = userVisitSearch.getSearch();
            var cachedSearch = search.getCachedSearch();
            
            if(cachedSearch == null) {
                size = searchControl.countSearchResults(search);
            } else {
                var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);
                
                if(cachedExecutedSearch != null) {
                    size = searchControl.countCachedExecutedSearchResults(cachedExecutedSearch);
                }
            }
        }
        
        return size;
    }
    
    protected Long createUserVisitSearchResults(EntityInstancePKHolder entityInstancePKHolder) {
        Long size;
        var search = searchControl.createSearch(party, partyVerified, searchType, session.getStartTimeLong(), searchUseType, null, partyPK);

        if(entityInstancePKHolder == null) {
            size = 0L;
        } else {
            var searchPK = search.getPrimaryKey();
            var entityInstancePKs = entityInstancePKHolder.entityInstancePKs;

            size = (long)entityInstancePKs.size();

            Collection<SearchResultValue> searchResults = new ArrayList<>(toIntExact(size));
            for(var entry : entityInstancePKs.entrySet()) {
                var sortOrder = entry.getValue();

                searchResults.add(new SearchResultValue(searchPK, entry.getKey(), sortOrder == null ? 0 : sortOrder));
            }

            searchControl.createSearchResults(searchResults);
        }
        
        try {
            searchControl.createUserVisitSearch(userVisit, searchType, search);
        } catch(PersistenceDatabaseException pde) {
            if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                var userVisitSearch = searchControl.getUserVisitSearch(userVisit, searchType);

                searchControl.removeSearch(search);

                if(userVisitSearch != null) {
                    size = getSize(userVisitSearch);
                    pde = null;
                }
            }

            if(pde != null) {
                throw pde;
            }
        }

        return size;
    }
    
    protected Long executeCachableSearch(final ExecutionErrorAccumulator eea) {
        Long size;
        final var parsedQuery = StringUtils.getInstance().trimToNull(query.toString());
        UserVisitSearch userVisitSearch = null;
        
        // If the query contained nothing but stopwords, it may be reduced down to an empty
        // String when it's pulled back out of the Query. If that's the case, create an empty
        // set of results.
        if(parsedQuery == null) {
            size = createUserVisitSearchResults(null);
        } else {
            final var sha1Utils = Sha1Utils.getInstance();
            final var querySha1Hash = sha1Utils.hash(q);
            final var parsedQuerySha1Hash = sha1Utils.hash(parsedQuery);

            var cachedSearch = searchControl.getCachedSearch(index, querySha1Hash, parsedQuerySha1Hash, searchDefaultOperator, searchSortOrder,
                    searchSortDirection);

            // There are three possible paths here:
            if(cachedSearch == null) {
                // 1) No existing cached search.
                final var entityInstancePKHolder = executeSearch(eea);

                if(!eea.hasExecutionErrors()) {
                    try {
                        cachedSearch = searchControl.createCachedSearch(index, querySha1Hash, q, parsedQuerySha1Hash, parsedQuery, searchDefaultOperator,
                                searchSortOrder, searchSortDirection, partyPK);
                        addCachedSearchIndexFields(cachedSearch);

                        var cachedSearchStatus = searchControl.getCachedSearchStatusForUpdate(cachedSearch);

                        createCachedExecutedSearchResults(cachedSearch, entityInstancePKHolder);
                        cachedSearchStatus.setIsConsistent(true);
                    } catch(PersistenceDatabaseException pde) {
                        // The only code in the try that should cause this error is the call to createCachedSearch(...). If it fails,
                        // then check to see if someone else already performed that search while we were also performing it. If they
                        // did, use their results instead (discarding ours).
                        if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                            cachedSearch = searchControl.getCachedSearch(index, querySha1Hash, parsedQuerySha1Hash, searchDefaultOperator, searchSortOrder,
                                searchSortDirection);

                            if(cachedSearch != null) {
                                pde = null;
                            }
                        }

                        if(pde != null) {
                            throw pde;
                        }
                    }
                }
            } else {
                // Pass 0: get CachedSearchStatus, check to see if it's consistent. If it is, we're done.
                // Pass 1: get CachedSearchStatus as an updatable entity. Check to see if it's consistent (someone else executed the search),
                //         if it is, use it. If it isn't re-execute the search.
                for(var i = 0; i < 2 ; i++) {
                    var cachedSearchStatus = i == 0 ? searchControl.getCachedSearchStatus(cachedSearch) : searchControl.getCachedSearchStatusForUpdate(cachedSearch);

                    if(cachedSearchStatus.getIsConsistent()) {
                        // 2) An existing cached search that's consistent with the indexes.
                        break;
                    } else if(i == 1) {
                        // 3) An existing cached search that is not consistent with the indexes, and needs to be executed again.
                        final var entityInstancePKHolder = executeSearch(eea);
                        final var hasExecutionErrors = eea.hasExecutionErrors();

                        // If re-executing the search fails, we'll reuse the existing inconsistent results.
                        var cachedExecutedSearch = hasExecutionErrors
                                ? searchControl.getCachedExecutedSearch(cachedSearch)
                                : searchControl.getCachedExecutedSearchForUpdate(cachedSearch);

                        if(!hasExecutionErrors) {
                            // Get what the current CachedExecutedSearch's results are.
                            var cachedExecutedSearchResults = searchControl.getCachedExecutedSearchResultsByCachedExecutedSearch(cachedExecutedSearch);
                            var entityInstancePKs = entityInstancePKHolder.entityInstancePKs;
                            var resultsAreDifferent = false;

                            // If the sizes are different, then the results have definately changed.
                            if(entityInstancePKs.size() == cachedExecutedSearchResults.size()) {
                                // Otherwise, get the existing results and compare them to the new ones. If there's any that aren't in there,
                                // or any whose sort order is different, then the results have again, definately changed.
                                for(var cachedExecutedSearchResult : cachedExecutedSearchResults) {
                                    var sortOrder = entityInstancePKs.get(cachedExecutedSearchResult.getEntityInstancePK());

                                    if(sortOrder == null) {
                                        resultsAreDifferent = true;
                                        break;
                                    } else {
                                        if(!sortOrder.equals(cachedExecutedSearchResult.getSortOrder())) {
                                            resultsAreDifferent = true;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                resultsAreDifferent = true;
                            }

                            if(resultsAreDifferent) {
                                // Delete the inconsistent cachedExecutedSearch.
                                searchControl.deleteCachedExecutedSearch(cachedExecutedSearch, partyPK);

                                // Create a new CachedExecutedSearch and mark the cachedSearch as consistent.
                                createCachedExecutedSearchResults(cachedSearch, entityInstancePKHolder);
                            }

                            cachedSearchStatus.setIsConsistent(true);
                        }
                    }
                }
            }

            if(cachedSearch != null) {
                var search = searchControl.createSearch(party, partyVerified, searchType, session.getStartTimeLong(), searchUseType, cachedSearch, partyPK);

                try {
                    userVisitSearch = searchControl.createUserVisitSearch(userVisit, searchType, search);
                } catch(PersistenceDatabaseException pde) {
                    if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                        userVisitSearch = searchControl.getUserVisitSearch(userVisit, searchType);

                        searchControl.removeSearch(search);

                        if(userVisitSearch != null) {
                            pde = null;
                        }
                    }

                    if(pde != null) {
                        throw pde;
                    }
                }
            }
            
            size = getSize(userVisitSearch);
        }
        
        return size;
    }
    
    protected CachedExecutedSearch createCachedExecutedSearchResults(CachedSearch cachedSearch, EntityInstancePKHolder entityInstancePKHolder) {
        var cachedExecutedSearch = searchControl.createCachedExecutedSearch(cachedSearch, partyPK);
        var cachedExecutedSearchPK = cachedExecutedSearch.getPrimaryKey();
        var entityInstancePKs = entityInstancePKHolder.entityInstancePKs;
        
        Collection<CachedExecutedSearchResultValue> cachedExecutedSearchResults = new ArrayList<>(entityInstancePKs.size());
        for(var entry : entityInstancePKs.entrySet()) {
            var sortOrder = entry.getValue();

            cachedExecutedSearchResults.add(new CachedExecutedSearchResultValue(cachedExecutedSearchPK, entry.getKey(), sortOrder == null ? 0 : sortOrder));
        }

        searchControl.createCachedExecutedSearchResults(cachedExecutedSearchResults);
        
        return cachedExecutedSearch;
    }
    
    public Long execute(final ExecutionErrorAccumulator eea) {
        Long size = null;

        // Remove any previous search by this SearchType and UserVisit.
        removeUserVisitSearch();
        
        // Attempt to parse the query if we've been given one.
        parseQuery(eea, field, fields);
        
        if(!eea.hasExecutionErrors()) {
            if(isResultCachable() && allFieldsKnown()) {
                size = executeCachableSearch(eea);
            } else {
                final var entityInstancePKHolder = executeSearch(eea);

                if(!eea.hasExecutionErrors()) {
                    size = createUserVisitSearchResults(entityInstancePKHolder);
                }
            }
        }
        
        return size;
    }
    
    protected Soundex getSoundex() {
        if(soundex == null) {
            soundex = new Soundex();
        }
        
        return soundex;
    }
    
    // Common searches for createdSince and modifiedSince values.
    private Long createdSince = null;
    private Long modifiedSince = null;
    
    public EntityInstancePKHolder getEntityInstancePKHolderByCreatedTime(Long createdTime) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, entitytimes "
                + "WHERE eni_ent_entitytypeid = ? AND eni_entityinstanceid = etim_eni_entityinstanceid AND etim_createdtime >= ?"),
                entityType, createdTime);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByModifiedTime(Long modifiedTime) {
        return getEntityInstancePKHolderFromQuery(EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ "
                + "FROM entityinstances, entitytimes "
                + "WHERE eni_ent_entitytypeid = ? AND eni_entityinstanceid = etim_eni_entityinstanceid AND etim_modifiedtime >= ?"),
                entityType, modifiedTime);
    }

    /**
     * Returns the createdSince.
     * @return the createdSince
     */
    public Long getCreatedSince() {
        return createdSince;
    }

    /**
     * Sets the createdSince.
     * @param createdSince the createdSince to set
     */
    public void setCreatedSince(Long createdSince) {
        this.createdSince = createdSince;
    }

    /**
     * Returns the modifiedSince.
     * @return the modifiedSince
     */
    public Long getModifiedSince() {
        return modifiedSince;
    }

    /**
     * Sets the modifiedSince.
     * @param modifiedSince the modifiedSince to set
     */
    public void setModifiedSince(Long modifiedSince) {
        this.modifiedSince = modifiedSince;
    }
    
    protected SortField[] getSortFields(final String searchSortOrderName) {
        return null;
    }
    
    private Sort getSort() {
        var searchSortOrderName = getSearchSortOrderName();
        var sortFields = getSortFields(searchSortOrderName);
        
        return sortFields == null || sortFields.length == 0 ? null : new Sort(sortFields);
    }
    
    private void addCachedSearchIndexFields(final CachedSearch cachedSearch) {
        var indexType = index.getLastDetail().getIndexType();
        var fieldsToCheck = fields == null ? new String[] { field } : fields;
        
        for(var fieldToCheck : fieldsToCheck) {
            searchControl.createCachedSearchIndexField(cachedSearch, getIndexControl().getIndexFieldByName(indexType, fieldToCheck), partyPK);
        }
    }
    
    private boolean allFieldsKnown() {
        var indexType = index.getLastDetail().getIndexType();
        var result = true;
        var fieldsToCheck = fields == null ? new String[] { field } : fields;
        
        for(var fieldToCheck : fieldsToCheck) {
            if(getIndexControl().getIndexFieldByName(indexType, fieldToCheck) == null) {
                result = false;
                break;
            }
        }
        
        return result;
    }
    
    protected EntityInstancePKHolder executeQuery(final ExecutionErrorAccumulator eea) {
        return new IndexQuery(eea, getIndexControl(), index, getSort(), query).execute();
    }

}

// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.forum.server.search;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.analysis.ForumMessageAnalyzer;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.server.search.BaseSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.core.server.factory.EntityInstanceFactory;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumMessageType;
import com.echothree.model.data.forum.server.entity.ForumMessageTypePartType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.SortField;

public class ForumMessageSearchEvaluator
        extends BaseSearchEvaluator {
    
    private Forum forum;
    private ForumMessageType forumMessageType;
    private boolean includeFutureForumThreads;
    
    /** Creates a new instance of ForumMessageSearchEvaluator */
    public ForumMessageSearchEvaluator(UserVisit userVisit, Language language, SearchType searchType, SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection, SearchUseType searchUseType, Forum forum, ForumMessageType forumMessageType) {
        super(userVisit, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, searchUseType, ComponentVendors.ECHOTHREE.name(),
                EntityTypes.ForumMessage.name(), IndexConstants.IndexType_FORUM_MESSAGE, language, null);
        
        this.forum = forum;
        this.forumMessageType = forumMessageType;
        
        var forumControl = Session.getModelController(ForumControl.class);
        ForumMessageTypePartType indexDefaultForumMessageTypePartType = forumControl.getIndexDefaultForumMessageTypePartType(forumMessageType);

        setField(indexDefaultForumMessageTypePartType.getForumMessagePartType().getForumMessagePartTypeName());
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByForum(Forum forum) {
        EntityInstancePKHolder entityInstancePKHolder;
        PreparedStatement ps = EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ " +
                "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, forummessages, forummessagedetails, forumforumthreads " +
                "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid " +
                "AND frmmsgdt_frmthrd_forumthreadid = frmfrmthrd_frmthrd_forumthreadid AND frmfrmthrd_thrutime = ? " +
                "AND frmfrmthrd_frm_forumid = ? " +
                "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                "AND ent_activedetailid = entdt_entitytypedetailid " +
                "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                "AND entdt_entitytypename = ? " +
                "AND ent_entitytypeid = eni_ent_entitytypeid AND frmmsg_forummessageid = eni_entityuniqueid " +
                (includeFutureForumThreads ? "" : "AND frmmsgdt_postedtime <= ?"));
        
        if(includeFutureForumThreads) {
            entityInstancePKHolder = getEntityInstancePKHolderFromQuery(ps,
                    Session.MAX_TIME, forum, ComponentVendors.ECHOTHREE.name(), EntityTypes.ForumMessage.name());
        } else {
            entityInstancePKHolder = getEntityInstancePKHolderFromQuery(ps,
                    Session.MAX_TIME, forum, ComponentVendors.ECHOTHREE.name(), EntityTypes.ForumMessage.name(), session.START_TIME);
        }

        return entityInstancePKHolder;
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByForumMessageType(ForumMessageType forumMessageType) {
        EntityInstancePKHolder entityInstancePKHolder;
        PreparedStatement ps = EntityInstanceFactory.getInstance().prepareStatement(
                "SELECT _PK_ " +
                "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, forummessages, forummessagedetails " +
                "WHERE frmmsg_activedetailid = frmmsgdt_forummessagedetailid " +
                "AND frmmsgdt_frmmsgtyp_forummessagetypeid = ? " +
                "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                "AND ent_activedetailid = entdt_entitytypedetailid " +
                "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                "AND entdt_entitytypename = ? " +
                "AND ent_entitytypeid = eni_ent_entitytypeid AND frmmsg_forummessageid = eni_entityuniqueid " +
                (includeFutureForumThreads ? "" : "AND frmmsgdt_postedtime <= ?"));

        if(includeFutureForumThreads) {
            entityInstancePKHolder = getEntityInstancePKHolderFromQuery(ps,
                    forumMessageType, ComponentVendors.ECHOTHREE.name(), EntityTypes.ForumMessage.name());
        } else {
            entityInstancePKHolder = getEntityInstancePKHolderFromQuery(ps,
                    forumMessageType, ComponentVendors.ECHOTHREE.name(), EntityTypes.ForumMessage.name(),  session.START_TIME);
        }

        return entityInstancePKHolder;
    }

    /**
     * Returns the includeFutureForumThreads.
     * @return the includeFutureForumThreads
     */
    public boolean isIncludeFutureForumThreads() {
        return includeFutureForumThreads;
    }

    /**
     * Sets the includeFutureForumThreads.
     * @param includeFutureForumThreads the includeFutureForumThreads to set
     */
    public void setIncludeFutureForumThreads(boolean includeFutureForumThreads) {
        this.includeFutureForumThreads = includeFutureForumThreads;
    }

    @Override
    public SortField[] getSortFields(String searchSortOrderName) {
        SortField[] sortFields = null;
        boolean reverse = searchSortDirection.getLastDetail().getSearchSortDirectionName().equals(SearchConstants.SearchSortDirection_DESCENDING);
        
        if(searchSortOrderName.equals(SearchConstants.SearchSortOrder_SCORE)) {
            sortFields = new SortField[]{
                new SortField(null, SortField.Type.SCORE, reverse),
                new SortField(ForumConstants.ForumMessagePartType_TITLE + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse)
            };
        } else if(searchSortOrderName.equals(SearchConstants.SearchSortOrder_TITLE)) {
            sortFields = new SortField[]{new SortField(ForumConstants.ForumMessagePartType_TITLE + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse)};
        } else if(searchSortOrderName.equals(SearchConstants.SearchSortOrder_POSTED_TIME)) {
            sortFields = new SortField[]{new SortField(IndexConstants.IndexField_PostedTime, SortField.Type.LONG, reverse)};
        } else if(searchSortOrderName.equals(SearchConstants.SearchSortOrder_CREATED_TIME)) {
            sortFields = new SortField[]{new SortField(IndexConstants.IndexField_CreatedTime, SortField.Type.LONG, reverse)};
        } else if(searchSortOrderName.equals(SearchConstants.SearchSortOrder_MODIFIED_TIME)) {
            sortFields = new SortField[]{new SortField(IndexConstants.IndexField_ModifiedTime, SortField.Type.LONG, reverse)};
        }
        
        return sortFields;
    }
    
    @Override
    public Analyzer getAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        return new ForumMessageAnalyzer(eea, language, entityType);
    }
    
    private static Set<String> dateTimeFields;
    
    static {
        Set<String> set = new HashSet<>(1);

        set.add(IndexConstants.IndexField_PostedTime);
        
        dateTimeFields = Collections.unmodifiableSet(set);
    }
    
    @Override
    protected Set<String> getDateTimeFields() {
        return dateTimeFields;
    }

    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = super.executeSearch(eea);
        
        if(resultSet == null || resultSet.size() > 0) {
            if(q != null) {
                if(resultSet == null || resultSet.size() > 0) {
                    EntityInstancePKHolder entityInstancePKHolder = executeQuery(eea);

                    if(resultSet == null) {
                        resultSet = entityInstancePKHolder;
                    } else {
                        resultSet.retainAll(entityInstancePKHolder);
                    }
                }
            }
        }

        if(resultSet == null || resultSet.size() > 0) {
            if(forum != null) {
                EntityInstancePKHolder entityInstancePKHolder = getEntityInstancePKHolderByForum(forum);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
        }

        if(resultSet == null || resultSet.size() > 0) {
            if(forumMessageType != null) {
                EntityInstancePKHolder entityInstancePKHolder = getEntityInstancePKHolderByForumMessageType(forumMessageType);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
        }

        return resultSet;
    }

}

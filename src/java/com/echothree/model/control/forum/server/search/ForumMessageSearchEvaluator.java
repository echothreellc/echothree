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

package com.echothree.model.control.forum.server.search;

import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.forum.ForumForeignKeys.FORUM_MESSAGES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.forum.ForumForumThreads.ForumForumThreads;
import static com.echothree.model.jooq.server.tables.forum.ForumMessageDetails.ForumMessageDetails;
import static com.echothree.model.jooq.server.tables.forum.ForumMessages.ForumMessages;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.analyzer.ForumMessageAnalyzer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFieldVariations;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.common.IndexTypes;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.search.common.SearchSortDirections;
import com.echothree.model.control.search.common.SearchSortOrders;
import com.echothree.model.control.search.server.search.BaseSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumMessageType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import org.apache.lucene.search.SortField;

public class ForumMessageSearchEvaluator
        extends BaseSearchEvaluator {
    
    private Forum forum;
    private ForumMessageType forumMessageType;
    private boolean includeFutureForumThreads;
    
    /** Creates a new instance of ForumMessageSearchEvaluator */
    public ForumMessageSearchEvaluator(UserVisit userVisit, Language language, SearchType searchType, SearchDefaultOperator searchDefaultOperator, SearchSortOrder searchSortOrder,
            SearchSortDirection searchSortDirection, SearchUseType searchUseType, Forum forum, ForumMessageType forumMessageType) {
        super(userVisit, searchDefaultOperator, searchType, searchSortOrder, searchSortDirection, searchUseType, com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name(),
                com.echothree.model.control.core.common.EntityTypes.ForumMessage.name(), IndexTypes.FORUM_MESSAGE.name(), language, null);
        
        this.forum = forum;
        this.forumMessageType = forumMessageType;
        
        var forumControl = Session.getModelController(ForumControl.class);
        var indexDefaultForumMessageTypePartType = forumControl.getIndexDefaultForumMessageTypePartType(forumMessageType);

        setField(indexDefaultForumMessageTypePartType.getForumMessagePartType().getForumMessagePartTypeName());
    }
    
    public EntityInstancePKHolder getEntityInstancePKHolderByForum(Forum forum) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                .join(ForumMessages).on(ForumMessages.FORUM_MESSAGE.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(ForumMessageDetails).onKey(FORUM_MESSAGES_ACTIVE_DETAIL_FK)
                .join(ForumForumThreads).on(ForumMessageDetails.FORUM_THREAD.eq(ForumForumThreads.FORUM_THREAD))
                .where(ForumForumThreads.THRU_TIME.eq(Session.MAX_TIME),
                        ForumForumThreads.FORUM.eq(forum.getPrimaryKey()),
                        ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                        EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.ForumMessage.name()));

        if(!includeFutureForumThreads) {
            query = query.and(ForumMessageDetails.POSTED_TIME.le(session.getStartTime()));
        }

        return getEntityInstancePKHolderFromQuery(query);
    }

    public EntityInstancePKHolder getEntityInstancePKHolderByForumMessageType(ForumMessageType forumMessageType) {
        var query = session.getDslContext()
                .select(EntityInstances.ENTITY_INSTANCE)
                .from(EntityInstances)
                .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                .join(ForumMessages).on(ForumMessages.FORUM_MESSAGE.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                .join(ForumMessageDetails).onKey(FORUM_MESSAGES_ACTIVE_DETAIL_FK)
                .where(ForumMessageDetails.FORUM_MESSAGE_TYPE.eq(forumMessageType.getPrimaryKey()),
                        ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                        EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.ForumMessage.name()));

        if(!includeFutureForumThreads) {
            query = query.and(ForumMessageDetails.POSTED_TIME.le(session.getStartTime()));
        }

        return getEntityInstancePKHolderFromQuery(query);
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
        var reverse = searchSortDirection.getLastDetail().getSearchSortDirectionName().equals(SearchSortDirections.DESCENDING.name());
        
        if(searchSortOrderName.equals(SearchSortOrders.SCORE.name())) {
            sortFields = new SortField[]{
                new SortField(null, SortField.Type.SCORE, reverse),
                new SortField(ForumConstants.ForumMessagePartType_TITLE + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(), SortField.Type.STRING, reverse)
            };
        } else if(searchSortOrderName.equals(SearchSortOrders.TITLE.name())) {
            sortFields = new SortField[]{new SortField(ForumConstants.ForumMessagePartType_TITLE + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(), SortField.Type.STRING, reverse)};
        } else if(searchSortOrderName.equals(SearchSortOrders.POSTED_TIME.name())) {
            sortFields = new SortField[]{new SortField(IndexFields.postedTime.name(), SortField.Type.LONG, reverse)};
        } else if(searchSortOrderName.equals(SearchSortOrders.CREATED_TIME.name())) {
            sortFields = new SortField[]{new SortField(IndexFields.createdTime.name(), SortField.Type.LONG, reverse)};
        } else if(searchSortOrderName.equals(SearchSortOrders.MODIFIED_TIME.name())) {
            sortFields = new SortField[]{new SortField(IndexFields.modifiedTime.name(), SortField.Type.LONG, reverse)};
        }
        
        return sortFields;
    }
    
    @Override
    public BasicAnalyzer getAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        return new ForumMessageAnalyzer(eea, language, entityType);
    }
    
    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        var resultSet = super.executeSearch(eea);
        
        if(resultSet == null || resultSet.size() > 0) {
            if(q != null) {
                if(resultSet == null || resultSet.size() > 0) {
                    var entityInstancePKHolder = executeQuery(eea);

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
                var entityInstancePKHolder = getEntityInstancePKHolderByForum(forum);

                if(resultSet == null) {
                    resultSet = entityInstancePKHolder;
                } else {
                    resultSet.retainAll(entityInstancePKHolder);
                }
            }
        }

        if(resultSet == null || resultSet.size() > 0) {
            if(forumMessageType != null) {
                var entityInstancePKHolder = getEntityInstancePKHolderByForumMessageType(forumMessageType);

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

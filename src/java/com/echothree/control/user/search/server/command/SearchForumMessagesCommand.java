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

package com.echothree.control.user.search.server.command;

import com.echothree.control.user.search.common.form.SearchForumMessagesForm;
import com.echothree.control.user.search.common.result.SearchForumMessagesResult;
import com.echothree.control.user.search.common.result.SearchResultFactory;
import com.echothree.model.control.forum.common.ForumConstants;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.forum.server.logic.ForumLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.forum.server.search.ForumMessageSearchEvaluator;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.persistence.Session;
import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SearchForumMessagesCommand
        extends BaseSearchCommand<SearchForumMessagesForm, SearchForumMessagesResult> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SearchDefaultOperatorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SearchSortDirectionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SearchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SearchSortOrderName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ForumName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ForumMessageType", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IncludeFutureForumThreads", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Q", FieldType.STRING, false, null, null),
                new FieldDefinition("CreatedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ModifiedSince", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("Fields", FieldType.STRING, false, null, null),
                new FieldDefinition("RememberPreferences", FieldType.BOOLEAN, false, null, null),
                new FieldDefinition("SearchUseTypeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of SearchForumMessagesCommand */
    public SearchForumMessagesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SearchResultFactory.getSearchForumMessagesResult();
        var searchLogic = SearchLogic.getInstance();
        var searchKind = searchLogic.getSearchKindByName(this, SearchKinds.FORUM_MESSAGE.name());

        if(!hasExecutionErrors()) {
            var searchTypeName = form.getSearchTypeName();
            var searchType = searchLogic.getSearchTypeByName(this, searchKind, searchTypeName);

            if(!hasExecutionErrors()) {
                var languageIsoName = form.getLanguageIsoName();
                var language = languageIsoName == null ? null : LanguageLogic.getInstance().getLanguageByName(this, languageIsoName);
                
                if(!hasExecutionErrors()) {
                    var searchControl = Session.getModelController(SearchControl.class);
                    var partySearchTypePreference = getPartySearchTypePreference(searchControl, searchType);
                    var partySearchTypePreferenceDetail = partySearchTypePreference == null ? null : partySearchTypePreference.getLastDetail();
                    boolean rememberPreferences = Boolean.valueOf(form.getRememberPreferences());
                    var searchDefaultOperatorName = form.getSearchDefaultOperatorName();
                    var searchDefaultOperator = searchDefaultOperatorName == null
                            ? getDefaultSearchDefaultOperator(searchLogic, rememberPreferences, partySearchTypePreferenceDetail)
                            : searchLogic.getSearchDefaultOperatorByName(this, searchDefaultOperatorName);

                    if(!hasExecutionErrors()) {
                        var searchSortOrderName = form.getSearchSortOrderName();
                        var searchSortOrder = searchSortOrderName == null
                                ? getDefaultSearchSortOrder(searchLogic, rememberPreferences, searchKind, partySearchTypePreferenceDetail)
                                : searchLogic.getSearchSortOrderByName(this, searchKind, searchSortOrderName);

                        if(!hasExecutionErrors()) {
                            var searchSortDirectionName = form.getSearchSortDirectionName();
                            var searchSortDirection = searchSortDirectionName == null
                                    ? getDefaultSearchSortDirection(searchLogic, rememberPreferences, partySearchTypePreferenceDetail)
                                    : searchLogic.getSearchSortDirectionByName(this, searchSortDirectionName);

                            if(!hasExecutionErrors()) {
                                var searchUseTypeName = form.getSearchUseTypeName();
                                var searchUseType = searchUseTypeName == null ? null : SearchLogic.getInstance().getSearchUseTypeByName(this, searchUseTypeName);

                                if(!hasExecutionErrors()) {
                                    var forumControl = Session.getModelController(ForumControl.class);
                                    var forumName = form.getForumName();
                                    var forum = forumControl.getForumByName(forumName);

                                    if(forum != null) {
                                        if(ForumLogic.getInstance().isForumRoleTypePermitted(this, forum, getParty(), ForumConstants.ForumRoleType_READER)) {
                                            var forumMessageTypeName = form.getForumMessageTypeName();
                                            var forumMessageType = forumMessageTypeName == null ? null : forumControl.getForumMessageTypeByName(forumMessageTypeName);

                                            if(forumMessageTypeName == null || forumMessageType != null) {
                                                var forumType = forum.getLastDetail().getForumType();
                                                var forumTypeMessageType = forumMessageType == null ? forumControl.getDefaultForumTypeMessageType(forumType)
                                                        : forumControl.getForumTypeMessageType(forumType, forumMessageType);

                                                if(forumTypeMessageType != null) {
                                                    var forumMessageSearchEvaluator = new ForumMessageSearchEvaluator(getUserVisit(),
                                                            language, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, searchUseType,
                                                            forum, forumTypeMessageType.getForumMessageType());
                                                    var createdSince = form.getCreatedSince();
                                                    var modifiedSince = form.getModifiedSince();
                                                    var fields = form.getFields();

                                                    forumMessageSearchEvaluator.setIncludeFutureForumThreads(Boolean.parseBoolean(form.getIncludeFutureForumThreads()));
                                                    forumMessageSearchEvaluator.setQ(this, form.getQ());
                                                    forumMessageSearchEvaluator.setCreatedSince(createdSince == null ? null : Long.valueOf(createdSince));
                                                    forumMessageSearchEvaluator.setModifiedSince(modifiedSince == null ? null : Long.valueOf(modifiedSince));
                                                    forumMessageSearchEvaluator.setFields(fields == null ? null : Splitter.on(':').trimResults().omitEmptyStrings().splitToList(fields).toArray(new String[0]));

                                                    if(!hasExecutionErrors()) {
                                                        result.setCount(forumMessageSearchEvaluator.execute(this));
                                                    }
                                                } else {
                                                    if(forumMessageTypeName == null) {
                                                        addExecutionError(ExecutionErrors.UnknownDefaultForumTypeMessageType.name(), forumType.getForumTypeName());
                                                    } else {
                                                        addExecutionError(ExecutionErrors.UnknownForumTypeMessageType.name(), forumType.getForumTypeName(), forumMessageTypeName);
                                                    }
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownForumMessageTypeName.name(), forumMessageTypeName);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.MissingRequiredForumRoleType.name(), ForumConstants.ForumRoleType_READER);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownForumName.name(), forumName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }
}

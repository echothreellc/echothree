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

package com.echothree.model.control.content.server.search;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFieldVariations;
import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.common.IndexTypes;
import com.echothree.model.control.content.server.analyzer.ContentCatalogAnalyzer;
import com.echothree.model.control.index.server.analyzer.BasicAnalyzer;
import com.echothree.model.control.search.common.SearchSortDirections;
import com.echothree.model.control.search.common.SearchSortOrders;
import com.echothree.model.control.search.server.search.BaseSearchEvaluator;
import com.echothree.model.control.search.server.search.EntityInstancePKHolder;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortOrder;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import org.apache.lucene.search.SortField;

public class ContentCatalogSearchEvaluator
        extends BaseSearchEvaluator {
    
    /** Creates a new instance of ContentCatalogSearchEvaluator */
    public ContentCatalogSearchEvaluator(UserVisit userVisit, Language language, SearchType searchType, SearchDefaultOperator searchDefaultOperator,
            SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection, SearchUseType searchUseType) {
        super(userVisit, searchDefaultOperator, searchType, searchSortOrder, searchSortDirection, searchUseType, ComponentVendors.ECHO_THREE.name(),
                EntityTypes.ContentCatalog.name(), IndexTypes.CONTENT_CATALOG.name(), language, null);
        
        setField(IndexFields.description.name());
    }

    @Override
    public SortField[] getSortFields(String searchSortOrderName) {
        SortField[] sortFields = null;
        var reverse = searchSortDirection.getLastDetail().getSearchSortDirectionName().equals(SearchSortDirections.DESCENDING.name());
        
        if(searchSortOrderName.equals(SearchSortOrders.SCORE.name())) {
            sortFields = new SortField[]{
                new SortField(null, SortField.Type.SCORE, reverse),
                new SortField(IndexFields.description.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(), SortField.Type.STRING, reverse)
            };
        } else if(searchSortOrderName.equals(SearchSortOrders.DESCRIPTION.name())) {
            sortFields = new SortField[]{new SortField(IndexFields.description.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(), SortField.Type.STRING, reverse)};
        } else if(searchSortOrderName.equals(SearchSortOrders.CONTENT_CATALOG_NAME.name())) {
            sortFields = new SortField[]{
                new SortField(IndexFields.contentCollectionName.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(), SortField.Type.STRING, reverse),
                new SortField(IndexFields.contentCatalogName.name() + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.sortable.name(), SortField.Type.STRING, reverse)
            };
        } else if(searchSortOrderName.equals(SearchSortOrders.CREATED_TIME.name())) {
            sortFields = new SortField[]{new SortField(IndexFields.createdTime.name(), SortField.Type.LONG, reverse)};
        } else if(searchSortOrderName.equals(SearchSortOrders.MODIFIED_TIME.name())) {
            sortFields = new SortField[]{new SortField(IndexFields.modifiedTime.name(), SortField.Type.LONG, reverse)};
        }
        
        return sortFields;
    }
    
    @Override
    public BasicAnalyzer getAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        return new ContentCatalogAnalyzer(eea, language, entityType);
    }
    
    protected EntityInstancePKHolder executeQSearch(final ExecutionErrorAccumulator eea, EntityInstancePKHolder resultSet) {
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
        
        return resultSet;
    }
    
    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        var resultSet = super.executeSearch(eea);
        
        resultSet = executeQSearch(eea, resultSet);
        
        return resultSet;
    }

}

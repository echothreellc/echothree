// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.offer.server.search;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.analysis.SecurityRoleGroupAnalyzer;
import com.echothree.model.control.search.common.SearchConstants;
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
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.SortField;

public class UseSearchEvaluator
        extends BaseSearchEvaluator {
    
    /** Creates a new instance of SecurityRoleGroupSearchEvaluator */
    public UseSearchEvaluator(UserVisit userVisit, Language language, SearchType searchType, SearchDefaultOperator searchDefaultOperator,
            SearchSortOrder searchSortOrder, SearchSortDirection searchSortDirection, SearchUseType searchUseType) {
        super(userVisit, searchType, searchDefaultOperator, searchSortOrder, searchSortDirection, searchUseType, ComponentVendors.ECHOTHREE.name(),
                EntityTypes.Use.name(), IndexConstants.IndexType_USE, language, null);
        
        setField(IndexConstants.IndexField_Description);
    }

    @Override
    public SortField[] getSortFields(String searchSortOrderName) {
        SortField[] sortFields = null;
        boolean reverse = searchSortDirection.getLastDetail().getSearchSortDirectionName().equals(SearchConstants.SearchSortDirection_DESCENDING);
        
        switch (searchSortOrderName) {
            case SearchConstants.SearchSortOrder_SCORE:
                sortFields = new SortField[]{
                    new SortField(null, SortField.Type.SCORE, reverse),
                    new SortField(IndexConstants.IndexField_Description + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse)
                };
                break;
            case SearchConstants.SearchSortOrder_DESCRIPTION:
                sortFields = new SortField[]{new SortField(IndexConstants.IndexField_Description + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse)};
                break;
            case SearchConstants.SearchSortOrder_USE_NAME:
                sortFields = new SortField[]{new SortField(IndexConstants.IndexField_UseName + IndexConstants.IndexFieldVariationSeparator + IndexConstants.IndexFieldVariation_Sortable, SortField.Type.STRING, reverse)};
                break;
            case SearchConstants.SearchSortOrder_CREATED_TIME:
                sortFields = new SortField[]{new SortField(IndexConstants.IndexField_CreatedTime, SortField.Type.LONG, reverse)};
                break;
            case SearchConstants.SearchSortOrder_MODIFIED_TIME:
                sortFields = new SortField[]{new SortField(IndexConstants.IndexField_ModifiedTime, SortField.Type.LONG, reverse)};
                break;
        }
        
        return sortFields;
    }
    
    @Override
    public Analyzer getAnalyzer(final ExecutionErrorAccumulator eea, final Language language) {
        return new SecurityRoleGroupAnalyzer(eea, language, entityType);
    }
    
    protected EntityInstancePKHolder executeQSearch(final ExecutionErrorAccumulator eea, EntityInstancePKHolder resultSet) {
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
        
        return resultSet;
    }
    
    @Override
    protected EntityInstancePKHolder executeSearch(final ExecutionErrorAccumulator eea) {
        EntityInstancePKHolder resultSet = super.executeSearch(eea);
        
        resultSet = executeQSearch(eea, resultSet);
        
        return resultSet;
    }

}

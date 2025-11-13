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

package com.echothree.model.control.search.server.transfer;

import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class SearchTransferCaches
        extends BaseTransferCaches {
    
    protected SearchControl searchControl;
    
    protected SearchKindTransferCache searchKindTransferCache;
    protected SearchKindDescriptionTransferCache searchKindDescriptionTransferCache;
    protected SearchTypeTransferCache searchTypeTransferCache;
    protected SearchTypeDescriptionTransferCache searchTypeDescriptionTransferCache;
    protected SearchSortOrderTransferCache searchSortOrderTransferCache;
    protected SearchSortOrderDescriptionTransferCache searchSortOrderDescriptionTransferCache;
    protected SearchSortDirectionTransferCache searchSortDirectionTransferCache;
    protected SearchSortDirectionDescriptionTransferCache searchSortDirectionDescriptionTransferCache;
    protected SearchDefaultOperatorTransferCache searchDefaultOperatorTransferCache;
    protected SearchDefaultOperatorDescriptionTransferCache searchDefaultOperatorDescriptionTransferCache;
    protected SearchUseTypeTransferCache searchUseTypeTransferCache;
    protected SearchUseTypeDescriptionTransferCache searchUseTypeDescriptionTransferCache;
    protected SearchResultActionTypeTransferCache searchResultActionTypeTransferCache;
    protected SearchResultActionTypeDescriptionTransferCache searchResultActionTypeDescriptionTransferCache;
    protected SearchCheckSpellingActionTypeTransferCache searchCheckSpellingActionTypeTransferCache;
    protected SearchCheckSpellingActionTypeDescriptionTransferCache searchCheckSpellingActionTypeDescriptionTransferCache;
    
    /** Creates a new instance of SearchTransferCaches */
    public SearchTransferCaches(SearchControl searchControl) {
        super();
        
        this.searchControl = searchControl;
    }
    
    public SearchKindTransferCache getSearchKindTransferCache() {
        if(searchKindTransferCache == null) {
            searchKindTransferCache = new SearchKindTransferCache(searchControl);
        }

        return searchKindTransferCache;
    }

    public SearchKindDescriptionTransferCache getSearchKindDescriptionTransferCache() {
        if(searchKindDescriptionTransferCache == null) {
            searchKindDescriptionTransferCache = new SearchKindDescriptionTransferCache(searchControl);
        }

        return searchKindDescriptionTransferCache;
    }

    public SearchTypeTransferCache getSearchTypeTransferCache() {
        if(searchTypeTransferCache == null) {
            searchTypeTransferCache = new SearchTypeTransferCache(searchControl);
        }

        return searchTypeTransferCache;
    }

    public SearchTypeDescriptionTransferCache getSearchTypeDescriptionTransferCache() {
        if(searchTypeDescriptionTransferCache == null) {
            searchTypeDescriptionTransferCache = new SearchTypeDescriptionTransferCache(searchControl);
        }

        return searchTypeDescriptionTransferCache;
    }

    public SearchSortOrderTransferCache getSearchSortOrderTransferCache() {
        if(searchSortOrderTransferCache == null) {
            searchSortOrderTransferCache = new SearchSortOrderTransferCache(searchControl);
        }

        return searchSortOrderTransferCache;
    }

    public SearchSortOrderDescriptionTransferCache getSearchSortOrderDescriptionTransferCache() {
        if(searchSortOrderDescriptionTransferCache == null) {
            searchSortOrderDescriptionTransferCache = new SearchSortOrderDescriptionTransferCache(searchControl);
        }

        return searchSortOrderDescriptionTransferCache;
    }

    public SearchSortDirectionTransferCache getSearchSortDirectionTransferCache() {
        if(searchSortDirectionTransferCache == null) {
            searchSortDirectionTransferCache = new SearchSortDirectionTransferCache(searchControl);
        }

        return searchSortDirectionTransferCache;
    }

    public SearchSortDirectionDescriptionTransferCache getSearchSortDirectionDescriptionTransferCache() {
        if(searchSortDirectionDescriptionTransferCache == null) {
            searchSortDirectionDescriptionTransferCache = new SearchSortDirectionDescriptionTransferCache(searchControl);
        }

        return searchSortDirectionDescriptionTransferCache;
    }

    public SearchDefaultOperatorTransferCache getSearchDefaultOperatorTransferCache() {
        if(searchDefaultOperatorTransferCache == null) {
            searchDefaultOperatorTransferCache = new SearchDefaultOperatorTransferCache(searchControl);
        }

        return searchDefaultOperatorTransferCache;
    }

    public SearchDefaultOperatorDescriptionTransferCache getSearchDefaultOperatorDescriptionTransferCache() {
        if(searchDefaultOperatorDescriptionTransferCache == null) {
            searchDefaultOperatorDescriptionTransferCache = new SearchDefaultOperatorDescriptionTransferCache(searchControl);
        }

        return searchDefaultOperatorDescriptionTransferCache;
    }

    public SearchUseTypeTransferCache getSearchUseTypeTransferCache() {
        if(searchUseTypeTransferCache == null) {
            searchUseTypeTransferCache = new SearchUseTypeTransferCache(searchControl);
        }

        return searchUseTypeTransferCache;
    }

    public SearchUseTypeDescriptionTransferCache getSearchUseTypeDescriptionTransferCache() {
        if(searchUseTypeDescriptionTransferCache == null) {
            searchUseTypeDescriptionTransferCache = new SearchUseTypeDescriptionTransferCache(searchControl);
        }

        return searchUseTypeDescriptionTransferCache;
    }

    public SearchResultActionTypeTransferCache getSearchResultActionTypeTransferCache() {
        if(searchResultActionTypeTransferCache == null) {
            searchResultActionTypeTransferCache = new SearchResultActionTypeTransferCache(searchControl);
        }

        return searchResultActionTypeTransferCache;
    }

    public SearchResultActionTypeDescriptionTransferCache getSearchResultActionTypeDescriptionTransferCache() {
        if(searchResultActionTypeDescriptionTransferCache == null) {
            searchResultActionTypeDescriptionTransferCache = new SearchResultActionTypeDescriptionTransferCache(searchControl);
        }

        return searchResultActionTypeDescriptionTransferCache;
    }

    public SearchCheckSpellingActionTypeTransferCache getSearchCheckSpellingActionTypeTransferCache() {
        if(searchCheckSpellingActionTypeTransferCache == null) {
            searchCheckSpellingActionTypeTransferCache = new SearchCheckSpellingActionTypeTransferCache(searchControl);
        }

        return searchCheckSpellingActionTypeTransferCache;
    }

    public SearchCheckSpellingActionTypeDescriptionTransferCache getSearchCheckSpellingActionTypeDescriptionTransferCache() {
        if(searchCheckSpellingActionTypeDescriptionTransferCache == null) {
            searchCheckSpellingActionTypeDescriptionTransferCache = new SearchCheckSpellingActionTypeDescriptionTransferCache(searchControl);
        }

        return searchCheckSpellingActionTypeDescriptionTransferCache;
    }

}

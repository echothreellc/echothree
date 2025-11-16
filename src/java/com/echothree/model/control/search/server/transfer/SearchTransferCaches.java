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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

public class SearchTransferCaches
        extends BaseTransferCaches {
    
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
    public SearchTransferCaches() {
        super();
    }
    
    public SearchKindTransferCache getSearchKindTransferCache() {
        if(searchKindTransferCache == null) {
            searchKindTransferCache = CDI.current().select(SearchKindTransferCache.class).get();
        }

        return searchKindTransferCache;
    }

    public SearchKindDescriptionTransferCache getSearchKindDescriptionTransferCache() {
        if(searchKindDescriptionTransferCache == null) {
            searchKindDescriptionTransferCache = CDI.current().select(SearchKindDescriptionTransferCache.class).get();
        }

        return searchKindDescriptionTransferCache;
    }

    public SearchTypeTransferCache getSearchTypeTransferCache() {
        if(searchTypeTransferCache == null) {
            searchTypeTransferCache = CDI.current().select(SearchTypeTransferCache.class).get();
        }

        return searchTypeTransferCache;
    }

    public SearchTypeDescriptionTransferCache getSearchTypeDescriptionTransferCache() {
        if(searchTypeDescriptionTransferCache == null) {
            searchTypeDescriptionTransferCache = CDI.current().select(SearchTypeDescriptionTransferCache.class).get();
        }

        return searchTypeDescriptionTransferCache;
    }

    public SearchSortOrderTransferCache getSearchSortOrderTransferCache() {
        if(searchSortOrderTransferCache == null) {
            searchSortOrderTransferCache = CDI.current().select(SearchSortOrderTransferCache.class).get();
        }

        return searchSortOrderTransferCache;
    }

    public SearchSortOrderDescriptionTransferCache getSearchSortOrderDescriptionTransferCache() {
        if(searchSortOrderDescriptionTransferCache == null) {
            searchSortOrderDescriptionTransferCache = CDI.current().select(SearchSortOrderDescriptionTransferCache.class).get();
        }

        return searchSortOrderDescriptionTransferCache;
    }

    public SearchSortDirectionTransferCache getSearchSortDirectionTransferCache() {
        if(searchSortDirectionTransferCache == null) {
            searchSortDirectionTransferCache = CDI.current().select(SearchSortDirectionTransferCache.class).get();
        }

        return searchSortDirectionTransferCache;
    }

    public SearchSortDirectionDescriptionTransferCache getSearchSortDirectionDescriptionTransferCache() {
        if(searchSortDirectionDescriptionTransferCache == null) {
            searchSortDirectionDescriptionTransferCache = CDI.current().select(SearchSortDirectionDescriptionTransferCache.class).get();
        }

        return searchSortDirectionDescriptionTransferCache;
    }

    public SearchDefaultOperatorTransferCache getSearchDefaultOperatorTransferCache() {
        if(searchDefaultOperatorTransferCache == null) {
            searchDefaultOperatorTransferCache = CDI.current().select(SearchDefaultOperatorTransferCache.class).get();
        }

        return searchDefaultOperatorTransferCache;
    }

    public SearchDefaultOperatorDescriptionTransferCache getSearchDefaultOperatorDescriptionTransferCache() {
        if(searchDefaultOperatorDescriptionTransferCache == null) {
            searchDefaultOperatorDescriptionTransferCache = CDI.current().select(SearchDefaultOperatorDescriptionTransferCache.class).get();
        }

        return searchDefaultOperatorDescriptionTransferCache;
    }

    public SearchUseTypeTransferCache getSearchUseTypeTransferCache() {
        if(searchUseTypeTransferCache == null) {
            searchUseTypeTransferCache = CDI.current().select(SearchUseTypeTransferCache.class).get();
        }

        return searchUseTypeTransferCache;
    }

    public SearchUseTypeDescriptionTransferCache getSearchUseTypeDescriptionTransferCache() {
        if(searchUseTypeDescriptionTransferCache == null) {
            searchUseTypeDescriptionTransferCache = CDI.current().select(SearchUseTypeDescriptionTransferCache.class).get();
        }

        return searchUseTypeDescriptionTransferCache;
    }

    public SearchResultActionTypeTransferCache getSearchResultActionTypeTransferCache() {
        if(searchResultActionTypeTransferCache == null) {
            searchResultActionTypeTransferCache = CDI.current().select(SearchResultActionTypeTransferCache.class).get();
        }

        return searchResultActionTypeTransferCache;
    }

    public SearchResultActionTypeDescriptionTransferCache getSearchResultActionTypeDescriptionTransferCache() {
        if(searchResultActionTypeDescriptionTransferCache == null) {
            searchResultActionTypeDescriptionTransferCache = CDI.current().select(SearchResultActionTypeDescriptionTransferCache.class).get();
        }

        return searchResultActionTypeDescriptionTransferCache;
    }

    public SearchCheckSpellingActionTypeTransferCache getSearchCheckSpellingActionTypeTransferCache() {
        if(searchCheckSpellingActionTypeTransferCache == null) {
            searchCheckSpellingActionTypeTransferCache = CDI.current().select(SearchCheckSpellingActionTypeTransferCache.class).get();
        }

        return searchCheckSpellingActionTypeTransferCache;
    }

    public SearchCheckSpellingActionTypeDescriptionTransferCache getSearchCheckSpellingActionTypeDescriptionTransferCache() {
        if(searchCheckSpellingActionTypeDescriptionTransferCache == null) {
            searchCheckSpellingActionTypeDescriptionTransferCache = CDI.current().select(SearchCheckSpellingActionTypeDescriptionTransferCache.class).get();
        }

        return searchCheckSpellingActionTypeDescriptionTransferCache;
    }

}

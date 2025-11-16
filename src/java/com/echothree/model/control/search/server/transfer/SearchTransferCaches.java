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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class SearchTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    SearchKindTransferCache searchKindTransferCache;
    
    @Inject
    SearchKindDescriptionTransferCache searchKindDescriptionTransferCache;
    
    @Inject
    SearchTypeTransferCache searchTypeTransferCache;
    
    @Inject
    SearchTypeDescriptionTransferCache searchTypeDescriptionTransferCache;
    
    @Inject
    SearchSortOrderTransferCache searchSortOrderTransferCache;
    
    @Inject
    SearchSortOrderDescriptionTransferCache searchSortOrderDescriptionTransferCache;
    
    @Inject
    SearchSortDirectionTransferCache searchSortDirectionTransferCache;
    
    @Inject
    SearchSortDirectionDescriptionTransferCache searchSortDirectionDescriptionTransferCache;
    
    @Inject
    SearchDefaultOperatorTransferCache searchDefaultOperatorTransferCache;
    
    @Inject
    SearchDefaultOperatorDescriptionTransferCache searchDefaultOperatorDescriptionTransferCache;
    
    @Inject
    SearchUseTypeTransferCache searchUseTypeTransferCache;
    
    @Inject
    SearchUseTypeDescriptionTransferCache searchUseTypeDescriptionTransferCache;
    
    @Inject
    SearchResultActionTypeTransferCache searchResultActionTypeTransferCache;
    
    @Inject
    SearchResultActionTypeDescriptionTransferCache searchResultActionTypeDescriptionTransferCache;
    
    @Inject
    SearchCheckSpellingActionTypeTransferCache searchCheckSpellingActionTypeTransferCache;
    
    @Inject
    SearchCheckSpellingActionTypeDescriptionTransferCache searchCheckSpellingActionTypeDescriptionTransferCache;

    /** Creates a new instance of SearchTransferCaches */
    protected SearchTransferCaches() {
        super();
    }
    
    public SearchKindTransferCache getSearchKindTransferCache() {
        return searchKindTransferCache;
    }

    public SearchKindDescriptionTransferCache getSearchKindDescriptionTransferCache() {
        return searchKindDescriptionTransferCache;
    }

    public SearchTypeTransferCache getSearchTypeTransferCache() {
        return searchTypeTransferCache;
    }

    public SearchTypeDescriptionTransferCache getSearchTypeDescriptionTransferCache() {
        return searchTypeDescriptionTransferCache;
    }

    public SearchSortOrderTransferCache getSearchSortOrderTransferCache() {
        return searchSortOrderTransferCache;
    }

    public SearchSortOrderDescriptionTransferCache getSearchSortOrderDescriptionTransferCache() {
        return searchSortOrderDescriptionTransferCache;
    }

    public SearchSortDirectionTransferCache getSearchSortDirectionTransferCache() {
        return searchSortDirectionTransferCache;
    }

    public SearchSortDirectionDescriptionTransferCache getSearchSortDirectionDescriptionTransferCache() {
        return searchSortDirectionDescriptionTransferCache;
    }

    public SearchDefaultOperatorTransferCache getSearchDefaultOperatorTransferCache() {
        return searchDefaultOperatorTransferCache;
    }

    public SearchDefaultOperatorDescriptionTransferCache getSearchDefaultOperatorDescriptionTransferCache() {
        return searchDefaultOperatorDescriptionTransferCache;
    }

    public SearchUseTypeTransferCache getSearchUseTypeTransferCache() {
        return searchUseTypeTransferCache;
    }

    public SearchUseTypeDescriptionTransferCache getSearchUseTypeDescriptionTransferCache() {
        return searchUseTypeDescriptionTransferCache;
    }

    public SearchResultActionTypeTransferCache getSearchResultActionTypeTransferCache() {
        return searchResultActionTypeTransferCache;
    }

    public SearchResultActionTypeDescriptionTransferCache getSearchResultActionTypeDescriptionTransferCache() {
        return searchResultActionTypeDescriptionTransferCache;
    }

    public SearchCheckSpellingActionTypeTransferCache getSearchCheckSpellingActionTypeTransferCache() {
        return searchCheckSpellingActionTypeTransferCache;
    }

    public SearchCheckSpellingActionTypeDescriptionTransferCache getSearchCheckSpellingActionTypeDescriptionTransferCache() {
        return searchCheckSpellingActionTypeDescriptionTransferCache;
    }

}

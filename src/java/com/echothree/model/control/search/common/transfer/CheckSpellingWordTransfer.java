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

package com.echothree.model.control.search.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class CheckSpellingWordTransfer
        extends BaseTransfer {
    
    private String word;
    private SearchCheckSpellingActionTypeTransfer searchCheckSpellingActionType;
    private ListWrapper<CheckSpellingSuggestionTransfer> checkSpellingSuggestions;

    /** Creates a new instance of CheckSpellingWordTransfer */
    public CheckSpellingWordTransfer(String word, SearchCheckSpellingActionTypeTransfer searchCheckSpellingActionType,
            ListWrapper<CheckSpellingSuggestionTransfer> checkSpellingSuggestions) {
        this.word = word;
        this.searchCheckSpellingActionType = searchCheckSpellingActionType;
        this.checkSpellingSuggestions = checkSpellingSuggestions;
    }

    /**
     * Returns the word.
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * Sets the word.
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Returns the searchCheckSpellingActionType.
     * @return the searchCheckSpellingActionType
     */
    public SearchCheckSpellingActionTypeTransfer getSearchCheckSpellingActionType() {
        return searchCheckSpellingActionType;
    }

    /**
     * Sets the searchCheckSpellingActionType.
     * @param searchCheckSpellingActionType the searchCheckSpellingActionType to set
     */
    public void setSearchCheckSpellingActionType(SearchCheckSpellingActionTypeTransfer searchCheckSpellingActionType) {
        this.searchCheckSpellingActionType = searchCheckSpellingActionType;
    }

    /**
     * Returns the checkSpellingSuggestions.
     * @return the checkSpellingSuggestions
     */
    public ListWrapper<CheckSpellingSuggestionTransfer> getCheckSpellingSuggestions() {
        return checkSpellingSuggestions;
    }

    /**
     * Sets the checkSpellingSuggestions.
     * @param checkSpellingSuggestions the checkSpellingSuggestions to set
     */
    public void setCheckSpellingSuggestions(ListWrapper<CheckSpellingSuggestionTransfer> checkSpellingSuggestions) {
        this.checkSpellingSuggestions = checkSpellingSuggestions;
    }
    
 }

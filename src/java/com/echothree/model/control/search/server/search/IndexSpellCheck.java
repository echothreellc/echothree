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

package com.echothree.model.control.search.server.search;

import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.search.common.transfer.CheckSpellingSuggestionTransfer;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.spell.DirectSpellChecker;

public class IndexSpellCheck
        extends BaseIndex<List<List<CheckSpellingSuggestionTransfer>>> {
    
    private String dictionaryField;
    private List<String> words;
    private List<String> analyzedWords;

    private void init(final String dictionaryField, final List<String> words, final List<String> analyzedWords) {
        this.dictionaryField = dictionaryField;
        this.words = words;
        this.analyzedWords = analyzedWords;
    }
    
    protected IndexSpellCheck(final ExecutionErrorAccumulator eea, final IndexControl indexControl, final Index index, final String dictionaryField,
            final List<String> words, final List<String> analyzedWords) {
        super(eea, indexControl, index);
        init(dictionaryField, words, analyzedWords);
    }
    
    private boolean hasNonNullElements(Collection<?> collection) {
        var result = false;
        
        for(var o : collection) {
            if(o != null) {
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    @Override
    public List<List<CheckSpellingSuggestionTransfer>> execute() {
        List<List<CheckSpellingSuggestionTransfer>> suggestions;
        
        // If all words have been analyzed out of existence, skip over everything.
        if(hasNonNullElements(analyzedWords)) {
            suggestions = super.execute();
        } else {
            if(EvaluatorDebugFlags.LogCheckSpelling) {
                getLog().info("  Skipping all.");
            }
            
            suggestions = Collections.nCopies(words.size(), null);
        }
        
        return suggestions;
    }
    
    @Override
    protected List<List<CheckSpellingSuggestionTransfer>> useIndex(IndexReader ir)
            throws IOException {
        List<List<CheckSpellingSuggestionTransfer>> suggestions;
        var directSpellChecker = new DirectSpellChecker();
        var analyzedWordsIter = analyzedWords.iterator();

        suggestions = new ArrayList<>(words.size());

        for(var word : words) {
            List<CheckSpellingSuggestionTransfer> checkSpellingSuggestions = null;
            var analyzedWord = analyzedWordsIter.next();

            if(analyzedWord == null) {
                if(EvaluatorDebugFlags.LogCheckSpelling) {
                    getLog().info("  Skipping: " + word);
                }
            } else {
                var term = new Term(dictionaryField, word);
                var suggestWords = directSpellChecker.suggestSimilar(term, 5, ir);

                if(EvaluatorDebugFlags.LogCheckSpelling) {
                    getLog().info("  Checking: " + word);
                }

                if(suggestWords.length == 0) {
                    if(EvaluatorDebugFlags.LogCheckSpelling) {
                        getLog().info("    No Suggestions.");
                    }
                } else {
                    checkSpellingSuggestions = new ArrayList<>(suggestWords.length);

                    if(EvaluatorDebugFlags.LogCheckSpelling) {
                        getLog().info("    Suggestions:");
                    }

                    for(var suggestWord : suggestWords) {
                        if(EvaluatorDebugFlags.LogCheckSpelling) {
                            getLog().info("      " + suggestWord.string + ", " + suggestWord.freq + ", " + suggestWord.score);
                        }

                        checkSpellingSuggestions.add(new CheckSpellingSuggestionTransfer(suggestWord.string));
                    }
                }

            }

            suggestions.add(checkSpellingSuggestions);
        }
        
        return suggestions;
    }

}

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

import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.common.IndexFieldVariations;
import com.echothree.model.control.party.common.Languages;
import com.echothree.model.control.search.common.SearchCheckSpellingActionTypes;
import com.echothree.model.control.search.common.SearchDefaultOperators;
import com.echothree.model.control.search.common.exception.ComplexQueriesUnsupportedException;
import com.echothree.model.control.search.common.exception.FieldRequiredException;
import com.echothree.model.control.search.common.exception.LanguageRequiredException;
import com.echothree.model.control.search.common.exception.LanguageUnsupportedException;
import com.echothree.model.control.search.common.exception.MultipleFieldsUnsupportedException;
import com.echothree.model.control.search.common.transfer.CheckSpellingSuggestionTransfer;
import com.echothree.model.control.search.common.transfer.CheckSpellingWordTransfer;
import com.echothree.model.control.search.server.logic.SearchCheckSpellingActionTypeLogic;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

public abstract class BaseSpellCheckEvaluator
        extends BaseEvaluator {
    
    protected BaseSpellCheckEvaluator(UserVisit userVisit, SearchDefaultOperator searchDefaultOperator, String componentVendorName,
            String entityTypeName, String indexTypeName, Language language, String indexName) {
        super(userVisit, searchDefaultOperator, componentVendorName, entityTypeName, indexTypeName, language, indexName);
    }
    
    private boolean isSimpleQuery(Query query, final String dictionaryField, final List<String> words) {
        boolean result;
        var isBoosted = false;

        // If it's a BoostQuery, then we'll unwrap the contained Query and mark
        // that we've been boosted if needed.
        if(query instanceof BoostQuery boostQuery) {

            isBoosted = boostQuery.getBoost() != 1.0f;

            if(EvaluatorDebugFlags.LogCheckSpelling) {
                getLog().info("    containedTermQuery.getBoost() " + boostQuery.getBoost());
            }
            
            query = boostQuery.getQuery();
        }
        
        if(EvaluatorDebugFlags.LogCheckSpelling) {
            getLog().info("    is boosted: " + isBoosted);
        }

        if(query instanceof TermQuery termQuery) {
            var term = termQuery.getTerm();
            var text = term.text();
            
            if(EvaluatorDebugFlags.LogCheckSpelling) {
                getLog().info("    containedTerm.field() " + term.field());
                getLog().info("    containedTerm.text() " + text);
            }
                        
            // For the query to be simple, it must not have a boost applied, and the
            // field for the query must be the default one. The exact comparison to 1.0f
            // is a bad idea, but there doesn't appear to be a choice for determining
            // this.
            result = (!isBoosted && dictionaryField.equals(term.field()));
            if(result) {
                words.add(text);
            }
        } else {
            if(EvaluatorDebugFlags.LogCheckSpelling) {
                getLog().info("    !!! not a TermQuery");
            }
            
            result = false;
        }
        
        if(EvaluatorDebugFlags.LogCheckSpelling) {
            getLog().info("    isSimpleQuery: " + result);
        }
        
        return result;
    }
    
    private boolean isSimpleBooleanQuery(final BooleanQuery booleanQuery, final String dictionaryField, final List<String> words) {
        var result = true;

        // When searchDefaultOperatorName == AND, Occur.MUST must occur on all clauses.
        // For OR, Occur.SHOULD must occur on all clauses.
        var requiredOccur = switch(SearchDefaultOperators.valueOf(getSearchDefaultOperatorName())) {
            case AND -> Occur.MUST;
            case OR -> Occur.SHOULD;
        };

        for(var booleanClause : booleanQuery) {
            var occur = booleanClause.occur();

            if(EvaluatorDebugFlags.LogCheckSpelling) {
                getLog().info("booleanClause " + booleanClause);
                getLog().info("  booleanClause.getOccur() " + occur.name());
                getLog().info("  booleanClause.isRequired() " + booleanClause.isRequired());
                getLog().info("  booleanClause.isProhibited() " + booleanClause.isProhibited());
            }

            // MUST and SHOULD are the only two Occurs that'll make it this far, MUST_NOT will never occur.
            switch(requiredOccur) {
                case MUST -> {
                    if(!booleanClause.isRequired() || booleanClause.isProhibited()) {
                        result = false;
                    }
                }
                case SHOULD -> {
                    if(booleanClause.isRequired() || booleanClause.isProhibited()) {
                        result = false;
                    }
                }
                default -> throw new IllegalStateException("Unexpected requiredOccur");
            }

            if(result) {
                var containedQuery = booleanClause.query();

                if(EvaluatorDebugFlags.LogCheckSpelling) {
                    getLog().info("  booleanClause.getQuery() " + containedQuery);
                    getLog().info("  booleanClause.getQuery().getClass() " + containedQuery.getClass());
                }

                result = isSimpleQuery(containedQuery, dictionaryField, words);
            }

            if(!result) {
                break;
            }
        }

        return result;
    }
    
    private boolean isSimpleQuery(final ExecutionErrorAccumulator eea, final String dictionaryField, final List<String> words) {
        var result = false;
        
        if(query == null) {
            parseQuery(eea, dictionaryField, null);
        }
        
        if(query != null) {
            if(query instanceof BooleanQuery) {
                result = isSimpleBooleanQuery((BooleanQuery)query, dictionaryField, words);
            } else {
                result = isSimpleQuery(query, dictionaryField, words);

                if(EvaluatorDebugFlags.LogCheckSpelling) {
                    getLog().info("!!! not a BooleanQuery, " + query.getClass());
                }
            }
        }

        if(EvaluatorDebugFlags.LogCheckSpelling) {
            getLog().info("isSimpleQuery: " + result);
        }

        return result;
    }
    
    private List<CheckSpellingWordTransfer> getCheckSpellingWordTransfers(final List<String> words, final List<String> analyzedWords,
            final List<List<CheckSpellingSuggestionTransfer>> suggestions) {
        var searchCheckSpellingActionTypeLogic = SearchCheckSpellingActionTypeLogic.getInstance();
        var checkSpellingWords = new ArrayList<CheckSpellingWordTransfer>(words.size());
        var analyzedWordsIter = analyzedWords.iterator();
        var wordSuggestionsIter = suggestions.iterator();
        
        words.forEach((word) -> {
            var analyzedWord = analyzedWordsIter.next();
            var checkSpellingSuggestions = wordSuggestionsIter.next();
            String searchCheckSpellingActionTypeName;
            
            if(analyzedWord == null) {
                searchCheckSpellingActionTypeName = SearchCheckSpellingActionTypes.IGNORED.name();
            } else if(checkSpellingSuggestions == null) {
                searchCheckSpellingActionTypeName = SearchCheckSpellingActionTypes.NO_SUGGESTIONS.name();
            } else {
                searchCheckSpellingActionTypeName = SearchCheckSpellingActionTypes.HAS_SUGGESTIONS.name();
            }
            
            checkSpellingWords.add(new CheckSpellingWordTransfer(word,
                    searchCheckSpellingActionTypeLogic.getSearchCheckSpellingActionTypeTransferByName(null, userVisit, searchCheckSpellingActionTypeName),
                    new ListWrapper<>(checkSpellingSuggestions)));
        });
        
        return checkSpellingWords;
    }
    
    private List<CheckSpellingWordTransfer> executeCheckSpelling(final ExecutionErrorAccumulator eea) {
        List<CheckSpellingWordTransfer> checkSpellingWords = null;
        
        if(fields == null) {
            if(field == null) {
                handleExecutionError(FieldRequiredException.class, eea, ExecutionErrors.FieldRequired.name());
            } else {
                final List<String> words = new ArrayList<>();

                // Switch the default field used by parseQuery(...) over to the dictionary one in order to avoid most analysis
                // beyond splitting the phrase into simple words.
                final var dictionaryField = field + IndexConstants.INDEX_FIELD_VARIATION_SEPARATOR + IndexFieldVariations.dictionary.name();
                final var simpleQuery = isSimpleQuery(eea, dictionaryField, words);

                if(EvaluatorDebugFlags.LogCheckSpelling) {
                    getLog().info("words = " + words);
                }

                if(simpleQuery) {
                    final var analyzer = getCachedAnalyzer(null, getLanguage());
                    final List<String> analyzedWords = new ArrayList<>(words.size());

                    for(var word : words) {
                        String analyzedWord = null;

                        try {
                            try(var stream = analyzer.tokenStream(field, new StringReader(word))) {
                                stream.reset();

                                while(stream.incrementToken()) {
                                    analyzedWord = stream.getAttribute(CharTermAttribute.class).toString();
                                }

                                stream.end();
                            }
                        } catch(IOException e) {
                            // not thrown b/c we're using a string reader...
                            throw new RuntimeException(e);
                        }

                        analyzedWords.add(analyzedWord);
                    }

                    if(EvaluatorDebugFlags.LogCheckSpelling) {
                        getLog().info("analyzedWords = " + analyzedWords);
                    }

                    final var suggestions = executeSpellCheck(eea, dictionaryField, words, analyzedWords);

                    if(!hasExecutionErrors(eea)) {
                        checkSpellingWords = getCheckSpellingWordTransfers(words, analyzedWords, suggestions);
                    }
                } else {
                    handleExecutionError(ComplexQueriesUnsupportedException.class, eea, ExecutionErrors.ComplexQueriesUnsupported.name());
                }
            }
        } else {
            handleExecutionError(MultipleFieldsUnsupportedException.class, eea, ExecutionErrors.MultipleFieldsUnsupported.name());
        }
        
        return checkSpellingWords;
    }
    
    public List<CheckSpellingWordTransfer> checkSpelling(final ExecutionErrorAccumulator eea) {
        List<CheckSpellingWordTransfer> checkSpellingWords = null;
        final var languageIsoName = getLanguageIsoName();
        
        if(languageIsoName == null) {
            handleExecutionError(LanguageRequiredException.class, eea, ExecutionErrors.LanguageRequired.name());
        } else {
            if(languageIsoName.equals(Languages.en.name())) {
                checkSpellingWords = executeCheckSpelling(eea);
            } else {
                handleExecutionError(LanguageUnsupportedException.class, eea, ExecutionErrors.LanguageUnsupported.name(), languageIsoName);
            }
        }
        
        return checkSpellingWords;
    }
    
    protected List<List<CheckSpellingSuggestionTransfer>> executeSpellCheck(final ExecutionErrorAccumulator eea, final String dictionaryField,
            final List<String> words, final List<String> analyzedWords) {
        return new IndexSpellCheck(eea, getIndexControl(), index, dictionaryField, words, analyzedWords).execute();
    }
    
}

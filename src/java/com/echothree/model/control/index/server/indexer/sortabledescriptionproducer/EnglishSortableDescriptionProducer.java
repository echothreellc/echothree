// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.index.server.indexer.sortabledescriptionproducer;

import com.echothree.model.control.index.server.analysis.WhitespaceLowerCaseAnalyzer;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class EnglishSortableDescriptionProducer
        implements SortableDescriptionProducer {

    private List<String> tokenizeString(String string) {
        Analyzer analyzer = new WhitespaceLowerCaseAnalyzer();
        List<String> result = new ArrayList<>();

        try {
            TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(string));

            tokenStream.reset();
            
            while(tokenStream.incrementToken()) {
                result.add(tokenStream.getAttribute(CharTermAttribute.class).toString());
            }
        } catch(IOException e) {
            // Ignore. IOException should not be thrown since we're using a StringReader.
        }

        return result;
    }
    
    private boolean isArticle(String string) {
        return string.equals("a") || string.equals("an") || string.equals("the");
    }
    
    private void appendWords(StringBuilder stringBuilder, Formatter formatter, List<String> words, int start, int end) {
        boolean afterBeginning = stringBuilder.length() != 0;
        
        for(int i = start ; i <= end ; i++) {
            String currentWord = words.get(i);
            
            if(afterBeginning) {
                stringBuilder.append(' ');
            }
            
            try {
                long currentLong = Long.parseLong(currentWord);
                
                if(currentLong < 0) {
                    stringBuilder.append('A');
                    currentLong = Long.MAX_VALUE + currentLong;
                } else {
                    stringBuilder.append('Z');
                }
                
                formatter.format("%019d", Math.abs(currentLong));
            } catch (NumberFormatException nfe) {
                // If there was a NumberFormatException when we tried to parse it, then
                // just used the currentWord as-is.
                stringBuilder.append(currentWord);
            }
            
            afterBeginning = true;
        }
    }
    
    private String sortableString(String string) {
        List<String> words = tokenizeString(string);
        StringBuilder result = new StringBuilder();
        Formatter formatter = new Formatter(result);
        int wordSize = words.size();
        int firstNonArticle = 0;
        
        for(int i = 0 ; i < wordSize ; i++) {
            if(!isArticle(words.get(i))) {
                firstNonArticle = i;
                break;
            }
        }
        
        appendWords(result, formatter, words, firstNonArticle, wordSize - 1);
        
        if(firstNonArticle != 0) {
            appendWords(result, formatter, words, 0, firstNonArticle - 1);
        }
        
        return result.toString();
    }
    
    @Override
    public String getSortableDescription(String stringDescription) {
        return sortableString(stringDescription);
    }

}

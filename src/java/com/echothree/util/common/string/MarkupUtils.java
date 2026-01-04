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

package com.echothree.util.common.string;

import com.google.common.base.Splitter;
import com.google.common.html.HtmlEscapers;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class MarkupUtils {
    
    private MarkupUtils() {
        super();
    }
    
    private static class MarkupUtilsHolder {
        static MarkupUtils instance = new MarkupUtils();
    }
    
    public static MarkupUtils getInstance() {
        return MarkupUtilsHolder.instance;
    }
    
    private static class MarkupTransformation {
        String markup;
        String startHtml;
        String endHtml;
        String requiredIn;
        
        MarkupTransformation(Map<String, MarkupTransformation> transformations, String markup, String startHtml, String endHtml, String requiredIn) {
            this.markup = markup;
            this.startHtml = startHtml;
            this.endHtml = endHtml;
            this.requiredIn = requiredIn;
            
            transformations.put(markup, this);
        }
    };
    
    private final static Map<String, MarkupTransformation> transformations;
    
    static {
        transformations = new HashMap<>();
        
        // TODO: Fill in additional tags from http://learningforlife.fsu.edu/webmaster/references/xhtml/tags/index.cfm
        new MarkupTransformation(transformations, "b", "<b>", "</b>", null);
        new MarkupTransformation(transformations, "i", "<i>", "</i>", null);
        new MarkupTransformation(transformations, "u", "<u>", "</u>", null);
        new MarkupTransformation(transformations, "s", "<s>", "</s>", null);
        new MarkupTransformation(transformations, "ul", "<ul>", "</ul>", null);
        new MarkupTransformation(transformations, "ol", "<ol>", "</ol>", null);
        new MarkupTransformation(transformations, "li", "<li>", "</li>", "ul:ol");
        new MarkupTransformation(transformations, "blockquote", "<blockquote>", "</blockquote>", null);
        new MarkupTransformation(transformations, "pre", "<pre>", "</pre>", null);
        new MarkupTransformation(transformations, "quote", "<hr /><blockquote>", "</blockquote><hr />", null);
        new MarkupTransformation(transformations, "br", "<br />", null, null);
        new MarkupTransformation(transformations, "red", "<span style=\"color: red\">", "</span>", null);
        new MarkupTransformation(transformations, "green", "<span style=\"color: green\">", "</span>", null);
        new MarkupTransformation(transformations, "blue", "<span style=\"color: blue\">", "</span>", null);
        new MarkupTransformation(transformations, "orange", "<span style=\"color: orange\">", "</span>", null);
        new MarkupTransformation(transformations, "black", "<span style=\"color: black\">", "</span>", null);
        new MarkupTransformation(transformations, "white", "<span style=\"color: white\">", "</span>", null);
        new MarkupTransformation(transformations, "yellow", "<span style=\"color: yellow\">", "</span>", null);
        new MarkupTransformation(transformations, "purple", "<span style=\"color: purple\">", "</span>", null);
        new MarkupTransformation(transformations, "table", "<table>", "</table>", null);
        new MarkupTransformation(transformations, "tr", "<tr>", "</tr>", "table");
        new MarkupTransformation(transformations, "td", "<td>", "</td>", "tr");
    }
    
    public String filter(final String originalContent) {
        var intermediateContent = HtmlEscapers.htmlEscaper().escape(originalContent);
        var finalContent = new StringBuilder(intermediateContent.length());
        StringBuilder markupBuilder = null;
        var endMarkup = false;
        Deque<MarkupTransformation> activeMarkup = new ArrayDeque<>();
        
        for(int ch : StringUtils.getInstance().codePoints(intermediateContent)) {
            switch(ch) {
                case '[' -> {
                    markupBuilder = new StringBuilder();
                    endMarkup = false;
                }
                case ']' -> {
                    var mt = transformations.get(markupBuilder.toString());

                    if(mt != null) {
                        if(endMarkup) {
                            if(mt == activeMarkup.peek()) {
                                activeMarkup.pop();
                                finalContent.append(mt.endHtml);
                            }
                        } else {
                            var pushIt = true;

                            if(mt.requiredIn != null) {
                                var lastMt = activeMarkup.peek();

                                pushIt = false;

                                if(lastMt != null) {
                                    var endingMarkup = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(mt.requiredIn).toArray(new String[0]);

                                    for(var i = 0; i < endingMarkup.length; i++) {
                                        if(endingMarkup[i].equals(lastMt.markup)) {
                                            pushIt = true;
                                            break;
                                        }
                                    }
                                }
                            }

                            if(pushIt) {
                                if(mt.endHtml != null) {
                                    activeMarkup.push(mt);
                                }

                                finalContent.append(mt.startHtml);
                            }
                        }
                    }

                    markupBuilder = null;
                }
                default -> {
                    if(markupBuilder == null) {
                        switch(ch) {
                            case '\t' -> finalContent.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                            case '\r' -> {
                                // Drop it.
                            }
                            case '\n' -> finalContent.append("<br />");
                            default -> finalContent.appendCodePoint(ch);
                        }
                    } else {
                        if(ch == '/' && markupBuilder.isEmpty()) {
                            endMarkup = true;
                        } else if(Character.isLetter(ch)) {
                            markupBuilder.appendCodePoint(Character.toLowerCase(ch));
                        }
                    }
                }
            }
        }
        
        while(activeMarkup.peek() != null) {
            finalContent.append(activeMarkup.pop().endHtml);
        }
        
        return finalContent.toString();
    }
    
}

// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
// Copyright 1999-2004 The Apache Software Foundation.
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

package com.echothree.view.client.web.taglib.util;

import java.util.ArrayList;
import java.util.List;
import org.cyberneko.html.filters.ElementRemover;

public class HtmlFilterHolder {

    protected List<HtmlTagHolder> htmlTagHolders = new ArrayList<>();

    protected HtmlFilterHolder previousHtmlFilterHolder;
    protected HtmlTagHolder currentHtmlTagHolder;

    public HtmlFilterHolder(HtmlFilterHolder previousHtmlFilterHolder) {
        this.previousHtmlFilterHolder = previousHtmlFilterHolder;
        this.currentHtmlTagHolder = null;
    }
    
    public HtmlFilterHolder getPreviousHtmlFilterHolder() {
        return previousHtmlFilterHolder;
    }
    
    public HtmlTagHolder getCurrentHtmlTagHolder() {
        return currentHtmlTagHolder;
    }
    
    public void addHtmlTagHolder(HtmlTagHolder htmlTagHolder) {
        htmlTagHolders.add(htmlTagHolder);
        currentHtmlTagHolder = htmlTagHolder;
        
        if(previousHtmlFilterHolder != null) {
            previousHtmlFilterHolder.addHtmlTagHolder(htmlTagHolder);
        }
    }
    
    public boolean hasCurrentHtmlTagHolder() {
        return currentHtmlTagHolder != null;
    }
    
    public void addAttribute(String attribute) {
        currentHtmlTagHolder.addAttribute(attribute);
        
        if(previousHtmlFilterHolder != null) {
            previousHtmlFilterHolder.addAttribute(attribute);
        }
    }
    
    public void clearCurrentHtmlTagHolder() {
        currentHtmlTagHolder = null;
        
        if(previousHtmlFilterHolder != null) {
            previousHtmlFilterHolder.clearCurrentHtmlTagHolder();
        }
    }

    public ElementRemover getElementRemover() {
        var elementRemover = new ElementRemover();

        for(var htmlTagHolder : htmlTagHolders) {
            switch(htmlTagHolder.getHtmlFilterAction()) {
                case ACCEPT -> {
                    var attributesSet = htmlTagHolder.getAttributes();
                    var attributes = attributesSet.isEmpty() ? null : attributesSet.toArray(new String[attributesSet.size()]);

                    elementRemover.acceptElement(htmlTagHolder.getTag(), attributes);
                }
                case REMOVE -> elementRemover.removeElement(htmlTagHolder.getTag());
            }
        }
        
        return elementRemover;
    }
    
}

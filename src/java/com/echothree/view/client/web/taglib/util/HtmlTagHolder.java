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

import java.util.HashSet;
import java.util.Set;

public class HtmlTagHolder {

    private String tag;
    private HtmlFilterAction htmlFilterAction;
    
    private Set<String> attributes = new HashSet<>();
    
    public HtmlTagHolder(String tag, String action) {
        this.tag = tag;
        this.htmlFilterAction = HtmlFilterAction.translateHtmlFilterAction(action);
    }

    /**
     * Returns the tag.
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Returns the htmlFilterAction.
     * @return the htmlFilterAction
     */
    public HtmlFilterAction getHtmlFilterAction() {
        return htmlFilterAction;
    }

    public void addAttribute(String attribute) {
        attributes.add(attribute);
    }
    
    /**
     * Returns the attributes.
     * @return the attributes
     */
    public Set<String> getAttributes() {
        return attributes;
    }

}

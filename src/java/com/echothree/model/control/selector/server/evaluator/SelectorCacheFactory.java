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

package com.echothree.model.control.selector.server.evaluator;

import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.util.server.persistence.Session;

public class SelectorCacheFactory {
    
    /**
     * The Singleton instance.
     */
    private static final SelectorCacheFactory instance = new SelectorCacheFactory();
    
    protected SelectorCacheFactory() {
        super();
    }
    
    /**
     * Returns the Singleton instance of SelectorCacheFactory.
     */
    public static SelectorCacheFactory getInstance() {
        return instance;
    }
    
    public SelectorCache getSelectorCache(Session session, String selectorKindName, String selectorTypeName) {
        var selectorControl = Session.getModelController(SelectorControl.class);
        var selectorKind = selectorControl.getSelectorKindByName(selectorKindName);
        SelectorCache selectorCache;
        
        if(selectorKind != null) {
            var selectorType = selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);
            
            if(selectorType != null) {
                selectorCache = new SelectorCache(selectorType);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
        
        return selectorCache;
    }
    
}

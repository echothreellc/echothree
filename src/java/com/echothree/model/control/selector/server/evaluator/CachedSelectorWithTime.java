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

import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorTime;

public class CachedSelectorWithTime
        extends CachedSelector {
    
    SelectorTime selectorTime;
    
    /** Create a new instance of CachedSelectorWithTime */
    /** Creates a new instance of CachedSelectorWithTime */
    public CachedSelectorWithTime(Selector selector) {
        super(selector);
        
        selectorTime = selectorControl.getSelectorTimeForUpdate(selector);
        if(selectorTime == null) {
            selectorControl.createSelectorTime(selector);
            selectorTime = selectorControl.getSelectorTimeForUpdate(selector);
        }
    }
    
    public Long getLastEvaluationTime() {
        return selectorTime.getLastEvaluationTime();
    }
    
    public void setLastEvaluationTime(Long lastEvaluationTime) {
        selectorTime.setLastEvaluationTime(lastEvaluationTime);
    }
    
    public Long getMaxEntityCreatedTime() {
        return selectorTime.getMaxEntityCreatedTime();
    }
    
    public void setMaxEntityCreatedTime(Long maxEntityCreatedTime) {
        selectorTime.setMaxEntityCreatedTime(maxEntityCreatedTime);
    }
    
    public Long getMaxEntityModifiedTime() {
        return selectorTime.getMaxEntityModifiedTime();
    }
    
    public void setMaxEntityModifiedTime(Long maxEntityModifiedTime) {
        selectorTime.setMaxEntityModifiedTime(maxEntityModifiedTime);
    }
    
    public Long getMaxEntityDeletedTime() {
        return selectorTime.getMaxEntityDeletedTime();
    }
    
    public void setMaxEntityDeletedTime(Long maxEntityDeletedTime) {
        selectorTime.setMaxEntityDeletedTime(maxEntityDeletedTime);
    }
    
}

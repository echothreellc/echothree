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

package com.echothree.view.common;

import java.io.Serializable;
import java.util.List;

public class BaseChoicesBean implements Serializable {
    
    List<String> labels;
    List<String> values;
    String defaultValue;
    
    private void init(List<String> labels, List<String> values, String defaultValue) {
        this.labels = labels;
        this.values = values;
        this.defaultValue = defaultValue;
    }
    
    /** Creates a new instance of BaseChoicesBean. */
    protected BaseChoicesBean(List<String> labels, List<String> values, String defaultValue) {
        init(labels, values, defaultValue);
    }
    
    /** Creates a new instance of BaseChoicesBean. */
    protected BaseChoicesBean() {
        init(null, null, null);
    }
    
    public List<String> getLabels() {
        return labels;
    }
    
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
    
    public List<String> getValues() {
        return values;
    }
    
    public void setValues(List<String> values) {
        this.values = values;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    @Override
    public String toString() {
        var result = new StringBuilder();
        var valueIter = values.iterator();

        labels.stream().map((label) -> {
            result.append('"').append(label).append(",\" \"").append(valueIter.next()).append('"');
            return label;
        }).filter((_item) -> valueIter.hasNext()).forEach((_item) -> {
            result.append("; ");
        });

        return result.toString();
    }

}

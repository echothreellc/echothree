// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.util.server.persistence.translator;

import java.util.Map;

public class ComponentVendorTranslator {

    private Map<String, EntityInstanceTranslator> nameTranslators;

    /** Creates a new instance of ComponentVendorTranslator */
    public ComponentVendorTranslator(Map<String, EntityInstanceTranslator> nameTranslators) {
        this.nameTranslators = nameTranslators;
    }

    /**
     * Returns the nameTranslators.
     * @return the nameTranslators
     */
    public Map<String, EntityInstanceTranslator> getNameTranslators() {
        return nameTranslators;
    }

    /**
     * Sets the nameTranslators.
     * @param nameTranslators the nameTranslators to set
     */
    public void setNameTranslators(Map<String, EntityInstanceTranslator> nameTranslators) {
        this.nameTranslators = nameTranslators;
    }

}

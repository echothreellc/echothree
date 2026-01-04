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

package com.echothree.model.control.geo.common.transfer;

public class StateTransfer
        extends GeoCodeTransfer {
    
    private CountryTransfer country;
    
    /** Creates a new instance of StateTransfer */
    public StateTransfer(CountryTransfer country, String geoCodeName, GeoCodeTypeTransfer geoCodeType,
            GeoCodeScopeTransfer geoCodeScope, Boolean isDefault, Integer sortOrder, String description) {
        super(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, description);
        
        this.country = country;
    }
    
    public CountryTransfer getCountry() {
        return country;
    }
    
    public void setCountry(CountryTransfer country) {
        this.country = country;
    }
    
}

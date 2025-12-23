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

package com.echothree.control.user.geo.common.form;

import com.echothree.util.common.form.BaseForm;

public interface AddCityToCountyForm
        extends BaseForm {
    
    String getCityGeoCodeName();
    void setCityGeoCodeName(String cityGeoCodeName);
    
    /**
     * Use either this field, or the CountyName field, not both.
     */
    String getCountyGeoCodeName();
    
    /**
     * Use either this field, or the CountyName field, not both.
     */
    void setCountyGeoCodeName(String countyGeoCodeName);
    
    /**
     * Use either this field, or the CountyGeoCodeName field, not both.
     */
    String getCountyName();
    
    /**
     * Use either this field, or the CountyName field, not both.
     */
    void setCountyName(String countyName);
    
}

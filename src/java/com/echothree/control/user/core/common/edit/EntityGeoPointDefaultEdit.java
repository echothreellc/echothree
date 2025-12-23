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

package com.echothree.control.user.core.common.edit;

import com.echothree.util.common.form.BaseEdit;

public interface EntityGeoPointDefaultEdit
        extends BaseEdit {

    String getLatitude();
    void setLatitude(String latitude);

    String getLongitude();
    void setLongitude(String longitude);

    String getElevation();
    void setElevation(String elevation);

    String getElevationUnitOfMeasureTypeName();
    void setElevationUnitOfMeasureTypeName(String elevationUnitOfMeasureTypeName);

    String getAltitude();
    void setAltitude(String altitude);

    String getAltitudeUnitOfMeasureTypeName();
    void setAltitudeUnitOfMeasureTypeName(String altitudeUnitOfMeasureTypeName);
    
}

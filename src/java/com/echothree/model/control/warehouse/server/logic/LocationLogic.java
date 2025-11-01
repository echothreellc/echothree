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

package com.echothree.model.control.warehouse.server.logic;

import com.echothree.model.control.warehouse.common.exception.InvalidLocationNameException;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.warehouse.server.entity.LocationNameElement;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class LocationLogic
        extends BaseLogic {

    protected LocationLogic() {
        super();
    }

    public static LocationLogic getInstance() {
        return CDI.current().select(LocationLogic.class).get();
    }

    public void validateLocationName(final ExecutionErrorAccumulator eea, final LocationType locationType, final String locationName) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var locationNameElements = warehouseControl.getLocationNameElementsByLocationType(locationType);
        var endIndex = 0;
        var validLocationName = true;

        for(var iter = locationNameElements.iterator(); iter.hasNext() && validLocationName;) {
            var locationNameElement = (LocationNameElement)iter.next();
            var locationNameElementDetail = locationNameElement.getLastDetail();
            var validationPattern = locationNameElementDetail.getValidationPattern();

            var beginIndex = locationNameElementDetail.getOffset();

            // LocationNameElements are sorted by their starting index, the last one will always
            // be able to give the ending index (the required length) for the location name.
            endIndex = beginIndex + locationNameElementDetail.getLength();

            // If there is a validation pattern for the LocationNameElement, test that substring
            // to ensure that it matches.
            try {
                // Get the substring first, this will throw an exception if the string is too short
                // and cause the validation to fail.
                var substr = locationName.substring(beginIndex, endIndex);

                if(validationPattern != null) {
                    var pattern = Pattern.compile(validationPattern);
                    var m = pattern.matcher(substr);

                    if(!m.matches()) {
                        validLocationName = false;
                    }
                }
            } catch (IndexOutOfBoundsException ioobe) {
                validLocationName = false;
            }
        }

        // Ensure the location name is of the appropriate length based on the final LocationNameElement.
        if(locationName.length() > endIndex) {
            validLocationName = false;
        }

        if(!validLocationName) {
            handleExecutionError(InvalidLocationNameException.class, eea, ExecutionErrors.InvalidLocationName.name(), locationName);
        }
    }

}

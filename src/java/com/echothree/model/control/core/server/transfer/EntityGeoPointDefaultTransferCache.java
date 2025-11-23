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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.EntityGeoPointDefaultTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.core.server.entity.EntityGeoPointDefault;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.string.GeoPointUtils;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityGeoPointDefaultTransferCache
        extends BaseCoreTransferCache<EntityGeoPointDefault, EntityGeoPointDefaultTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    UnitOfMeasureKind elevationUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_ELEVATION);
    UnitOfMeasureKind altitudeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_ALTITUDE);
    GeoPointUtils geoPointUtils = GeoPointUtils.getInstance();
    
    /** Creates a new instance of EntityGeoPointDefaultTransferCache */
    protected EntityGeoPointDefaultTransferCache() {
        super();
    }
    
    public EntityGeoPointDefaultTransfer getEntityGeoPointDefaultTransfer(final UserVisit userVisit, final EntityGeoPointDefault entityGeoPointDefault) {
        var entityGeoPointDefaultTransfer = get(entityGeoPointDefault);
        
        if(entityGeoPointDefaultTransfer == null) {
            var entityAttribute = coreControl.getEntityAttributeTransfer(userVisit, entityGeoPointDefault.getEntityAttribute(), null);
            var unformattedLatitude = entityGeoPointDefault.getLatitude();
            var latitude = geoPointUtils.formatDegrees(unformattedLatitude);
            var unformattedLongitude = entityGeoPointDefault.getLongitude();
            var longitude = geoPointUtils.formatDegrees(unformattedLongitude);
            var elevation = formatUnitOfMeasure(userVisit, elevationUnitOfMeasureKind, entityGeoPointDefault.getElevation());
            var altitude = formatUnitOfMeasure(userVisit, altitudeUnitOfMeasureKind, entityGeoPointDefault.getAltitude());
            
            entityGeoPointDefaultTransfer = new EntityGeoPointDefaultTransfer(entityAttribute, unformattedLatitude, latitude,
                    unformattedLongitude, longitude, elevation, altitude);
            put(userVisit, entityGeoPointDefault, entityGeoPointDefaultTransfer);
        }
        
        return entityGeoPointDefaultTransfer;
    }
    
}

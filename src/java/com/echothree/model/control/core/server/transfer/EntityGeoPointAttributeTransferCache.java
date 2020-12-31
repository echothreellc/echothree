// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityGeoPointAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.string.GeoPointUtils;
import com.echothree.util.server.persistence.Session;

public class EntityGeoPointAttributeTransferCache
        extends BaseCoreTransferCache<EntityGeoPointAttribute, EntityGeoPointAttributeTransfer> {
    
    UomControl uomControl = Session.getModelController(UomControl.class);
    UnitOfMeasureKind elevationUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_ELEVATION);
    UnitOfMeasureKind altitudeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_ALTITUDE);
    GeoPointUtils geoPointUtils = GeoPointUtils.getInstance();
    
    /** Creates a new instance of EntityGeoPointAttributeTransferCache */
    public EntityGeoPointAttributeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public EntityGeoPointAttributeTransfer getEntityGeoPointAttributeTransfer(EntityGeoPointAttribute entityGeoPointAttribute, EntityInstance entityInstance) {
        EntityGeoPointAttributeTransfer entityGeoPointAttributeTransfer = get(entityGeoPointAttribute);
        
        if(entityGeoPointAttributeTransfer == null) {
            EntityAttributeTransfer entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityGeoPointAttribute.getEntityAttribute(), entityInstance) : null;
            EntityInstanceTransfer entityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, entityGeoPointAttribute.getEntityInstance(), false, false, false, false, false);
            int unformattedLatitude = entityGeoPointAttribute.getLatitude();
            String latitude = geoPointUtils.formatDegrees(unformattedLatitude);
            int unformattedLongitude = entityGeoPointAttribute.getLongitude();
            String longitude = geoPointUtils.formatDegrees(unformattedLongitude);
            String elevation = formatUnitOfMeasure(elevationUnitOfMeasureKind, entityGeoPointAttribute.getElevation());
            String altitude = formatUnitOfMeasure(altitudeUnitOfMeasureKind, entityGeoPointAttribute.getAltitude());
            
            entityGeoPointAttributeTransfer = new EntityGeoPointAttributeTransfer(entityAttribute, entityInstanceTransfer, unformattedLatitude, latitude,
                    unformattedLongitude, longitude, elevation, altitude);
            put(entityGeoPointAttribute, entityGeoPointAttributeTransfer);
        }
        
        return entityGeoPointAttributeTransfer;
    }
    
}

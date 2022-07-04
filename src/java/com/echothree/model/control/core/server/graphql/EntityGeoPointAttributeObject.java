// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.core.server.graphql;

import com.echothree.control.user.core.server.command.GetEntityAttributeCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.util.common.string.GeoPointUtils;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity geo point attribute object")
@GraphQLName("EntityGeoPointAttribute")
public class EntityGeoPointAttributeObject
        extends BaseGraphQl {
    
    private final EntityGeoPointAttribute entityGeoPointAttribute; // Always Present
    
    public EntityGeoPointAttributeObject(EntityGeoPointAttribute entityGeoPointAttribute) {
        this.entityGeoPointAttribute = entityGeoPointAttribute;
    }

    private Boolean hasEntityAttributeAccess;
    
    private boolean getHasEntityAttributeAccess(final DataFetchingEnvironment env) {
        if(hasEntityAttributeAccess == null) {
            var baseSingleEntityCommand = new GetEntityAttributeCommand(getUserVisitPK(env), null);
            
            baseSingleEntityCommand.security();
            
            hasEntityAttributeAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasEntityAttributeAccess;
    }

    @GraphQLField
    @GraphQLDescription("unformatted latitude")
    @GraphQLNonNull
    public int getUnformattedLatitude() {
        return entityGeoPointAttribute.getLatitude();
    }

    @GraphQLField
    @GraphQLDescription("latitude")
    @GraphQLNonNull
    public String getLatitude() {
        return GeoPointUtils.getInstance().formatDegrees(entityGeoPointAttribute.getLatitude());
    }

    @GraphQLField
    @GraphQLDescription("unformatted longitude")
    @GraphQLNonNull
    public int getUnformattedLongitude() {
        return entityGeoPointAttribute.getLongitude();
    }

    @GraphQLField
    @GraphQLDescription("longitude")
    @GraphQLNonNull
    public String getLongitude() {
        return GeoPointUtils.getInstance().formatDegrees(entityGeoPointAttribute.getLongitude());
    }

    @GraphQLField
    @GraphQLDescription("unformatted elevation")
    public Long getUnformattedElevation() {
        return entityGeoPointAttribute.getElevation();
    }

    @GraphQLField
    @GraphQLDescription("elevation")
    public String getElevation(final DataFetchingEnvironment env) {
        final var elevation = entityGeoPointAttribute.getElevation();
        String formattedElevation = null;

        if(elevation != null) {
            var uomControl = Session.getModelController(UomControl.class);
            var elevationUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_ELEVATION);

            formattedElevation = UnitOfMeasureUtils.getInstance().formatUnitOfMeasure(getUserVisit(env), elevationUnitOfMeasureKind, elevation);
        }

        return formattedElevation;
    }

    @GraphQLField
    @GraphQLDescription("unformatted altitude")
    public Long getUnformattedAltitude() {
        return entityGeoPointAttribute.getAltitude();
    }

    @GraphQLField
    @GraphQLDescription("altitude")
    public String getAltitude(final DataFetchingEnvironment env) {
        final var altitude = entityGeoPointAttribute.getAltitude();
        String formattedAltitude = null;

        if(altitude != null) {
            var uomControl = Session.getModelController(UomControl.class);
            var altitudeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_ALTITUDE);

            formattedAltitude = UnitOfMeasureUtils.getInstance().formatUnitOfMeasure(getUserVisit(env), altitudeUnitOfMeasureKind, altitude);
        }

        return formattedAltitude;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityGeoPointAttribute.getEntityAttribute(), entityGeoPointAttribute.getEntityInstance()) : null;
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return new EntityInstanceObject(entityGeoPointAttribute.getEntityInstance());
    }
    
}
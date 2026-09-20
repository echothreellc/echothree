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

package com.echothree.model.control.warehouse.server.control;


import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.warehouse.common.choice.LocationUseTypeChoicesBean;
import com.echothree.model.control.warehouse.common.transfer.LocationUseTypeTransfer;
import com.echothree.model.control.warehouse.server.transfer.LocationUseTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.common.pk.LocationUseTypePK;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;
import com.echothree.model.data.warehouse.server.entity.LocationUseTypeDescription;
import com.echothree.model.data.warehouse.server.factory.LocationUseTypeDescriptionFactory;
import com.echothree.model.data.warehouse.server.factory.LocationUseTypeFactory;
import static com.echothree.model.jooq.server.tables.warehouse.LocationUseTypeDescriptions.LocationUseTypeDescriptions;
import static com.echothree.model.jooq.server.tables.warehouse.LocationUseTypes.LocationUseTypes;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class LocationUseTypeControl
        extends BaseModelControl {

    /** Creates a new instance of WarehouseControl */
    protected LocationUseTypeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Location Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected LocationUseTypeFactory locationUseTypeFactory;

    @Inject
    LocationUseTypeTransferCache locationUseTypeTransferCache;

    public LocationUseType createLocationUseType(final String locationUseTypeName, final Boolean allowMultiple, final Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var locationUseType = locationUseTypeFactory.create(locationUseTypeName, allowMultiple, isDefault, sortOrder);

        sendEvent(locationUseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return locationUseType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.LocationUseType */
    public LocationUseType getLocationUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new LocationUseTypePK(entityInstance.getEntityUniqueId());

        return locationUseTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public LocationUseType getLocationUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getLocationUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public LocationUseType getLocationUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getLocationUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLocationUseTypes() {
        return session.getDslContext()
                .selectCount()
                .from(LocationUseTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public LocationUseType getLocationUseTypeByName(String locationUseTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LocationUseTypes.fields())
                .from(LocationUseTypes)
                .where(LocationUseTypes.LOCATION_USE_TYPE_NAME.eq(locationUseTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return locationUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public LocationUseType getLocationUseTypeByName(String locationUseTypeName) {
        return getLocationUseTypeByName(locationUseTypeName, EntityPermission.READ_ONLY);
    }

    public LocationUseType getLocationUseTypeByNameForUpdate(String locationUseTypeName) {
        return getLocationUseTypeByName(locationUseTypeName, EntityPermission.READ_WRITE);
    }

    public LocationUseType getDefaultLocationUseType(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LocationUseTypes.fields())
                .from(LocationUseTypes)
                .where(LocationUseTypes.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return locationUseTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    private List<LocationUseType> getLocationUseTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(LocationUseTypes.fields())
                .from(LocationUseTypes);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(LocationUseTypes.SORT_ORDER, LocationUseTypes.LOCATION_USE_TYPE_NAME),
                    LocationUseTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return locationUseTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<LocationUseType> getLocationUseTypes() {
        return getLocationUseTypes(EntityPermission.READ_ONLY);
    }

    public List<LocationUseType> getLocationUseTypesForUpdate() {
        return getLocationUseTypes(EntityPermission.READ_WRITE);
    }

    public LocationUseTypeTransfer getLocationUseTypeTransfer(UserVisit userVisit, LocationUseType locationUseType) {
        return locationUseTypeTransferCache.getLocationUseTypeTransfer(userVisit, locationUseType);
    }

    public List<LocationUseTypeTransfer> getLocationUseTypeTransfers(UserVisit userVisit, Collection<LocationUseType> locationUseTypes) {
        List<LocationUseTypeTransfer> locationUseTypeTransfers = new ArrayList<>(locationUseTypes.size());

        locationUseTypes.forEach((locationUseType) -> {
            locationUseTypeTransfers.add(locationUseTypeTransferCache.getLocationUseTypeTransfer(userVisit, locationUseType));
        });

        return locationUseTypeTransfers;
    }

    public List<LocationUseTypeTransfer> getLocationUseTypeTransfers(UserVisit userVisit) {
        return getLocationUseTypeTransfers(userVisit, getLocationUseTypes());
    }

    public LocationUseTypeChoicesBean getLocationUseTypeChoices(String defaultLocationUseTypeChoice, Language language,
            boolean allowNullChoice) {
        var locationUseTypes = getLocationUseTypes();
        var size = locationUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultLocationUseTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var locationUseType : locationUseTypes) {
            var label = getBestLocationUseTypeDescription(locationUseType, language);
            var value = locationUseType.getLocationUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultLocationUseTypeChoice != null && defaultLocationUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && locationUseType.getIsDefault()))
                defaultValue = value;
        }
        
        return new LocationUseTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Location Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected LocationUseTypeDescriptionFactory locationUseTypeDescriptionFactory;

    public LocationUseTypeDescription createLocationUseTypeDescription(LocationUseType locationUseType, Language language, String description, final BasePK createdBy) {
        var locationUseTypeDescription =  locationUseTypeDescriptionFactory.create(locationUseType, language, description);

        sendEvent(locationUseType.getPrimaryKey(), EventTypes.MODIFY, locationUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return locationUseTypeDescription;
    }
    
    public LocationUseTypeDescription getLocationUseTypeDescription(LocationUseType locationUseType, Language language) {
        var query = session.getDslContext()
                .select(LocationUseTypeDescriptions.fields())
                .from(LocationUseTypeDescriptions)
                .where(LocationUseTypeDescriptions.LOCATION_USE_TYPE.eq(locationUseType.getPrimaryKey()),
                        LocationUseTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));
        
        return locationUseTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestLocationUseTypeDescription(LocationUseType locationUseType, Language language) {
        String description;
        var locationUseTypeDescription = getLocationUseTypeDescription(locationUseType, language);
        
        if(locationUseTypeDescription == null && !language.getIsDefault()) {
            locationUseTypeDescription = getLocationUseTypeDescription(locationUseType, partyControl.getDefaultLanguage());
        }
        
        if(locationUseTypeDescription == null) {
            description = locationUseType.getLocationUseTypeName();
        } else {
            description = locationUseTypeDescription.getDescription();
        }
        
        return description;
    }
    
}

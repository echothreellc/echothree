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

package com.echothree.model.control.warehouse.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.warehouse.common.choice.LocationUseTypeChoicesBean;
import com.echothree.model.control.warehouse.common.transfer.LocationUseTypeTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.common.pk.LocationUseTypePK;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;
import com.echothree.model.data.warehouse.server.entity.LocationUseTypeDescription;
import com.echothree.model.data.warehouse.server.factory.LocationUseTypeDescriptionFactory;
import com.echothree.model.data.warehouse.server.factory.LocationUseTypeFactory;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LocationUseTypeControl
        extends BaseWarehouseControl {

    /** Creates a new instance of WarehouseControl */
    protected LocationUseTypeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Location Use Types
    // --------------------------------------------------------------------------------
    
    public LocationUseType createLocationUseType(final String locationUseTypeName, final Boolean allowMultiple, final Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var locationUseType = LocationUseTypeFactory.getInstance().create(locationUseTypeName, allowMultiple, isDefault, sortOrder);

        sendEvent(locationUseType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return locationUseType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.LocationUseType */
    public LocationUseType getLocationUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new LocationUseTypePK(entityInstance.getEntityUniqueId());

        return LocationUseTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public LocationUseType getLocationUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getLocationUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public LocationUseType getLocationUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getLocationUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLocationUseTypes() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM locationusetypes
                """);
    }

    public LocationUseType getLocationUseTypeByName(String locationUseTypeName, EntityPermission entityPermission) {
        LocationUseType locationUseType;

        try {
            var ps = LocationUseTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM locationusetypes " +
                            "WHERE locutyp_locationusetypename = ?");

            ps.setString(1, locationUseTypeName);

            locationUseType = LocationUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return locationUseType;
    }

    public LocationUseType getLocationUseTypeByName(String locationUseTypeName) {
        return getLocationUseTypeByName(locationUseTypeName, EntityPermission.READ_ONLY);
    }

    public LocationUseType getLocationUseTypeByNameForUpdate(String locationUseTypeName) {
        return getLocationUseTypeByName(locationUseTypeName, EntityPermission.READ_WRITE);
    }

    public LocationUseType getDefaultLocationUseType(final EntityPermission entityPermission) {
        var ps = LocationUseTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                        "FROM locationusetypes " +
                        "WHERE locutyp_isdefault = 1");

        return LocationUseTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }

    private List<LocationUseType> getLocationUseTypes(EntityPermission entityPermission) {
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM locationusetypes " +
                    "ORDER BY locutyp_sortorder, locutyp_locationusetypename " +
                    "_LIMIT_";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM locationusetypes " +
                    "FOR UPDATE";
        }

        var ps = LocationUseTypeFactory.getInstance().prepareStatement(query);

        return LocationUseTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }

    public List<LocationUseType> getLocationUseTypes() {
        return getLocationUseTypes(EntityPermission.READ_ONLY);
    }

    public List<LocationUseType> getLocationUseTypesForUpdate() {
        return getLocationUseTypes(EntityPermission.READ_WRITE);
    }

    public LocationUseTypeTransfer getLocationUseTypeTransfer(UserVisit userVisit, LocationUseType locationUseType) {
        return getWarehouseTransferCaches(userVisit).getLocationUseTypeTransferCache().getLocationUseTypeTransfer(locationUseType);
    }

    public List<LocationUseTypeTransfer> getLocationUseTypeTransfers(UserVisit userVisit, Collection<LocationUseType> locationUseTypes) {
        List<LocationUseTypeTransfer> locationUseTypeTransfers = new ArrayList<>(locationUseTypes.size());

        locationUseTypes.forEach((locationUseType) -> {
            locationUseTypeTransfers.add(getWarehouseTransferCaches(userVisit).getLocationUseTypeTransferCache().getLocationUseTypeTransfer(locationUseType));
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
    
    public LocationUseTypeDescription createLocationUseTypeDescription(LocationUseType locationUseType, Language language, String description, final BasePK createdBy) {
        var locationUseTypeDescription =  LocationUseTypeDescriptionFactory.getInstance().create(locationUseType, language, description);

        sendEvent(locationUseType.getPrimaryKey(), EventTypes.MODIFY, locationUseTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return locationUseTypeDescription;
    }
    
    public LocationUseTypeDescription getLocationUseTypeDescription(LocationUseType locationUseType, Language language) {
        LocationUseTypeDescription locationUseTypeDescription;
        
        try {
            var ps = LocationUseTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM locationusetypedescriptions " +
                    "WHERE locutypd_locutyp_locationusetypeid = ? AND locutypd_lang_languageid = ?");
            
            ps.setLong(1, locationUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            locationUseTypeDescription = LocationUseTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationUseTypeDescription;
    }
    
    public String getBestLocationUseTypeDescription(LocationUseType locationUseType, Language language) {
        String description;
        var locationUseTypeDescription = getLocationUseTypeDescription(locationUseType, language);
        
        if(locationUseTypeDescription == null && !language.getIsDefault()) {
            locationUseTypeDescription = getLocationUseTypeDescription(locationUseType, getPartyControl().getDefaultLanguage());
        }
        
        if(locationUseTypeDescription == null) {
            description = locationUseType.getLocationUseTypeName();
        } else {
            description = locationUseTypeDescription.getDescription();
        }
        
        return description;
    }
    
}

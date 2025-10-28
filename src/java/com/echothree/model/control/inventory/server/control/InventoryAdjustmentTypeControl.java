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

package com.echothree.model.control.inventory.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.common.choice.InventoryAdjustmentTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryAdjustmentTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryAdjustmentTypeTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryAdjustmentTypePK;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentTypeDescription;
import com.echothree.model.data.inventory.server.factory.InventoryAdjustmentTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryAdjustmentTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryAdjustmentTypeFactory;
import com.echothree.model.data.inventory.server.value.InventoryAdjustmentTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryAdjustmentTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InventoryAdjustmentTypeControl
        extends BaseInventoryControl {

    /** Creates a new instance of InventoryControl */
    public InventoryAdjustmentTypeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Transaction Types
    // --------------------------------------------------------------------------------

    public InventoryAdjustmentType createInventoryAdjustmentType(String inventoryAdjustmentTypeName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInventoryAdjustmentType = getDefaultInventoryAdjustmentType();
        var defaultFound = defaultInventoryAdjustmentType != null;

        if(defaultFound && isDefault) {
            var defaultInventoryAdjustmentTypeDetailValue = getDefaultInventoryAdjustmentTypeDetailValueForUpdate();

            defaultInventoryAdjustmentTypeDetailValue.setIsDefault(false);
            updateInventoryAdjustmentTypeFromValue(defaultInventoryAdjustmentTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryAdjustmentType = InventoryAdjustmentTypeFactory.getInstance().create();
        var inventoryAdjustmentTypeDetail = InventoryAdjustmentTypeDetailFactory.getInstance().create(inventoryAdjustmentType,
                inventoryAdjustmentTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        inventoryAdjustmentType = InventoryAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryAdjustmentType.getPrimaryKey());
        inventoryAdjustmentType.setActiveDetail(inventoryAdjustmentTypeDetail);
        inventoryAdjustmentType.setLastDetail(inventoryAdjustmentTypeDetail);
        inventoryAdjustmentType.store();

        sendEvent(inventoryAdjustmentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryAdjustmentType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryAdjustmentType */
    public InventoryAdjustmentType getInventoryAdjustmentTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryAdjustmentTypePK(entityInstance.getEntityUniqueId());

        return InventoryAdjustmentTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryAdjustmentTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryAdjustmentTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByPK(InventoryAdjustmentTypePK pk) {
        return InventoryAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countInventoryAdjustmentTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM inventoryadjustmenttypes, inventoryadjustmenttypedetails " +
                "WHERE invadjtyp_activedetailid = invadjtypdt_inventoryadjustmenttypedetailid");
    }

    private static final Map<EntityPermission, String> getInventoryAdjustmentTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM inventoryadjustmenttypes, inventoryadjustmenttypedetails " +
                "WHERE invadjtyp_activedetailid = invadjtypdt_inventoryadjustmenttypedetailid " +
                "AND invadjtypdt_inventoryadjustmenttypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM inventoryadjustmenttypes, inventoryadjustmenttypedetails " +
                "WHERE invadjtyp_activedetailid = invadjtypdt_inventoryadjustmenttypedetailid " +
                "AND invadjtypdt_inventoryadjustmenttypename = ? " +
                "FOR UPDATE");
        getInventoryAdjustmentTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByName(String inventoryAdjustmentTypeName, EntityPermission entityPermission) {
        return InventoryAdjustmentTypeFactory.getInstance().getEntityFromQuery(entityPermission, getInventoryAdjustmentTypeByNameQueries, inventoryAdjustmentTypeName);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByName(String inventoryAdjustmentTypeName) {
        return getInventoryAdjustmentTypeByName(inventoryAdjustmentTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryAdjustmentType getInventoryAdjustmentTypeByNameForUpdate(String inventoryAdjustmentTypeName) {
        return getInventoryAdjustmentTypeByName(inventoryAdjustmentTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentTypeDetailValue getInventoryAdjustmentTypeDetailValueForUpdate(InventoryAdjustmentType inventoryAdjustmentType) {
        return inventoryAdjustmentType == null? null: inventoryAdjustmentType.getLastDetailForUpdate().getInventoryAdjustmentTypeDetailValue().clone();
    }

    public InventoryAdjustmentTypeDetailValue getInventoryAdjustmentTypeDetailValueByNameForUpdate(String inventoryAdjustmentTypeName) {
        return getInventoryAdjustmentTypeDetailValueForUpdate(getInventoryAdjustmentTypeByNameForUpdate(inventoryAdjustmentTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultInventoryAdjustmentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM inventoryadjustmenttypes, inventoryadjustmenttypedetails " +
                "WHERE invadjtyp_activedetailid = invadjtypdt_inventoryadjustmenttypedetailid " +
                "AND invadjtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM inventoryadjustmenttypes, inventoryadjustmenttypedetails " +
                "WHERE invadjtyp_activedetailid = invadjtypdt_inventoryadjustmenttypedetailid " +
                "AND invadjtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultInventoryAdjustmentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public InventoryAdjustmentType getDefaultInventoryAdjustmentType(EntityPermission entityPermission) {
        return InventoryAdjustmentTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultInventoryAdjustmentTypeQueries);
    }

    public InventoryAdjustmentType getDefaultInventoryAdjustmentType() {
        return getDefaultInventoryAdjustmentType(EntityPermission.READ_ONLY);
    }

    public InventoryAdjustmentType getDefaultInventoryAdjustmentTypeForUpdate() {
        return getDefaultInventoryAdjustmentType(EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentTypeDetailValue getDefaultInventoryAdjustmentTypeDetailValueForUpdate() {
        return getDefaultInventoryAdjustmentTypeForUpdate().getLastDetailForUpdate().getInventoryAdjustmentTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getInventoryAdjustmentTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM inventoryadjustmenttypes, inventoryadjustmenttypedetails " +
                "WHERE invadjtyp_activedetailid = invadjtypdt_inventoryadjustmenttypedetailid " +
                "ORDER BY invadjtypdt_sortorder, invadjtypdt_inventoryadjustmenttypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM inventoryadjustmenttypes, inventoryadjustmenttypedetails " +
                "WHERE invadjtyp_activedetailid = invadjtypdt_inventoryadjustmenttypedetailid " +
                "FOR UPDATE");
        getInventoryAdjustmentTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InventoryAdjustmentType> getInventoryAdjustmentTypes(EntityPermission entityPermission) {
        return InventoryAdjustmentTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getInventoryAdjustmentTypesQueries);
    }

    public List<InventoryAdjustmentType> getInventoryAdjustmentTypes() {
        return getInventoryAdjustmentTypes(EntityPermission.READ_ONLY);
    }

    public List<InventoryAdjustmentType> getInventoryAdjustmentTypesForUpdate() {
        return getInventoryAdjustmentTypes(EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentTypeTransfer getInventoryAdjustmentTypeTransfer(UserVisit userVisit, InventoryAdjustmentType inventoryAdjustmentType) {
        return getInventoryTransferCaches(userVisit).getInventoryAdjustmentTypeTransferCache().getTransfer(inventoryAdjustmentType);
    }

    public List<InventoryAdjustmentTypeTransfer> getInventoryAdjustmentTypeTransfers(UserVisit userVisit, Collection<InventoryAdjustmentType> inventoryAdjustmentTypes) {
        List<InventoryAdjustmentTypeTransfer> inventoryAdjustmentTypeTransfers = new ArrayList<>(inventoryAdjustmentTypes.size());
        var inventoryAdjustmentTypeTransferCache = getInventoryTransferCaches(userVisit).getInventoryAdjustmentTypeTransferCache();

        inventoryAdjustmentTypes.forEach((inventoryAdjustmentType) ->
                inventoryAdjustmentTypeTransfers.add(inventoryAdjustmentTypeTransferCache.getTransfer(inventoryAdjustmentType))
        );

        return inventoryAdjustmentTypeTransfers;
    }

    public List<InventoryAdjustmentTypeTransfer> getInventoryAdjustmentTypeTransfers(UserVisit userVisit) {
        return getInventoryAdjustmentTypeTransfers(userVisit, getInventoryAdjustmentTypes());
    }

    public InventoryAdjustmentTypeChoicesBean getInventoryAdjustmentTypeChoices(String defaultInventoryAdjustmentTypeChoice,
            Language language, boolean allowNullChoice) {
        var inventoryAdjustmentTypes = getInventoryAdjustmentTypes();
        var size = inventoryAdjustmentTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryAdjustmentTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryAdjustmentType : inventoryAdjustmentTypes) {
            var inventoryAdjustmentTypeDetail = inventoryAdjustmentType.getLastDetail();

            var label = getBestInventoryAdjustmentTypeDescription(inventoryAdjustmentType, language);
            var value = inventoryAdjustmentTypeDetail.getInventoryAdjustmentTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryAdjustmentTypeChoice != null && defaultInventoryAdjustmentTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryAdjustmentTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryAdjustmentTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryAdjustmentTypeFromValue(InventoryAdjustmentTypeDetailValue inventoryAdjustmentTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(inventoryAdjustmentTypeDetailValue.hasBeenModified()) {
            var inventoryAdjustmentType = InventoryAdjustmentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryAdjustmentTypeDetailValue.getInventoryAdjustmentTypePK());
            var inventoryAdjustmentTypeDetail = inventoryAdjustmentType.getActiveDetailForUpdate();

            inventoryAdjustmentTypeDetail.setThruTime(session.START_TIME_LONG);
            inventoryAdjustmentTypeDetail.store();

            var inventoryAdjustmentTypePK = inventoryAdjustmentTypeDetail.getInventoryAdjustmentTypePK(); // Not updated
            var inventoryAdjustmentTypeName = inventoryAdjustmentTypeDetailValue.getInventoryAdjustmentTypeName();
            var isDefault = inventoryAdjustmentTypeDetailValue.getIsDefault();
            var sortOrder = inventoryAdjustmentTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryAdjustmentType = getDefaultInventoryAdjustmentType();
                var defaultFound = defaultInventoryAdjustmentType != null && !defaultInventoryAdjustmentType.equals(inventoryAdjustmentType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryAdjustmentTypeDetailValue = getDefaultInventoryAdjustmentTypeDetailValueForUpdate();

                    defaultInventoryAdjustmentTypeDetailValue.setIsDefault(false);
                    updateInventoryAdjustmentTypeFromValue(defaultInventoryAdjustmentTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryAdjustmentTypeDetail = InventoryAdjustmentTypeDetailFactory.getInstance().create(inventoryAdjustmentTypePK,
                    inventoryAdjustmentTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            inventoryAdjustmentType.setActiveDetail(inventoryAdjustmentTypeDetail);
            inventoryAdjustmentType.setLastDetail(inventoryAdjustmentTypeDetail);

            sendEvent(inventoryAdjustmentTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryAdjustmentTypeFromValue(InventoryAdjustmentTypeDetailValue inventoryAdjustmentTypeDetailValue, BasePK updatedBy) {
        updateInventoryAdjustmentTypeFromValue(inventoryAdjustmentTypeDetailValue, true, updatedBy);
    }

    private void deleteInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType, boolean checkDefault, BasePK deletedBy) {
        var inventoryAdjustmentTypeDetail = inventoryAdjustmentType.getLastDetailForUpdate();

        deleteInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(inventoryAdjustmentType, deletedBy);
        // TODO: deleteInventoryTransactionsByInventoryAdjustmentType(inventoryAdjustmentType, deletedBy);

        inventoryAdjustmentTypeDetail.setThruTime(session.START_TIME_LONG);
        inventoryAdjustmentType.setActiveDetail(null);
        inventoryAdjustmentType.store();

        if(checkDefault) {
        // Check for default, and pick one if necessary
            var defaultInventoryAdjustmentType = getDefaultInventoryAdjustmentType();
            if(defaultInventoryAdjustmentType == null) {
                var inventoryAdjustmentTypes = getInventoryAdjustmentTypesForUpdate();

                if(!inventoryAdjustmentTypes.isEmpty()) {
                    var iter = inventoryAdjustmentTypes.iterator();
                    if(iter.hasNext()) {
                        defaultInventoryAdjustmentType = iter.next();
                    }
                    var inventoryAdjustmentTypeDetailValue = Objects.requireNonNull(defaultInventoryAdjustmentType).getLastDetailForUpdate().getInventoryAdjustmentTypeDetailValue().clone();

                    inventoryAdjustmentTypeDetailValue.setIsDefault(true);
                    updateInventoryAdjustmentTypeFromValue(inventoryAdjustmentTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(inventoryAdjustmentType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType, BasePK deletedBy) {
        deleteInventoryAdjustmentType(inventoryAdjustmentType, true, deletedBy);
    }

    private void deleteInventoryAdjustmentTypes(List<InventoryAdjustmentType> inventoryAdjustmentTypes, boolean checkDefault, BasePK deletedBy) {
        inventoryAdjustmentTypes.forEach((inventoryAdjustmentType) -> deleteInventoryAdjustmentType(inventoryAdjustmentType, checkDefault, deletedBy));
    }

    public void deleteInventoryAdjustmentTypes(List<InventoryAdjustmentType> inventoryAdjustmentTypes, BasePK deletedBy) {
        deleteInventoryAdjustmentTypes(inventoryAdjustmentTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Type Descriptions
    // --------------------------------------------------------------------------------

    public InventoryAdjustmentTypeDescription createInventoryAdjustmentTypeDescription(InventoryAdjustmentType inventoryAdjustmentType, Language language, String description, BasePK createdBy) {
        var inventoryAdjustmentTypeDescription = InventoryAdjustmentTypeDescriptionFactory.getInstance().create(inventoryAdjustmentType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(inventoryAdjustmentType.getPrimaryKey(), EventTypes.MODIFY, inventoryAdjustmentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryAdjustmentTypeDescription;
    }

    private static final Map<EntityPermission, String> getInventoryAdjustmentTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM inventoryadjustmenttypedescriptions " +
                "WHERE invadjtypd_invadjtyp_inventoryadjustmenttypeid = ? AND invadjtypd_lang_languageid = ? AND invadjtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM inventoryadjustmenttypedescriptions " +
                "WHERE invadjtypd_invadjtyp_inventoryadjustmenttypeid = ? AND invadjtypd_lang_languageid = ? AND invadjtypd_thrutime = ? " +
                "FOR UPDATE");
        getInventoryAdjustmentTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private InventoryAdjustmentTypeDescription getInventoryAdjustmentTypeDescription(InventoryAdjustmentType inventoryAdjustmentType, Language language, EntityPermission entityPermission) {
        return InventoryAdjustmentTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getInventoryAdjustmentTypeDescriptionQueries,
                inventoryAdjustmentType, language, Session.MAX_TIME);
    }

    public InventoryAdjustmentTypeDescription getInventoryAdjustmentTypeDescription(InventoryAdjustmentType inventoryAdjustmentType, Language language) {
        return getInventoryAdjustmentTypeDescription(inventoryAdjustmentType, language, EntityPermission.READ_ONLY);
    }

    public InventoryAdjustmentTypeDescription getInventoryAdjustmentTypeDescriptionForUpdate(InventoryAdjustmentType inventoryAdjustmentType, Language language) {
        return getInventoryAdjustmentTypeDescription(inventoryAdjustmentType, language, EntityPermission.READ_WRITE);
    }

    public InventoryAdjustmentTypeDescriptionValue getInventoryAdjustmentTypeDescriptionValue(InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription) {
        return inventoryAdjustmentTypeDescription == null? null: inventoryAdjustmentTypeDescription.getInventoryAdjustmentTypeDescriptionValue().clone();
    }

    public InventoryAdjustmentTypeDescriptionValue getInventoryAdjustmentTypeDescriptionValueForUpdate(InventoryAdjustmentType inventoryAdjustmentType, Language language) {
        return getInventoryAdjustmentTypeDescriptionValue(getInventoryAdjustmentTypeDescriptionForUpdate(inventoryAdjustmentType, language));
    }

    private static final Map<EntityPermission, String> getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM inventoryadjustmenttypedescriptions, languages " +
                "WHERE invadjtypd_invadjtyp_inventoryadjustmenttypeid = ? AND invadjtypd_thrutime = ? AND invadjtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM inventoryadjustmenttypedescriptions " +
                "WHERE invadjtypd_invadjtyp_inventoryadjustmenttypeid = ? AND invadjtypd_thrutime = ? " +
                "FOR UPDATE");
        getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InventoryAdjustmentTypeDescription> getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType, EntityPermission entityPermission) {
        return InventoryAdjustmentTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentTypeQueries,
                inventoryAdjustmentType, Session.MAX_TIME);
    }

    public List<InventoryAdjustmentTypeDescription> getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType) {
        return getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(inventoryAdjustmentType, EntityPermission.READ_ONLY);
    }

    public List<InventoryAdjustmentTypeDescription> getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentTypeForUpdate(InventoryAdjustmentType inventoryAdjustmentType) {
        return getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(inventoryAdjustmentType, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryAdjustmentTypeDescription(InventoryAdjustmentType inventoryAdjustmentType, Language language) {
        String description;
        var inventoryAdjustmentTypeDescription = getInventoryAdjustmentTypeDescription(inventoryAdjustmentType, language);

        if(inventoryAdjustmentTypeDescription == null && !language.getIsDefault()) {
            inventoryAdjustmentTypeDescription = getInventoryAdjustmentTypeDescription(inventoryAdjustmentType, getPartyControl().getDefaultLanguage());
        }

        if(inventoryAdjustmentTypeDescription == null) {
            description = inventoryAdjustmentType.getLastDetail().getInventoryAdjustmentTypeName();
        } else {
            description = inventoryAdjustmentTypeDescription.getDescription();
        }

        return description;
    }

    public InventoryAdjustmentTypeDescriptionTransfer getInventoryAdjustmentTypeDescriptionTransfer(UserVisit userVisit, InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription) {
        return getInventoryTransferCaches(userVisit).getInventoryAdjustmentTypeDescriptionTransferCache().getTransfer(inventoryAdjustmentTypeDescription);
    }

    public List<InventoryAdjustmentTypeDescriptionTransfer> getInventoryAdjustmentTypeDescriptionTransfersByInventoryAdjustmentType(UserVisit userVisit, InventoryAdjustmentType inventoryAdjustmentType) {
        var inventoryAdjustmentTypeDescriptions = getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(inventoryAdjustmentType);
        List<InventoryAdjustmentTypeDescriptionTransfer> inventoryAdjustmentTypeDescriptionTransfers = new ArrayList<>(inventoryAdjustmentTypeDescriptions.size());
        var inventoryAdjustmentTypeDescriptionTransferCache = getInventoryTransferCaches(userVisit).getInventoryAdjustmentTypeDescriptionTransferCache();

        inventoryAdjustmentTypeDescriptions.forEach((inventoryAdjustmentTypeDescription) ->
                inventoryAdjustmentTypeDescriptionTransfers.add(inventoryAdjustmentTypeDescriptionTransferCache.getTransfer(inventoryAdjustmentTypeDescription))
        );

        return inventoryAdjustmentTypeDescriptionTransfers;
    }

    public void updateInventoryAdjustmentTypeDescriptionFromValue(InventoryAdjustmentTypeDescriptionValue inventoryAdjustmentTypeDescriptionValue, BasePK updatedBy) {
        if(inventoryAdjustmentTypeDescriptionValue.hasBeenModified()) {
            var inventoryAdjustmentTypeDescription = InventoryAdjustmentTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryAdjustmentTypeDescriptionValue.getPrimaryKey());

            inventoryAdjustmentTypeDescription.setThruTime(session.START_TIME_LONG);
            inventoryAdjustmentTypeDescription.store();

            var inventoryAdjustmentType = inventoryAdjustmentTypeDescription.getInventoryAdjustmentType();
            var language = inventoryAdjustmentTypeDescription.getLanguage();
            var description = inventoryAdjustmentTypeDescriptionValue.getDescription();

            inventoryAdjustmentTypeDescription = InventoryAdjustmentTypeDescriptionFactory.getInstance().create(inventoryAdjustmentType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(inventoryAdjustmentType.getPrimaryKey(), EventTypes.MODIFY, inventoryAdjustmentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryAdjustmentTypeDescription(InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription, BasePK deletedBy) {
        inventoryAdjustmentTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(inventoryAdjustmentTypeDescription.getInventoryAdjustmentTypePK(), EventTypes.MODIFY, inventoryAdjustmentTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType, BasePK deletedBy) {
        var inventoryAdjustmentTypeDescriptions = getInventoryAdjustmentTypeDescriptionsByInventoryAdjustmentTypeForUpdate(inventoryAdjustmentType);

        inventoryAdjustmentTypeDescriptions.forEach((inventoryAdjustmentTypeDescription) -> 
                deleteInventoryAdjustmentTypeDescription(inventoryAdjustmentTypeDescription, deletedBy)
        );
    }

}

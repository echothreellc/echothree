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
import com.echothree.model.control.inventory.common.choice.InventoryTransactionTypeChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionTypeDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionTypeTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryTransactionTypePK;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTypeDescription;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionTypeFactory;
import com.echothree.model.data.inventory.server.value.InventoryTransactionTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
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
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class InventoryTransactionTypeControl
        extends BaseInventoryControl {

    /** Creates a new instance of InventoryControl */
    protected InventoryTransactionTypeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Transaction Types
    // --------------------------------------------------------------------------------

    public InventoryTransactionType createInventoryTransactionType(String inventoryTransactionTypeName, SequenceType inventoryTransactionSequenceType, Workflow inventoryTransactionWorkflow,
            WorkflowEntrance inventoryTransactionWorkflowEntrance, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInventoryTransactionType = getDefaultInventoryTransactionType();
        var defaultFound = defaultInventoryTransactionType != null;

        if(defaultFound && isDefault) {
            var defaultInventoryTransactionTypeDetailValue = getDefaultInventoryTransactionTypeDetailValueForUpdate();

            defaultInventoryTransactionTypeDetailValue.setIsDefault(false);
            updateInventoryTransactionTypeFromValue(defaultInventoryTransactionTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryTransactionType = InventoryTransactionTypeFactory.getInstance().create();
        var inventoryTransactionTypeDetail = InventoryTransactionTypeDetailFactory.getInstance().create(inventoryTransactionType, inventoryTransactionTypeName, inventoryTransactionSequenceType,
                inventoryTransactionWorkflow, inventoryTransactionWorkflowEntrance, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        inventoryTransactionType = InventoryTransactionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryTransactionType.getPrimaryKey());
        inventoryTransactionType.setActiveDetail(inventoryTransactionTypeDetail);
        inventoryTransactionType.setLastDetail(inventoryTransactionTypeDetail);
        inventoryTransactionType.store();

        sendEvent(inventoryTransactionType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryTransactionType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryTransactionType */
    public InventoryTransactionType getInventoryTransactionTypeByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryTransactionTypePK(entityInstance.getEntityUniqueId());

        return InventoryTransactionTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public InventoryTransactionType getInventoryTransactionTypeByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryTransactionTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionType getInventoryTransactionTypeByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryTransactionTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionType getInventoryTransactionTypeByPK(InventoryTransactionTypePK pk) {
        return InventoryTransactionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countInventoryTransactionTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM inventorytransactiontypes, inventorytransactiontypedetails " +
                "WHERE invtnxtyp_activedetailid = invtnxtypdt_inventorytransactiontypedetailid");
    }

    private static final Map<EntityPermission, String> getInventoryTransactionTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM inventorytransactiontypes, inventorytransactiontypedetails " +
                "WHERE invtnxtyp_activedetailid = invtnxtypdt_inventorytransactiontypedetailid " +
                "AND invtnxtypdt_inventorytransactiontypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM inventorytransactiontypes, inventorytransactiontypedetails " +
                "WHERE invtnxtyp_activedetailid = invtnxtypdt_inventorytransactiontypedetailid " +
                "AND invtnxtypdt_inventorytransactiontypename = ? " +
                "FOR UPDATE");
        getInventoryTransactionTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public InventoryTransactionType getInventoryTransactionTypeByName(String inventoryTransactionTypeName, EntityPermission entityPermission) {
        return InventoryTransactionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getInventoryTransactionTypeByNameQueries, inventoryTransactionTypeName);
    }

    public InventoryTransactionType getInventoryTransactionTypeByName(String inventoryTransactionTypeName) {
        return getInventoryTransactionTypeByName(inventoryTransactionTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionType getInventoryTransactionTypeByNameForUpdate(String inventoryTransactionTypeName) {
        return getInventoryTransactionTypeByName(inventoryTransactionTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTypeDetailValue getInventoryTransactionTypeDetailValueForUpdate(InventoryTransactionType inventoryTransactionType) {
        return inventoryTransactionType == null? null: inventoryTransactionType.getLastDetailForUpdate().getInventoryTransactionTypeDetailValue().clone();
    }

    public InventoryTransactionTypeDetailValue getInventoryTransactionTypeDetailValueByNameForUpdate(String inventoryTransactionTypeName) {
        return getInventoryTransactionTypeDetailValueForUpdate(getInventoryTransactionTypeByNameForUpdate(inventoryTransactionTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultInventoryTransactionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM inventorytransactiontypes, inventorytransactiontypedetails " +
                "WHERE invtnxtyp_activedetailid = invtnxtypdt_inventorytransactiontypedetailid " +
                "AND invtnxtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM inventorytransactiontypes, inventorytransactiontypedetails " +
                "WHERE invtnxtyp_activedetailid = invtnxtypdt_inventorytransactiontypedetailid " +
                "AND invtnxtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultInventoryTransactionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public InventoryTransactionType getDefaultInventoryTransactionType(EntityPermission entityPermission) {
        return InventoryTransactionTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultInventoryTransactionTypeQueries);
    }

    public InventoryTransactionType getDefaultInventoryTransactionType() {
        return getDefaultInventoryTransactionType(EntityPermission.READ_ONLY);
    }

    public InventoryTransactionType getDefaultInventoryTransactionTypeForUpdate() {
        return getDefaultInventoryTransactionType(EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTypeDetailValue getDefaultInventoryTransactionTypeDetailValueForUpdate() {
        return getDefaultInventoryTransactionTypeForUpdate().getLastDetailForUpdate().getInventoryTransactionTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getInventoryTransactionTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM inventorytransactiontypes, inventorytransactiontypedetails " +
                "WHERE invtnxtyp_activedetailid = invtnxtypdt_inventorytransactiontypedetailid " +
                "ORDER BY invtnxtypdt_sortorder, invtnxtypdt_inventorytransactiontypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM inventorytransactiontypes, inventorytransactiontypedetails " +
                "WHERE invtnxtyp_activedetailid = invtnxtypdt_inventorytransactiontypedetailid " +
                "FOR UPDATE");
        getInventoryTransactionTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InventoryTransactionType> getInventoryTransactionTypes(EntityPermission entityPermission) {
        return InventoryTransactionTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getInventoryTransactionTypesQueries);
    }

    public List<InventoryTransactionType> getInventoryTransactionTypes() {
        return getInventoryTransactionTypes(EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionType> getInventoryTransactionTypesForUpdate() {
        return getInventoryTransactionTypes(EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTypeTransfer getInventoryTransactionTypeTransfer(UserVisit userVisit, InventoryTransactionType inventoryTransactionType) {
        return inventoryTransactionTypeTransferCache.getTransfer(userVisit, inventoryTransactionType);
    }

    public List<InventoryTransactionTypeTransfer> getInventoryTransactionTypeTransfers(UserVisit userVisit, Collection<InventoryTransactionType> inventoryTransactionTypes) {
        List<InventoryTransactionTypeTransfer> inventoryTransactionTypeTransfers = new ArrayList<>(inventoryTransactionTypes.size());

        inventoryTransactionTypes.forEach((inventoryTransactionType) ->
                inventoryTransactionTypeTransfers.add(inventoryTransactionTypeTransferCache.getTransfer(userVisit, inventoryTransactionType))
        );

        return inventoryTransactionTypeTransfers;
    }

    public List<InventoryTransactionTypeTransfer> getInventoryTransactionTypeTransfers(UserVisit userVisit) {
        return getInventoryTransactionTypeTransfers(userVisit, getInventoryTransactionTypes());
    }

    public InventoryTransactionTypeChoicesBean getInventoryTransactionTypeChoices(String defaultInventoryTransactionTypeChoice,
            Language language, boolean allowNullChoice) {
        var inventoryTransactionTypes = getInventoryTransactionTypes();
        var size = inventoryTransactionTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryTransactionTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryTransactionType : inventoryTransactionTypes) {
            var inventoryTransactionTypeDetail = inventoryTransactionType.getLastDetail();

            var label = getBestInventoryTransactionTypeDescription(inventoryTransactionType, language);
            var value = inventoryTransactionTypeDetail.getInventoryTransactionTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryTransactionTypeChoice != null && defaultInventoryTransactionTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryTransactionTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryTransactionTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryTransactionTypeFromValue(InventoryTransactionTypeDetailValue inventoryTransactionTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(inventoryTransactionTypeDetailValue.hasBeenModified()) {
            var inventoryTransactionType = InventoryTransactionTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryTransactionTypeDetailValue.getInventoryTransactionTypePK());
            var inventoryTransactionTypeDetail = inventoryTransactionType.getActiveDetailForUpdate();

            inventoryTransactionTypeDetail.setThruTime(session.getStartTime());
            inventoryTransactionTypeDetail.store();

            var inventoryTransactionTypePK = inventoryTransactionTypeDetail.getInventoryTransactionTypePK(); // Not updated
            var inventoryTransactionTypeName = inventoryTransactionTypeDetailValue.getInventoryTransactionTypeName();
            var inventoryTransactionSequenceTypePK = inventoryTransactionTypeDetailValue.getInventoryTransactionSequenceTypePK();
            var inventoryTransactionWorkflowPK = inventoryTransactionTypeDetailValue.getInventoryTransactionWorkflowPK();
            var inventoryTransactionWorkflowEntrancePK = inventoryTransactionTypeDetailValue.getInventoryTransactionWorkflowEntrancePK();
            var isDefault = inventoryTransactionTypeDetailValue.getIsDefault();
            var sortOrder = inventoryTransactionTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryTransactionType = getDefaultInventoryTransactionType();
                var defaultFound = defaultInventoryTransactionType != null && !defaultInventoryTransactionType.equals(inventoryTransactionType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryTransactionTypeDetailValue = getDefaultInventoryTransactionTypeDetailValueForUpdate();

                    defaultInventoryTransactionTypeDetailValue.setIsDefault(false);
                    updateInventoryTransactionTypeFromValue(defaultInventoryTransactionTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryTransactionTypeDetail = InventoryTransactionTypeDetailFactory.getInstance().create(inventoryTransactionTypePK, inventoryTransactionTypeName, inventoryTransactionSequenceTypePK,
                    inventoryTransactionWorkflowPK, inventoryTransactionWorkflowEntrancePK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            inventoryTransactionType.setActiveDetail(inventoryTransactionTypeDetail);
            inventoryTransactionType.setLastDetail(inventoryTransactionTypeDetail);

            sendEvent(inventoryTransactionTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryTransactionTypeFromValue(InventoryTransactionTypeDetailValue inventoryTransactionTypeDetailValue, BasePK updatedBy) {
        updateInventoryTransactionTypeFromValue(inventoryTransactionTypeDetailValue, true, updatedBy);
    }

    private void deleteInventoryTransactionType(InventoryTransactionType inventoryTransactionType, boolean checkDefault, BasePK deletedBy) {
        var inventoryTransactionTypeDetail = inventoryTransactionType.getLastDetailForUpdate();

        deleteInventoryTransactionTypeDescriptionsByInventoryTransactionType(inventoryTransactionType, deletedBy);
        // TODO: deleteInventoryTransactionsByInventoryTransactionType(inventoryTransactionType, deletedBy);

        inventoryTransactionTypeDetail.setThruTime(session.getStartTime());
        inventoryTransactionType.setActiveDetail(null);
        inventoryTransactionType.store();

        if(checkDefault) {
        // Check for default, and pick one if necessary
            var defaultInventoryTransactionType = getDefaultInventoryTransactionType();
            if(defaultInventoryTransactionType == null) {
                var inventoryTransactionTypes = getInventoryTransactionTypesForUpdate();

                if(!inventoryTransactionTypes.isEmpty()) {
                    var iter = inventoryTransactionTypes.iterator();
                    if(iter.hasNext()) {
                        defaultInventoryTransactionType = iter.next();
                    }
                    var inventoryTransactionTypeDetailValue = Objects.requireNonNull(defaultInventoryTransactionType).getLastDetailForUpdate().getInventoryTransactionTypeDetailValue().clone();

                    inventoryTransactionTypeDetailValue.setIsDefault(true);
                    updateInventoryTransactionTypeFromValue(inventoryTransactionTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(inventoryTransactionType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryTransactionType(InventoryTransactionType inventoryTransactionType, BasePK deletedBy) {
        deleteInventoryTransactionType(inventoryTransactionType, true, deletedBy);
    }

    private void deleteInventoryTransactionTypes(List<InventoryTransactionType> inventoryTransactionTypes, boolean checkDefault, BasePK deletedBy) {
        inventoryTransactionTypes.forEach((inventoryTransactionType) -> deleteInventoryTransactionType(inventoryTransactionType, checkDefault, deletedBy));
    }

    public void deleteInventoryTransactionTypes(List<InventoryTransactionType> inventoryTransactionTypes, BasePK deletedBy) {
        deleteInventoryTransactionTypes(inventoryTransactionTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Type Descriptions
    // --------------------------------------------------------------------------------

    public InventoryTransactionTypeDescription createInventoryTransactionTypeDescription(InventoryTransactionType inventoryTransactionType, Language language, String description, BasePK createdBy) {
        var inventoryTransactionTypeDescription = InventoryTransactionTypeDescriptionFactory.getInstance().create(inventoryTransactionType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryTransactionType.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryTransactionTypeDescription;
    }

    private static final Map<EntityPermission, String> getInventoryTransactionTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM inventorytransactiontypedescriptions " +
                "WHERE invtnxtypd_invtnxtyp_inventorytransactiontypeid = ? AND invtnxtypd_lang_languageid = ? AND invtnxtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM inventorytransactiontypedescriptions " +
                "WHERE invtnxtypd_invtnxtyp_inventorytransactiontypeid = ? AND invtnxtypd_lang_languageid = ? AND invtnxtypd_thrutime = ? " +
                "FOR UPDATE");
        getInventoryTransactionTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private InventoryTransactionTypeDescription getInventoryTransactionTypeDescription(InventoryTransactionType inventoryTransactionType, Language language, EntityPermission entityPermission) {
        return InventoryTransactionTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getInventoryTransactionTypeDescriptionQueries,
                inventoryTransactionType, language, Session.MAX_TIME);
    }

    public InventoryTransactionTypeDescription getInventoryTransactionTypeDescription(InventoryTransactionType inventoryTransactionType, Language language) {
        return getInventoryTransactionTypeDescription(inventoryTransactionType, language, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionTypeDescription getInventoryTransactionTypeDescriptionForUpdate(InventoryTransactionType inventoryTransactionType, Language language) {
        return getInventoryTransactionTypeDescription(inventoryTransactionType, language, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTypeDescriptionValue getInventoryTransactionTypeDescriptionValue(InventoryTransactionTypeDescription inventoryTransactionTypeDescription) {
        return inventoryTransactionTypeDescription == null? null: inventoryTransactionTypeDescription.getInventoryTransactionTypeDescriptionValue().clone();
    }

    public InventoryTransactionTypeDescriptionValue getInventoryTransactionTypeDescriptionValueForUpdate(InventoryTransactionType inventoryTransactionType, Language language) {
        return getInventoryTransactionTypeDescriptionValue(getInventoryTransactionTypeDescriptionForUpdate(inventoryTransactionType, language));
    }

    private static final Map<EntityPermission, String> getInventoryTransactionTypeDescriptionsByInventoryTransactionTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM inventorytransactiontypedescriptions, languages " +
                "WHERE invtnxtypd_invtnxtyp_inventorytransactiontypeid = ? AND invtnxtypd_thrutime = ? AND invtnxtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM inventorytransactiontypedescriptions " +
                "WHERE invtnxtypd_invtnxtyp_inventorytransactiontypeid = ? AND invtnxtypd_thrutime = ? " +
                "FOR UPDATE");
        getInventoryTransactionTypeDescriptionsByInventoryTransactionTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InventoryTransactionTypeDescription> getInventoryTransactionTypeDescriptionsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType, EntityPermission entityPermission) {
        return InventoryTransactionTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getInventoryTransactionTypeDescriptionsByInventoryTransactionTypeQueries,
                inventoryTransactionType, Session.MAX_TIME);
    }

    public List<InventoryTransactionTypeDescription> getInventoryTransactionTypeDescriptionsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionTypeDescriptionsByInventoryTransactionType(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionTypeDescription> getInventoryTransactionTypeDescriptionsByInventoryTransactionTypeForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionTypeDescriptionsByInventoryTransactionType(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryTransactionTypeDescription(InventoryTransactionType inventoryTransactionType, Language language) {
        String description;
        var inventoryTransactionTypeDescription = getInventoryTransactionTypeDescription(inventoryTransactionType, language);

        if(inventoryTransactionTypeDescription == null && !language.getIsDefault()) {
            inventoryTransactionTypeDescription = getInventoryTransactionTypeDescription(inventoryTransactionType, partyControl.getDefaultLanguage());
        }

        if(inventoryTransactionTypeDescription == null) {
            description = inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName();
        } else {
            description = inventoryTransactionTypeDescription.getDescription();
        }

        return description;
    }

    public InventoryTransactionTypeDescriptionTransfer getInventoryTransactionTypeDescriptionTransfer(UserVisit userVisit, InventoryTransactionTypeDescription inventoryTransactionTypeDescription) {
        return inventoryTransactionTypeDescriptionTransferCache.getTransfer(userVisit, inventoryTransactionTypeDescription);
    }

    public List<InventoryTransactionTypeDescriptionTransfer> getInventoryTransactionTypeDescriptionTransfersByInventoryTransactionType(UserVisit userVisit, InventoryTransactionType inventoryTransactionType) {
        var inventoryTransactionTypeDescriptions = getInventoryTransactionTypeDescriptionsByInventoryTransactionType(inventoryTransactionType);
        List<InventoryTransactionTypeDescriptionTransfer> inventoryTransactionTypeDescriptionTransfers = new ArrayList<>(inventoryTransactionTypeDescriptions.size());

        inventoryTransactionTypeDescriptions.forEach((inventoryTransactionTypeDescription) ->
                inventoryTransactionTypeDescriptionTransfers.add(inventoryTransactionTypeDescriptionTransferCache.getTransfer(userVisit, inventoryTransactionTypeDescription))
        );

        return inventoryTransactionTypeDescriptionTransfers;
    }

    public void updateInventoryTransactionTypeDescriptionFromValue(InventoryTransactionTypeDescriptionValue inventoryTransactionTypeDescriptionValue, BasePK updatedBy) {
        if(inventoryTransactionTypeDescriptionValue.hasBeenModified()) {
            var inventoryTransactionTypeDescription = InventoryTransactionTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryTransactionTypeDescriptionValue.getPrimaryKey());

            inventoryTransactionTypeDescription.setThruTime(session.getStartTime());
            inventoryTransactionTypeDescription.store();

            var inventoryTransactionType = inventoryTransactionTypeDescription.getInventoryTransactionType();
            var language = inventoryTransactionTypeDescription.getLanguage();
            var description = inventoryTransactionTypeDescriptionValue.getDescription();

            inventoryTransactionTypeDescription = InventoryTransactionTypeDescriptionFactory.getInstance().create(inventoryTransactionType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryTransactionType.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryTransactionTypeDescription(InventoryTransactionTypeDescription inventoryTransactionTypeDescription, BasePK deletedBy) {
        inventoryTransactionTypeDescription.setThruTime(session.getStartTime());

        sendEvent(inventoryTransactionTypeDescription.getInventoryTransactionTypePK(), EventTypes.MODIFY, inventoryTransactionTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryTransactionTypeDescriptionsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType, BasePK deletedBy) {
        var inventoryTransactionTypeDescriptions = getInventoryTransactionTypeDescriptionsByInventoryTransactionTypeForUpdate(inventoryTransactionType);

        inventoryTransactionTypeDescriptions.forEach((inventoryTransactionTypeDescription) -> 
                deleteInventoryTransactionTypeDescription(inventoryTransactionTypeDescription, deletedBy)
        );
    }

}

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

package com.echothree.model.control.inventory.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.common.choice.InventoryCostingMethodChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryCostingMethodDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryCostingMethodTransfer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryCostingMethodPK;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethodDescription;
import com.echothree.model.data.inventory.server.factory.InventoryCostingMethodDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryCostingMethodDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryCostingMethodFactory;
import com.echothree.model.data.inventory.server.value.InventoryCostingMethodDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryCostingMethodDetailValue;
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
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class InventoryCostingMethodControl
        extends BaseInventoryControl {

    /** Creates a new instance of InventoryControl */
    protected InventoryCostingMethodControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Adjustment Types
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryCostingMethodFactory inventoryCostingMethodFactory;

    @Inject
    protected InventoryCostingMethodDetailFactory inventoryCostingMethodDetailFactory;

    public InventoryCostingMethod createInventoryCostingMethod(String inventoryCostingMethodName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInventoryCostingMethod = getDefaultInventoryCostingMethod();
        var defaultFound = defaultInventoryCostingMethod != null;

        if(defaultFound && isDefault) {
            var defaultInventoryCostingMethodDetailValue = getDefaultInventoryCostingMethodDetailValueForUpdate();

            defaultInventoryCostingMethodDetailValue.setIsDefault(false);
            updateInventoryCostingMethodFromValue(defaultInventoryCostingMethodDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryCostingMethod = inventoryCostingMethodFactory.create();
        var inventoryCostingMethodDetail = inventoryCostingMethodDetailFactory.create(inventoryCostingMethod,
                inventoryCostingMethodName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryCostingMethod = inventoryCostingMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryCostingMethod.getPrimaryKey());
        inventoryCostingMethod.setActiveDetail(inventoryCostingMethodDetail);
        inventoryCostingMethod.setLastDetail(inventoryCostingMethodDetail);
        inventoryCostingMethod.store();

        sendEvent(inventoryCostingMethod.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryCostingMethod;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryCostingMethod */
    public InventoryCostingMethod getInventoryCostingMethodByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryCostingMethodPK(entityInstance.getEntityUniqueId());

        return inventoryCostingMethodFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryCostingMethod getInventoryCostingMethodByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryCostingMethodByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryCostingMethod getInventoryCostingMethodByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryCostingMethodByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryCostingMethod getInventoryCostingMethodByPK(InventoryCostingMethodPK pk) {
        return inventoryCostingMethodFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countInventoryCostingMethods() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM inventorycostingmethods, inventorycostingmethoddetails
                WHERE invcm_activedetailid = invcmdt_inventorycostingmethoddetailid
                """);
    }

    private static final Map<EntityPermission, String> getInventoryCostingMethodByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM inventorycostingmethods, inventorycostingmethoddetails
                WHERE invcm_activedetailid = invcmdt_inventorycostingmethoddetailid
                AND invcmdt_inventorycostingmethodname = ?
                """);
        queryMap.put(EntityPermission.READ_WRITE, """
                SELECT _ALL_
                FROM inventorycostingmethods, inventorycostingmethoddetails
                WHERE invcm_activedetailid = invcmdt_inventorycostingmethoddetailid
                AND invcmdt_inventorycostingmethodname = ?
                FOR UPDATE
                """);
        getInventoryCostingMethodByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public InventoryCostingMethod getInventoryCostingMethodByName(String inventoryCostingMethodName, EntityPermission entityPermission) {
        return inventoryCostingMethodFactory.getEntityFromQuery(entityPermission, getInventoryCostingMethodByNameQueries, inventoryCostingMethodName);
    }

    public InventoryCostingMethod getInventoryCostingMethodByName(String inventoryCostingMethodName) {
        return getInventoryCostingMethodByName(inventoryCostingMethodName, EntityPermission.READ_ONLY);
    }

    public InventoryCostingMethod getInventoryCostingMethodByNameForUpdate(String inventoryCostingMethodName) {
        return getInventoryCostingMethodByName(inventoryCostingMethodName, EntityPermission.READ_WRITE);
    }

    public InventoryCostingMethodDetailValue getInventoryCostingMethodDetailValueForUpdate(InventoryCostingMethod inventoryCostingMethod) {
        return inventoryCostingMethod == null? null: inventoryCostingMethod.getLastDetailForUpdate().getInventoryCostingMethodDetailValue().clone();
    }

    public InventoryCostingMethodDetailValue getInventoryCostingMethodDetailValueByNameForUpdate(String inventoryCostingMethodName) {
        return getInventoryCostingMethodDetailValueForUpdate(getInventoryCostingMethodByNameForUpdate(inventoryCostingMethodName));
    }

    private static final Map<EntityPermission, String> getDefaultInventoryCostingMethodQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM inventorycostingmethods, inventorycostingmethoddetails
                WHERE invcm_activedetailid = invcmdt_inventorycostingmethoddetailid
                AND invcmdt_isdefault = 1
                """);
        queryMap.put(EntityPermission.READ_WRITE, """
                SELECT _ALL_
                FROM inventorycostingmethods, inventorycostingmethoddetails
                WHERE invcm_activedetailid = invcmdt_inventorycostingmethoddetailid
                AND invcmdt_isdefault = 1
                FOR UPDATE
                """);
        getDefaultInventoryCostingMethodQueries = Collections.unmodifiableMap(queryMap);
    }

    public InventoryCostingMethod getDefaultInventoryCostingMethod(EntityPermission entityPermission) {
        return inventoryCostingMethodFactory.getEntityFromQuery(entityPermission, getDefaultInventoryCostingMethodQueries);
    }

    public InventoryCostingMethod getDefaultInventoryCostingMethod() {
        return getDefaultInventoryCostingMethod(EntityPermission.READ_ONLY);
    }

    public InventoryCostingMethod getDefaultInventoryCostingMethodForUpdate() {
        return getDefaultInventoryCostingMethod(EntityPermission.READ_WRITE);
    }

    public InventoryCostingMethodDetailValue getDefaultInventoryCostingMethodDetailValueForUpdate() {
        return getDefaultInventoryCostingMethodForUpdate().getLastDetailForUpdate().getInventoryCostingMethodDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getInventoryCostingMethodsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM inventorycostingmethods, inventorycostingmethoddetails
                WHERE invcm_activedetailid = invcmdt_inventorycostingmethoddetailid
                ORDER BY invcmdt_sortorder, invcmdt_inventorycostingmethodname
                _LIMIT_
                """);
        queryMap.put(EntityPermission.READ_WRITE, """
                SELECT _ALL_
                FROM inventorycostingmethods, inventorycostingmethoddetails
                WHERE invcm_activedetailid = invcmdt_inventorycostingmethoddetailid
                FOR UPDATE
                """);
        getInventoryCostingMethodsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InventoryCostingMethod> getInventoryCostingMethods(EntityPermission entityPermission) {
        return inventoryCostingMethodFactory.getEntitiesFromQuery(entityPermission, getInventoryCostingMethodsQueries);
    }

    public List<InventoryCostingMethod> getInventoryCostingMethods() {
        return getInventoryCostingMethods(EntityPermission.READ_ONLY);
    }

    public List<InventoryCostingMethod> getInventoryCostingMethodsForUpdate() {
        return getInventoryCostingMethods(EntityPermission.READ_WRITE);
    }

    public InventoryCostingMethodTransfer getInventoryCostingMethodTransfer(UserVisit userVisit, InventoryCostingMethod inventoryCostingMethod) {
        return inventoryCostingMethodTransferCache.getTransfer(userVisit, inventoryCostingMethod);
    }

    public List<InventoryCostingMethodTransfer> getInventoryCostingMethodTransfers(UserVisit userVisit, Collection<InventoryCostingMethod> inventoryCostingMethods) {
        List<InventoryCostingMethodTransfer> inventoryCostingMethodTransfers = new ArrayList<>(inventoryCostingMethods.size());

        inventoryCostingMethods.forEach((inventoryCostingMethod) ->
                inventoryCostingMethodTransfers.add(inventoryCostingMethodTransferCache.getTransfer(userVisit, inventoryCostingMethod))
        );

        return inventoryCostingMethodTransfers;
    }

    public List<InventoryCostingMethodTransfer> getInventoryCostingMethodTransfers(UserVisit userVisit) {
        return getInventoryCostingMethodTransfers(userVisit, getInventoryCostingMethods());
    }

    public InventoryCostingMethodChoicesBean getInventoryCostingMethodChoices(String defaultInventoryCostingMethodChoice,
            Language language, boolean allowNullChoice) {
        var inventoryCostingMethods = getInventoryCostingMethods();
        var size = inventoryCostingMethods.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryCostingMethodChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryCostingMethod : inventoryCostingMethods) {
            var inventoryCostingMethodDetail = inventoryCostingMethod.getLastDetail();

            var label = getBestInventoryCostingMethodDescription(inventoryCostingMethod, language);
            var value = inventoryCostingMethodDetail.getInventoryCostingMethodName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryCostingMethodChoice != null && defaultInventoryCostingMethodChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryCostingMethodDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryCostingMethodChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryCostingMethodFromValue(InventoryCostingMethodDetailValue inventoryCostingMethodDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(inventoryCostingMethodDetailValue.hasBeenModified()) {
            var inventoryCostingMethod = inventoryCostingMethodFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryCostingMethodDetailValue.getInventoryCostingMethodPK());
            var inventoryCostingMethodDetail = inventoryCostingMethod.getActiveDetailForUpdate();

            inventoryCostingMethodDetail.setThruTime(session.getStartTime());
            inventoryCostingMethodDetail.store();

            var inventoryCostingMethodPK = inventoryCostingMethodDetail.getInventoryCostingMethodPK(); // Not updated
            var inventoryCostingMethodName = inventoryCostingMethodDetailValue.getInventoryCostingMethodName();
            var isDefault = inventoryCostingMethodDetailValue.getIsDefault();
            var sortOrder = inventoryCostingMethodDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryCostingMethod = getDefaultInventoryCostingMethod();
                var defaultFound = defaultInventoryCostingMethod != null && !defaultInventoryCostingMethod.equals(inventoryCostingMethod);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryCostingMethodDetailValue = getDefaultInventoryCostingMethodDetailValueForUpdate();

                    defaultInventoryCostingMethodDetailValue.setIsDefault(false);
                    updateInventoryCostingMethodFromValue(defaultInventoryCostingMethodDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryCostingMethodDetail = inventoryCostingMethodDetailFactory.create(inventoryCostingMethodPK,
                    inventoryCostingMethodName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            inventoryCostingMethod.setActiveDetail(inventoryCostingMethodDetail);
            inventoryCostingMethod.setLastDetail(inventoryCostingMethodDetail);

            sendEvent(inventoryCostingMethodPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryCostingMethodFromValue(InventoryCostingMethodDetailValue inventoryCostingMethodDetailValue, BasePK updatedBy) {
        updateInventoryCostingMethodFromValue(inventoryCostingMethodDetailValue, true, updatedBy);
    }

    private void deleteInventoryCostingMethod(InventoryCostingMethod inventoryCostingMethod, boolean checkDefault, BasePK deletedBy) {
        var inventoryCostingMethodDetail = inventoryCostingMethod.getLastDetailForUpdate();

        deleteInventoryCostingMethodDescriptionsByInventoryCostingMethod(inventoryCostingMethod, deletedBy);
        // TODO: deleteInventoryTransactionsByInventoryCostingMethod(inventoryCostingMethod, deletedBy);

        inventoryCostingMethodDetail.setThruTime(session.getStartTime());
        inventoryCostingMethod.setActiveDetail(null);
        inventoryCostingMethod.store();

        if(checkDefault) {
        // Check for default, and pick one if necessary
            var defaultInventoryCostingMethod = getDefaultInventoryCostingMethod();
            if(defaultInventoryCostingMethod == null) {
                var inventoryCostingMethods = getInventoryCostingMethodsForUpdate();

                if(!inventoryCostingMethods.isEmpty()) {
                    var iter = inventoryCostingMethods.iterator();
                    if(iter.hasNext()) {
                        defaultInventoryCostingMethod = iter.next();
                    }
                    var inventoryCostingMethodDetailValue = Objects.requireNonNull(defaultInventoryCostingMethod).getLastDetailForUpdate().getInventoryCostingMethodDetailValue().clone();

                    inventoryCostingMethodDetailValue.setIsDefault(true);
                    updateInventoryCostingMethodFromValue(inventoryCostingMethodDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(inventoryCostingMethod.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryCostingMethod(InventoryCostingMethod inventoryCostingMethod, BasePK deletedBy) {
        deleteInventoryCostingMethod(inventoryCostingMethod, true, deletedBy);
    }

    private void deleteInventoryCostingMethods(List<InventoryCostingMethod> inventoryCostingMethods, boolean checkDefault, BasePK deletedBy) {
        inventoryCostingMethods.forEach((inventoryCostingMethod) -> deleteInventoryCostingMethod(inventoryCostingMethod, checkDefault, deletedBy));
    }

    public void deleteInventoryCostingMethods(List<InventoryCostingMethod> inventoryCostingMethods, BasePK deletedBy) {
        deleteInventoryCostingMethods(inventoryCostingMethods, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Adjustment Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryCostingMethodDescriptionFactory inventoryCostingMethodDescriptionFactory;

    public InventoryCostingMethodDescription createInventoryCostingMethodDescription(InventoryCostingMethod inventoryCostingMethod, Language language, String description, BasePK createdBy) {
        var inventoryCostingMethodDescription = inventoryCostingMethodDescriptionFactory.create(inventoryCostingMethod, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryCostingMethod.getPrimaryKey(), EventTypes.MODIFY, inventoryCostingMethodDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryCostingMethodDescription;
    }

    private static final Map<EntityPermission, String> getInventoryCostingMethodDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM inventorycostingmethoddescriptions
                WHERE invcmd_invcm_inventorycostingmethodid = ? AND invcmd_lang_languageid = ? AND invcmd_thrutime = ?
                """);
        queryMap.put(EntityPermission.READ_WRITE, """
                SELECT _ALL_
                FROM inventorycostingmethoddescriptions
                WHERE invcmd_invcm_inventorycostingmethodid = ? AND invcmd_lang_languageid = ? AND invcmd_thrutime = ?
                FOR UPDATE
                """);
        getInventoryCostingMethodDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private InventoryCostingMethodDescription getInventoryCostingMethodDescription(InventoryCostingMethod inventoryCostingMethod, Language language, EntityPermission entityPermission) {
        return inventoryCostingMethodDescriptionFactory.getEntityFromQuery(entityPermission, getInventoryCostingMethodDescriptionQueries,
                inventoryCostingMethod, language, Session.MAX_TIME);
    }

    public InventoryCostingMethodDescription getInventoryCostingMethodDescription(InventoryCostingMethod inventoryCostingMethod, Language language) {
        return getInventoryCostingMethodDescription(inventoryCostingMethod, language, EntityPermission.READ_ONLY);
    }

    public InventoryCostingMethodDescription getInventoryCostingMethodDescriptionForUpdate(InventoryCostingMethod inventoryCostingMethod, Language language) {
        return getInventoryCostingMethodDescription(inventoryCostingMethod, language, EntityPermission.READ_WRITE);
    }

    public InventoryCostingMethodDescriptionValue getInventoryCostingMethodDescriptionValue(InventoryCostingMethodDescription inventoryCostingMethodDescription) {
        return inventoryCostingMethodDescription == null? null: inventoryCostingMethodDescription.getInventoryCostingMethodDescriptionValue().clone();
    }

    public InventoryCostingMethodDescriptionValue getInventoryCostingMethodDescriptionValueForUpdate(InventoryCostingMethod inventoryCostingMethod, Language language) {
        return getInventoryCostingMethodDescriptionValue(getInventoryCostingMethodDescriptionForUpdate(inventoryCostingMethod, language));
    }

    private static final Map<EntityPermission, String> getInventoryCostingMethodDescriptionsByInventoryCostingMethodQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY, """
                SELECT _ALL_
                FROM inventorycostingmethoddescriptions, languages
                WHERE invcmd_invcm_inventorycostingmethodid = ? AND invcmd_thrutime = ? AND invcmd_lang_languageid = lang_languageid
                ORDER BY lang_sortorder, lang_languageisoname
                _LIMIT_
                """);
        queryMap.put(EntityPermission.READ_WRITE, """
                SELECT _ALL_
                FROM inventorycostingmethoddescriptions
                WHERE invcmd_invcm_inventorycostingmethodid = ? AND invcmd_thrutime = ?
                FOR UPDATE
                """);
        getInventoryCostingMethodDescriptionsByInventoryCostingMethodQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<InventoryCostingMethodDescription> getInventoryCostingMethodDescriptionsByInventoryCostingMethod(InventoryCostingMethod inventoryCostingMethod, EntityPermission entityPermission) {
        return inventoryCostingMethodDescriptionFactory.getEntitiesFromQuery(entityPermission, getInventoryCostingMethodDescriptionsByInventoryCostingMethodQueries,
                inventoryCostingMethod, Session.MAX_TIME);
    }

    public List<InventoryCostingMethodDescription> getInventoryCostingMethodDescriptionsByInventoryCostingMethod(InventoryCostingMethod inventoryCostingMethod) {
        return getInventoryCostingMethodDescriptionsByInventoryCostingMethod(inventoryCostingMethod, EntityPermission.READ_ONLY);
    }

    public List<InventoryCostingMethodDescription> getInventoryCostingMethodDescriptionsByInventoryCostingMethodForUpdate(InventoryCostingMethod inventoryCostingMethod) {
        return getInventoryCostingMethodDescriptionsByInventoryCostingMethod(inventoryCostingMethod, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryCostingMethodDescription(InventoryCostingMethod inventoryCostingMethod, Language language) {
        String description;
        var inventoryCostingMethodDescription = getInventoryCostingMethodDescription(inventoryCostingMethod, language);

        if(inventoryCostingMethodDescription == null && !language.getIsDefault()) {
            inventoryCostingMethodDescription = getInventoryCostingMethodDescription(inventoryCostingMethod, partyControl.getDefaultLanguage());
        }

        if(inventoryCostingMethodDescription == null) {
            description = inventoryCostingMethod.getLastDetail().getInventoryCostingMethodName();
        } else {
            description = inventoryCostingMethodDescription.getDescription();
        }

        return description;
    }

    public InventoryCostingMethodDescriptionTransfer getInventoryCostingMethodDescriptionTransfer(UserVisit userVisit, InventoryCostingMethodDescription inventoryCostingMethodDescription) {
        return inventoryCostingMethodDescriptionTransferCache.getTransfer(userVisit, inventoryCostingMethodDescription);
    }

    public List<InventoryCostingMethodDescriptionTransfer> getInventoryCostingMethodDescriptionTransfersByInventoryCostingMethod(UserVisit userVisit, InventoryCostingMethod inventoryCostingMethod) {
        var inventoryCostingMethodDescriptions = getInventoryCostingMethodDescriptionsByInventoryCostingMethod(inventoryCostingMethod);
        List<InventoryCostingMethodDescriptionTransfer> inventoryCostingMethodDescriptionTransfers = new ArrayList<>(inventoryCostingMethodDescriptions.size());

        inventoryCostingMethodDescriptions.forEach((inventoryCostingMethodDescription) ->
                inventoryCostingMethodDescriptionTransfers.add(inventoryCostingMethodDescriptionTransferCache.getTransfer(userVisit, inventoryCostingMethodDescription))
        );

        return inventoryCostingMethodDescriptionTransfers;
    }

    public void updateInventoryCostingMethodDescriptionFromValue(InventoryCostingMethodDescriptionValue inventoryCostingMethodDescriptionValue, BasePK updatedBy) {
        if(inventoryCostingMethodDescriptionValue.hasBeenModified()) {
            var inventoryCostingMethodDescription = inventoryCostingMethodDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryCostingMethodDescriptionValue.getPrimaryKey());

            inventoryCostingMethodDescription.setThruTime(session.getStartTime());
            inventoryCostingMethodDescription.store();

            var inventoryCostingMethod = inventoryCostingMethodDescription.getInventoryCostingMethod();
            var language = inventoryCostingMethodDescription.getLanguage();
            var description = inventoryCostingMethodDescriptionValue.getDescription();

            inventoryCostingMethodDescription = inventoryCostingMethodDescriptionFactory.create(inventoryCostingMethod, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryCostingMethod.getPrimaryKey(), EventTypes.MODIFY, inventoryCostingMethodDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryCostingMethodDescription(InventoryCostingMethodDescription inventoryCostingMethodDescription, BasePK deletedBy) {
        inventoryCostingMethodDescription.setThruTime(session.getStartTime());

        sendEvent(inventoryCostingMethodDescription.getInventoryCostingMethodPK(), EventTypes.MODIFY, inventoryCostingMethodDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryCostingMethodDescriptionsByInventoryCostingMethod(InventoryCostingMethod inventoryCostingMethod, BasePK deletedBy) {
        var inventoryCostingMethodDescriptions = getInventoryCostingMethodDescriptionsByInventoryCostingMethodForUpdate(inventoryCostingMethod);

        inventoryCostingMethodDescriptions.forEach((inventoryCostingMethodDescription) -> 
                deleteInventoryCostingMethodDescription(inventoryCostingMethodDescription, deletedBy)
        );
    }

}

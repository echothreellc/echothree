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
import com.echothree.model.control.inventory.server.transfer.InventoryCostingMethodDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryCostingMethodTransferCache;
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
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_COSTING_METHODS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryCostingMethodDescriptions.InventoryCostingMethodDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryCostingMethodDetails.InventoryCostingMethodDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryCostingMethods.InventoryCostingMethods;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class InventoryCostingMethodControl
        extends BaseModelControl {

    @Inject
    InventoryCostingMethodTransferCache inventoryCostingMethodTransferCache;

    @Inject
    InventoryCostingMethodDescriptionTransferCache inventoryCostingMethodDescriptionTransferCache;

    /**
     * Creates a new instance of InventoryCostingMethodControl
     */
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

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryCostingMethod
     */
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
        return session.getDslContext()
                .selectCount()
                .from(InventoryCostingMethods)
                .join(InventoryCostingMethodDetails).onKey(INVENTORY_COSTING_METHODS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public InventoryCostingMethod getInventoryCostingMethodByName(final String inventoryCostingMethodName,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryCostingMethods.fields())
                .from(InventoryCostingMethods)
                .join(InventoryCostingMethodDetails).onKey(INVENTORY_COSTING_METHODS_ACTIVE_DETAIL_FK)
                .where(InventoryCostingMethodDetails.INVENTORY_COSTING_METHOD_NAME.eq(inventoryCostingMethodName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryCostingMethodFactory.getEntityFromQuery(entityPermission,
                inventoryCostingMethodFactory.prepareStatement(query.getSQL()), query.getBindValues().toArray());
    }

    public InventoryCostingMethod getInventoryCostingMethodByName(String inventoryCostingMethodName) {
        return getInventoryCostingMethodByName(inventoryCostingMethodName, EntityPermission.READ_ONLY);
    }

    public InventoryCostingMethod getInventoryCostingMethodByNameForUpdate(String inventoryCostingMethodName) {
        return getInventoryCostingMethodByName(inventoryCostingMethodName, EntityPermission.READ_WRITE);
    }

    public InventoryCostingMethodDetailValue getInventoryCostingMethodDetailValueForUpdate(InventoryCostingMethod inventoryCostingMethod) {
        return inventoryCostingMethod == null ? null : inventoryCostingMethod.getLastDetailForUpdate().getInventoryCostingMethodDetailValue().clone();
    }

    public InventoryCostingMethodDetailValue getInventoryCostingMethodDetailValueByNameForUpdate(String inventoryCostingMethodName) {
        return getInventoryCostingMethodDetailValueForUpdate(getInventoryCostingMethodByNameForUpdate(inventoryCostingMethodName));
    }

    public InventoryCostingMethod getDefaultInventoryCostingMethod(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryCostingMethods.fields())
                .from(InventoryCostingMethods)
                .join(InventoryCostingMethodDetails).onKey(INVENTORY_COSTING_METHODS_ACTIVE_DETAIL_FK)
                .where(InventoryCostingMethodDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryCostingMethodFactory.getEntityFromQuery(entityPermission,
                inventoryCostingMethodFactory.prepareStatement(query.getSQL()), query.getBindValues().toArray());
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

    private List<InventoryCostingMethod> getInventoryCostingMethods(final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryCostingMethods.fields())
                .from(InventoryCostingMethods)
                .join(InventoryCostingMethodDetails).onKey(INVENTORY_COSTING_METHODS_ACTIVE_DETAIL_FK);

        var sql = switch(entityPermission) {
            case READ_ONLY -> baseQuery
                    .orderBy(InventoryCostingMethodDetails.SORT_ORDER, InventoryCostingMethodDetails.INVENTORY_COSTING_METHOD_NAME)
                    .getSQL() + " _LIMIT_";
            case READ_WRITE -> baseQuery
                    .forUpdate()
                    .getSQL();
        };

        return inventoryCostingMethodFactory.getEntitiesFromQuery(entityPermission,
                inventoryCostingMethodFactory.prepareStatement(sql));
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

            labels.add(label == null ? value : label);
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

    private InventoryCostingMethodDescription getInventoryCostingMethodDescription(final InventoryCostingMethod inventoryCostingMethod,
            final Language language, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryCostingMethodDescriptions.fields())
                .from(InventoryCostingMethodDescriptions)
                .where(InventoryCostingMethodDescriptions.INVENTORY_COSTING_METHOD.eq(inventoryCostingMethod.getPrimaryKey()),
                        InventoryCostingMethodDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryCostingMethodDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryCostingMethodDescriptionFactory.getEntityFromQuery(entityPermission,
                inventoryCostingMethodDescriptionFactory.prepareStatement(query.getSQL()), query.getBindValues().toArray());
    }

    public InventoryCostingMethodDescription getInventoryCostingMethodDescription(InventoryCostingMethod inventoryCostingMethod, Language language) {
        return getInventoryCostingMethodDescription(inventoryCostingMethod, language, EntityPermission.READ_ONLY);
    }

    public InventoryCostingMethodDescription getInventoryCostingMethodDescriptionForUpdate(InventoryCostingMethod inventoryCostingMethod, Language language) {
        return getInventoryCostingMethodDescription(inventoryCostingMethod, language, EntityPermission.READ_WRITE);
    }

    public InventoryCostingMethodDescriptionValue getInventoryCostingMethodDescriptionValue(InventoryCostingMethodDescription inventoryCostingMethodDescription) {
        return inventoryCostingMethodDescription == null ? null : inventoryCostingMethodDescription.getInventoryCostingMethodDescriptionValue().clone();
    }

    public InventoryCostingMethodDescriptionValue getInventoryCostingMethodDescriptionValueForUpdate(InventoryCostingMethod inventoryCostingMethod, Language language) {
        return getInventoryCostingMethodDescriptionValue(getInventoryCostingMethodDescriptionForUpdate(inventoryCostingMethod, language));
    }

    private List<InventoryCostingMethodDescription> getInventoryCostingMethodDescriptionsByInventoryCostingMethod(
            final InventoryCostingMethod inventoryCostingMethod, final EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.getDslContext()
                    .select(InventoryCostingMethodDescriptions.fields())
                    .from(InventoryCostingMethodDescriptions)
                    .join(Languages)
                    .on(InventoryCostingMethodDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(InventoryCostingMethodDescriptions.INVENTORY_COSTING_METHOD.eq(inventoryCostingMethod.getPrimaryKey()),
                            InventoryCostingMethodDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryCostingMethodDescriptions.fields())
                    .from(InventoryCostingMethodDescriptions)
                    .where(InventoryCostingMethodDescriptions.INVENTORY_COSTING_METHOD.eq(inventoryCostingMethod.getPrimaryKey()),
                            InventoryCostingMethodDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        var sql = query.getSQL() + (entityPermission == EntityPermission.READ_ONLY ? " _LIMIT_" : "");

        return inventoryCostingMethodDescriptionFactory.getEntitiesFromQuery(entityPermission,
                inventoryCostingMethodDescriptionFactory.prepareStatement(sql), query.getBindValues().toArray());
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

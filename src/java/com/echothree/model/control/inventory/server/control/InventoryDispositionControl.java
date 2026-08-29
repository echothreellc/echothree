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
import com.echothree.model.control.inventory.common.choice.InventoryDispositionChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryDispositionDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryDispositionTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryDispositionDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryDispositionTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryDispositionPK;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionDescription;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.factory.InventoryDispositionDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryDispositionDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryDispositionFactory;
import com.echothree.model.data.inventory.server.value.InventoryDispositionDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryDispositionDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_DISPOSITIONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryDispositionDescriptions.InventoryDispositionDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryDispositionDetails.InventoryDispositionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryDispositions.InventoryDispositions;
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
public class InventoryDispositionControl
        extends BaseModelControl {

    /** Creates a new instance of InventoryDispositionControl */
    protected InventoryDispositionControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Dispositions
    // --------------------------------------------------------------------------------

    @Inject
    InventoryDispositionFactory inventoryDispositionFactory;

    @Inject
    InventoryDispositionDetailFactory inventoryDispositionDetailFactory;

    @Inject
    InventoryDispositionTransferCache inventoryDispositionTransferCache;

    public InventoryDisposition createInventoryDisposition(InventoryTransactionType inventoryTransactionType,
            String inventoryDispositionName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInventoryDisposition = getDefaultInventoryDisposition(inventoryTransactionType);
        var defaultFound = defaultInventoryDisposition != null;

        if(defaultFound && isDefault) {
            var defaultInventoryDispositionDetailValue = getDefaultInventoryDispositionDetailValueForUpdate(inventoryTransactionType);

            defaultInventoryDispositionDetailValue.setIsDefault(false);
            updateInventoryDispositionFromValue(defaultInventoryDispositionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryDisposition = inventoryDispositionFactory.create();
        var inventoryDispositionDetail = inventoryDispositionDetailFactory.create(inventoryDisposition,
                inventoryTransactionType, inventoryDispositionName, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryDisposition = inventoryDispositionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryDisposition.getPrimaryKey());
        inventoryDisposition.setActiveDetail(inventoryDispositionDetail);
        inventoryDisposition.setLastDetail(inventoryDispositionDetail);
        inventoryDisposition.store();

        sendEvent(inventoryDisposition.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryDisposition;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryDisposition */
    public InventoryDisposition getInventoryDispositionByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryDispositionPK(entityInstance.getEntityUniqueId());

        return inventoryDispositionFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryDisposition getInventoryDispositionByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryDispositionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryDisposition getInventoryDispositionByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryDispositionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryDisposition getInventoryDispositionByPK(InventoryDispositionPK pk) {
        return inventoryDispositionFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countInventoryDispositionsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryDispositions)
                .join(InventoryDispositionDetails).onKey(INVENTORY_DISPOSITIONS_ACTIVE_DETAIL_FK)
                .where(InventoryDispositionDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public InventoryDisposition getInventoryDispositionByName(InventoryTransactionType inventoryTransactionType,
            String inventoryDispositionName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryDispositions.fields())
                .from(InventoryDispositions)
                .join(InventoryDispositionDetails).onKey(INVENTORY_DISPOSITIONS_ACTIVE_DETAIL_FK)
                .where(InventoryDispositionDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()),
                        InventoryDispositionDetails.INVENTORY_DISPOSITION_NAME.eq(inventoryDispositionName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryDispositionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryDisposition getInventoryDispositionByName(InventoryTransactionType inventoryTransactionType,
            String inventoryDispositionName) {
        return getInventoryDispositionByName(inventoryTransactionType, inventoryDispositionName, EntityPermission.READ_ONLY);
    }

    public InventoryDisposition getInventoryDispositionByNameForUpdate(InventoryTransactionType inventoryTransactionType,
            String inventoryDispositionName) {
        return getInventoryDispositionByName(inventoryTransactionType, inventoryDispositionName, EntityPermission.READ_WRITE);
    }

    public InventoryDispositionDetailValue getInventoryDispositionDetailValueForUpdate(
            InventoryDisposition inventoryDisposition) {
        return inventoryDisposition == null? null: inventoryDisposition.getLastDetailForUpdate(
                ).getInventoryDispositionDetailValue().clone();
    }

    public InventoryDispositionDetailValue getInventoryDispositionDetailValueByNameForUpdate(
            InventoryTransactionType inventoryTransactionType, String inventoryDispositionName) {
        return getInventoryDispositionDetailValueForUpdate(getInventoryDispositionByNameForUpdate(inventoryTransactionType,
                inventoryDispositionName));
    }

    public InventoryDisposition getDefaultInventoryDisposition(InventoryTransactionType inventoryTransactionType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryDispositions.fields())
                .from(InventoryDispositions)
                .join(InventoryDispositionDetails).onKey(INVENTORY_DISPOSITIONS_ACTIVE_DETAIL_FK)
                .where(InventoryDispositionDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()),
                        InventoryDispositionDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryDispositionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryDisposition getDefaultInventoryDisposition(InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryDisposition(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public InventoryDisposition getDefaultInventoryDispositionForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryDisposition(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    public InventoryDispositionDetailValue getDefaultInventoryDispositionDetailValueForUpdate(
            InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryDispositionForUpdate(
                inventoryTransactionType).getLastDetailForUpdate().getInventoryDispositionDetailValue().clone();
    }

    private List<InventoryDisposition> getInventoryDispositions(InventoryTransactionType inventoryTransactionType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryDispositions.fields())
                .from(InventoryDispositions)
                .join(InventoryDispositionDetails).onKey(INVENTORY_DISPOSITIONS_ACTIVE_DETAIL_FK)
                .where(InventoryDispositionDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryDispositionDetails.SORT_ORDER,
                            InventoryDispositionDetails.INVENTORY_DISPOSITION_NAME),
                    InventoryDispositionFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryDispositionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryDisposition> getInventoryDispositions(InventoryTransactionType inventoryTransactionType) {
        return getInventoryDispositions(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public List<InventoryDisposition> getInventoryDispositionsByInventoryTransactionType(
            InventoryTransactionType inventoryTransactionType) {
        return getInventoryDispositions(inventoryTransactionType);
    }

    public List<InventoryDisposition> getInventoryDispositionsForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getInventoryDispositions(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    public InventoryDispositionTransfer getInventoryDispositionTransfer(UserVisit userVisit,
            InventoryDisposition inventoryDisposition) {
        return inventoryDispositionTransferCache.getTransfer(userVisit, inventoryDisposition);
    }

    public List<InventoryDispositionTransfer> getInventoryDispositionTransfers(UserVisit userVisit,
            Collection<InventoryDisposition> inventoryDispositions) {
        List<InventoryDispositionTransfer> inventoryDispositionTransfers = new ArrayList<>(inventoryDispositions.size());

        inventoryDispositions.forEach((inventoryDisposition) ->
                inventoryDispositionTransfers.add(inventoryDispositionTransferCache.getTransfer(userVisit,
                        inventoryDisposition))
        );

        return inventoryDispositionTransfers;
    }

    public List<InventoryDispositionTransfer> getInventoryDispositionTransfers(UserVisit userVisit,
            InventoryTransactionType inventoryTransactionType) {
        return getInventoryDispositionTransfers(userVisit, getInventoryDispositions(inventoryTransactionType));
    }

    public InventoryDispositionChoicesBean getInventoryDispositionChoices(String defaultInventoryDispositionChoice,
            Language language, boolean allowNullChoice,
            InventoryTransactionType inventoryTransactionType) {
        var inventoryDispositions = getInventoryDispositions(inventoryTransactionType);
        var size = inventoryDispositions.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryDispositionChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryDisposition : inventoryDispositions) {
            var inventoryDispositionDetail = inventoryDisposition.getLastDetail();

            var label = getBestInventoryDispositionDescription(inventoryDisposition, language);
            var value = inventoryDispositionDetail.getInventoryDispositionName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryDispositionChoice != null && defaultInventoryDispositionChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryDispositionDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryDispositionChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryDispositionFromValue(InventoryDispositionDetailValue inventoryDispositionDetailValue,
            boolean checkDefault, BasePK updatedBy) {
        if(inventoryDispositionDetailValue.hasBeenModified()) {
            var inventoryDisposition = inventoryDispositionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryDispositionDetailValue.getInventoryDispositionPK());
            var inventoryDispositionDetail = inventoryDisposition.getActiveDetailForUpdate();

            inventoryDispositionDetail.setThruTime(session.getStartTime());
            inventoryDispositionDetail.store();

            var inventoryTransactionType = inventoryDispositionDetail.getInventoryTransactionType(); // Not updated
            var inventoryTransactionTypePK = inventoryTransactionType.getPrimaryKey(); // Not updated
            var inventoryDispositionPK = inventoryDispositionDetail.getInventoryDispositionPK(); // Not updated
            var inventoryDispositionName = inventoryDispositionDetailValue.getInventoryDispositionName();
            var isDefault = inventoryDispositionDetailValue.getIsDefault();
            var sortOrder = inventoryDispositionDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryDisposition = getDefaultInventoryDisposition(inventoryTransactionType);
                var defaultFound = 
                        defaultInventoryDisposition != null && !defaultInventoryDisposition.equals(inventoryDisposition);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryDispositionDetailValue = 
                            getDefaultInventoryDispositionDetailValueForUpdate(inventoryTransactionType);

                    defaultInventoryDispositionDetailValue.setIsDefault(false);
                    updateInventoryDispositionFromValue(defaultInventoryDispositionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryDispositionDetail = inventoryDispositionDetailFactory.create(inventoryDispositionPK,
                    inventoryTransactionTypePK, inventoryDispositionName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            inventoryDisposition.setActiveDetail(inventoryDispositionDetail);
            inventoryDisposition.setLastDetail(inventoryDispositionDetail);

            sendEvent(inventoryDispositionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryDispositionFromValue(InventoryDispositionDetailValue inventoryDispositionDetailValue,
            BasePK updatedBy) {
        updateInventoryDispositionFromValue(inventoryDispositionDetailValue, true, updatedBy);
    }

    public void deleteInventoryDisposition(InventoryDisposition inventoryDisposition, BasePK deletedBy) {
        deleteInventoryDispositionDescriptionsByInventoryDisposition(inventoryDisposition, deletedBy);

        var inventoryDispositionDetail = inventoryDisposition.getLastDetailForUpdate();
        inventoryDispositionDetail.setThruTime(session.getStartTime());
        inventoryDisposition.setActiveDetail(null);
        inventoryDisposition.store();

        // Check for default, and pick one if necessary
        var inventoryTransactionType = inventoryDispositionDetail.getInventoryTransactionType();
        var defaultInventoryDisposition = getDefaultInventoryDisposition(inventoryTransactionType);
        if(defaultInventoryDisposition == null) {
            var inventoryDispositions = getInventoryDispositionsForUpdate(inventoryTransactionType);

            if(!inventoryDispositions.isEmpty()) {
                var iter = inventoryDispositions.iterator();
                if(iter.hasNext()) {
                    defaultInventoryDisposition = iter.next();
                }
                var inventoryDispositionDetailValue = 
                        Objects.requireNonNull(
                                defaultInventoryDisposition).getLastDetailForUpdate().getInventoryDispositionDetailValue().clone();

                inventoryDispositionDetailValue.setIsDefault(true);
                updateInventoryDispositionFromValue(inventoryDispositionDetailValue, false, deletedBy);
            }
        }

        sendEvent(inventoryDisposition.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryDispositionsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType, BasePK deletedBy) {
        var inventoryDispositions = getInventoryDispositionsForUpdate(inventoryTransactionType);

        inventoryDispositions.forEach(inventoryDisposition ->
                deleteInventoryDisposition(inventoryDisposition, deletedBy));
    }

    // --------------------------------------------------------------------------------
    //   Inventory Disposition Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    InventoryDispositionDescriptionFactory inventoryDispositionDescriptionFactory;

    @Inject
    InventoryDispositionDescriptionTransferCache inventoryDispositionDescriptionTransferCache;

    public InventoryDispositionDescription createInventoryDispositionDescription(
            InventoryDisposition inventoryDisposition, Language language, String description, BasePK createdBy) {
        var inventoryDispositionDescription = inventoryDispositionDescriptionFactory.create(inventoryDisposition,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryDisposition.getPrimaryKey(), EventTypes.MODIFY, inventoryDispositionDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return inventoryDispositionDescription;
    }

    public long countInventoryDispositionDescriptionsByInventoryDisposition(
            InventoryDisposition inventoryDisposition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryDispositionDescriptions)
                .where(InventoryDispositionDescriptions.INVENTORY_DISPOSITION.eq(inventoryDisposition.getPrimaryKey()),
                        InventoryDispositionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryDispositionDescriptionsByLanguage(Language language) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryDispositionDescriptions)
                .where(InventoryDispositionDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryDispositionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private InventoryDispositionDescription getInventoryDispositionDescription(
            InventoryDisposition inventoryDisposition, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryDispositionDescriptions.fields())
                .from(InventoryDispositionDescriptions)
                .where(InventoryDispositionDescriptions.INVENTORY_DISPOSITION.eq(inventoryDisposition.getPrimaryKey()),
                        InventoryDispositionDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryDispositionDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryDispositionDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryDispositionDescription getInventoryDispositionDescription(
            InventoryDisposition inventoryDisposition, Language language) {
        return getInventoryDispositionDescription(inventoryDisposition, language, EntityPermission.READ_ONLY);
    }

    public InventoryDispositionDescription getInventoryDispositionDescriptionForUpdate(
            InventoryDisposition inventoryDisposition, Language language) {
        return getInventoryDispositionDescription(inventoryDisposition, language, EntityPermission.READ_WRITE);
    }

    public InventoryDispositionDescriptionValue getInventoryDispositionDescriptionValue(
            InventoryDispositionDescription inventoryDispositionDescription) {
        return inventoryDispositionDescription == null ? null
                : inventoryDispositionDescription.getInventoryDispositionDescriptionValue().clone();
    }

    public InventoryDispositionDescriptionValue getInventoryDispositionDescriptionValueForUpdate(
            InventoryDisposition inventoryDisposition, Language language) {
        return getInventoryDispositionDescriptionValue(getInventoryDispositionDescriptionForUpdate(inventoryDisposition,
                language));
    }

    private List<InventoryDispositionDescription> getInventoryDispositionDescriptionsByInventoryDisposition(
            InventoryDisposition inventoryDisposition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryDispositionDescriptions.fields())
                    .from(InventoryDispositionDescriptions)
                    .join(Languages).on(InventoryDispositionDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(InventoryDispositionDescriptions.INVENTORY_DISPOSITION.eq(inventoryDisposition.getPrimaryKey()),
                            InventoryDispositionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    InventoryDispositionDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryDispositionDescriptions.fields())
                    .from(InventoryDispositionDescriptions)
                    .where(InventoryDispositionDescriptions.INVENTORY_DISPOSITION.eq(inventoryDisposition.getPrimaryKey()),
                            InventoryDispositionDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryDispositionDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryDispositionDescription> getInventoryDispositionDescriptionsByInventoryDisposition(
            InventoryDisposition inventoryDisposition) {
        return getInventoryDispositionDescriptionsByInventoryDisposition(inventoryDisposition, EntityPermission.READ_ONLY);
    }

    public List<InventoryDispositionDescription> getInventoryDispositionDescriptionsByInventoryDispositionForUpdate(
            InventoryDisposition inventoryDisposition) {
        return getInventoryDispositionDescriptionsByInventoryDisposition(inventoryDisposition, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryDispositionDescription(InventoryDisposition inventoryDisposition, Language language) {
        String description;
        var inventoryDispositionDescription = getInventoryDispositionDescription(inventoryDisposition, language);

        if(inventoryDispositionDescription == null && !language.getIsDefault()) {
            inventoryDispositionDescription = getInventoryDispositionDescription(inventoryDisposition,
                    partyControl.getDefaultLanguage());
        }

        if(inventoryDispositionDescription == null) {
            description = inventoryDisposition.getLastDetail().getInventoryDispositionName();
        } else {
            description = inventoryDispositionDescription.getDescription();
        }

        return description;
    }

    public InventoryDispositionDescriptionTransfer getInventoryDispositionDescriptionTransfer(UserVisit userVisit,
            InventoryDispositionDescription inventoryDispositionDescription) {
        return inventoryDispositionDescriptionTransferCache.getTransfer(userVisit, inventoryDispositionDescription);
    }

    public List<InventoryDispositionDescriptionTransfer> getInventoryDispositionDescriptionTransfers(
            UserVisit userVisit, Collection<InventoryDispositionDescription> inventoryDispositionDescriptions) {
        var transfers = new ArrayList<InventoryDispositionDescriptionTransfer>(inventoryDispositionDescriptions.size());

        inventoryDispositionDescriptions.forEach(inventoryDispositionDescription ->
                transfers.add(inventoryDispositionDescriptionTransferCache.getTransfer(userVisit,
                        inventoryDispositionDescription)));

        return transfers;
    }

    public List<InventoryDispositionDescriptionTransfer> getInventoryDispositionDescriptionTransfersByInventoryDisposition(
            UserVisit userVisit, InventoryDisposition inventoryDisposition) {
        return getInventoryDispositionDescriptionTransfers(userVisit,
                getInventoryDispositionDescriptionsByInventoryDisposition(inventoryDisposition));
    }

    public void updateInventoryDispositionDescriptionFromValue(
            InventoryDispositionDescriptionValue inventoryDispositionDescriptionValue, BasePK updatedBy) {
        if(inventoryDispositionDescriptionValue.hasBeenModified()) {
            var inventoryDispositionDescription = inventoryDispositionDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryDispositionDescriptionValue.getPrimaryKey());

            inventoryDispositionDescription.setThruTime(session.getStartTime());
            inventoryDispositionDescription.store();

            var inventoryDisposition = inventoryDispositionDescription.getInventoryDisposition();
            var language = inventoryDispositionDescription.getLanguage();
            var description = inventoryDispositionDescriptionValue.getDescription();

            inventoryDispositionDescription = inventoryDispositionDescriptionFactory.create(inventoryDisposition,
                    language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryDisposition.getPrimaryKey(), EventTypes.MODIFY, inventoryDispositionDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryDispositionDescription(InventoryDispositionDescription inventoryDispositionDescription,
            BasePK deletedBy) {
        inventoryDispositionDescription.setThruTime(session.getStartTime());

        sendEvent(inventoryDispositionDescription.getInventoryDispositionPK(), EventTypes.MODIFY,
                inventoryDispositionDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryDispositionDescriptionsByInventoryDisposition(
            InventoryDisposition inventoryDisposition, BasePK deletedBy) {
        var inventoryDispositionDescriptions = 
                getInventoryDispositionDescriptionsByInventoryDispositionForUpdate(inventoryDisposition);

        inventoryDispositionDescriptions.forEach((inventoryDispositionDescription) -> 
                deleteInventoryDispositionDescription(inventoryDispositionDescription, deletedBy)
        );
    }

}

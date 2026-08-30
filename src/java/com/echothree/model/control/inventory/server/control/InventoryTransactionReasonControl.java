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
import com.echothree.model.control.inventory.common.choice.InventoryTransactionReasonChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionReasonDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionReasonTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryTransactionReasonDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryTransactionReasonTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.inventory.common.pk.InventoryTransactionReasonPK;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReason;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReasonDescription;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionReasonDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionReasonDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionReasonFactory;
import com.echothree.model.data.inventory.server.value.InventoryTransactionReasonDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionReasonDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_REASONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionReasonDescriptions.InventoryTransactionReasonDescriptions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionReasonDetails.InventoryTransactionReasonDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionReasons.InventoryTransactionReasons;
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
public class InventoryTransactionReasonControl
        extends BaseModelControl {

    /** Creates a new instance of InventoryTransactionReasonControl */
    protected InventoryTransactionReasonControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Transaction Reasons
    // --------------------------------------------------------------------------------

    @Inject
    InventoryTransactionReasonFactory inventoryTransactionReasonFactory;

    @Inject
    InventoryTransactionReasonDetailFactory inventoryTransactionReasonDetailFactory;

    @Inject
    InventoryTransactionReasonTransferCache inventoryTransactionReasonTransferCache;

    public InventoryTransactionReason createInventoryTransactionReason(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionReasonName, InventoryDisposition inventoryDisposition, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultInventoryTransactionReason = getDefaultInventoryTransactionReason(inventoryTransactionType);
        var defaultFound = defaultInventoryTransactionReason != null;

        if(defaultFound && isDefault) {
            var defaultInventoryTransactionReasonDetailValue = getDefaultInventoryTransactionReasonDetailValueForUpdate(inventoryTransactionType);

            defaultInventoryTransactionReasonDetailValue.setIsDefault(false);
            updateInventoryTransactionReasonFromValue(defaultInventoryTransactionReasonDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryTransactionReason = inventoryTransactionReasonFactory.create();
        var inventoryTransactionReasonDetail = inventoryTransactionReasonDetailFactory.create(inventoryTransactionReason,
                inventoryTransactionType, inventoryTransactionReasonName, inventoryDisposition, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryTransactionReason = inventoryTransactionReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryTransactionReason.getPrimaryKey());
        inventoryTransactionReason.setActiveDetail(inventoryTransactionReasonDetail);
        inventoryTransactionReason.setLastDetail(inventoryTransactionReasonDetail);
        inventoryTransactionReason.store();

        sendEvent(inventoryTransactionReason.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryTransactionReason;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryTransactionReason */
    public InventoryTransactionReason getInventoryTransactionReasonByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryTransactionReasonPK(entityInstance.getEntityUniqueId());

        return inventoryTransactionReasonFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryTransactionReasonByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryTransactionReasonByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByPK(InventoryTransactionReasonPK pk) {
        return inventoryTransactionReasonFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countInventoryTransactionReasonsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionReasons)
                .join(InventoryTransactionReasonDetails).onKey(INVENTORY_TRANSACTION_REASONS_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionReasonDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryTransactionReasonsByInventoryDisposition(InventoryDisposition inventoryDisposition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionReasons)
                .join(InventoryTransactionReasonDetails).onKey(INVENTORY_TRANSACTION_REASONS_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionReasonDetails.INVENTORY_DISPOSITION.eq(inventoryDisposition.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByName(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionReasonName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionReasons.fields())
                .from(InventoryTransactionReasons)
                .join(InventoryTransactionReasonDetails).onKey(INVENTORY_TRANSACTION_REASONS_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionReasonDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()),
                        InventoryTransactionReasonDetails.INVENTORY_TRANSACTION_REASON_NAME.eq(inventoryTransactionReasonName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionReasonFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByName(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionReasonName) {
        return getInventoryTransactionReasonByName(inventoryTransactionType, inventoryTransactionReasonName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByNameForUpdate(InventoryTransactionType inventoryTransactionType,
            String inventoryTransactionReasonName) {
        return getInventoryTransactionReasonByName(inventoryTransactionType, inventoryTransactionReasonName, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionReasonDetailValue getInventoryTransactionReasonDetailValueForUpdate(
            InventoryTransactionReason inventoryTransactionReason) {
        return inventoryTransactionReason == null? null: inventoryTransactionReason.getLastDetailForUpdate(
                ).getInventoryTransactionReasonDetailValue().clone();
    }

    public InventoryTransactionReasonDetailValue getInventoryTransactionReasonDetailValueByNameForUpdate(
            InventoryTransactionType inventoryTransactionType, String inventoryTransactionReasonName) {
        return getInventoryTransactionReasonDetailValueForUpdate(getInventoryTransactionReasonByNameForUpdate(inventoryTransactionType,
                inventoryTransactionReasonName));
    }

    public InventoryTransactionReason getDefaultInventoryTransactionReason(InventoryTransactionType inventoryTransactionType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionReasons.fields())
                .from(InventoryTransactionReasons)
                .join(InventoryTransactionReasonDetails).onKey(INVENTORY_TRANSACTION_REASONS_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionReasonDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()),
                        InventoryTransactionReasonDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionReasonFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionReason getDefaultInventoryTransactionReason(InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryTransactionReason(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionReason getDefaultInventoryTransactionReasonForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryTransactionReason(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionReasonDetailValue getDefaultInventoryTransactionReasonDetailValueForUpdate(
            InventoryTransactionType inventoryTransactionType) {
        return getDefaultInventoryTransactionReasonForUpdate(
                inventoryTransactionType).getLastDetailForUpdate().getInventoryTransactionReasonDetailValue().clone();
    }

    private List<InventoryTransactionReason> getInventoryTransactionReasons(InventoryTransactionType inventoryTransactionType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionReasons.fields())
                .from(InventoryTransactionReasons)
                .join(InventoryTransactionReasonDetails).onKey(INVENTORY_TRANSACTION_REASONS_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionReasonDetails.INVENTORY_TRANSACTION_TYPE.eq(inventoryTransactionType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryTransactionReasonDetails.SORT_ORDER,
                            InventoryTransactionReasonDetails.INVENTORY_TRANSACTION_REASON_NAME),
                    InventoryTransactionReasonFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionReasonFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionReason> getInventoryTransactionReasons(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionReasons(inventoryTransactionType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionReason> getInventoryTransactionReasonsByInventoryTransactionType(
            InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionReasons(inventoryTransactionType);
    }

    public List<InventoryTransactionReason> getInventoryTransactionReasonsForUpdate(InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionReasons(inventoryTransactionType, EntityPermission.READ_WRITE);
    }

    private List<InventoryTransactionReason> getInventoryTransactionReasonsByInventoryDisposition(
            InventoryDisposition inventoryDisposition, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionReasons.fields())
                .from(InventoryTransactionReasons)
                .join(InventoryTransactionReasonDetails).onKey(INVENTORY_TRANSACTION_REASONS_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionReasonDetails.INVENTORY_DISPOSITION.eq(inventoryDisposition.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(InventoryTransactionReasonDetails.SORT_ORDER,
                            InventoryTransactionReasonDetails.INVENTORY_TRANSACTION_REASON_NAME),
                    InventoryTransactionReasonFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionReasonFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionReason> getInventoryTransactionReasonsByInventoryDisposition(
            InventoryDisposition inventoryDisposition) {
        return getInventoryTransactionReasonsByInventoryDisposition(inventoryDisposition, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionReason> getInventoryTransactionReasonsByInventoryDispositionForUpdate(
            InventoryDisposition inventoryDisposition) {
        return getInventoryTransactionReasonsByInventoryDisposition(inventoryDisposition, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionReasonTransfer getInventoryTransactionReasonTransfer(UserVisit userVisit,
            InventoryTransactionReason inventoryTransactionReason) {
        return inventoryTransactionReasonTransferCache.getTransfer(userVisit, inventoryTransactionReason);
    }

    public List<InventoryTransactionReasonTransfer> getInventoryTransactionReasonTransfers(UserVisit userVisit,
            Collection<InventoryTransactionReason> inventoryTransactionReasons) {
        List<InventoryTransactionReasonTransfer> inventoryTransactionReasonTransfers = new ArrayList<>(inventoryTransactionReasons.size());

        inventoryTransactionReasons.forEach((inventoryTransactionReason) ->
                inventoryTransactionReasonTransfers.add(inventoryTransactionReasonTransferCache.getTransfer(userVisit,
                        inventoryTransactionReason))
        );

        return inventoryTransactionReasonTransfers;
    }

    public List<InventoryTransactionReasonTransfer> getInventoryTransactionReasonTransfers(UserVisit userVisit,
            InventoryTransactionType inventoryTransactionType) {
        return getInventoryTransactionReasonTransfers(userVisit, getInventoryTransactionReasons(inventoryTransactionType));
    }

    public InventoryTransactionReasonChoicesBean getInventoryTransactionReasonChoices(String defaultInventoryTransactionReasonChoice,
            Language language, boolean allowNullChoice,
            InventoryTransactionType inventoryTransactionType) {
        var inventoryTransactionReasons = getInventoryTransactionReasons(inventoryTransactionType);
        var size = inventoryTransactionReasons.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultInventoryTransactionReasonChoice == null) {
                defaultValue = "";
            }
        }

        for(var inventoryTransactionReason : inventoryTransactionReasons) {
            var inventoryTransactionReasonDetail = inventoryTransactionReason.getLastDetail();

            var label = getBestInventoryTransactionReasonDescription(inventoryTransactionReason, language);
            var value = inventoryTransactionReasonDetail.getInventoryTransactionReasonName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultInventoryTransactionReasonChoice != null && defaultInventoryTransactionReasonChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryTransactionReasonDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new InventoryTransactionReasonChoicesBean(labels, values, defaultValue);
    }

    private void updateInventoryTransactionReasonFromValue(InventoryTransactionReasonDetailValue inventoryTransactionReasonDetailValue,
            boolean checkDefault, BasePK updatedBy) {
        if(inventoryTransactionReasonDetailValue.hasBeenModified()) {
            var inventoryTransactionReason = inventoryTransactionReasonFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryTransactionReasonDetailValue.getInventoryTransactionReasonPK());
            var inventoryTransactionReasonDetail = inventoryTransactionReason.getActiveDetailForUpdate();

            inventoryTransactionReasonDetail.setThruTime(session.getStartTime());
            inventoryTransactionReasonDetail.store();

            var inventoryTransactionType = inventoryTransactionReasonDetail.getInventoryTransactionType(); // Not updated
            var inventoryTransactionTypePK = inventoryTransactionType.getPrimaryKey(); // Not updated
            var inventoryTransactionReasonPK = inventoryTransactionReasonDetail.getInventoryTransactionReasonPK(); // Not updated
            var inventoryTransactionReasonName = inventoryTransactionReasonDetailValue.getInventoryTransactionReasonName();
            var inventoryDispositionPK = inventoryTransactionReasonDetailValue.getInventoryDispositionPK();
            var isDefault = inventoryTransactionReasonDetailValue.getIsDefault();
            var sortOrder = inventoryTransactionReasonDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultInventoryTransactionReason = getDefaultInventoryTransactionReason(inventoryTransactionType);
                var defaultFound = 
                        defaultInventoryTransactionReason != null && !defaultInventoryTransactionReason.equals(inventoryTransactionReason);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryTransactionReasonDetailValue = 
                            getDefaultInventoryTransactionReasonDetailValueForUpdate(inventoryTransactionType);

                    defaultInventoryTransactionReasonDetailValue.setIsDefault(false);
                    updateInventoryTransactionReasonFromValue(defaultInventoryTransactionReasonDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            inventoryTransactionReasonDetail = inventoryTransactionReasonDetailFactory.create(inventoryTransactionReasonPK,
                    inventoryTransactionTypePK, inventoryTransactionReasonName, inventoryDispositionPK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            inventoryTransactionReason.setActiveDetail(inventoryTransactionReasonDetail);
            inventoryTransactionReason.setLastDetail(inventoryTransactionReasonDetail);

            sendEvent(inventoryTransactionReasonPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateInventoryTransactionReasonFromValue(InventoryTransactionReasonDetailValue inventoryTransactionReasonDetailValue,
            BasePK updatedBy) {
        updateInventoryTransactionReasonFromValue(inventoryTransactionReasonDetailValue, true, updatedBy);
    }

    public void deleteInventoryTransactionReason(InventoryTransactionReason inventoryTransactionReason, BasePK deletedBy) {
        var inventoryTransactionReasonDetail = inventoryTransactionReason.getLastDetailForUpdate();

        deleteInventoryTransactionReasonDescriptionsByInventoryTransactionReason(inventoryTransactionReason, deletedBy);

        inventoryTransactionReasonDetail.setThruTime(session.getStartTime());
        inventoryTransactionReason.setActiveDetail(null);
        inventoryTransactionReason.store();

        // Check for default, and pick one if necessary
        var inventoryTransactionType = inventoryTransactionReasonDetail.getInventoryTransactionType();
        var defaultInventoryTransactionReason = getDefaultInventoryTransactionReason(inventoryTransactionType);
        if(defaultInventoryTransactionReason == null) {
            var inventoryTransactionReasons = getInventoryTransactionReasonsForUpdate(inventoryTransactionType);

            if(!inventoryTransactionReasons.isEmpty()) {
                var iter = inventoryTransactionReasons.iterator();
                if(iter.hasNext()) {
                    defaultInventoryTransactionReason = iter.next();
                }
                var inventoryTransactionReasonDetailValue = 
                        Objects.requireNonNull(
                                defaultInventoryTransactionReason).getLastDetailForUpdate().getInventoryTransactionReasonDetailValue().clone();

                inventoryTransactionReasonDetailValue.setIsDefault(true);
                updateInventoryTransactionReasonFromValue(inventoryTransactionReasonDetailValue, false, deletedBy);
            }
        }

        sendEvent(inventoryTransactionReason.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryTransactionReasonsByInventoryTransactionType(InventoryTransactionType inventoryTransactionType, BasePK deletedBy) {
        var inventoryTransactionReasons = getInventoryTransactionReasonsForUpdate(inventoryTransactionType);

        inventoryTransactionReasons.forEach(inventoryTransactionReason ->
                deleteInventoryTransactionReason(inventoryTransactionReason, deletedBy));
    }

    public void deleteInventoryTransactionReasonsByInventoryDisposition(InventoryDisposition inventoryDisposition, BasePK deletedBy) {
        var inventoryTransactionReasons = getInventoryTransactionReasonsByInventoryDispositionForUpdate(inventoryDisposition);

        inventoryTransactionReasons.forEach(inventoryTransactionReason ->
                deleteInventoryTransactionReason(inventoryTransactionReason, deletedBy));
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Reason Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    InventoryTransactionReasonDescriptionFactory inventoryTransactionReasonDescriptionFactory;

    @Inject
    InventoryTransactionReasonDescriptionTransferCache inventoryTransactionReasonDescriptionTransferCache;

    public InventoryTransactionReasonDescription createInventoryTransactionReasonDescription(
            InventoryTransactionReason inventoryTransactionReason, Language language, String description, BasePK createdBy) {
        var inventoryTransactionReasonDescription = inventoryTransactionReasonDescriptionFactory.create(inventoryTransactionReason,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryTransactionReason.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionReasonDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return inventoryTransactionReasonDescription;
    }

    public long countInventoryTransactionReasonDescriptionsByInventoryTransactionReason(
            InventoryTransactionReason inventoryTransactionReason) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionReasonDescriptions)
                .where(InventoryTransactionReasonDescriptions.INVENTORY_TRANSACTION_REASON.eq(inventoryTransactionReason.getPrimaryKey()),
                        InventoryTransactionReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryTransactionReasonDescriptionsByLanguage(Language language) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionReasonDescriptions)
                .where(InventoryTransactionReasonDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryTransactionReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private InventoryTransactionReasonDescription getInventoryTransactionReasonDescription(
            InventoryTransactionReason inventoryTransactionReason, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionReasonDescriptions.fields())
                .from(InventoryTransactionReasonDescriptions)
                .where(InventoryTransactionReasonDescriptions.INVENTORY_TRANSACTION_REASON.eq(inventoryTransactionReason.getPrimaryKey()),
                        InventoryTransactionReasonDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        InventoryTransactionReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionReasonDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionReasonDescription getInventoryTransactionReasonDescription(
            InventoryTransactionReason inventoryTransactionReason, Language language) {
        return getInventoryTransactionReasonDescription(inventoryTransactionReason, language, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionReasonDescription getInventoryTransactionReasonDescriptionForUpdate(
            InventoryTransactionReason inventoryTransactionReason, Language language) {
        return getInventoryTransactionReasonDescription(inventoryTransactionReason, language, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionReasonDescriptionValue getInventoryTransactionReasonDescriptionValue(
            InventoryTransactionReasonDescription inventoryTransactionReasonDescription) {
        return inventoryTransactionReasonDescription == null ? null
                : inventoryTransactionReasonDescription.getInventoryTransactionReasonDescriptionValue().clone();
    }

    public InventoryTransactionReasonDescriptionValue getInventoryTransactionReasonDescriptionValueForUpdate(
            InventoryTransactionReason inventoryTransactionReason, Language language) {
        return getInventoryTransactionReasonDescriptionValue(getInventoryTransactionReasonDescriptionForUpdate(inventoryTransactionReason,
                language));
    }

    private List<InventoryTransactionReasonDescription> getInventoryTransactionReasonDescriptionsByInventoryTransactionReason(
            InventoryTransactionReason inventoryTransactionReason, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionReasonDescriptions.fields())
                    .from(InventoryTransactionReasonDescriptions)
                    .join(Languages).on(InventoryTransactionReasonDescriptions.LANGUAGE.eq(Languages.LANGUAGE))
                    .where(InventoryTransactionReasonDescriptions.INVENTORY_TRANSACTION_REASON.eq(inventoryTransactionReason.getPrimaryKey()),
                            InventoryTransactionReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    InventoryTransactionReasonDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionReasonDescriptions.fields())
                    .from(InventoryTransactionReasonDescriptions)
                    .where(InventoryTransactionReasonDescriptions.INVENTORY_TRANSACTION_REASON.eq(inventoryTransactionReason.getPrimaryKey()),
                            InventoryTransactionReasonDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionReasonDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryTransactionReasonDescription> getInventoryTransactionReasonDescriptionsByInventoryTransactionReason(
            InventoryTransactionReason inventoryTransactionReason) {
        return getInventoryTransactionReasonDescriptionsByInventoryTransactionReason(inventoryTransactionReason, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionReasonDescription> getInventoryTransactionReasonDescriptionsByInventoryTransactionReasonForUpdate(
            InventoryTransactionReason inventoryTransactionReason) {
        return getInventoryTransactionReasonDescriptionsByInventoryTransactionReason(inventoryTransactionReason, EntityPermission.READ_WRITE);
    }

    public String getBestInventoryTransactionReasonDescription(InventoryTransactionReason inventoryTransactionReason, Language language) {
        String description;
        var inventoryTransactionReasonDescription = getInventoryTransactionReasonDescription(inventoryTransactionReason, language);

        if(inventoryTransactionReasonDescription == null && !language.getIsDefault()) {
            inventoryTransactionReasonDescription = getInventoryTransactionReasonDescription(inventoryTransactionReason,
                    partyControl.getDefaultLanguage());
        }

        if(inventoryTransactionReasonDescription == null) {
            description = inventoryTransactionReason.getLastDetail().getInventoryTransactionReasonName();
        } else {
            description = inventoryTransactionReasonDescription.getDescription();
        }

        return description;
    }

    public InventoryTransactionReasonDescriptionTransfer getInventoryTransactionReasonDescriptionTransfer(UserVisit userVisit,
            InventoryTransactionReasonDescription inventoryTransactionReasonDescription) {
        return inventoryTransactionReasonDescriptionTransferCache.getTransfer(userVisit, inventoryTransactionReasonDescription);
    }

    public List<InventoryTransactionReasonDescriptionTransfer> getInventoryTransactionReasonDescriptionTransfers(
            UserVisit userVisit, Collection<InventoryTransactionReasonDescription> inventoryTransactionReasonDescriptions) {
        var transfers = new ArrayList<InventoryTransactionReasonDescriptionTransfer>(inventoryTransactionReasonDescriptions.size());

        inventoryTransactionReasonDescriptions.forEach(inventoryTransactionReasonDescription ->
                transfers.add(inventoryTransactionReasonDescriptionTransferCache.getTransfer(userVisit,
                        inventoryTransactionReasonDescription)));

        return transfers;
    }

    public List<InventoryTransactionReasonDescriptionTransfer> getInventoryTransactionReasonDescriptionTransfersByInventoryTransactionReason(
            UserVisit userVisit, InventoryTransactionReason inventoryTransactionReason) {
        return getInventoryTransactionReasonDescriptionTransfers(userVisit,
                getInventoryTransactionReasonDescriptionsByInventoryTransactionReason(inventoryTransactionReason));
    }

    public void updateInventoryTransactionReasonDescriptionFromValue(
            InventoryTransactionReasonDescriptionValue inventoryTransactionReasonDescriptionValue, BasePK updatedBy) {
        if(inventoryTransactionReasonDescriptionValue.hasBeenModified()) {
            var inventoryTransactionReasonDescription = inventoryTransactionReasonDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryTransactionReasonDescriptionValue.getPrimaryKey());

            inventoryTransactionReasonDescription.setThruTime(session.getStartTime());
            inventoryTransactionReasonDescription.store();

            var inventoryTransactionReason = inventoryTransactionReasonDescription.getInventoryTransactionReason();
            var language = inventoryTransactionReasonDescription.getLanguage();
            var description = inventoryTransactionReasonDescriptionValue.getDescription();

            inventoryTransactionReasonDescription = inventoryTransactionReasonDescriptionFactory.create(inventoryTransactionReason,
                    language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryTransactionReason.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionReasonDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryTransactionReasonDescription(InventoryTransactionReasonDescription inventoryTransactionReasonDescription,
            BasePK deletedBy) {
        inventoryTransactionReasonDescription.setThruTime(session.getStartTime());

        sendEvent(inventoryTransactionReasonDescription.getInventoryTransactionReasonPK(), EventTypes.MODIFY,
                inventoryTransactionReasonDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteInventoryTransactionReasonDescriptionsByInventoryTransactionReason(
            InventoryTransactionReason inventoryTransactionReason, BasePK deletedBy) {
        var inventoryTransactionReasonDescriptions = 
                getInventoryTransactionReasonDescriptionsByInventoryTransactionReasonForUpdate(inventoryTransactionReason);

        inventoryTransactionReasonDescriptions.forEach((inventoryTransactionReasonDescription) -> 
                deleteInventoryTransactionReasonDescription(inventoryTransactionReasonDescription, deletedBy)
        );
    }

}

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

package com.echothree.model.control.picklist.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.picklist.common.choice.PicklistAliasTypeChoicesBean;
import com.echothree.model.control.picklist.common.choice.PicklistTimeTypeChoicesBean;
import com.echothree.model.control.picklist.common.choice.PicklistTypeChoicesBean;
import com.echothree.model.control.picklist.common.transfer.PicklistAliasTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistAliasTypeDescriptionTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistAliasTypeTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTimeTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTimeTypeDescriptionTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTimeTypeTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTypeDescriptionTransfer;
import com.echothree.model.control.picklist.common.transfer.PicklistTypeTransfer;
import com.echothree.model.control.picklist.server.transfer.PicklistAliasTransferCache;
import com.echothree.model.control.picklist.server.transfer.PicklistAliasTypeDescriptionTransferCache;
import com.echothree.model.control.picklist.server.transfer.PicklistAliasTypeTransferCache;
import com.echothree.model.control.picklist.server.transfer.PicklistTimeTransferCache;
import com.echothree.model.control.picklist.server.transfer.PicklistTimeTypeDescriptionTransferCache;
import com.echothree.model.control.picklist.server.transfer.PicklistTimeTypeTransferCache;
import com.echothree.model.control.picklist.server.transfer.PicklistTypeDescriptionTransferCache;
import com.echothree.model.control.picklist.server.transfer.PicklistTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.picklist.common.pk.PicklistAliasTypePK;
import com.echothree.model.data.picklist.common.pk.PicklistTimeTypePK;
import com.echothree.model.data.picklist.common.pk.PicklistTypePK;
import com.echothree.model.data.picklist.server.entity.Picklist;
import com.echothree.model.data.picklist.server.entity.PicklistAlias;
import com.echothree.model.data.picklist.server.entity.PicklistAliasType;
import com.echothree.model.data.picklist.server.entity.PicklistAliasTypeDescription;
import com.echothree.model.data.picklist.server.entity.PicklistTime;
import com.echothree.model.data.picklist.server.entity.PicklistTimeType;
import com.echothree.model.data.picklist.server.entity.PicklistTimeTypeDescription;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.model.data.picklist.server.entity.PicklistTypeDescription;
import com.echothree.model.data.picklist.server.factory.PicklistAliasFactory;
import com.echothree.model.data.picklist.server.factory.PicklistAliasTypeDescriptionFactory;
import com.echothree.model.data.picklist.server.factory.PicklistAliasTypeDetailFactory;
import com.echothree.model.data.picklist.server.factory.PicklistAliasTypeFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTimeFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTimeTypeDescriptionFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTimeTypeDetailFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTimeTypeFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTypeDescriptionFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTypeDetailFactory;
import com.echothree.model.data.picklist.server.factory.PicklistTypeFactory;
import com.echothree.model.data.picklist.server.value.PicklistAliasTypeDescriptionValue;
import com.echothree.model.data.picklist.server.value.PicklistAliasTypeDetailValue;
import com.echothree.model.data.picklist.server.value.PicklistAliasValue;
import com.echothree.model.data.picklist.server.value.PicklistTimeTypeDescriptionValue;
import com.echothree.model.data.picklist.server.value.PicklistTimeTypeDetailValue;
import com.echothree.model.data.picklist.server.value.PicklistTimeValue;
import com.echothree.model.data.picklist.server.value.PicklistTypeDescriptionValue;
import com.echothree.model.data.picklist.server.value.PicklistTypeDetailValue;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLISTS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_ALIASES_PICKLIST_ALIAS_TYPE_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_ALIASES_PICKLIST_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_ALIAS_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_ALIAS_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_TIMES_PICKLIST_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_TIMES_PICKLIST_TIME_TYPE_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_TIME_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_TIME_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_TIME_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.picklist.PicklistForeignKeys.PICKLIST_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.picklist.PicklistAliasTypeDescriptions.PicklistAliasTypeDescriptions;
import static com.echothree.model.jooq.server.tables.picklist.PicklistAliasTypeDetails.PicklistAliasTypeDetails;
import static com.echothree.model.jooq.server.tables.picklist.PicklistAliasTypes.PicklistAliasTypes;
import static com.echothree.model.jooq.server.tables.picklist.PicklistAliases.PicklistAliases;
import static com.echothree.model.jooq.server.tables.picklist.PicklistDetails.PicklistDetails;
import static com.echothree.model.jooq.server.tables.picklist.PicklistTimeTypeDescriptions.PicklistTimeTypeDescriptions;
import static com.echothree.model.jooq.server.tables.picklist.PicklistTimeTypeDetails.PicklistTimeTypeDetails;
import static com.echothree.model.jooq.server.tables.picklist.PicklistTimeTypes.PicklistTimeTypes;
import static com.echothree.model.jooq.server.tables.picklist.PicklistTimes.PicklistTimes;
import static com.echothree.model.jooq.server.tables.picklist.PicklistTypeDescriptions.PicklistTypeDescriptions;
import static com.echothree.model.jooq.server.tables.picklist.PicklistTypeDetails.PicklistTypeDetails;
import static com.echothree.model.jooq.server.tables.picklist.PicklistTypes.PicklistTypes;
import static com.echothree.model.jooq.server.tables.picklist.Picklists.Picklists;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;

@CommandScope
public class PicklistControl
        extends BaseModelControl {
    
    /** Creates a new instance of PicklistControl */
    protected PicklistControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Picklist Types
    // --------------------------------------------------------------------------------

    @Inject
    protected PicklistTypeDetailFactory picklistTypeDetailFactory;

    @Inject
    private PicklistTypeFactory picklistTypeFactory;

    @Inject
    private PicklistTypeTransferCache picklistTypeTransferCache;

    public PicklistType createPicklistType(String picklistTypeName, PicklistType parentPicklistType, SequenceType picklistSequenceType, Workflow picklistWorkflow,
            WorkflowEntrance picklistWorkflowEntrance, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPicklistType = getDefaultPicklistType();
        var defaultFound = defaultPicklistType != null;

        if(defaultFound && isDefault) {
            var defaultPicklistTypeDetailValue = getDefaultPicklistTypeDetailValueForUpdate();

            defaultPicklistTypeDetailValue.setIsDefault(false);
            updatePicklistTypeFromValue(defaultPicklistTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var picklistType = picklistTypeFactory.create();
        var picklistTypeDetail = picklistTypeDetailFactory.create(picklistType, picklistTypeName, parentPicklistType, picklistSequenceType,
                picklistWorkflow, picklistWorkflowEntrance, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        picklistType = picklistTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                picklistType.getPrimaryKey());
        picklistType.setActiveDetail(picklistTypeDetail);
        picklistType.setLastDetail(picklistTypeDetail);
        picklistType.store();

        sendEvent(picklistType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return picklistType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PicklistType */
    public PicklistType getPicklistTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PicklistTypePK(entityInstance.getEntityUniqueId());

        return picklistTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public PicklistType getPicklistTypeByEntityInstance(EntityInstance entityInstance) {
        return getPicklistTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PicklistType getPicklistTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPicklistTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPicklistTypes() {
        return session.getDslContext()
                .selectCount()
                .from(PicklistTypes)
                .join(PicklistTypeDetails).onKey(PICKLIST_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PicklistType getPicklistTypeByName(String picklistTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistTypes.fields())
                .from(PicklistTypes)
                .join(PicklistTypeDetails).onKey(PICKLIST_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistTypeDetails.PICKLIST_TYPE_NAME.eq(picklistTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistType getPicklistTypeByName(String picklistTypeName) {
        return getPicklistTypeByName(picklistTypeName, EntityPermission.READ_ONLY);
    }

    public PicklistType getPicklistTypeByNameForUpdate(String picklistTypeName) {
        return getPicklistTypeByName(picklistTypeName, EntityPermission.READ_WRITE);
    }

    public PicklistTypeDetailValue getPicklistTypeDetailValueForUpdate(PicklistType picklistType) {
        return picklistType == null? null: picklistType.getLastDetailForUpdate().getPicklistTypeDetailValue().clone();
    }

    public PicklistTypeDetailValue getPicklistTypeDetailValueByNameForUpdate(String picklistTypeName) {
        return getPicklistTypeDetailValueForUpdate(getPicklistTypeByNameForUpdate(picklistTypeName));
    }

    private PicklistType getDefaultPicklistType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistTypes.fields())
                .from(PicklistTypes)
                .join(PicklistTypeDetails).onKey(PICKLIST_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistType getDefaultPicklistType() {
        return getDefaultPicklistType(EntityPermission.READ_ONLY);
    }

    public PicklistType getDefaultPicklistTypeForUpdate() {
        return getDefaultPicklistType(EntityPermission.READ_WRITE);
    }

    public PicklistTypeDetailValue getDefaultPicklistTypeDetailValueForUpdate() {
        return getDefaultPicklistTypeForUpdate().getLastDetailForUpdate().getPicklistTypeDetailValue().clone();
    }

    private List<PicklistType> getPicklistTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistTypes.fields())
                .from(PicklistTypes)
                .join(PicklistTypeDetails).onKey(PICKLIST_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PicklistTypeDetails.SORT_ORDER, PicklistTypeDetails.PICKLIST_TYPE_NAME),
                    PicklistTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistType> getPicklistTypes() {
        return getPicklistTypes(EntityPermission.READ_ONLY);
    }

    public List<PicklistType> getPicklistTypesForUpdate() {
        return getPicklistTypes(EntityPermission.READ_WRITE);
    }

    private List<PicklistType> getPicklistTypesByParentPicklistType(PicklistType parentPicklistType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistTypes.fields())
                .from(PicklistTypes)
                .join(PicklistTypeDetails).onKey(PICKLIST_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistTypeDetails.PARENT_PICKLIST_TYPE.eq(parentPicklistType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PicklistTypeDetails.SORT_ORDER, PicklistTypeDetails.PICKLIST_TYPE_NAME),
                    PicklistTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistType> getPicklistTypesByParentPicklistType(PicklistType parentPicklistType) {
        return getPicklistTypesByParentPicklistType(parentPicklistType, EntityPermission.READ_ONLY);
    }

    public List<PicklistType> getPicklistTypesByParentPicklistTypeForUpdate(PicklistType parentPicklistType) {
        return getPicklistTypesByParentPicklistType(parentPicklistType, EntityPermission.READ_WRITE);
    }

    public PicklistTypeTransfer getPicklistTypeTransfer(UserVisit userVisit, PicklistType picklistType) {
        return picklistTypeTransferCache.getPicklistTypeTransfer(userVisit, picklistType);
    }

    public List<PicklistTypeTransfer> getPicklistTypeTransfers(UserVisit userVisit, Collection<PicklistType> picklistTypes) {
        List<PicklistTypeTransfer> picklistTypeTransfers = new ArrayList<>(picklistTypes.size());

        picklistTypes.forEach((picklistType) ->
                picklistTypeTransfers.add(picklistTypeTransferCache.getPicklistTypeTransfer(userVisit, picklistType))
        );

        return picklistTypeTransfers;
    }

    public List<PicklistTypeTransfer> getPicklistTypeTransfers(UserVisit userVisit) {
        return getPicklistTypeTransfers(userVisit, getPicklistTypes());
    }

    public PicklistTypeChoicesBean getPicklistTypeChoices(String defaultPicklistTypeChoice,
            Language language, boolean allowNullChoice) {
        var picklistTypes = getPicklistTypes();
        var size = picklistTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPicklistTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var picklistType : picklistTypes) {
            var picklistTypeDetail = picklistType.getLastDetail();

            var label = getBestPicklistTypeDescription(picklistType, language);
            var value = picklistTypeDetail.getPicklistTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPicklistTypeChoice != null && defaultPicklistTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && picklistTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PicklistTypeChoicesBean(labels, values, defaultValue);
    }

    public boolean isParentPicklistTypeSafe(PicklistType picklistType,
            PicklistType parentPicklistType) {
        var safe = true;

        if(parentPicklistType != null) {
            Set<PicklistType> parentPicklistTypes = new HashSet<>();

            parentPicklistTypes.add(picklistType);
            do {
                if(parentPicklistTypes.contains(parentPicklistType)) {
                    safe = false;
                    break;
                }

                parentPicklistTypes.add(parentPicklistType);
                parentPicklistType = parentPicklistType.getLastDetail().getParentPicklistType();
            } while(parentPicklistType != null);
        }

        return safe;
    }

    private void updatePicklistTypeFromValue(PicklistTypeDetailValue picklistTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(picklistTypeDetailValue.hasBeenModified()) {
            var picklistType = picklistTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     picklistTypeDetailValue.getPicklistTypePK());
            var picklistTypeDetail = picklistType.getActiveDetailForUpdate();

            picklistTypeDetail.setThruTime(session.getStartTime());
            picklistTypeDetail.store();

            var picklistTypePK = picklistTypeDetail.getPicklistTypePK(); // Not updated
            var picklistTypeName = picklistTypeDetailValue.getPicklistTypeName();
            var parentPicklistTypePK = picklistTypeDetailValue.getParentPicklistTypePK();
            var picklistSequenceTypePK = picklistTypeDetailValue.getPicklistSequenceTypePK();
            var picklistWorkflowPK = picklistTypeDetailValue.getPicklistWorkflowPK();
            var picklistWorkflowEntrancePK = picklistTypeDetailValue.getPicklistWorkflowEntrancePK();
            var isDefault = picklistTypeDetailValue.getIsDefault();
            var sortOrder = picklistTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPicklistType = getDefaultPicklistType();
                var defaultFound = defaultPicklistType != null && !defaultPicklistType.equals(picklistType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPicklistTypeDetailValue = getDefaultPicklistTypeDetailValueForUpdate();

                    defaultPicklistTypeDetailValue.setIsDefault(false);
                    updatePicklistTypeFromValue(defaultPicklistTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            picklistTypeDetail = picklistTypeDetailFactory.create(picklistTypePK, picklistTypeName, parentPicklistTypePK, picklistSequenceTypePK,
                    picklistWorkflowPK, picklistWorkflowEntrancePK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            picklistType.setActiveDetail(picklistTypeDetail);
            picklistType.setLastDetail(picklistTypeDetail);

            sendEvent(picklistTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePicklistTypeFromValue(PicklistTypeDetailValue picklistTypeDetailValue, BasePK updatedBy) {
        updatePicklistTypeFromValue(picklistTypeDetailValue, true, updatedBy);
    }

    private void deletePicklistType(PicklistType picklistType, boolean checkDefault, BasePK deletedBy) {
        var picklistTypeDetail = picklistType.getLastDetailForUpdate();
        
        deletePicklistTypesByParentPicklistType(picklistType, deletedBy);
        deletePicklistTypeDescriptionsByPicklistType(picklistType, deletedBy);
        deletePicklistAliasTypesByPicklistType(picklistType, deletedBy);
        // TODO: deletePicklistsByPicklistType(picklistType, deletedBy);

        picklistTypeDetail.setThruTime(session.getStartTime());
        picklistType.setActiveDetail(null);
        picklistType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultPicklistType = getDefaultPicklistType();

            if(defaultPicklistType == null) {
                var picklistTypes = getPicklistTypesForUpdate();

                if(!picklistTypes.isEmpty()) {
                    var iter = picklistTypes.iterator();
                    if(iter.hasNext()) {
                        defaultPicklistType = iter.next();
                    }
                    var picklistTypeDetailValue = Objects.requireNonNull(defaultPicklistType).getLastDetailForUpdate().getPicklistTypeDetailValue().clone();

                    picklistTypeDetailValue.setIsDefault(true);
                    updatePicklistTypeFromValue(picklistTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(picklistType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePicklistType(PicklistType picklistType, BasePK deletedBy) {
        deletePicklistType(picklistType, true, deletedBy);
    }

    private void deletePicklistTypes(List<PicklistType> picklistTypes, boolean checkDefault, BasePK deletedBy) {
        picklistTypes.forEach((picklistType) -> deletePicklistType(picklistType, checkDefault, deletedBy));
    }

    public void deletePicklistTypes(List<PicklistType> picklistTypes, BasePK deletedBy) {
        deletePicklistTypes(picklistTypes, true, deletedBy);
    }

    private void deletePicklistTypesByParentPicklistType(PicklistType parentPicklistType, BasePK deletedBy) {
        deletePicklistTypes(getPicklistTypesByParentPicklistTypeForUpdate(parentPicklistType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private PicklistTypeDescriptionFactory picklistTypeDescriptionFactory;

    @Inject
    private PicklistTypeDescriptionTransferCache picklistTypeDescriptionTransferCache;

    public PicklistTypeDescription createPicklistTypeDescription(PicklistType picklistType, Language language, String description, BasePK createdBy) {
        var picklistTypeDescription = picklistTypeDescriptionFactory.create(picklistType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(picklistType.getPrimaryKey(), EventTypes.MODIFY, picklistTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return picklistTypeDescription;
    }

    private PicklistTypeDescription getPicklistTypeDescription(PicklistType picklistType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistTypeDescriptions.fields())
                .from(PicklistTypeDescriptions)
                .where(PicklistTypeDescriptions.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()),
                        PicklistTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PicklistTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistTypeDescription getPicklistTypeDescription(PicklistType picklistType, Language language) {
        return getPicklistTypeDescription(picklistType, language, EntityPermission.READ_ONLY);
    }

    public PicklistTypeDescription getPicklistTypeDescriptionForUpdate(PicklistType picklistType, Language language) {
        return getPicklistTypeDescription(picklistType, language, EntityPermission.READ_WRITE);
    }

    public PicklistTypeDescriptionValue getPicklistTypeDescriptionValue(PicklistTypeDescription picklistTypeDescription) {
        return picklistTypeDescription == null? null: picklistTypeDescription.getPicklistTypeDescriptionValue().clone();
    }

    public PicklistTypeDescriptionValue getPicklistTypeDescriptionValueForUpdate(PicklistType picklistType, Language language) {
        return getPicklistTypeDescriptionValue(getPicklistTypeDescriptionForUpdate(picklistType, language));
    }

    private List<PicklistTypeDescription> getPicklistTypeDescriptionsByPicklistType(PicklistType picklistType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PicklistTypeDescriptions.fields())
                    .from(PicklistTypeDescriptions)
                    .join(Languages).onKey(PICKLIST_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PicklistTypeDescriptions.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()),
                            PicklistTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PicklistTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PicklistTypeDescriptions.fields())
                    .from(PicklistTypeDescriptions)
                    .where(PicklistTypeDescriptions.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()),
                            PicklistTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return picklistTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistTypeDescription> getPicklistTypeDescriptionsByPicklistType(PicklistType picklistType) {
        return getPicklistTypeDescriptionsByPicklistType(picklistType, EntityPermission.READ_ONLY);
    }

    public List<PicklistTypeDescription> getPicklistTypeDescriptionsByPicklistTypeForUpdate(PicklistType picklistType) {
        return getPicklistTypeDescriptionsByPicklistType(picklistType, EntityPermission.READ_WRITE);
    }

    public String getBestPicklistTypeDescription(PicklistType picklistType, Language language) {
        String description;
        var picklistTypeDescription = getPicklistTypeDescription(picklistType, language);

        if(picklistTypeDescription == null && !language.getIsDefault()) {
            picklistTypeDescription = getPicklistTypeDescription(picklistType, partyControl.getDefaultLanguage());
        }

        if(picklistTypeDescription == null) {
            description = picklistType.getLastDetail().getPicklistTypeName();
        } else {
            description = picklistTypeDescription.getDescription();
        }

        return description;
    }

    public PicklistTypeDescriptionTransfer getPicklistTypeDescriptionTransfer(UserVisit userVisit, PicklistTypeDescription picklistTypeDescription) {
        return picklistTypeDescriptionTransferCache.getPicklistTypeDescriptionTransfer(userVisit, picklistTypeDescription);
    }

    public List<PicklistTypeDescriptionTransfer> getPicklistTypeDescriptionTransfersByPicklistType(UserVisit userVisit, PicklistType picklistType) {
        var picklistTypeDescriptions = getPicklistTypeDescriptionsByPicklistType(picklistType);
        List<PicklistTypeDescriptionTransfer> picklistTypeDescriptionTransfers = new ArrayList<>(picklistTypeDescriptions.size());

        picklistTypeDescriptions.forEach((picklistTypeDescription) ->
                picklistTypeDescriptionTransfers.add(picklistTypeDescriptionTransferCache.getPicklistTypeDescriptionTransfer(userVisit, picklistTypeDescription))
        );

        return picklistTypeDescriptionTransfers;
    }

    public void updatePicklistTypeDescriptionFromValue(PicklistTypeDescriptionValue picklistTypeDescriptionValue, BasePK updatedBy) {
        if(picklistTypeDescriptionValue.hasBeenModified()) {
            var picklistTypeDescription = picklistTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    picklistTypeDescriptionValue.getPrimaryKey());

            picklistTypeDescription.setThruTime(session.getStartTime());
            picklistTypeDescription.store();

            var picklistType = picklistTypeDescription.getPicklistType();
            var language = picklistTypeDescription.getLanguage();
            var description = picklistTypeDescriptionValue.getDescription();

            picklistTypeDescription = picklistTypeDescriptionFactory.create(picklistType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(picklistType.getPrimaryKey(), EventTypes.MODIFY, picklistTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePicklistTypeDescription(PicklistTypeDescription picklistTypeDescription, BasePK deletedBy) {
        picklistTypeDescription.setThruTime(session.getStartTime());

        sendEvent(picklistTypeDescription.getPicklistTypePK(), EventTypes.MODIFY, picklistTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePicklistTypeDescriptionsByPicklistType(PicklistType picklistType, BasePK deletedBy) {
        var picklistTypeDescriptions = getPicklistTypeDescriptionsByPicklistTypeForUpdate(picklistType);

        picklistTypeDescriptions.forEach((picklistTypeDescription) -> 
                deletePicklistTypeDescription(picklistTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Picklist Time Types
    // --------------------------------------------------------------------------------

    @Inject
    protected PicklistTimeTypeDetailFactory picklistTimeTypeDetailFactory;

    @Inject
    private PicklistTimeTypeFactory picklistTimeTypeFactory;

    @Inject
    private PicklistTimeTypeTransferCache picklistTimeTypeTransferCache;

    public PicklistTimeType createPicklistTimeType(PicklistType picklistType, String picklistTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPicklistTimeType = getDefaultPicklistTimeType(picklistType);
        var defaultFound = defaultPicklistTimeType != null;

        if(defaultFound && isDefault) {
            var defaultPicklistTimeTypeDetailValue = getDefaultPicklistTimeTypeDetailValueForUpdate(picklistType);

            defaultPicklistTimeTypeDetailValue.setIsDefault(false);
            updatePicklistTimeTypeFromValue(defaultPicklistTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var picklistTimeType = picklistTimeTypeFactory.create();
        var picklistTimeTypeDetail = picklistTimeTypeDetailFactory.create(picklistTimeType, picklistType, picklistTimeTypeName, isDefault,
                sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        picklistTimeType = picklistTimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                picklistTimeType.getPrimaryKey());
        picklistTimeType.setActiveDetail(picklistTimeTypeDetail);
        picklistTimeType.setLastDetail(picklistTimeTypeDetail);
        picklistTimeType.store();

        sendEvent(picklistTimeType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return picklistTimeType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PicklistTimeType */
    public PicklistTimeType getPicklistTimeTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PicklistTimeTypePK(entityInstance.getEntityUniqueId());

        return picklistTimeTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public PicklistTimeType getPicklistTimeTypeByEntityInstance(EntityInstance entityInstance) {
        return getPicklistTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PicklistTimeType getPicklistTimeTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPicklistTimeTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPicklistTimeTypesByPicklistType(final PicklistType picklistType) {
        return session.getDslContext()
                .selectCount()
                .from(PicklistTimeTypes)
                .join(PicklistTimeTypeDetails).onKey(PICKLIST_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistTimeTypeDetails.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PicklistTimeType getPicklistTimeTypeByName(PicklistType picklistType, String picklistTimeTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistTimeTypes.fields())
                .from(PicklistTimeTypes)
                .join(PicklistTimeTypeDetails).onKey(PICKLIST_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistTimeTypeDetails.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()),
                        PicklistTimeTypeDetails.PICKLIST_TIME_TYPE_NAME.eq(picklistTimeTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistTimeTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistTimeType getPicklistTimeTypeByName(PicklistType picklistType, String picklistTimeTypeName) {
        return getPicklistTimeTypeByName(picklistType, picklistTimeTypeName, EntityPermission.READ_ONLY);
    }

    public PicklistTimeType getPicklistTimeTypeByNameForUpdate(PicklistType picklistType, String picklistTimeTypeName) {
        return getPicklistTimeTypeByName(picklistType, picklistTimeTypeName, EntityPermission.READ_WRITE);
    }

    public PicklistTimeTypeDetailValue getPicklistTimeTypeDetailValueForUpdate(PicklistTimeType picklistTimeType) {
        return picklistTimeType == null? null: picklistTimeType.getLastDetailForUpdate().getPicklistTimeTypeDetailValue().clone();
    }

    public PicklistTimeTypeDetailValue getPicklistTimeTypeDetailValueByNameForUpdate(PicklistType picklistType, String picklistTimeTypeName) {
        return getPicklistTimeTypeDetailValueForUpdate(getPicklistTimeTypeByNameForUpdate(picklistType, picklistTimeTypeName));
    }

    private PicklistTimeType getDefaultPicklistTimeType(PicklistType picklistType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistTimeTypes.fields())
                .from(PicklistTimeTypes)
                .join(PicklistTimeTypeDetails).onKey(PICKLIST_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistTimeTypeDetails.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()),
                        PicklistTimeTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistTimeTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistTimeType getDefaultPicklistTimeType(PicklistType picklistType) {
        return getDefaultPicklistTimeType(picklistType, EntityPermission.READ_ONLY);
    }

    public PicklistTimeType getDefaultPicklistTimeTypeForUpdate(PicklistType picklistType) {
        return getDefaultPicklistTimeType(picklistType, EntityPermission.READ_WRITE);
    }

    public PicklistTimeTypeDetailValue getDefaultPicklistTimeTypeDetailValueForUpdate(PicklistType picklistType) {
        return getDefaultPicklistTimeTypeForUpdate(picklistType).getLastDetailForUpdate().getPicklistTimeTypeDetailValue().clone();
    }

    private List<PicklistTimeType> getPicklistTimeTypes(PicklistType picklistType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistTimeTypes.fields())
                .from(PicklistTimeTypes)
                .join(PicklistTimeTypeDetails).onKey(PICKLIST_TIME_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistTimeTypeDetails.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PicklistTimeTypeDetails.SORT_ORDER, PicklistTimeTypeDetails.PICKLIST_TIME_TYPE_NAME),
                    PicklistTimeTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistTimeTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistTimeType> getPicklistTimeTypes(PicklistType picklistType) {
        return getPicklistTimeTypes(picklistType, EntityPermission.READ_ONLY);
    }

    public List<PicklistTimeType> getPicklistTimeTypesForUpdate(PicklistType picklistType) {
        return getPicklistTimeTypes(picklistType, EntityPermission.READ_WRITE);
    }

    public PicklistTimeTypeTransfer getPicklistTimeTypeTransfer(UserVisit userVisit, PicklistTimeType picklistTimeType) {
        return picklistTimeTypeTransferCache.getPicklistTimeTypeTransfer(userVisit, picklistTimeType);
    }

    public List<PicklistTimeTypeTransfer> getPicklistTimeTypeTransfers(UserVisit userVisit, Collection<PicklistTimeType> picklistTimeTypes) {
        List<PicklistTimeTypeTransfer> picklistTimeTypeTransfers = new ArrayList<>(picklistTimeTypes.size());

        picklistTimeTypes.forEach((picklistTimeType) ->
                picklistTimeTypeTransfers.add(picklistTimeTypeTransferCache.getPicklistTimeTypeTransfer(userVisit, picklistTimeType))
        );

        return picklistTimeTypeTransfers;
    }

    public List<PicklistTimeTypeTransfer> getPicklistTimeTypeTransfers(UserVisit userVisit, PicklistType picklistType) {
        return getPicklistTimeTypeTransfers(userVisit, getPicklistTimeTypes(picklistType));
    }

    public PicklistTimeTypeChoicesBean getPicklistTimeTypeChoices(String defaultPicklistTimeTypeChoice, Language language, boolean allowNullChoice,
            PicklistType picklistType) {
        var picklistTimeTypes = getPicklistTimeTypes(picklistType);
        var size = picklistTimeTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPicklistTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var picklistTimeType : picklistTimeTypes) {
            var picklistTimeTypeDetail = picklistTimeType.getLastDetail();

            var label = getBestPicklistTimeTypeDescription(picklistTimeType, language);
            var value = picklistTimeTypeDetail.getPicklistTimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPicklistTimeTypeChoice != null && defaultPicklistTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && picklistTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PicklistTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePicklistTimeTypeFromValue(PicklistTimeTypeDetailValue picklistTimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(picklistTimeTypeDetailValue.hasBeenModified()) {
            var picklistTimeType = picklistTimeTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     picklistTimeTypeDetailValue.getPicklistTimeTypePK());
            var picklistTimeTypeDetail = picklistTimeType.getActiveDetailForUpdate();

            picklistTimeTypeDetail.setThruTime(session.getStartTime());
            picklistTimeTypeDetail.store();

            var picklistType = picklistTimeTypeDetail.getPicklistType(); // Not updated
            var picklistTypePK = picklistType.getPrimaryKey(); // Not updated
            var picklistTimeTypePK = picklistTimeTypeDetail.getPicklistTimeTypePK(); // Not updated
            var picklistTimeTypeName = picklistTimeTypeDetailValue.getPicklistTimeTypeName();
            var isDefault = picklistTimeTypeDetailValue.getIsDefault();
            var sortOrder = picklistTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPicklistTimeType = getDefaultPicklistTimeType(picklistType);
                var defaultFound = defaultPicklistTimeType != null && !defaultPicklistTimeType.equals(picklistTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPicklistTimeTypeDetailValue = getDefaultPicklistTimeTypeDetailValueForUpdate(picklistType);

                    defaultPicklistTimeTypeDetailValue.setIsDefault(false);
                    updatePicklistTimeTypeFromValue(defaultPicklistTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            picklistTimeTypeDetail = picklistTimeTypeDetailFactory.create(picklistTimeTypePK, picklistTypePK, picklistTimeTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            picklistTimeType.setActiveDetail(picklistTimeTypeDetail);
            picklistTimeType.setLastDetail(picklistTimeTypeDetail);

            sendEvent(picklistTimeTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePicklistTimeTypeFromValue(PicklistTimeTypeDetailValue picklistTimeTypeDetailValue, BasePK updatedBy) {
        updatePicklistTimeTypeFromValue(picklistTimeTypeDetailValue, true, updatedBy);
    }

    public void deletePicklistTimeType(PicklistTimeType picklistTimeType, BasePK deletedBy) {
        deletePicklistTimesByPicklistTimeType(picklistTimeType, deletedBy);
        deletePicklistTimeTypeDescriptionsByPicklistTimeType(picklistTimeType, deletedBy);

        var picklistTimeTypeDetail = picklistTimeType.getLastDetailForUpdate();
        picklistTimeTypeDetail.setThruTime(session.getStartTime());
        picklistTimeType.setActiveDetail(null);
        picklistTimeType.store();

        // Check for default, and pick one if necessary
        var picklistType = picklistTimeTypeDetail.getPicklistType();
        var defaultPicklistTimeType = getDefaultPicklistTimeType(picklistType);
        if(defaultPicklistTimeType == null) {
            var picklistTimeTypes = getPicklistTimeTypesForUpdate(picklistType);

            if(!picklistTimeTypes.isEmpty()) {
                var iter = picklistTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultPicklistTimeType = iter.next();
                }
                var picklistTimeTypeDetailValue = Objects.requireNonNull(defaultPicklistTimeType).getLastDetailForUpdate().getPicklistTimeTypeDetailValue().clone();

                picklistTimeTypeDetailValue.setIsDefault(true);
                updatePicklistTimeTypeFromValue(picklistTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(picklistTimeType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private PicklistTimeTypeDescriptionFactory picklistTimeTypeDescriptionFactory;

    @Inject
    private PicklistTimeTypeDescriptionTransferCache picklistTimeTypeDescriptionTransferCache;

    public PicklistTimeTypeDescription createPicklistTimeTypeDescription(PicklistTimeType picklistTimeType, Language language, String description, BasePK createdBy) {
        var picklistTimeTypeDescription = picklistTimeTypeDescriptionFactory.create(picklistTimeType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(picklistTimeType.getPrimaryKey(), EventTypes.MODIFY, picklistTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return picklistTimeTypeDescription;
    }

    private PicklistTimeTypeDescription getPicklistTimeTypeDescription(PicklistTimeType picklistTimeType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistTimeTypeDescriptions.fields())
                .from(PicklistTimeTypeDescriptions)
                .where(PicklistTimeTypeDescriptions.PICKLIST_TIME_TYPE.eq(picklistTimeType.getPrimaryKey()),
                        PicklistTimeTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PicklistTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistTimeTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistTimeTypeDescription getPicklistTimeTypeDescription(PicklistTimeType picklistTimeType, Language language) {
        return getPicklistTimeTypeDescription(picklistTimeType, language, EntityPermission.READ_ONLY);
    }

    public PicklistTimeTypeDescription getPicklistTimeTypeDescriptionForUpdate(PicklistTimeType picklistTimeType, Language language) {
        return getPicklistTimeTypeDescription(picklistTimeType, language, EntityPermission.READ_WRITE);
    }

    public PicklistTimeTypeDescriptionValue getPicklistTimeTypeDescriptionValue(PicklistTimeTypeDescription picklistTimeTypeDescription) {
        return picklistTimeTypeDescription == null? null: picklistTimeTypeDescription.getPicklistTimeTypeDescriptionValue().clone();
    }

    public PicklistTimeTypeDescriptionValue getPicklistTimeTypeDescriptionValueForUpdate(PicklistTimeType picklistTimeType, Language language) {
        return getPicklistTimeTypeDescriptionValue(getPicklistTimeTypeDescriptionForUpdate(picklistTimeType, language));
    }

    private List<PicklistTimeTypeDescription> getPicklistTimeTypeDescriptionsByPicklistTimeType(PicklistTimeType picklistTimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PicklistTimeTypeDescriptions.fields())
                    .from(PicklistTimeTypeDescriptions)
                    .join(Languages).onKey(PICKLIST_TIME_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PicklistTimeTypeDescriptions.PICKLIST_TIME_TYPE.eq(picklistTimeType.getPrimaryKey()),
                            PicklistTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PicklistTimeTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PicklistTimeTypeDescriptions.fields())
                    .from(PicklistTimeTypeDescriptions)
                    .where(PicklistTimeTypeDescriptions.PICKLIST_TIME_TYPE.eq(picklistTimeType.getPrimaryKey()),
                            PicklistTimeTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return picklistTimeTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistTimeTypeDescription> getPicklistTimeTypeDescriptionsByPicklistTimeType(PicklistTimeType picklistTimeType) {
        return getPicklistTimeTypeDescriptionsByPicklistTimeType(picklistTimeType, EntityPermission.READ_ONLY);
    }

    public List<PicklistTimeTypeDescription> getPicklistTimeTypeDescriptionsByPicklistTimeTypeForUpdate(PicklistTimeType picklistTimeType) {
        return getPicklistTimeTypeDescriptionsByPicklistTimeType(picklistTimeType, EntityPermission.READ_WRITE);
    }

    public String getBestPicklistTimeTypeDescription(PicklistTimeType picklistTimeType, Language language) {
        String description;
        var picklistTimeTypeDescription = getPicklistTimeTypeDescription(picklistTimeType, language);

        if(picklistTimeTypeDescription == null && !language.getIsDefault()) {
            picklistTimeTypeDescription = getPicklistTimeTypeDescription(picklistTimeType, partyControl.getDefaultLanguage());
        }

        if(picklistTimeTypeDescription == null) {
            description = picklistTimeType.getLastDetail().getPicklistTimeTypeName();
        } else {
            description = picklistTimeTypeDescription.getDescription();
        }

        return description;
    }

    public PicklistTimeTypeDescriptionTransfer getPicklistTimeTypeDescriptionTransfer(UserVisit userVisit, PicklistTimeTypeDescription picklistTimeTypeDescription) {
        return picklistTimeTypeDescriptionTransferCache.getPicklistTimeTypeDescriptionTransfer(userVisit, picklistTimeTypeDescription);
    }

    public List<PicklistTimeTypeDescriptionTransfer> getPicklistTimeTypeDescriptionTransfersByPicklistTimeType(UserVisit userVisit, PicklistTimeType picklistTimeType) {
        var picklistTimeTypeDescriptions = getPicklistTimeTypeDescriptionsByPicklistTimeType(picklistTimeType);
        List<PicklistTimeTypeDescriptionTransfer> picklistTimeTypeDescriptionTransfers = new ArrayList<>(picklistTimeTypeDescriptions.size());

        picklistTimeTypeDescriptions.forEach((picklistTimeTypeDescription) ->
                picklistTimeTypeDescriptionTransfers.add(picklistTimeTypeDescriptionTransferCache.getPicklistTimeTypeDescriptionTransfer(userVisit, picklistTimeTypeDescription))
        );

        return picklistTimeTypeDescriptionTransfers;
    }

    public void updatePicklistTimeTypeDescriptionFromValue(PicklistTimeTypeDescriptionValue picklistTimeTypeDescriptionValue, BasePK updatedBy) {
        if(picklistTimeTypeDescriptionValue.hasBeenModified()) {
            var picklistTimeTypeDescription = picklistTimeTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    picklistTimeTypeDescriptionValue.getPrimaryKey());

            picklistTimeTypeDescription.setThruTime(session.getStartTime());
            picklistTimeTypeDescription.store();

            var picklistTimeType = picklistTimeTypeDescription.getPicklistTimeType();
            var language = picklistTimeTypeDescription.getLanguage();
            var description = picklistTimeTypeDescriptionValue.getDescription();

            picklistTimeTypeDescription = picklistTimeTypeDescriptionFactory.create(picklistTimeType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(picklistTimeType.getPrimaryKey(), EventTypes.MODIFY, picklistTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePicklistTimeTypeDescription(PicklistTimeTypeDescription picklistTimeTypeDescription, BasePK deletedBy) {
        picklistTimeTypeDescription.setThruTime(session.getStartTime());

        sendEvent(picklistTimeTypeDescription.getPicklistTimeTypePK(), EventTypes.MODIFY, picklistTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePicklistTimeTypeDescriptionsByPicklistTimeType(PicklistTimeType picklistTimeType, BasePK deletedBy) {
        var picklistTimeTypeDescriptions = getPicklistTimeTypeDescriptionsByPicklistTimeTypeForUpdate(picklistTimeType);

        picklistTimeTypeDescriptions.forEach((picklistTimeTypeDescription) -> 
                deletePicklistTimeTypeDescription(picklistTimeTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Picklist Alias Types
    // --------------------------------------------------------------------------------

    @Inject
    protected PicklistAliasTypeDetailFactory picklistAliasTypeDetailFactory;

    @Inject
    private PicklistAliasTypeFactory picklistAliasTypeFactory;

    @Inject
    private PicklistAliasTypeTransferCache picklistAliasTypeTransferCache;

    public PicklistAliasType createPicklistAliasType(PicklistType picklistType, String picklistAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultPicklistAliasType = getDefaultPicklistAliasType(picklistType);
        var defaultFound = defaultPicklistAliasType != null;

        if(defaultFound && isDefault) {
            var defaultPicklistAliasTypeDetailValue = getDefaultPicklistAliasTypeDetailValueForUpdate(picklistType);

            defaultPicklistAliasTypeDetailValue.setIsDefault(false);
            updatePicklistAliasTypeFromValue(defaultPicklistAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var picklistAliasType = picklistAliasTypeFactory.create();
        var picklistAliasTypeDetail = picklistAliasTypeDetailFactory.create(picklistAliasType, picklistType, picklistAliasTypeName,
                validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        picklistAliasType = picklistAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, picklistAliasType.getPrimaryKey());
        picklistAliasType.setActiveDetail(picklistAliasTypeDetail);
        picklistAliasType.setLastDetail(picklistAliasTypeDetail);
        picklistAliasType.store();

        sendEvent(picklistAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return picklistAliasType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.PicklistAliasType */
    public PicklistAliasType getPicklistAliasTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new PicklistAliasTypePK(entityInstance.getEntityUniqueId());

        return picklistAliasTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public PicklistAliasType getPicklistAliasTypeByEntityInstance(EntityInstance entityInstance) {
        return getPicklistAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public PicklistAliasType getPicklistAliasTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getPicklistAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countPicklistAliasTypesByPicklistType(final PicklistType picklistType) {
        return session.getDslContext()
                .selectCount()
                .from(PicklistAliasTypes)
                .join(PicklistAliasTypeDetails).onKey(PICKLIST_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistAliasTypeDetails.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PicklistAliasType getPicklistAliasTypeByName(PicklistType picklistType, String picklistAliasTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistAliasTypes.fields())
                .from(PicklistAliasTypes)
                .join(PicklistAliasTypeDetails).onKey(PICKLIST_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistAliasTypeDetails.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()),
                        PicklistAliasTypeDetails.PICKLIST_ALIAS_TYPE_NAME.eq(picklistAliasTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistAliasType getPicklistAliasTypeByName(PicklistType picklistType, String picklistAliasTypeName) {
        return getPicklistAliasTypeByName(picklistType, picklistAliasTypeName, EntityPermission.READ_ONLY);
    }

    public PicklistAliasType getPicklistAliasTypeByNameForUpdate(PicklistType picklistType, String picklistAliasTypeName) {
        return getPicklistAliasTypeByName(picklistType, picklistAliasTypeName, EntityPermission.READ_WRITE);
    }

    public PicklistAliasTypeDetailValue getPicklistAliasTypeDetailValueForUpdate(PicklistAliasType picklistAliasType) {
        return picklistAliasType == null? null: picklistAliasType.getLastDetailForUpdate().getPicklistAliasTypeDetailValue().clone();
    }

    public PicklistAliasTypeDetailValue getPicklistAliasTypeDetailValueByNameForUpdate(PicklistType picklistType,
            String picklistAliasTypeName) {
        return getPicklistAliasTypeDetailValueForUpdate(getPicklistAliasTypeByNameForUpdate(picklistType, picklistAliasTypeName));
    }

    private PicklistAliasType getDefaultPicklistAliasType(PicklistType picklistType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistAliasTypes.fields())
                .from(PicklistAliasTypes)
                .join(PicklistAliasTypeDetails).onKey(PICKLIST_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistAliasTypeDetails.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()),
                        PicklistAliasTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistAliasType getDefaultPicklistAliasType(PicklistType picklistType) {
        return getDefaultPicklistAliasType(picklistType, EntityPermission.READ_ONLY);
    }

    public PicklistAliasType getDefaultPicklistAliasTypeForUpdate(PicklistType picklistType) {
        return getDefaultPicklistAliasType(picklistType, EntityPermission.READ_WRITE);
    }

    public PicklistAliasTypeDetailValue getDefaultPicklistAliasTypeDetailValueForUpdate(PicklistType picklistType) {
        return getDefaultPicklistAliasTypeForUpdate(picklistType).getLastDetailForUpdate().getPicklistAliasTypeDetailValue().clone();
    }

    private List<PicklistAliasType> getPicklistAliasTypes(PicklistType picklistType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistAliasTypes.fields())
                .from(PicklistAliasTypes)
                .join(PicklistAliasTypeDetails).onKey(PICKLIST_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(PicklistAliasTypeDetails.PICKLIST_TYPE.eq(picklistType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PicklistAliasTypeDetails.SORT_ORDER, PicklistAliasTypeDetails.PICKLIST_ALIAS_TYPE_NAME),
                    PicklistAliasTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistAliasType> getPicklistAliasTypes(PicklistType picklistType) {
        return getPicklistAliasTypes(picklistType, EntityPermission.READ_ONLY);
    }

    public List<PicklistAliasType> getPicklistAliasTypesForUpdate(PicklistType picklistType) {
        return getPicklistAliasTypes(picklistType, EntityPermission.READ_WRITE);
    }

    public PicklistAliasTypeTransfer getPicklistAliasTypeTransfer(UserVisit userVisit, PicklistAliasType picklistAliasType) {
        return picklistAliasTypeTransferCache.getPicklistAliasTypeTransfer(userVisit, picklistAliasType);
    }

    public List<PicklistAliasTypeTransfer> getPicklistAliasTypeTransfers(UserVisit userVisit, Collection<PicklistAliasType> picklistAliasTypes) {
        List<PicklistAliasTypeTransfer> picklistAliasTypeTransfers = new ArrayList<>(picklistAliasTypes.size());

        picklistAliasTypes.forEach((picklistAliasType) ->
                picklistAliasTypeTransfers.add(picklistAliasTypeTransferCache.getPicklistAliasTypeTransfer(userVisit, picklistAliasType))
        );

        return picklistAliasTypeTransfers;
    }

    public List<PicklistAliasTypeTransfer> getPicklistAliasTypeTransfers(UserVisit userVisit, PicklistType picklistType) {
        return getPicklistAliasTypeTransfers(userVisit, getPicklistAliasTypes(picklistType));
    }

    public PicklistAliasTypeChoicesBean getPicklistAliasTypeChoices(String defaultPicklistAliasTypeChoice, Language language,
            boolean allowNullChoice, PicklistType picklistType) {
        var picklistAliasTypes = getPicklistAliasTypes(picklistType);
        var size = picklistAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultPicklistAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var picklistAliasType : picklistAliasTypes) {
            var picklistAliasTypeDetail = picklistAliasType.getLastDetail();

            var label = getBestPicklistAliasTypeDescription(picklistAliasType, language);
            var value = picklistAliasTypeDetail.getPicklistAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultPicklistAliasTypeChoice != null && defaultPicklistAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && picklistAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new PicklistAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updatePicklistAliasTypeFromValue(PicklistAliasTypeDetailValue picklistAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(picklistAliasTypeDetailValue.hasBeenModified()) {
            var picklistAliasType = picklistAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    picklistAliasTypeDetailValue.getPicklistAliasTypePK());
            var picklistAliasTypeDetail = picklistAliasType.getActiveDetailForUpdate();

            picklistAliasTypeDetail.setThruTime(session.getStartTime());
            picklistAliasTypeDetail.store();

            var picklistAliasTypePK = picklistAliasTypeDetail.getPicklistAliasTypePK();
            var picklistType = picklistAliasTypeDetail.getPicklistType();
            var picklistTypePK = picklistType.getPrimaryKey();
            var picklistAliasTypeName = picklistAliasTypeDetailValue.getPicklistAliasTypeName();
            var validationPattern = picklistAliasTypeDetailValue.getValidationPattern();
            var isDefault = picklistAliasTypeDetailValue.getIsDefault();
            var sortOrder = picklistAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultPicklistAliasType = getDefaultPicklistAliasType(picklistType);
                var defaultFound = defaultPicklistAliasType != null && !defaultPicklistAliasType.equals(picklistAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPicklistAliasTypeDetailValue = getDefaultPicklistAliasTypeDetailValueForUpdate(picklistType);

                    defaultPicklistAliasTypeDetailValue.setIsDefault(false);
                    updatePicklistAliasTypeFromValue(defaultPicklistAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            picklistAliasTypeDetail = picklistAliasTypeDetailFactory.create(picklistAliasTypePK, picklistTypePK, picklistAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            picklistAliasType.setActiveDetail(picklistAliasTypeDetail);
            picklistAliasType.setLastDetail(picklistAliasTypeDetail);

            sendEvent(picklistAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updatePicklistAliasTypeFromValue(PicklistAliasTypeDetailValue picklistAliasTypeDetailValue, BasePK updatedBy) {
        updatePicklistAliasTypeFromValue(picklistAliasTypeDetailValue, true, updatedBy);
    }

    public void deletePicklistAliasType(PicklistAliasType picklistAliasType, BasePK deletedBy) {
        deletePicklistAliasesByPicklistAliasType(picklistAliasType, deletedBy);
        deletePicklistAliasTypeDescriptionsByPicklistAliasType(picklistAliasType, deletedBy);

        var picklistAliasTypeDetail = picklistAliasType.getLastDetailForUpdate();
        picklistAliasTypeDetail.setThruTime(session.getStartTime());
        picklistAliasType.setActiveDetail(null);
        picklistAliasType.store();

        // Check for default, and pick one if necessary
        var picklistType = picklistAliasTypeDetail.getPicklistType();
        var defaultPicklistAliasType = getDefaultPicklistAliasType(picklistType);
        if(defaultPicklistAliasType == null) {
            var picklistAliasTypes = getPicklistAliasTypesForUpdate(picklistType);

            if(!picklistAliasTypes.isEmpty()) {
                var iter = picklistAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultPicklistAliasType = iter.next();
                }
                var picklistAliasTypeDetailValue = Objects.requireNonNull(defaultPicklistAliasType).getLastDetailForUpdate().getPicklistAliasTypeDetailValue().clone();

                picklistAliasTypeDetailValue.setIsDefault(true);
                updatePicklistAliasTypeFromValue(picklistAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(picklistAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deletePicklistAliasTypes(List<PicklistAliasType> picklistAliasTypes, BasePK deletedBy) {
        picklistAliasTypes.forEach((picklistAliasType) -> 
                deletePicklistAliasType(picklistAliasType, deletedBy)
        );
    }

    public void deletePicklistAliasTypesByPicklistType(PicklistType picklistType, BasePK deletedBy) {
        deletePicklistAliasTypes(getPicklistAliasTypesForUpdate(picklistType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private PicklistAliasTypeDescriptionFactory picklistAliasTypeDescriptionFactory;

    @Inject
    private PicklistAliasTypeDescriptionTransferCache picklistAliasTypeDescriptionTransferCache;

    public PicklistAliasTypeDescription createPicklistAliasTypeDescription(PicklistAliasType picklistAliasType, Language language, String description, BasePK createdBy) {
        var picklistAliasTypeDescription = picklistAliasTypeDescriptionFactory.create(picklistAliasType, language,
                description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(picklistAliasType.getPrimaryKey(), EventTypes.MODIFY, picklistAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return picklistAliasTypeDescription;
    }

    private PicklistAliasTypeDescription getPicklistAliasTypeDescription(PicklistAliasType picklistAliasType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistAliasTypeDescriptions.fields())
                .from(PicklistAliasTypeDescriptions)
                .where(PicklistAliasTypeDescriptions.PICKLIST_ALIAS_TYPE.eq(picklistAliasType.getPrimaryKey()),
                        PicklistAliasTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        PicklistAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistAliasTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistAliasTypeDescription getPicklistAliasTypeDescription(PicklistAliasType picklistAliasType, Language language) {
        return getPicklistAliasTypeDescription(picklistAliasType, language, EntityPermission.READ_ONLY);
    }

    public PicklistAliasTypeDescription getPicklistAliasTypeDescriptionForUpdate(PicklistAliasType picklistAliasType, Language language) {
        return getPicklistAliasTypeDescription(picklistAliasType, language, EntityPermission.READ_WRITE);
    }

    public PicklistAliasTypeDescriptionValue getPicklistAliasTypeDescriptionValue(PicklistAliasTypeDescription picklistAliasTypeDescription) {
        return picklistAliasTypeDescription == null? null: picklistAliasTypeDescription.getPicklistAliasTypeDescriptionValue().clone();
    }

    public PicklistAliasTypeDescriptionValue getPicklistAliasTypeDescriptionValueForUpdate(PicklistAliasType picklistAliasType, Language language) {
        return getPicklistAliasTypeDescriptionValue(getPicklistAliasTypeDescriptionForUpdate(picklistAliasType, language));
    }

    private List<PicklistAliasTypeDescription> getPicklistAliasTypeDescriptionsByPicklistAliasType(PicklistAliasType picklistAliasType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PicklistAliasTypeDescriptions.fields())
                    .from(PicklistAliasTypeDescriptions)
                    .join(Languages).onKey(PICKLIST_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(PicklistAliasTypeDescriptions.PICKLIST_ALIAS_TYPE.eq(picklistAliasType.getPrimaryKey()),
                            PicklistAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    PicklistAliasTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PicklistAliasTypeDescriptions.fields())
                    .from(PicklistAliasTypeDescriptions)
                    .where(PicklistAliasTypeDescriptions.PICKLIST_ALIAS_TYPE.eq(picklistAliasType.getPrimaryKey()),
                            PicklistAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return picklistAliasTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistAliasTypeDescription> getPicklistAliasTypeDescriptionsByPicklistAliasType(PicklistAliasType picklistAliasType) {
        return getPicklistAliasTypeDescriptionsByPicklistAliasType(picklistAliasType, EntityPermission.READ_ONLY);
    }

    public List<PicklistAliasTypeDescription> getPicklistAliasTypeDescriptionsByPicklistAliasTypeForUpdate(PicklistAliasType picklistAliasType) {
        return getPicklistAliasTypeDescriptionsByPicklistAliasType(picklistAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestPicklistAliasTypeDescription(PicklistAliasType picklistAliasType, Language language) {
        String description;
        var picklistAliasTypeDescription = getPicklistAliasTypeDescription(picklistAliasType, language);

        if(picklistAliasTypeDescription == null && !language.getIsDefault()) {
            picklistAliasTypeDescription = getPicklistAliasTypeDescription(picklistAliasType, partyControl.getDefaultLanguage());
        }

        if(picklistAliasTypeDescription == null) {
            description = picklistAliasType.getLastDetail().getPicklistAliasTypeName();
        } else {
            description = picklistAliasTypeDescription.getDescription();
        }

        return description;
    }

    public PicklistAliasTypeDescriptionTransfer getPicklistAliasTypeDescriptionTransfer(UserVisit userVisit, PicklistAliasTypeDescription picklistAliasTypeDescription) {
        return picklistAliasTypeDescriptionTransferCache.getPicklistAliasTypeDescriptionTransfer(userVisit, picklistAliasTypeDescription);
    }

    public List<PicklistAliasTypeDescriptionTransfer> getPicklistAliasTypeDescriptionTransfersByPicklistAliasType(UserVisit userVisit, PicklistAliasType picklistAliasType) {
        var picklistAliasTypeDescriptions = getPicklistAliasTypeDescriptionsByPicklistAliasType(picklistAliasType);
        List<PicklistAliasTypeDescriptionTransfer> picklistAliasTypeDescriptionTransfers = new ArrayList<>(picklistAliasTypeDescriptions.size());

        picklistAliasTypeDescriptions.forEach((picklistAliasTypeDescription) ->
                picklistAliasTypeDescriptionTransfers.add(picklistAliasTypeDescriptionTransferCache.getPicklistAliasTypeDescriptionTransfer(userVisit, picklistAliasTypeDescription))
        );

        return picklistAliasTypeDescriptionTransfers;
    }

    public void updatePicklistAliasTypeDescriptionFromValue(PicklistAliasTypeDescriptionValue picklistAliasTypeDescriptionValue, BasePK updatedBy) {
        if(picklistAliasTypeDescriptionValue.hasBeenModified()) {
            var picklistAliasTypeDescription = picklistAliasTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     picklistAliasTypeDescriptionValue.getPrimaryKey());

            picklistAliasTypeDescription.setThruTime(session.getStartTime());
            picklistAliasTypeDescription.store();

            var picklistAliasType = picklistAliasTypeDescription.getPicklistAliasType();
            var language = picklistAliasTypeDescription.getLanguage();
            var description = picklistAliasTypeDescriptionValue.getDescription();

            picklistAliasTypeDescription = picklistAliasTypeDescriptionFactory.create(picklistAliasType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(picklistAliasType.getPrimaryKey(), EventTypes.MODIFY, picklistAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePicklistAliasTypeDescription(PicklistAliasTypeDescription picklistAliasTypeDescription, BasePK deletedBy) {
        picklistAliasTypeDescription.setThruTime(session.getStartTime());

        sendEvent(picklistAliasTypeDescription.getPicklistAliasTypePK(), EventTypes.MODIFY, picklistAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePicklistAliasTypeDescriptionsByPicklistAliasType(PicklistAliasType picklistAliasType, BasePK deletedBy) {
        var picklistAliasTypeDescriptions = getPicklistAliasTypeDescriptionsByPicklistAliasTypeForUpdate(picklistAliasType);

        picklistAliasTypeDescriptions.forEach((picklistAliasTypeDescription) -> 
                deletePicklistAliasTypeDescription(picklistAliasTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Picklist Times
    // --------------------------------------------------------------------------------

    @Inject
    private PicklistTimeFactory picklistTimeFactory;

    @Inject
    private PicklistTimeTransferCache picklistTimeTransferCache;

    public PicklistTime createPicklistTime(Picklist picklist, PicklistTimeType picklistTimeType, Long time, BasePK createdBy) {
        var picklistTime = picklistTimeFactory.create(picklist, picklistTimeType, time, session.getStartTime(), Session.MAX_TIME);

        sendEvent(picklist.getPrimaryKey(), EventTypes.MODIFY, picklistTime.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return picklistTime;
    }

    public long countPicklistTimesByPicklist(Picklist picklist) {
        return session.getDslContext()
                .selectCount()
                .from(PicklistTimes)
                .where(PicklistTimes.PICKLIST.eq(picklist.getPrimaryKey()),
                        PicklistTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPicklistTimesByPicklistTimeType(PicklistTimeType picklistTimeType) {
        return session.getDslContext()
                .selectCount()
                .from(PicklistTimes)
                .where(PicklistTimes.PICKLIST_TIME_TYPE.eq(picklistTimeType.getPrimaryKey()),
                        PicklistTimes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PicklistTime getPicklistTime(Picklist picklist, PicklistTimeType picklistTimeType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistTimes.fields())
                .from(PicklistTimes)
                .where(PicklistTimes.PICKLIST.eq(picklist.getPrimaryKey()),
                        PicklistTimes.PICKLIST_TIME_TYPE.eq(picklistTimeType.getPrimaryKey()),
                        PicklistTimes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistTimeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistTime getPicklistTime(Picklist picklist, PicklistTimeType picklistTimeType) {
        return getPicklistTime(picklist, picklistTimeType, EntityPermission.READ_ONLY);
    }

    public PicklistTime getPicklistTimeForUpdate(Picklist picklist, PicklistTimeType picklistTimeType) {
        return getPicklistTime(picklist, picklistTimeType, EntityPermission.READ_WRITE);
    }

    public PicklistTimeValue getPicklistTimeValue(PicklistTime picklistTime) {
        return picklistTime == null? null: picklistTime.getPicklistTimeValue().clone();
    }

    public PicklistTimeValue getPicklistTimeValueForUpdate(Picklist picklist, PicklistTimeType picklistTimeType) {
        return getPicklistTimeValue(getPicklistTimeForUpdate(picklist, picklistTimeType));
    }

    private List<PicklistTime> getPicklistTimesByPicklist(Picklist picklist, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PicklistTimes.fields())
                    .from(PicklistTimes)
                    .join(PicklistTimeTypes).onKey(PICKLIST_TIMES_PICKLIST_TIME_TYPE_FK)
                    .join(PicklistTimeTypeDetails).onKey(PICKLIST_TIME_TYPES_LAST_DETAIL_FK)
                    .where(PicklistTimes.PICKLIST.eq(picklist.getPrimaryKey()),
                            PicklistTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PicklistTimeTypeDetails.SORT_ORDER, PicklistTimeTypeDetails.PICKLIST_TIME_TYPE_NAME),
                    PicklistTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PicklistTimes.fields())
                    .from(PicklistTimes)
                    .where(PicklistTimes.PICKLIST.eq(picklist.getPrimaryKey()),
                            PicklistTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return picklistTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistTime> getPicklistTimesByPicklist(Picklist picklist) {
        return getPicklistTimesByPicklist(picklist, EntityPermission.READ_ONLY);
    }

    public List<PicklistTime> getPicklistTimesByPicklistForUpdate(Picklist picklist) {
        return getPicklistTimesByPicklist(picklist, EntityPermission.READ_WRITE);
    }

    private List<PicklistTime> getPicklistTimesByPicklistTimeType(PicklistTimeType picklistTimeType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PicklistTimes.fields())
                    .from(PicklistTimes)
                    .join(Picklists).onKey(PICKLIST_TIMES_PICKLIST_FK)
                    .join(PicklistDetails).onKey(PICKLISTS_LAST_DETAIL_FK)
                    .where(PicklistTimes.PICKLIST_TIME_TYPE.eq(picklistTimeType.getPrimaryKey()),
                            PicklistTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PicklistDetails.PICKLIST_NAME),
                    PicklistTimeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PicklistTimes.fields())
                    .from(PicklistTimes)
                    .where(PicklistTimes.PICKLIST_TIME_TYPE.eq(picklistTimeType.getPrimaryKey()),
                            PicklistTimes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return picklistTimeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistTime> getPicklistTimesByPicklistTimeType(PicklistTimeType picklistTimeType) {
        return getPicklistTimesByPicklistTimeType(picklistTimeType, EntityPermission.READ_ONLY);
    }

    public List<PicklistTime> getPicklistTimesByPicklistTimeTypeForUpdate(PicklistTimeType picklistTimeType) {
        return getPicklistTimesByPicklistTimeType(picklistTimeType, EntityPermission.READ_WRITE);
    }

    public PicklistTimeTransfer getPicklistTimeTransfer(UserVisit userVisit, PicklistTime picklistTime) {
        return picklistTimeTransferCache.getPicklistTimeTransfer(userVisit, picklistTime);
    }

    public List<PicklistTimeTransfer> getPicklistTimeTransfers(UserVisit userVisit, Collection<PicklistTime> picklistTimes) {
        List<PicklistTimeTransfer> picklistTimeTransfers = new ArrayList<>(picklistTimes.size());

        picklistTimes.forEach((picklistTime) ->
                picklistTimeTransfers.add(picklistTimeTransferCache.getPicklistTimeTransfer(userVisit, picklistTime))
        );

        return picklistTimeTransfers;
    }

    public List<PicklistTimeTransfer> getPicklistTimeTransfersByPicklist(UserVisit userVisit, Picklist picklist) {
        return getPicklistTimeTransfers(userVisit, getPicklistTimesByPicklist(picklist));
    }

    public List<PicklistTimeTransfer> getPicklistTimeTransfersByPicklistTimeType(UserVisit userVisit, PicklistTimeType picklistTimeType) {
        return getPicklistTimeTransfers(userVisit, getPicklistTimesByPicklistTimeType(picklistTimeType));
    }

    public void updatePicklistTimeFromValue(PicklistTimeValue picklistTimeValue, BasePK updatedBy) {
        if(picklistTimeValue.hasBeenModified()) {
            var picklistTime = picklistTimeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    picklistTimeValue.getPrimaryKey());

            picklistTime.setThruTime(session.getStartTime());
            picklistTime.store();

            var picklistPK = picklistTime.getPicklistPK(); // Not updated
            var picklistTimeTypePK = picklistTime.getPicklistTimeTypePK(); // Not updated
            var time = picklistTimeValue.getTime();

            picklistTime = picklistTimeFactory.create(picklistPK, picklistTimeTypePK, time, session.getStartTime(), Session.MAX_TIME);

            sendEvent(picklistPK, EventTypes.MODIFY, picklistTime.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePicklistTime(PicklistTime picklistTime, BasePK deletedBy) {
        picklistTime.setThruTime(session.getStartTime());

        sendEvent(picklistTime.getPicklistTimeTypePK(), EventTypes.MODIFY, picklistTime.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePicklistTimes(List<PicklistTime> picklistTimes, BasePK deletedBy) {
        picklistTimes.forEach((picklistTime) -> 
                deletePicklistTime(picklistTime, deletedBy)
        );
    }

    public void deletePicklistTimesByPicklist(Picklist picklist, BasePK deletedBy) {
        deletePicklistTimes(getPicklistTimesByPicklistForUpdate(picklist), deletedBy);
    }

    public void deletePicklistTimesByPicklistTimeType(PicklistTimeType picklistTimeType, BasePK deletedBy) {
        deletePicklistTimes(getPicklistTimesByPicklistTimeTypeForUpdate(picklistTimeType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Picklist Aliases
    // --------------------------------------------------------------------------------

    @Inject
    private PicklistAliasFactory picklistAliasFactory;

    @Inject
    private PicklistAliasTransferCache picklistAliasTransferCache;

    public PicklistAlias createPicklistAlias(Picklist picklist, PicklistAliasType picklistAliasType, String alias, BasePK createdBy) {
        var picklistAlias = picklistAliasFactory.create(picklist, picklistAliasType, alias, session.getStartTime(), Session.MAX_TIME);

        sendEvent(picklist.getPrimaryKey(), EventTypes.MODIFY, picklistAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return picklistAlias;
    }

    public long countPicklistAliasesBy(final Picklist picklist) {
        return session.getDslContext()
                .selectCount()
                .from(PicklistAliases)
                .where(PicklistAliases.PICKLIST.eq(picklist.getPrimaryKey()),
                        PicklistAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPicklistAliasesBy(final PicklistAliasType picklistAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(PicklistAliases)
                .where(PicklistAliases.PICKLIST_ALIAS_TYPE.eq(picklistAliasType.getPrimaryKey()),
                        PicklistAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PicklistAlias getPicklistAlias(Picklist picklist, PicklistAliasType picklistAliasType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistAliases.fields())
                .from(PicklistAliases)
                .where(PicklistAliases.PICKLIST.eq(picklist.getPrimaryKey()),
                        PicklistAliases.PICKLIST_ALIAS_TYPE.eq(picklistAliasType.getPrimaryKey()),
                        PicklistAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistAliasFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistAlias getPicklistAlias(Picklist picklist, PicklistAliasType picklistAliasType) {
        return getPicklistAlias(picklist, picklistAliasType, EntityPermission.READ_ONLY);
    }

    public PicklistAlias getPicklistAliasForUpdate(Picklist picklist, PicklistAliasType picklistAliasType) {
        return getPicklistAlias(picklist, picklistAliasType, EntityPermission.READ_WRITE);
    }

    public PicklistAliasValue getPicklistAliasValue(PicklistAlias picklistAlias) {
        return picklistAlias == null? null: picklistAlias.getPicklistAliasValue().clone();
    }

    public PicklistAliasValue getPicklistAliasValueForUpdate(Picklist picklist, PicklistAliasType picklistAliasType) {
        return getPicklistAliasValue(getPicklistAliasForUpdate(picklist, picklistAliasType));
    }

    private PicklistAlias getPicklistAliasByAlias(PicklistAliasType picklistAliasType, String alias, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PicklistAliases.fields())
                .from(PicklistAliases)
                .where(PicklistAliases.PICKLIST_ALIAS_TYPE.eq(picklistAliasType.getPrimaryKey()),
                        PicklistAliases.ALIAS.eq(alias),
                        PicklistAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return picklistAliasFactory.getEntityFromQuery(entityPermission, query);
    }

    public PicklistAlias getPicklistAliasByAlias(PicklistAliasType picklistAliasType, String alias) {
        return getPicklistAliasByAlias(picklistAliasType, alias, EntityPermission.READ_ONLY);
    }

    public PicklistAlias getPicklistAliasByAliasForUpdate(PicklistAliasType picklistAliasType, String alias) {
        return getPicklistAliasByAlias(picklistAliasType, alias, EntityPermission.READ_WRITE);
    }

    private List<PicklistAlias> getPicklistAliasesByPicklist(Picklist picklist, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PicklistAliases.fields())
                    .from(PicklistAliases)
                    .join(PicklistAliasTypes).onKey(PICKLIST_ALIASES_PICKLIST_ALIAS_TYPE_FK)
                    .join(PicklistAliasTypeDetails).onKey(PICKLIST_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(PicklistAliases.PICKLIST.eq(picklist.getPrimaryKey()),
                            PicklistAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PicklistAliasTypeDetails.SORT_ORDER, PicklistAliasTypeDetails.PICKLIST_ALIAS_TYPE_NAME),
                    PicklistAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PicklistAliases.fields())
                    .from(PicklistAliases)
                    .where(PicklistAliases.PICKLIST.eq(picklist.getPrimaryKey()),
                            PicklistAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return picklistAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistAlias> getPicklistAliasesByPicklist(Picklist picklist) {
        return getPicklistAliasesByPicklist(picklist, EntityPermission.READ_ONLY);
    }

    public List<PicklistAlias> getPicklistAliasesByPicklistForUpdate(Picklist picklist) {
        return getPicklistAliasesByPicklist(picklist, EntityPermission.READ_WRITE);
    }

    private List<PicklistAlias> getPicklistAliasesByPicklistAliasType(PicklistAliasType picklistAliasType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PicklistAliases.fields())
                    .from(PicklistAliases)
                    .join(Picklists).onKey(PICKLIST_ALIASES_PICKLIST_FK)
                    .join(PicklistDetails).onKey(PICKLISTS_LAST_DETAIL_FK)
                    .where(PicklistAliases.PICKLIST_ALIAS_TYPE.eq(picklistAliasType.getPrimaryKey()),
                            PicklistAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PicklistDetails.PICKLIST_NAME),
                    PicklistAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PicklistAliases.fields())
                    .from(PicklistAliases)
                    .where(PicklistAliases.PICKLIST_ALIAS_TYPE.eq(picklistAliasType.getPrimaryKey()),
                            PicklistAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return picklistAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PicklistAlias> getPicklistAliasesByPicklistAliasType(PicklistAliasType picklistAliasType) {
        return getPicklistAliasesByPicklistAliasType(picklistAliasType, EntityPermission.READ_ONLY);
    }

    public List<PicklistAlias> getPicklistAliasesByPicklistAliasTypeForUpdate(PicklistAliasType picklistAliasType) {
        return getPicklistAliasesByPicklistAliasType(picklistAliasType, EntityPermission.READ_WRITE);
    }

    public PicklistAliasTransfer getPicklistAliasTransfer(UserVisit userVisit, PicklistAlias picklistAlias) {
        return picklistAliasTransferCache.getPicklistAliasTransfer(userVisit, picklistAlias);
    }

    public List<PicklistAliasTransfer> getPicklistAliasTransfersByPicklist(UserVisit userVisit, Picklist picklist) {
        var picklistaliases = getPicklistAliasesByPicklist(picklist);
        List<PicklistAliasTransfer> picklistAliasTransfers = new ArrayList<>(picklistaliases.size());

        picklistaliases.forEach((picklistAlias) ->
                picklistAliasTransfers.add(picklistAliasTransferCache.getPicklistAliasTransfer(userVisit, picklistAlias))
        );

        return picklistAliasTransfers;
    }

    public void updatePicklistAliasFromValue(PicklistAliasValue picklistAliasValue, BasePK updatedBy) {
        if(picklistAliasValue.hasBeenModified()) {
            var picklistAlias = picklistAliasFactory.getEntityFromPK(EntityPermission.READ_WRITE, picklistAliasValue.getPrimaryKey());

            picklistAlias.setThruTime(session.getStartTime());
            picklistAlias.store();

            var picklistPK = picklistAlias.getPicklistPK();
            var picklistAliasTypePK = picklistAlias.getPicklistAliasTypePK();
            var alias  = picklistAliasValue.getAlias();

            picklistAlias = picklistAliasFactory.create(picklistPK, picklistAliasTypePK, alias, session.getStartTime(), Session.MAX_TIME);

            sendEvent(picklistPK, EventTypes.MODIFY, picklistAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePicklistAlias(PicklistAlias picklistAlias, BasePK deletedBy) {
        picklistAlias.setThruTime(session.getStartTime());

        sendEvent(picklistAlias.getPicklistPK(), EventTypes.MODIFY, picklistAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deletePicklistAliasesByPicklistAliasType(PicklistAliasType picklistAliasType, BasePK deletedBy) {
        var picklistaliases = getPicklistAliasesByPicklistAliasTypeForUpdate(picklistAliasType);

        picklistaliases.forEach((picklistAlias) -> 
                deletePicklistAlias(picklistAlias, deletedBy)
        );
    }

    public void deletePicklistAliasesByPicklist(Picklist picklist, BasePK deletedBy) {
        var picklistaliases = getPicklistAliasesByPicklistForUpdate(picklist);

        picklistaliases.forEach((picklistAlias) -> 
                deletePicklistAlias(picklistAlias, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Picklists
    // --------------------------------------------------------------------------------

    public Picklist getPicklistByName(PicklistType picklistType, String picklistName, EntityPermission entityPermission) {
        // TODO
        return null;
    }

    public Picklist getPicklistByName(PicklistType picklistType, String picklistName) {
        // TODO
        return null;
    }

    public PicklistTransfer getPicklistTransfer(UserVisit userVisit, Picklist picklist) {
        // TODO
        return null;
    }

}

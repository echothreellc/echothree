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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.EventGroupStatusChoicesBean;
import com.echothree.model.control.core.common.choice.EventTypeChoicesBean;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.EntityVisitTransfer;
import com.echothree.model.control.core.common.transfer.EventGroupTransfer;
import com.echothree.model.control.core.common.transfer.EventTransfer;
import com.echothree.model.control.core.common.transfer.EventTypeTransfer;
import static com.echothree.model.control.core.common.workflow.EventGroupStatusConstants.WorkflowStep_EVENT_GROUP_STATUS_ACTIVE;
import static com.echothree.model.control.core.common.workflow.EventGroupStatusConstants.Workflow_EVENT_GROUP_STATUS;
import com.echothree.model.control.core.server.CoreDebugFlags;
import com.echothree.model.control.core.server.eventbus.SentEvent;
import com.echothree.model.control.core.server.eventbus.SentEventEventBus;
import com.echothree.model.control.core.server.kafka.EventTopic;
import com.echothree.model.control.core.server.transfer.EntityTimeTransferCache;
import com.echothree.model.control.core.server.transfer.EntityVisitTransferCache;
import com.echothree.model.control.core.server.transfer.EventGroupTransferCache;
import com.echothree.model.control.core.server.transfer.EventTransferCache;
import com.echothree.model.control.core.server.transfer.EventTypeTransferCache;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.common.exception.UnknownQueueTypeNameException;
import com.echothree.model.control.queue.server.logic.QueuedEntityLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.common.pk.EventGroupPK;
import com.echothree.model.data.core.common.pk.EventTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityVisit;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.core.server.entity.EventGroup;
import com.echothree.model.data.core.server.entity.EventSubscriber;
import com.echothree.model.data.core.server.entity.EventSubscriberEntityInstance;
import com.echothree.model.data.core.server.entity.EventSubscriberEntityType;
import com.echothree.model.data.core.server.entity.EventSubscriberEventType;
import com.echothree.model.data.core.server.entity.EventType;
import com.echothree.model.data.core.server.entity.EventTypeDescription;
import com.echothree.model.data.core.server.entity.QueuedEvent;
import com.echothree.model.data.core.server.entity.QueuedSubscriberEvent;
import com.echothree.model.data.core.server.factory.EntityTimeFactory;
import com.echothree.model.data.core.server.factory.EntityVisitFactory;
import com.echothree.model.data.core.server.factory.EventFactory;
import com.echothree.model.data.core.server.factory.EventGroupDetailFactory;
import com.echothree.model.data.core.server.factory.EventGroupFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberDetailFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberEntityInstanceFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberEntityTypeFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberEventTypeFactory;
import com.echothree.model.data.core.server.factory.EventSubscriberFactory;
import com.echothree.model.data.core.server.factory.EventTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EventTypeFactory;
import com.echothree.model.data.core.server.factory.QueuedEventFactory;
import com.echothree.model.data.core.server.factory.QueuedSubscriberEventFactory;
import com.echothree.model.data.core.server.value.EventGroupDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TIMES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.EVENT_GROUPS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.EVENT_SUBSCRIBERS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.EVENT_SUBSCRIBERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.EVENT_SUBSCRIBER_ENTITY_INSTANCES_EVENT_SUBSCRIBER_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.EVENT_SUBSCRIBER_ENTITY_TYPES_EVENT_SUBSCRIBER_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.EVENT_SUBSCRIBER_EVENT_TYPES_EVENT_SUBSCRIBER_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.QUEUED_SUBSCRIBER_EVENTS_EVENT_FK;
import static com.echothree.model.jooq.server.keys.workflow.WorkflowForeignKeys.WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTimes.EntityTimes;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.core.EntityVisits.EntityVisits;
import static com.echothree.model.jooq.server.tables.core.EventGroupDetails.EventGroupDetails;
import static com.echothree.model.jooq.server.tables.core.EventGroups.EventGroups;
import static com.echothree.model.jooq.server.tables.core.EventSubscriberDetails.EventSubscriberDetails;
import static com.echothree.model.jooq.server.tables.core.EventSubscriberEntityInstances.EventSubscriberEntityInstances;
import static com.echothree.model.jooq.server.tables.core.EventSubscriberEntityTypes.EventSubscriberEntityTypes;
import static com.echothree.model.jooq.server.tables.core.EventSubscriberEventTypes.EventSubscriberEventTypes;
import static com.echothree.model.jooq.server.tables.core.EventSubscribers.EventSubscribers;
import static com.echothree.model.jooq.server.tables.core.EventTypeDescriptions.EventTypeDescriptions;
import static com.echothree.model.jooq.server.tables.core.EventTypes.EventTypes;
import static com.echothree.model.jooq.server.tables.core.Events.Events;
import static com.echothree.model.jooq.server.tables.core.QueuedEvents.QueuedEvents;
import static com.echothree.model.jooq.server.tables.core.QueuedSubscriberEvents.QueuedSubscriberEvents;
import static com.echothree.model.jooq.server.tables.workflow.WorkflowEntityStatuses.WorkflowEntityStatuses;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.EntityInstanceUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

@CommandScope
public class EventControl
        extends BaseModelControl {

    @Inject
    protected CacheEntryControl cacheEntryControl;

    @Inject
    protected IndexControl indexControl;

    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected QueuedEntityLogic queuedEntityLogic;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of EventControl */
    protected EventControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity Times
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityTimeFactory entityTimeFactory;

    @Inject
    EntityTimeTransferCache entityTimeTransferCache;

    public EntityTime createEntityTime(EntityInstance entityInstance, Long createdTime, Long modifiedTime, Long deletedTime) {
        return entityTimeFactory.create(entityInstance, createdTime, modifiedTime, deletedTime);

    }

    public EntityTime getEntityTime(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityTimes.fields())
                .from(EntityTimes)
                .where(EntityTimes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()));

        return entityTimeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    public EntityTime getEntityTimeForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityTimes.fields())
                .from(EntityTimes)
                .where(EntityTimes.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .forUpdate();

        return entityTimeFactory.getEntityFromQuery(EntityPermission.READ_WRITE, query);
    }

    public static final Integer negativeOne = -1;

    public List<EntityTime> getEntityTimesByEntityType(EntityType entityType) {
        return getEntityTimesByEntityTypeWithLimit(entityType, negativeOne);
    }

    public List<EntityTime> getEntityTimesByEntityTypeWithLimit(EntityType entityType, Integer limit) {
        var baseQuery = session.getDslContext()
                .select(EntityTimes.fields())
                .from(EntityTimes)
                .join(EntityInstances).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                .where(EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                .orderBy(EntityTimes.CREATED_TIME);

        var query = limit == -1 ? baseQuery : baseQuery.limit(limit);

        return entityTimeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY,
                session.applyLimit(query, EntityTimeFactory.class));
    }

    public List<EntityTime> getEntityTimesByEntityTypeCreatedAfter(EntityType entityType, Long createdTime) {
        return getEntityTimesByEntityTypeCreatedAfterWithLimit(entityType, createdTime, negativeOne);
    }

    public List<EntityTime> getEntityTimesByEntityTypeCreatedAfterWithLimit(EntityType entityType, Long createdTime, Integer limit) {
        var baseQuery = session.getDslContext()
                .select(EntityTimes.fields())
                .from(EntityTimes)
                .join(EntityInstances).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                .where(EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityTimes.CREATED_TIME.gt(createdTime))
                .orderBy(EntityTimes.CREATED_TIME);

        var query = limit == -1 ? baseQuery : baseQuery.limit(limit);

        return entityTimeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY,
                session.applyLimit(query, EntityTimeFactory.class));
    }

    public List<EntityTime> getEntityTimesByEntityTypeModifiedAfter(EntityType entityType, Long modifiedTime) {
        return getEntityTimesByEntityTypeModifiedAfterWithLimit(entityType, modifiedTime, negativeOne);
    }

    public List<EntityTime> getEntityTimesByEntityTypeModifiedAfterWithLimit(EntityType entityType, Long modifiedTime, Integer limit) {
        var baseQuery = session.getDslContext()
                .select(EntityTimes.fields())
                .from(EntityTimes)
                .join(EntityInstances).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                .where(EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityTimes.MODIFIED_TIME.gt(modifiedTime))
                .orderBy(EntityTimes.MODIFIED_TIME);

        var query = limit == -1 ? baseQuery : baseQuery.limit(limit);

        return entityTimeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY,
                session.applyLimit(query, EntityTimeFactory.class));
    }

    public List<EntityTime> getEntityTimesByEntityTypeDeletedAfter(EntityType entityType, Long deletedTime) {
        return getEntityTimesByEntityTypeDeletedAfterWithLimit(entityType, deletedTime, negativeOne);
    }

    public List<EntityTime> getEntityTimesByEntityTypeDeletedAfterWithLimit(EntityType entityType, Long deletedTime, Integer limit) {
        var baseQuery = session.getDslContext()
                .select(EntityTimes.fields())
                .from(EntityTimes)
                .join(EntityInstances).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                .where(EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityTimes.DELETED_TIME.gt(deletedTime))
                .orderBy(EntityTimes.DELETED_TIME);

        var query = limit == -1 ? baseQuery : baseQuery.limit(limit);

        return entityTimeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY,
                session.applyLimit(query, EntityTimeFactory.class));
    }

    public EntityTimeTransfer getEntityTimeTransfer(UserVisit userVisit, EntityTime entityTime) {
        return entityTimeTransferCache.getEntityTimeTransfer(userVisit, entityTime);
    }

    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected EventGroupDetailFactory eventGroupDetailFactory;

    @Inject
    protected EventGroupFactory eventGroupFactory;

    @Inject
    EventGroupTransferCache eventGroupTransferCache;

    boolean alreadyCreatingEventGroup = false;

    public EventGroup getActiveEventGroup(BasePK createdBy) {
        EventGroup eventGroup = null;

        if(!alreadyCreatingEventGroup) {
            var workflowStep = workflowControl.getWorkflowStepUsingNames(Workflow_EVENT_GROUP_STATUS,
                    WorkflowStep_EVENT_GROUP_STATUS_ACTIVE);

            if(workflowStep != null) {
                var query = session.getDslContext()
                        .select(EventGroups.fields())
                        .from(EventGroups)
                        .join(EntityInstances).on(EventGroups.EVENT_GROUP.cast(Long.class).eq(EntityInstances.ENTITY_UNIQUE))
                        .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                        .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                        .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                        .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                        .join(EventGroupDetails).onKey(EVENT_GROUPS_ACTIVE_DETAIL_FK)
                        .join(WorkflowEntityStatuses).onKey(WORKFLOW_ENTITY_STATUSES_ENTITY_INSTANCE_FK)
                        .join(EntityTimes).onKey(ENTITY_TIMES_ENTITY_INSTANCE_FK)
                        .where(ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(com.echothree.model.control.core.common.ComponentVendors.ECHO_THREE.name()),
                                EntityTypeDetails.ENTITY_TYPE_NAME.eq(com.echothree.model.control.core.common.EntityTypes.EventGroup.name()),
                                WorkflowEntityStatuses.WORKFLOW_STEP.eq(workflowStep.getPrimaryKey()),
                                WorkflowEntityStatuses.THRU_TIME.eq(Session.MAX_TIME))
                        .orderBy(EntityTimes.CREATED_TIME.desc());

                var eventGroups = eventGroupFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);

                if(eventGroups != null && !eventGroups.isEmpty()) {
                    eventGroup = eventGroups.iterator().next();
                } else {
                    alreadyCreatingEventGroup = true;
                    eventGroup = createEventGroup(createdBy);
                    alreadyCreatingEventGroup = false;
                }
            }
        }

        return eventGroup;
    }

    public EventGroup createEventGroup(BasePK createdBy) {
        EventGroup eventGroup = null;
        var workflow = workflowControl.getWorkflowByName(Workflow_EVENT_GROUP_STATUS);

        if(workflow != null) {
            var workflowEntrance = workflowControl.getDefaultWorkflowEntrance(workflow);

            if(workflowEntrance != null && (workflowControl.countWorkflowEntranceStepsByWorkflowEntrance(workflowEntrance) > 0)) {
                var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.EVENT_GROUP.name());
                var eventGroupName = sequenceGeneratorLogic.getNextSequenceValue(sequence);

                eventGroup = createEventGroup(eventGroupName, createdBy);

                var entityInstance = getEntityInstanceByBaseEntity(eventGroup);
                workflowControl.addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
            }
        }

        return eventGroup;
    }
    
    public EventGroup createEventGroup(String eventGroupName, BasePK createdBy) {
        var eventGroup = eventGroupFactory.create();
        var eventGroupDetail = eventGroupDetailFactory.create(eventGroup, eventGroupName,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        eventGroup = eventGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                eventGroup.getPrimaryKey());
        eventGroup.setActiveDetail(eventGroupDetail);
        eventGroup.setLastDetail(eventGroupDetail);
        eventGroup.store();

        sendEvent(eventGroup.getPrimaryKey(), com.echothree.model.control.core.common.EventTypes.CREATE, null, null, createdBy);

        return eventGroup;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EventGroup */
    public EventGroup getEventGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EventGroupPK(entityInstance.getEntityUniqueId());

        return eventGroupFactory.getEntityFromPK(entityPermission, pk);
    }

    public EventGroup getEventGroupByEntityInstance(EntityInstance entityInstance) {
        return getEventGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EventGroup getEventGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEventGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEventGroups() {
        return session.getDslContext()
                .selectCount()
                .from(EventGroups)
                .join(EventGroupDetails).onKey(EVENT_GROUPS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public EventGroupDetailValue getEventGroupDetailValueForUpdate(EventGroup eventGroup) {
        return eventGroup.getLastDetailForUpdate().getEventGroupDetailValue().clone();
    }

    private EventGroup getEventGroupByName(String eventGroupName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EventGroups.fields())
                .from(EventGroups)
                .join(EventGroupDetails).onKey(EVENT_GROUPS_ACTIVE_DETAIL_FK)
                .where(EventGroupDetails.EVENT_GROUP_NAME.eq(eventGroupName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return eventGroupFactory.getEntityFromQuery(entityPermission, query);
    }

    public EventGroup getEventGroupByName(String eventGroupName) {
        return getEventGroupByName(eventGroupName, EntityPermission.READ_ONLY);
    }

    public EventGroup getEventGroupByNameForUpdate(String eventGroupName) {
        return getEventGroupByName(eventGroupName, EntityPermission.READ_WRITE);
    }

    public EventGroupDetailValue getEventGroupDetailValueByNameForUpdate(String eventGroupName) {
        return getEventGroupDetailValueForUpdate(getEventGroupByNameForUpdate(eventGroupName));
    }

    private List<EventGroup> getEventGroups(EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EventGroups.fields())
                    .from(EventGroups)
                    .join(EventGroupDetails).onKey(EVENT_GROUPS_ACTIVE_DETAIL_FK)
                    .orderBy(EventGroupDetails.EVENT_GROUP_NAME),
                    EventGroupFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EventGroups.fields())
                    .from(EventGroups)
                    .where(EventGroups.ACTIVE_DETAIL.isNotNull())
                    .forUpdate();
        };

        return eventGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EventGroup> getEventGroups() {
        return getEventGroups(EntityPermission.READ_ONLY);
    }

    public List<EventGroup> getEventGroupsForUpdate() {
        return getEventGroups(EntityPermission.READ_WRITE);
    }

    public EventGroupTransfer getEventGroupTransfer(UserVisit userVisit, EventGroup eventGroup) {
        return eventGroupTransferCache.getEventGroupTransfer(userVisit, eventGroup);
    }

    public List<EventGroupTransfer> getEventGroupTransfers(UserVisit userVisit, Collection<EventGroup> eventGroups) {
        List<EventGroupTransfer> eventGroupTransfers = new ArrayList<>(eventGroups.size());

        eventGroups.forEach((eventGroup) ->
                eventGroupTransfers.add(eventGroupTransferCache.getEventGroupTransfer(userVisit, eventGroup))
        );

        return eventGroupTransfers;
    }

    public List<EventGroupTransfer> getEventGroupTransfers(UserVisit userVisit) {
        return getEventGroupTransfers(userVisit, getEventGroups());
    }

    public EventGroupStatusChoicesBean getEventGroupStatusChoices(String defaultEventGroupStatusChoice, Language language, boolean allowNullChoice,
            EventGroup eventGroup, PartyPK partyPK) {
        var eventGroupStatusChoicesBean = new EventGroupStatusChoicesBean();

        if(eventGroup == null) {
            workflowControl.getWorkflowEntranceChoices(eventGroupStatusChoicesBean, defaultEventGroupStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(Workflow_EVENT_GROUP_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(eventGroup.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(Workflow_EVENT_GROUP_STATUS,
                    entityInstance);

            workflowControl.getWorkflowDestinationChoices(eventGroupStatusChoicesBean, defaultEventGroupStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }

        return eventGroupStatusChoicesBean;
    }

    public void setEventGroupStatus(ExecutionErrorAccumulator eea, EventGroup eventGroup, String eventGroupStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(eventGroup);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(Workflow_EVENT_GROUP_STATUS,
                entityInstance);
        var workflowDestination = eventGroupStatusChoice == null? null:
                workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), eventGroupStatusChoice);

        if(workflowDestination != null || eventGroupStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownEventGroupStatusChoice.name(), eventGroupStatusChoice);
        }
    }

    // --------------------------------------------------------------------------------
    //   Event Types
    // --------------------------------------------------------------------------------

    @Inject
    protected EventTypeFactory eventTypeFactory;

    @Inject
    EventTypeTransferCache eventTypeTransferCache;

    public EventType createEventType(String eventTypeName) {
        return eventTypeFactory.create(eventTypeName);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EventType */
    public EventType getEventTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EventTypePK(entityInstance.getEntityUniqueId());

        return eventTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public EventType getEventTypeByEntityInstance(EntityInstance entityInstance) {
        return getEventTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EventType getEventTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEventTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEventTypes() {
        return session.getDslContext()
                .selectCount()
                .from(EventTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public EventType getEventTypeByName(String eventTypeName) {
        var query = session.getDslContext()
                .select(EventTypes.fields())
                .from(EventTypes)
                .where(EventTypes.EVENT_TYPE_NAME.eq(eventTypeName));

        return eventTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    private final Map<EventTypes, EventType> eventTypeCache = new HashMap<>();

    public EventType getEventTypeByEventTypesFromCache(EventTypes eventTypeEnum) {
        var eventType = eventTypeCache.get(eventTypeEnum);

        if(eventType == null) {
            eventType = getEventTypeByName(eventTypeEnum.name());

            if(eventType != null) {
                eventTypeCache.put(eventTypeEnum, eventType);
            }
        }

        return eventType;
    }

    public List<EventType> getEventTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(EventTypes.fields())
                .from(EventTypes)
                .orderBy(EventTypes.EVENT_TYPE_NAME),
                EventTypeFactory.class);

        return eventTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public EventTypeChoicesBean getEventTypeChoices(String defaultEventTypeChoice, Language language,
            boolean allowNullChoice) {
        var eventTypes = getEventTypes();
        var size = eventTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultEventTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var eventType : eventTypes) {
            var label = getBestEventTypeDescription(eventType, language);
            var value = eventType.getEventTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultEventTypeChoice != null && defaultEventTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null)) {
                defaultValue = value;
            }
        }

        return new EventTypeChoicesBean(labels, values, defaultValue);
    }
    
    public EventTypeTransfer getEventTypeTransfer(UserVisit userVisit, EventType eventType) {
        return eventTypeTransferCache.getEventTypeTransfer(userVisit, eventType);
    }

    public List<EventTypeTransfer> getEventTypeTransfers(UserVisit userVisit, Collection<EventType> eventTypes) {
        List<EventTypeTransfer> eventTypeTransfers = new ArrayList<>(eventTypes.size());

        eventTypes.forEach((eventType) ->
                eventTypeTransfers.add(eventTypeTransferCache.getEventTypeTransfer(userVisit, eventType))
        );

        return eventTypeTransfers;
    }

    public List<EventTypeTransfer> getEventTypeTransfers(UserVisit userVisit) {
        return getEventTypeTransfers(userVisit, getEventTypes());
    }

    // --------------------------------------------------------------------------------
    //   Event Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected EventTypeDescriptionFactory eventTypeDescriptionFactory;

    public EventTypeDescription createEventTypeDescription(EventType eventType, Language language, String description) {
        var eventTypeDescription = eventTypeDescriptionFactory.create(eventType, language, description);

        return eventTypeDescription;
    }

    public EventTypeDescription getEventTypeDescription(EventType eventType, Language language) {
        var query = session.getDslContext()
                .select(EventTypeDescriptions.fields())
                .from(EventTypeDescriptions)
                .where(EventTypeDescriptions.EVENT_TYPE.eq(eventType.getPrimaryKey()),
                        EventTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return eventTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    public String getBestEventTypeDescription(EventType eventType, Language language) {
        String description;
        var eventTypeDescription = getEventTypeDescription(eventType, language);

        if(eventTypeDescription == null && !language.getIsDefault()) {
            eventTypeDescription = getEventTypeDescription(eventType, partyControl.getDefaultLanguage());
        }

        if(eventTypeDescription == null) {
            description = eventType.getEventTypeName();
        } else {
            description = eventTypeDescription.getDescription();
        }

        return description;
    }

    // --------------------------------------------------------------------------------
    //   Events
    // --------------------------------------------------------------------------------

    @Inject
    protected EventFactory eventFactory;

    @Inject
    EventTransferCache eventTransferCache;

    public Event createEvent(EventGroup eventGroup, Long eventTime, EntityInstance entityInstance, EventType eventType,
            EntityInstance relatedEntityInstance, EventType relatedEventType, EntityInstance createdBy) {
        var eventTimeSequence = session.getNextEventTimeSequence(entityInstance.getPrimaryKey());

        return eventFactory.create(eventGroup, eventTime, eventTimeSequence, entityInstance, eventType, relatedEntityInstance, relatedEventType,
                createdBy);
    }

    public long countEventsByEventGroup(EventGroup eventGroup) {
        return session.getDslContext()
                .selectCount()
                .from(Events)
                .where(Events.EVENT_GROUP.eq(eventGroup.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEventsByEntityInstance(EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(Events)
                .where(Events.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEventsByEventType(EventType eventType) {
        return session.getDslContext()
                .selectCount()
                .from(Events)
                .where(Events.EVENT_TYPE.eq(eventType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEventsByCreatedBy(EntityInstance createdBy) {
        return session.getDslContext()
                .selectCount()
                .from(Events)
                .where(Events.CREATED_BY.eq(createdBy.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<Event> getEventsByEventGroup(EventGroup eventGroup) {
        var query = session.applyLimit(session.getDslContext()
                .select(Events.fields())
                .from(Events)
                .where(Events.EVENT_GROUP.eq(eventGroup.getPrimaryKey()))
                .orderBy(Events.EVENT_TIME, Events.EVENT_TIME_SEQUENCE),
                EventFactory.class);

        return eventFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public List<Event> getEventsByEntityInstance(EntityInstance entityInstance) {
        var query = session.applyLimit(session.getDslContext()
                .select(Events.fields())
                .from(Events)
                .where(Events.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .orderBy(Events.EVENT_TIME, Events.EVENT_TIME_SEQUENCE),
                EventFactory.class);

        return eventFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public List<Event> getEventsByEventType(EventType eventType) {
        var query = session.applyLimit(session.getDslContext()
                .select(Events.fields())
                .from(Events)
                .where(Events.EVENT_TYPE.eq(eventType.getPrimaryKey()))
                .orderBy(Events.EVENT_TIME, Events.EVENT_TIME_SEQUENCE),
                EventFactory.class);

        return eventFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public List<Event> getEventsByCreatedBy(EntityInstance createdBy) {
        var query = session.applyLimit(session.getDslContext()
                .select(Events.fields())
                .from(Events)
                .where(Events.CREATED_BY.eq(createdBy.getPrimaryKey()))
                .orderBy(Events.EVENT_TIME, Events.EVENT_TIME_SEQUENCE),
                EventFactory.class);

        return eventFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public List<Event> getEventsByEntityInstanceAndEventTypeForUpdate(EntityInstance entityInstance, EventType eventType) {
        // History trimming removes the oldest events from the beginning of this list.
        var query = session.getDslContext()
                .select(Events.fields())
                .from(Events)
                .where(Events.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        Events.EVENT_TYPE.eq(eventType.getPrimaryKey()))
                .orderBy(Events.EVENT_TIME)
                .forUpdate();

        return eventFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }

    public List<EventTransfer> getEventTransfers(UserVisit userVisit, Collection<Event> events) {
        List<EventTransfer> eventTransfers = new ArrayList<>(events.size());

        events.forEach((event) ->
                eventTransfers.add(eventTransferCache.getEventTransfer(userVisit, event))
        );

        return eventTransfers;
    }

    public List<EventTransfer> getEventTransfersByEntityInstance(UserVisit userVisit, EventGroup eventGroup) {
        return getEventTransfers(userVisit, getEventsByEventGroup(eventGroup));
    }

    public List<EventTransfer> getEventTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
        return getEventTransfers(userVisit, getEventsByEntityInstance(entityInstance));
    }

    public List<EventTransfer> getEventTransfersByEntityInstance(UserVisit userVisit, EventType eventType) {
        return getEventTransfers(userVisit, getEventsByEventType(eventType));
    }

    public List<EventTransfer> getEventTransfersByCreatedBy(UserVisit userVisit, EntityInstance createdBy) {
        return getEventTransfers(userVisit, getEventsByCreatedBy(createdBy));
    }

    public void removeEvent(Event event) {
        event.remove();
    }

    // --------------------------------------------------------------------------------
    //   Queued Events
    // --------------------------------------------------------------------------------

    @Inject
    protected QueuedEventFactory queuedEventFactory;

    public QueuedEvent createQueuedEvent(Event event) {
        return queuedEventFactory.create(event);
    }

    public List<QueuedEvent> getQueuedEventsForUpdate() {
        var query = session.getDslContext()
                .select(QueuedEvents.fields())
                .from(QueuedEvents)
                .forUpdate();

        return queuedEventFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }

    public void removeQueuedEvent(QueuedEvent queuedEvent) {
        queuedEvent.remove();
    }

    // --------------------------------------------------------------------------------
    //   Entity Visits
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityVisitFactory entityVisitFactory;

    @Inject
    EntityVisitTransferCache entityVisitTransferCache;

    public EntityVisit createEntityVisit(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance) {
        return entityVisitFactory.create(entityInstance, visitedEntityInstance, session.getStartTime());
    }

    private EntityVisit getEntityVisit(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityVisits.fields())
                .from(EntityVisits)
                .where(EntityVisits.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityVisits.VISITED_ENTITY_INSTANCE.eq(visitedEntityInstance.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityVisitFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityVisit getEntityVisit(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance) {
        return getEntityVisit(entityInstance, visitedEntityInstance, EntityPermission.READ_ONLY);
    }

    public EntityVisit getEntityVisitForUpdate(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance) {
        return getEntityVisit(entityInstance, visitedEntityInstance, EntityPermission.READ_WRITE);
    }

    public EntityVisitTransfer getEntityVisitTransfer(UserVisit userVisit, EntityVisit entityVisit) {
        return entityVisitTransferCache.getEntityVisitTransfer(userVisit, entityVisit);
    }

    // --------------------------------------------------------------------------------
    //   Event Subscribers
    // --------------------------------------------------------------------------------

    @Inject
    protected EventSubscriberDetailFactory eventSubscriberDetailFactory;

    @Inject
    protected EventSubscriberFactory eventSubscriberFactory;

    public EventSubscriber createEventSubscriber(EntityInstance entityInstance, String description, Integer sortOrder,
            BasePK createdBy) {
        var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.EVENT_SUBSCRIBER.name());
        var sequence = sequenceControl.getDefaultSequence(sequenceType);
        var eventSubscriberName = sequenceGeneratorLogic.getNextSequenceValue(sequence);

        return createEventSubscriber(eventSubscriberName, entityInstance, description, sortOrder, createdBy);
    }

    public EventSubscriber createEventSubscriber(String eventSubscriberName, EntityInstance entityInstance, String description,
            Integer sortOrder, BasePK createdBy) {
        var eventSubscriber = eventSubscriberFactory.create();
        var eventSubscriberDetail = eventSubscriberDetailFactory.create(
                eventSubscriber, eventSubscriberName, entityInstance, description, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        eventSubscriber = eventSubscriberFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                eventSubscriber.getPrimaryKey());
        eventSubscriber.setActiveDetail(eventSubscriberDetail);
        eventSubscriber.setLastDetail(eventSubscriberDetail);
        eventSubscriber.store();

        sendEvent(eventSubscriber.getPrimaryKey(), com.echothree.model.control.core.common.EventTypes.CREATE, null, null, createdBy);

        return eventSubscriber;
    }

    private EventSubscriber getEventSubscriberByName(String eventSubscriberName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EventSubscribers.fields())
                .from(EventSubscribers)
                .join(EventSubscriberDetails).onKey(EVENT_SUBSCRIBERS_ACTIVE_DETAIL_FK)
                .where(EventSubscriberDetails.EVENT_SUBSCRIBER_NAME.eq(eventSubscriberName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return eventSubscriberFactory.getEntityFromQuery(entityPermission, query);
    }

    public EventSubscriber getEventSubscriberByName(String eventSubscriberName) {
        return getEventSubscriberByName(eventSubscriberName, EntityPermission.READ_ONLY);
    }

    public EventSubscriber getEventSubscriberByNameForUpdate(String eventSubscriberName) {
        return getEventSubscriberByName(eventSubscriberName, EntityPermission.READ_WRITE);
    }

    private List<EventSubscriber> getEventSubscribersByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EventSubscribers.fields())
                    .from(EventSubscribers)
                    .join(EventSubscriberDetails).onKey(EVENT_SUBSCRIBERS_ACTIVE_DETAIL_FK)
                    .where(EventSubscriberDetails.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                    .orderBy(EventSubscriberDetails.SORT_ORDER, EventSubscriberDetails.EVENT_SUBSCRIBER_NAME),
                    EventSubscriberFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EventSubscribers.fields())
                    .from(EventSubscribers)
                    .join(EventSubscriberDetails).onKey(EVENT_SUBSCRIBERS_ACTIVE_DETAIL_FK)
                    .where(EventSubscriberDetails.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                    .forUpdate();
        };

        return eventSubscriberFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EventSubscriber> getEventSubscribersByEntityInstance(EntityInstance entityInstance) {
        return getEventSubscribersByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public List<EventSubscriber> getEventSubscribersByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEventSubscribersByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

//    public EventSubscriberTransfer getEventSubscriberTransfer(UserVisit userVisit, EventSubscriber eventSubscriber) {
//        return eventSubscriberTransferCache.getEventSubscriberTransfer(userVisit, eventSubscriber);
//    }
//
//    public List<EventSubscriberTransfer> getEventSubscriberTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
//        List<EventSubscriber> eventSubscribers = getEventSubscribersByEntityInstance(entityInstance);
//        List<EventSubscriberTransfer> eventSubscriberTransfers = new ArrayList<EventSubscriberTransfer>(eventSubscribers.size());
//        EventSubscriberTransferCache eventSubscriberTransferCache = getPaymentTransferCaches(userVisit).getEventSubscriberTransferCache();
//
//        for(var eventSubscriber : eventSubscribers) {
//            eventSubscriberTransfers.add(eventSubscriberTransferCache.getEventSubscriberTransfer(userVisit, eventSubscriber));
//        }
//
//        return eventSubscriberTransfers;
//    }

    public void deleteEventSubscriber(EventSubscriber eventSubscriber, BasePK deletedBy) {
        removeQueuedSubscriberEventsByEventSubscriber(eventSubscriber);

        var eventSubscriberDetail = eventSubscriber.getLastDetailForUpdate();
        eventSubscriberDetail.setThruTime(session.getStartTime());
        eventSubscriber.setActiveDetail(null);
        eventSubscriber.store();

        sendEvent(eventSubscriber.getPrimaryKey(), com.echothree.model.control.core.common.EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteEventSubscribers(List<EventSubscriber> eventSubscribers, BasePK deletedBy) {
        eventSubscribers.forEach((eventSubscriber) ->
                deleteEventSubscriber(eventSubscriber, deletedBy)
        );
    }

    public void deleteEventSubscribersByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEventSubscribers(getEventSubscribersByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Queued Events
    // --------------------------------------------------------------------------------

    @Inject
    protected QueuedSubscriberEventFactory queuedSubscriberEventFactory;

    public QueuedSubscriberEvent createQueuedSubscriberEvent(EventSubscriber eventSubscriber, Event event) {
        return queuedSubscriberEventFactory.create(eventSubscriber, event);
    }

    private List<QueuedSubscriberEvent> getQueuedSubscriberEventsByEventSubscriber(EventSubscriber eventSubscriber,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(QueuedSubscriberEvents.fields())
                    .from(QueuedSubscriberEvents)
                    .join(Events).onKey(QUEUED_SUBSCRIBER_EVENTS_EVENT_FK)
                    .where(QueuedSubscriberEvents.EVENT_SUBSCRIBER.eq(eventSubscriber.getPrimaryKey()))
                    .orderBy(Events.EVENT_TIME),
                    QueuedSubscriberEventFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(QueuedSubscriberEvents.fields())
                    .from(QueuedSubscriberEvents)
                    .where(QueuedSubscriberEvents.EVENT_SUBSCRIBER.eq(eventSubscriber.getPrimaryKey()))
                    .forUpdate();
        };

        return queuedSubscriberEventFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<QueuedSubscriberEvent> getQueuedSubscriberEventsByEventSubscriber(EventSubscriber eventSubscriber) {
        return getQueuedSubscriberEventsByEventSubscriber(eventSubscriber, EntityPermission.READ_ONLY);
    }

    public List<QueuedSubscriberEvent> getQueuedSubscriberEventsByEventSubscriberForUpdate(EventSubscriber eventSubscriber) {
        return getQueuedSubscriberEventsByEventSubscriber(eventSubscriber, EntityPermission.READ_WRITE);
    }

    public void removeQueuedSubscriberEvent(QueuedSubscriberEvent queuedSubscriberEvent) {
        queuedSubscriberEvent.remove();
    }

    public void removeQueuedSubscriberEvents(List<QueuedSubscriberEvent> queuedSubscriberEvents) {
        queuedSubscriberEvents.forEach(this::removeQueuedSubscriberEvent);
    }

    public void removeQueuedSubscriberEventsByEventSubscriber(EventSubscriber eventSubscriber) {
        removeQueuedSubscriberEvents(getQueuedSubscriberEventsByEventSubscriberForUpdate(eventSubscriber));
    }

    // --------------------------------------------------------------------------------
    //   Event Subscriber Event Types
    // --------------------------------------------------------------------------------

    @Inject
    protected EventSubscriberEventTypeFactory eventSubscriberEventTypeFactory;

    public EventSubscriberEventType createEventSubscriberEventType(EventSubscriber eventSubscriber, EventType eventType,
            BasePK createdBy) {
        var eventSubscriberEventType = eventSubscriberEventTypeFactory.create(
                eventSubscriber, eventType, session.getStartTime(), Session.MAX_TIME);

        sendEvent(eventSubscriber.getPrimaryKey(), com.echothree.model.control.core.common.EventTypes.MODIFY, eventSubscriberEventType.getPrimaryKey(), com.echothree.model.control.core.common.EventTypes.CREATE, createdBy);

        return eventSubscriberEventType;
    }

    private List<EventSubscriberEventType> getEventSubscriberEventTypes(EventType eventType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EventSubscriberEventTypes.fields())
                    .from(EventSubscriberEventTypes)
                    .join(EventSubscribers).onKey(EVENT_SUBSCRIBER_EVENT_TYPES_EVENT_SUBSCRIBER_FK)
                    .join(EventSubscriberDetails).onKey(EVENT_SUBSCRIBERS_LAST_DETAIL_FK)
                    .where(EventSubscriberEventTypes.EVENT_TYPE.eq(eventType.getPrimaryKey()),
                            EventSubscriberEventTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EventSubscriberDetails.EVENT_SUBSCRIBER_NAME),
                    EventSubscriberEventTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EventSubscriberEventTypes.fields())
                    .from(EventSubscriberEventTypes)
                    .where(EventSubscriberEventTypes.EVENT_TYPE.eq(eventType.getPrimaryKey()),
                            EventSubscriberEventTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return eventSubscriberEventTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EventSubscriberEventType> getEventSubscriberEventTypes(EventType eventType) {
        return getEventSubscriberEventTypes(eventType, EntityPermission.READ_ONLY);
    }

    public List<EventSubscriberEventType> getEventSubscriberEventTypesForUpdate(EventType eventType) {
        return getEventSubscriberEventTypes(eventType, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Event Subscriber Entity Types
    // --------------------------------------------------------------------------------

    @Inject
    protected EventSubscriberEntityTypeFactory eventSubscriberEntityTypeFactory;

    public EventSubscriberEntityType createEventSubscriberEntityType(EventSubscriber eventSubscriber, EntityType entityType,
            EventType eventType, BasePK createdBy) {
        var eventSubscriberEntityType = eventSubscriberEntityTypeFactory.create(
                eventSubscriber, entityType, eventType, session.getStartTime(), Session.MAX_TIME);

        sendEvent(eventSubscriber.getPrimaryKey(), com.echothree.model.control.core.common.EventTypes.MODIFY, eventSubscriberEntityType.getPrimaryKey(), com.echothree.model.control.core.common.EventTypes.CREATE, createdBy);

        return eventSubscriberEntityType;
    }

    private List<EventSubscriberEntityType> getEventSubscriberEntityTypes(EntityType entityType, EventType eventType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EventSubscriberEntityTypes.fields())
                    .from(EventSubscriberEntityTypes)
                    .join(EventSubscribers).onKey(EVENT_SUBSCRIBER_ENTITY_TYPES_EVENT_SUBSCRIBER_FK)
                    .join(EventSubscriberDetails).onKey(EVENT_SUBSCRIBERS_LAST_DETAIL_FK)
                    .where(EventSubscriberEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                            EventSubscriberEntityTypes.EVENT_TYPE.eq(eventType.getPrimaryKey()),
                            EventSubscriberEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EventSubscriberDetails.EVENT_SUBSCRIBER_NAME),
                    EventSubscriberEntityTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EventSubscriberEntityTypes.fields())
                    .from(EventSubscriberEntityTypes)
                    .where(EventSubscriberEntityTypes.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                            EventSubscriberEntityTypes.EVENT_TYPE.eq(eventType.getPrimaryKey()),
                            EventSubscriberEntityTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return eventSubscriberEntityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EventSubscriberEntityType> getEventSubscriberEntityTypes(EntityType entityType, EventType eventType) {
        return getEventSubscriberEntityTypes(entityType, eventType, EntityPermission.READ_ONLY);
    }

    public List<EventSubscriberEntityType> getEventSubscriberEntityTypesForUpdate(EntityType entityType, EventType eventType) {
        return getEventSubscriberEntityTypes(entityType, eventType, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Event Subscriber Entity Instances
    // --------------------------------------------------------------------------------

    @Inject
    protected EventSubscriberEntityInstanceFactory eventSubscriberEntityInstanceFactory;

    public EventSubscriberEntityInstance createEventSubscriberEntityInstance(EventSubscriber eventSubscriber,
            EntityInstance entityInstance, EventType eventType, BasePK createdBy) {
        var eventSubscriberEntityInstance = eventSubscriberEntityInstanceFactory.create(
                eventSubscriber, entityInstance, eventType, session.getStartTime(), Session.MAX_TIME);

        sendEvent(eventSubscriber.getPrimaryKey(), com.echothree.model.control.core.common.EventTypes.MODIFY, eventSubscriberEntityInstance.getPrimaryKey(), com.echothree.model.control.core.common.EventTypes.CREATE, createdBy);

        return eventSubscriberEntityInstance;
    }

    private List<EventSubscriberEntityInstance> getEventSubscriberEntityInstances(EntityInstance entityInstance,
            EventType eventType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EventSubscriberEntityInstances.fields())
                    .from(EventSubscriberEntityInstances)
                    .join(EventSubscribers).onKey(EVENT_SUBSCRIBER_ENTITY_INSTANCES_EVENT_SUBSCRIBER_FK)
                    .join(EventSubscriberDetails).onKey(EVENT_SUBSCRIBERS_LAST_DETAIL_FK)
                    .where(EventSubscriberEntityInstances.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                            EventSubscriberEntityInstances.EVENT_TYPE.eq(eventType.getPrimaryKey()),
                            EventSubscriberEntityInstances.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EventSubscriberDetails.EVENT_SUBSCRIBER_NAME),
                    EventSubscriberEntityInstanceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EventSubscriberEntityInstances.fields())
                    .from(EventSubscriberEntityInstances)
                    .where(EventSubscriberEntityInstances.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                            EventSubscriberEntityInstances.EVENT_TYPE.eq(eventType.getPrimaryKey()),
                            EventSubscriberEntityInstances.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return eventSubscriberEntityInstanceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EventSubscriberEntityInstance> getEventSubscriberEntityInstances(EntityInstance entityInstance, EventType eventType) {
        return getEventSubscriberEntityInstances(entityInstance, eventType, EntityPermission.READ_ONLY);
    }

    public List<EventSubscriberEntityInstance> getEventSubscriberEntityInstancesForUpdate(EntityInstance entityInstance, EventType eventType) {
        return getEventSubscriberEntityInstances(entityInstance, eventType, EntityPermission.READ_WRITE);
    }

    // --------------------------------------------------------------------------------
    //   Utilities
    // --------------------------------------------------------------------------------

    @Inject
    EventTopic eventTopic;

//    Caused by: java.lang.NullPointerException: Cannot invoke "java.sql.Connection.prepareStatement(String)" because "this.connection" is null
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.util.server.persistence.Session.prepareStatement(Session.java:255)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.util.server.persistence.Session.prepareStatement(Session.java:282)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.util.server.persistence.Session.queryForLong(Session.java:359)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.model.control.index.server.control.IndexControl.countIndexTypesByEntityType(IndexControl.java:143)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.model.control.index.server.control.IndexControl.isEntityTypeUsedByIndexTypes(IndexControl.java:151)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.model.control.index.server.control.IndexControl$Proxy$_$$_WeldClientProxy.isEntityTypeUsedByIndexTypes(Unknown Source)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.model.control.core.server.control.EventControl.queueEntityInstanceToIndexing(EventControl.java:1292)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.model.control.core.server.control.EventControl.sendEvent(EventControl.java:1439)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.model.control.core.server.control.EventControl.sendEvent(EventControl.java:1460)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.model.control.core.server.control.EventControl$Proxy$_$$_WeldClientProxy.sendEvent(Unknown Source)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.util.server.control.BaseModelControl.sendEvent(BaseModelControl.java:89)
//    at deployment.echothree.ear.echothree-server.jar//com.echothree.model.control.offer.server.control.OfferNameElementControl.updateOfferNameElementFromValue(OfferNameElementControl.java:211)

// https://stackoverflow.com/questions/26514456/removing-specific-cdi-managed-beans-from-session

    private void queueEntityInstanceToIndexing(final EntityInstance entityInstance) {
        if(indexControl.isEntityTypeUsedByIndexTypes(entityInstance.getEntityType())) {
            try {
                queuedEntityLogic.createQueuedEntityUsingNames(null, QueueTypes.INDEXING.name(), entityInstance);
            } catch(UnknownQueueTypeNameException uqtne) {
                // This will be thrown early in the setup process because the QueueType has not yet been created.
                // Log as an informational message, but otherwise ignore it.
                getLog().info(uqtne.getMessage());
            }
        }
    }

    private EntityTime createOrGetEntityTime(final EntityInstance entityInstance, final Long eventTime) {
        var entityTime = getEntityTimeForUpdate(entityInstance);

        // This is why we don't call setCreatedTime(...) on CREATE events - it's already taken care of here.
        if(entityTime == null) {
            // Initially created read-only, convert to read/write. If we're in sendEvent(...),
            // we need an EntityTime. If there wasn't one previously, we do the best we can,
            // using the eventTime as its createdTime.
            createEntityTime(entityInstance, eventTime, null, null);
            entityTime = getEntityTimeForUpdate(entityInstance);
        }

        return entityTime;
    }

    private void pruneEvents(final EntityInstance entityInstance, final EventType eventType, final Integer maximumHistory) {
        final var events = getEventsByEntityInstanceAndEventTypeForUpdate(entityInstance, eventType);
        final var eventCountToRemove = events.size() - maximumHistory;

        if(eventCountToRemove > 0) {
            final var i = events.iterator();

            for(var j = 0; j < eventCountToRemove; j++) {
                removeEvent(i.next());
            }
        }
    }

    @Override
    public Event sendEvent(final EntityInstance entityInstance, final EventTypes eventTypeEnum, final EntityInstance relatedEntityInstance,
            final EventTypes relatedEventTypeEnum, final BasePK createdByBasePK) {
        final var eventType = getEventTypeByEventTypesFromCache(eventTypeEnum);
        final var relatedEventType = relatedEventTypeEnum == null ? null : getEventTypeByEventTypesFromCache(relatedEventTypeEnum);
        final var createdByEntityInstance = createdByBasePK == null ? null : getEntityInstanceByBasePK(createdByBasePK);
        Event event = null;

        if(CoreDebugFlags.LogSentEvents) {
            getLog().info("entityInstance = " + EntityInstanceUtils.getEntityRefByEntityInstance(entityInstance)
                    + ", eventType = " + eventType.getEventTypeName()
                    + ", relatedEntityInstance = " + EntityInstanceUtils.getEntityRefByEntityInstance(relatedEntityInstance)
                    + ", relatedEventType = " + (relatedEventType == null ? "(null)" : relatedEventType.getEventTypeName())
                    + ", createdByEntityInstance = " + EntityInstanceUtils.getEntityRefByEntityInstance(createdByEntityInstance));
        }

        final var eventTime = session.getStartTime();
        final var entityTime = createOrGetEntityTime(entityInstance, eventTime);
        var shouldClearCache = false;
        var shouldQueueEntityInstanceToIndexing = false;
        var shouldUpdateVisitedTime = false;
        var shouldQueueEventToSubscribers = false;
        var shouldCreateEntityAttributeDefaults = false;
        Integer maximumHistory = null;

        switch(eventTypeEnum) {
            case CREATE -> {
                shouldCreateEntityAttributeDefaults = true;
                shouldQueueEntityInstanceToIndexing = true;
                shouldQueueEventToSubscribers = true;
            }
            case READ -> {
                shouldUpdateVisitedTime = true;
                shouldQueueEventToSubscribers = true;
            }
            case TOUCH -> {
                entityTime.setModifiedTime(eventTime);
                shouldClearCache = true;
                shouldQueueEntityInstanceToIndexing = true;
                maximumHistory = 5;
            }
            case MODIFY -> {
                entityTime.setModifiedTime(eventTime);
                shouldClearCache = true;
                shouldQueueEntityInstanceToIndexing = true;
                shouldQueueEventToSubscribers = true;
            }
            case DELETE -> {
                entityInstanceControl.deleteEntityInstanceDependencies(entityInstance, createdByBasePK);
                entityTime.setDeletedTime(eventTime);
                shouldClearCache = true;
                shouldQueueEntityInstanceToIndexing = true;
                shouldQueueEventToSubscribers = true;
            }
        }

        // Support for tracking the last time an EntityInstance (just as an Employee Party) has visited a given
        // EntityInstance. If the EntityInstance has not been modified since the last visit, the READ event caused
        // by the visit will be suppressed vs. being recorded.
        var shouldSuppressEvent = false;
        if(shouldUpdateVisitedTime && createdByEntityInstance != null) {
            final var entityVisit = getEntityVisitForUpdate(createdByEntityInstance, entityInstance);

            if(entityVisit == null) {
                createEntityVisit(createdByEntityInstance, entityInstance);
            } else {
                var modifiedTime = entityTime.getModifiedTime();

                // Prefer the real modified time, but if that's null (meaning it has never been modified),
                // fall back to the created time.
                if(modifiedTime == null) {
                    modifiedTime = entityTime.getCreatedTime();
                }

                // There is a possible override for suppressing events in here by EntityType. This was added to ensure
                // that full auditing is available for some EntityTypes such as PartyPaymentMethod.
                if(entityVisit.getVisitedTime() >= modifiedTime && !entityInstance.getEntityType().getLastDetail().getKeepAllHistory()) {
                    shouldSuppressEvent = true;
                }

                entityVisit.setVisitedTime(eventTime);
            }
        }

        if(!shouldSuppressEvent) {
            final var eventGroup = createdByBasePK == null ? null : getActiveEventGroup(createdByBasePK);

            event = createEvent(eventGroup, eventTime, entityInstance, eventType, relatedEntityInstance, relatedEventType,
                    createdByEntityInstance);

            if(shouldQueueEventToSubscribers) {
                createQueuedEvent(event);
            }

            if(maximumHistory != null) {
                pruneEvents(entityInstance, eventType, maximumHistory);
            }

            eventTopic.sendEvent(event);

            SentEventEventBus.eventBus.post(new SentEvent(event));
        }

        if(shouldClearCache) {
            cacheEntryControl.removeCacheEntriesByEntityInstance(entityInstance);
        }

        if(shouldQueueEntityInstanceToIndexing) {
            queueEntityInstanceToIndexing(entityInstance);
        }

        if(shouldCreateEntityAttributeDefaults) {
            entityInstanceControl.createEntityAttributeDefaults(entityInstance, createdByBasePK);
        }

        return event;
    }

    @Override
    public Event sendEvent(final BasePK basePK, final EventTypes eventType, final BasePK relatedBasePK,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        var entityInstance = getEntityInstanceByBasePK(basePK);
        Event event = null;

        if(entityInstance == null) {
            getLog().error("sendEventUsingNames: getEntityInstanceByBasePK failed on " + basePK.toString());
        } else {
            var relatedEntityInstance = relatedBasePK == null ? null : getEntityInstanceByBasePK(relatedBasePK);

            event = sendEvent(entityInstance, eventType, relatedEntityInstance, relatedEventType, createdByBasePK);
        }

        return event;
    }

    @Override
    public Event sendEvent(final EntityInstance entityInstance, final EventTypes eventType, final BasePK relatedBasePK,
            final EventTypes relatedEventType, final BasePK createdByBasePK) {
        var relatedEntityInstance = relatedBasePK == null ? null : getEntityInstanceByBasePK(relatedBasePK);

        return sendEvent(entityInstance, eventType, relatedEntityInstance, relatedEventType, createdByBasePK);
    }

}

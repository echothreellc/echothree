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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.EventGroupStatusChoicesBean;
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
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.common.exception.UnknownQueueTypeNameException;
import com.echothree.model.control.queue.server.logic.QueuedEntityLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
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
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.kafka.EventTopic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.EntityInstanceUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class EventControl
        extends BaseCoreControl {

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
    
    public EntityTime createEntityTime(EntityInstance entityInstance, Long createdTime, Long modifiedTime, Long deletedTime) {
        return entityTimeFactory.create(entityInstance, createdTime, modifiedTime, deletedTime);

    }

    public EntityTime getEntityTime(EntityInstance entityInstance) {
        EntityTime entityTime;

        try {
            var ps = entityTimeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM entitytimes " +
                            "WHERE etim_eni_entityinstanceid = ?");

            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());

            entityTime = entityTimeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTime;
    }

    public EntityTime getEntityTimeForUpdate(EntityInstance entityInstance) {
        EntityTime entityTime;

        try {
            var ps = entityTimeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM entitytimes " +
                            "WHERE etim_eni_entityinstanceid = ? " +
                            "FOR UPDATE");

            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());

            entityTime = entityTimeFactory.getEntityFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTime;
    }

    public static final Integer negativeOne = -1;

    public static final String selectEntityTimesByEntityType = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "ORDER BY etim_createdtime";

    public static final String selectEntityTimesByEntityTypeWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "ORDER BY etim_createdtime " +
            "LIMIT ?";

    public List<EntityTime> getEntityTimesByEntityType(EntityType entityType) {
        return getEntityTimesByEntityTypeWithLimit(entityType, negativeOne);
    }

    public List<EntityTime> getEntityTimesByEntityTypeWithLimit(EntityType entityType, Integer limit) {
        List<EntityTime> entityTimes;

        try {
            int intLimit = limit;
            var ps = entityTimeFactory.prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityType: selectEntityTimesByEntityTypeWithLimit);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            if(intLimit != -1) {
                ps.setInt(2, intLimit);
            }

            entityTimes = entityTimeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTimes;
    }

    public static final String selectEntityTimesByEntityTypeCreatedAfter = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_createdtime IS NOT NULL AND etim_createdtime > ? " +
            "ORDER BY etim_createdtime";
    public static final String selectEntityTimesByEntityTypeCreatedAfterWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_createdtime IS NOT NULL AND etim_createdtime > ? " +
            "ORDER BY etim_createdtime " +
            "LIMIT ?";

    public List<EntityTime> getEntityTimesByEntityTypeCreatedAfter(EntityType entityType, Long createdTime) {
        return getEntityTimesByEntityTypeCreatedAfterWithLimit(entityType, createdTime, negativeOne);
    }

    public List<EntityTime> getEntityTimesByEntityTypeCreatedAfterWithLimit(EntityType entityType, Long createdTime, Integer limit) {
        List<EntityTime> entityTimes;

        try {
            int intLimit = limit;
            var ps = entityTimeFactory.prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityTypeCreatedAfter: selectEntityTimesByEntityTypeCreatedAfterWithLimit);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, createdTime);
            if(intLimit != -1) {
                ps.setInt(3, intLimit);
            }

            entityTimes = entityTimeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTimes;
    }

    public static final String selectEntityTimesByEntityTypeModifiedAfter = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_modifiedtime IS NOT NULL AND etim_modifiedtime > ? " +
            "ORDER BY etim_modifiedtime";
    public static final String selectEntityTimesByEntityTypeModifiedAfterWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_modifiedtime IS NOT NULL AND etim_modifiedtime > ? " +
            "ORDER BY etim_modifiedtime " +
            "LIMIT ?";

    public List<EntityTime> getEntityTimesByEntityTypeModifiedAfter(EntityType entityType, Long modifiedTime) {
        return getEntityTimesByEntityTypeModifiedAfterWithLimit(entityType, modifiedTime, negativeOne);
    }

    public List<EntityTime> getEntityTimesByEntityTypeModifiedAfterWithLimit(EntityType entityType, Long modifiedTime, Integer limit) {
        List<EntityTime> entityTimes;

        try {
            int intLimit = limit;
            var ps = entityTimeFactory.prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityTypeModifiedAfter: selectEntityTimesByEntityTypeModifiedAfterWithLimit);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, modifiedTime);
            if(intLimit != -1) {
                ps.setInt(3, intLimit);
            }

            entityTimes = entityTimeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTimes;
    }

    public static final String selectEntityTimesByEntityTypeDeletedAfter = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_deletedtime IS NOT NULL AND etim_deletedtime > ? " +
            "ORDER BY etim_deletedtime";
    public static final String selectEntityTimesByEntityTypeDeletedAfterWithLimit = "SELECT _ALL_ " +
            "FROM entitytimes, entityinstances, entitytypes " +
            "WHERE ent_entitytypeid = ? AND ent_entitytypeid = eni_ent_entitytypeid AND eni_entityinstanceid = etim_eni_entityinstanceid " +
            "AND etim_deletedtime IS NOT NULL AND etim_deletedtime > ? " +
            "ORDER BY etim_deletedtime " +
            "LIMIT ?";

    public List<EntityTime> getEntityTimesByEntityTypeDeletedAfter(EntityType entityType, Long deletedTime) {
        return getEntityTimesByEntityTypeDeletedAfterWithLimit(entityType, deletedTime, negativeOne);
    }

    public List<EntityTime> getEntityTimesByEntityTypeDeletedAfterWithLimit(EntityType entityType, Long deletedTime, Integer limit) {
        List<EntityTime> entityTimes;

        try {
            int intLimit = limit;
            var ps = entityTimeFactory.prepareStatement(
                    intLimit == -1? selectEntityTimesByEntityTypeDeletedAfter: selectEntityTimesByEntityTypeDeletedAfterWithLimit);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, deletedTime);
            if(intLimit != -1) {
                ps.setInt(3, intLimit);
            }

            entityTimes = entityTimeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityTimes;
    }

    public EntityTimeTransfer getEntityTimeTransfer(UserVisit userVisit, EntityTime entityTime) {
        return getCoreTransferCaches(userVisit).getEntityTimeTransferCache().getEntityTimeTransfer(entityTime);
    }

    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected EventGroupFactory eventGroupFactory;

    @Inject
    protected EventGroupDetailFactory eventGroupDetailFactory;

    boolean alreadyCreatingEventGroup = false;

    public EventGroup getActiveEventGroup(BasePK createdBy) {
        EventGroup eventGroup = null;

        if(!alreadyCreatingEventGroup) {
            var workflowStep = workflowControl.getWorkflowStepUsingNames(Workflow_EVENT_GROUP_STATUS,
                    WorkflowStep_EVENT_GROUP_STATUS_ACTIVE);

            if(workflowStep != null) {
                List<EventGroup> eventGroups;

                try {
                    var ps = eventGroupFactory.prepareStatement(
                            "SELECT _ALL_ " +
                                    "FROM componentvendors, componentvendordetails, entitytypes, entitytypedetails, entityinstances, " +
                                    "eventgroups, eventgroupdetails, workflowentitystatuses, entitytimes " +
                                    "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid " +
                                    "AND cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                                    "AND ent_activedetailid = entdt_entitytypedetailid " +
                                    "AND cvnd_componentvendorid = entdt_cvnd_componentvendorid " +
                                    "AND entdt_entitytypename = ? " +
                                    "AND ent_entitytypeid = eni_ent_entitytypeid AND evgrp_eventgroupid = eni_entityuniqueid " +
                                    "AND eni_entityinstanceid = wkfles_eni_entityinstanceid AND wkfles_wkfls_workflowstepid = ? AND wkfles_thrutime = ? " +
                                    "AND eni_entityinstanceid = etim_eni_entityinstanceid " +
                                    "ORDER BY etim_createdtime DESC");

                    ps.setString(1, ComponentVendors.ECHO_THREE.name());
                    ps.setString(2, EntityTypes.EventGroup.name());
                    ps.setLong(3, workflowStep.getPrimaryKey().getEntityId());
                    ps.setLong(4, Session.MAX_TIME);

                    eventGroups = eventGroupFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }

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
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        eventGroup = eventGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                eventGroup.getPrimaryKey());
        eventGroup.setActiveDetail(eventGroupDetail);
        eventGroup.setLastDetail(eventGroupDetail);
        eventGroup.store();

        sendEvent(eventGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return eventGroup;
    }

    public EventGroupDetailValue getEventGroupDetailValueForUpdate(EventGroup eventGroup) {
        return eventGroup.getLastDetailForUpdate().getEventGroupDetailValue().clone();
    }

    private EventGroup getEventGroupByName(String eventGroupName, EntityPermission entityPermission) {
        EventGroup eventGroup;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventgroups, eventgroupdetails " +
                        "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid AND evgrpdt_eventgroupname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventgroups, eventgroupdetails " +
                        "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid AND evgrpdt_eventgroupname = ? " +
                        "FOR UPDATE";
            }

            var ps = eventGroupFactory.prepareStatement(query);

            ps.setString(1, eventGroupName);

            eventGroup = eventGroupFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return eventGroup;
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
        String query = null;

        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM eventgroups, eventgroupdetails " +
                    "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid " +
                    "ORDER BY evgrpdt_eventgroupname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM eventgroups, eventgroupdetails " +
                    "WHERE evgrp_activedetailid = evgrpdt_eventgroupdetailid " +
                    "FOR UPDATE";
        }

        var ps = eventGroupFactory.prepareStatement(query);

        return eventGroupFactory.getEntitiesFromQuery(entityPermission, ps);
    }

    public List<EventGroup> getEventGroups() {
        return getEventGroups(EntityPermission.READ_ONLY);
    }

    public List<EventGroup> getEventGroupsForUpdate() {
        return getEventGroups(EntityPermission.READ_WRITE);
    }

    public EventGroupTransfer getEventGroupTransfer(UserVisit userVisit, EventGroup eventGroup) {
        return getCoreTransferCaches(userVisit).getEventGroupTransferCache().getEventGroupTransfer(eventGroup);
    }

    public List<EventGroupTransfer> getEventGroupTransfers(UserVisit userVisit, Collection<EventGroup> eventGroups) {
        List<EventGroupTransfer> eventGroupTransfers = new ArrayList<>(eventGroups.size());
        var eventGroupTransferCache = getCoreTransferCaches(userVisit).getEventGroupTransferCache();

        eventGroups.forEach((eventGroup) ->
                eventGroupTransfers.add(eventGroupTransferCache.getEventGroupTransfer(eventGroup))
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
    
    public EventType createEventType(String eventTypeName) {
        var eventType = eventTypeFactory.create(eventTypeName);

        return eventType;
    }

    public EventType getEventTypeByName(String eventTypeName) {
        EventType eventType;

        try {
            var ps = eventTypeFactory.prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM eventtypes " +
                            "WHERE evty_eventtypename = ?");

            ps.setString(1, eventTypeName);

            eventType = eventTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return eventType;
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
        var ps = eventTypeFactory.prepareStatement(
                "SELECT _ALL_ " +
                        "FROM eventtypes " +
                        "ORDER BY evty_eventtypename");

        return eventTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }

    public EventTypeTransfer getEventTypeTransfer(UserVisit userVisit, EventType eventType) {
        return getCoreTransferCaches(userVisit).getEventTypeTransferCache().getEventTypeTransfer(eventType);
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
        EventTypeDescription eventTypeDescription;

        try {
            var ps = eventTypeDescriptionFactory.prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM eventtypedescriptions " +
                            "WHERE evtyd_evty_eventtypeid = ? AND evtyd_lang_languageid = ?");

            ps.setLong(1, eventType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());

            eventTypeDescription = eventTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return eventTypeDescription;
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
    
    public Event createEvent(EventGroup eventGroup, Long eventTime, EntityInstance entityInstance, EventType eventType,
            EntityInstance relatedEntityInstance, EventType relatedEventType, EntityInstance createdBy) {
        var eventTimeSequence = session.getNextEventTimeSequence(entityInstance.getPrimaryKey());

        return eventFactory.create(eventGroup, eventTime, eventTimeSequence, entityInstance, eventType, relatedEntityInstance, relatedEventType,
                createdBy);
    }

    public long countEventsByEventGroup(EventGroup eventGroup) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM events " +
                        "WHERE ev_evgrp_eventgroupid = ?",
                eventGroup);
    }

    public long countEventsByEntityInstance(EntityInstance entityInstance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM events " +
                        "WHERE ev_eni_entityinstanceid = ?",
                entityInstance);
    }

    public long countEventsByEventType(EventType eventType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM events " +
                        "WHERE ev_evty_eventtypeid = ?",
                eventType);
    }

    public long countEventsByCreatedBy(EntityInstance createdBy) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM events " +
                        "WHERE ev_createdbyid = ?",
                createdBy);
    }

    private static final Map<EntityPermission, String> getEventsByEventGroupQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM events "
                        + "WHERE ev_evgrp_eventgroupid = ? "
                        + "ORDER BY ev_eventtime, ev_eventtimesequence "
                        + "_LIMIT_");
        getEventsByEventGroupQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEventGroup(EventGroup eventGroup) {
        return eventFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByEventGroupQueries,
                eventGroup);
    }

    private static final Map<EntityPermission, String> getEventsByEntityInstanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM events "
                        + "WHERE ev_eni_entityinstanceid = ? "
                        + "ORDER BY ev_eventtime, ev_eventtimesequence "
                        + "_LIMIT_");
        getEventsByEntityInstanceQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEntityInstance(EntityInstance entityInstance) {
        return eventFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByEntityInstanceQueries,
                entityInstance);
    }

    private static final Map<EntityPermission, String> getEventsByEventTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM events "
                        + "WHERE ev_eni_entityinstanceid = ? "
                        + "ORDER BY ev_eventtime, ev_eventtimesequence "
                        + "_LIMIT_");
        getEventsByEventTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEventType(EventType eventType) {
        return eventFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByEventTypeQueries,
                eventType);
    }

    private static final Map<EntityPermission, String> getEventsByCreatedByQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM events "
                        + "WHERE ev_createdbyid = ? "
                        + "ORDER BY ev_eventtime, ev_eventtimesequence "
                        + "_LIMIT_");
        getEventsByCreatedByQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByCreatedBy(EntityInstance createdBy) {
        return eventFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, getEventsByCreatedByQueries,
                createdBy);
    }

    private static final Map<EntityPermission, String> getEventsByEntityInstanceAndEventTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM events "
                        + "WHERE ev_eni_entityinstanceid = ? AND ev_evty_eventtypeid = ? "
                        + "ORDER BY ev_eventtime "
                        + "FOR UPDATE");
        getEventsByEntityInstanceAndEventTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<Event> getEventsByEntityInstanceAndEventTypeForUpdate(EntityInstance entityInstance, EventType eventType) {
        return eventFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, getEventsByEntityInstanceAndEventTypeQueries,
                entityInstance, eventType);
    }

    public List<EventTransfer> getEventTransfers(UserVisit userVisit, Collection<Event> events) {
        List<EventTransfer> eventTransfers = new ArrayList<>(events.size());
        var eventTransferCache = getCoreTransferCaches(userVisit).getEventTransferCache();

        events.forEach((event) ->
                eventTransfers.add(eventTransferCache.getEventTransfer(event))
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
        var ps = queuedEventFactory.prepareStatement(
                "SELECT _ALL_ " +
                        "FROM queuedevents " +
                        "FOR UPDATE");

        return queuedEventFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
    }

    public void removeQueuedEvent(QueuedEvent queuedEvent) {
        queuedEvent.remove();
    }

    // --------------------------------------------------------------------------------
    //   Entity Visits
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityVisitFactory entityVisitFactory;
    
    public EntityVisit createEntityVisit(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance) {
        return entityVisitFactory.create(entityInstance, visitedEntityInstance, session.START_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getEntityVisitQueries;

    static {
        getEntityVisitQueries = Map.of(
                EntityPermission.READ_ONLY, """
                        SELECT _ALL_
                        FROM entityvisits
                        WHERE evis_eni_entityinstanceid = ? AND evis_visitedentityinstanceid = ?
                        """,
                EntityPermission.READ_WRITE, """
                        SELECT _ALL_
                        FROM entityvisits
                        WHERE evis_eni_entityinstanceid = ? AND evis_visitedentityinstanceid = ?
                        FOR UPDATE
                        """);
    }

    private EntityVisit getEntityVisit(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance,
            final EntityPermission entityPermission) {
        return entityVisitFactory.getEntityFromQuery(entityPermission, getEntityVisitQueries, entityInstance, visitedEntityInstance);
    }

    public EntityVisit getEntityVisit(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance) {
        return getEntityVisit(entityInstance, visitedEntityInstance, EntityPermission.READ_ONLY);
    }

    public EntityVisit getEntityVisitForUpdate(final EntityInstance entityInstance, final EntityInstance visitedEntityInstance) {
        return getEntityVisit(entityInstance, visitedEntityInstance, EntityPermission.READ_WRITE);
    }

    public EntityVisitTransfer getEntityVisitTransfer(UserVisit userVisit, EntityVisit entityVisit) {
        return getCoreTransferCaches(userVisit).getEntityVisitTransferCache().getEntityVisitTransfer(entityVisit);
    }

    // --------------------------------------------------------------------------------
    //   Event Subscribers
    // --------------------------------------------------------------------------------

    @Inject
    protected EventSubscriberFactory eventSubscriberFactory;
    
    @Inject
    protected EventSubscriberDetailFactory eventSubscriberDetailFactory;
    
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
        var eventSubscriberDetail = eventSubscriberDetailFactory.create(session,
                eventSubscriber, eventSubscriberName, entityInstance, description, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        eventSubscriber = eventSubscriberFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                eventSubscriber.getPrimaryKey());
        eventSubscriber.setActiveDetail(eventSubscriberDetail);
        eventSubscriber.setLastDetail(eventSubscriberDetail);
        eventSubscriber.store();

        sendEvent(eventSubscriber.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return eventSubscriber;
    }

    private EventSubscriber getEventSubscriberByName(String eventSubscriberName, EntityPermission entityPermission) {
        EventSubscriber eventSubscriber;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eventsubscribername = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eventsubscribername = ? " +
                        "FOR UPDATE";
            }

            var ps = eventSubscriberFactory.prepareStatement(query);

            ps.setString(1, eventSubscriberName);

            eventSubscriber = eventSubscriberFactory.getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return eventSubscriber;
    }

    public EventSubscriber getEventSubscriberByName(String eventSubscriberName) {
        return getEventSubscriberByName(eventSubscriberName, EntityPermission.READ_ONLY);
    }

    public EventSubscriber getEventSubscriberByNameForUpdate(String eventSubscriberName) {
        return getEventSubscriberByName(eventSubscriberName, EntityPermission.READ_WRITE);
    }

    private List<EventSubscriber> getEventSubscribersByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        List<EventSubscriber> eventSubscribers;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eni_entityinstanceid = ? " +
                        "ORDER BY evsdt_sortorder, evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribers, eventsubscriberdetails " +
                        "WHERE evs_activedetailid = evsdt_eventsubscriberdetailid AND evsdt_eni_entityinstanceid = ? " +
                        "FOR UPDATE";
            }

            var ps = eventSubscriberFactory.prepareStatement(query);

            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());

            eventSubscribers = eventSubscriberFactory.getEntitiesFromQuery(entityPermission,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return eventSubscribers;
    }

    public List<EventSubscriber> getEventSubscribersByEntityInstance(EntityInstance entityInstance) {
        return getEventSubscribersByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public List<EventSubscriber> getEventSubscribersByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEventSubscribersByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

//    public EventSubscriberTransfer getEventSubscriberTransfer(UserVisit userVisit, EventSubscriber eventSubscriber) {
//        return getPaymentTransferCaches(userVisit).getEventSubscriberTransferCache().getEventSubscriberTransfer(eventSubscriber);
//    }
//
//    public List<EventSubscriberTransfer> getEventSubscriberTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
//        List<EventSubscriber> eventSubscribers = getEventSubscribersByEntityInstance(entityInstance);
//        List<EventSubscriberTransfer> eventSubscriberTransfers = new ArrayList<EventSubscriberTransfer>(eventSubscribers.size());
//        EventSubscriberTransferCache eventSubscriberTransferCache = getPaymentTransferCaches(userVisit).getEventSubscriberTransferCache();
//
//        for(var eventSubscriber : eventSubscribers) {
//            eventSubscriberTransfers.add(eventSubscriberTransferCache.getEventSubscriberTransfer(eventSubscriber));
//        }
//
//        return eventSubscriberTransfers;
//    }

    public void deleteEventSubscriber(EventSubscriber eventSubscriber, BasePK deletedBy) {
        removeQueuedSubscriberEventsByEventSubscriber(eventSubscriber);

        var eventSubscriberDetail = eventSubscriber.getLastDetailForUpdate();
        eventSubscriberDetail.setThruTime(session.START_TIME_LONG);
        eventSubscriber.setActiveDetail(null);
        eventSubscriber.store();

        sendEvent(eventSubscriber.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
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
        List<QueuedSubscriberEvent> queuedSubscriberEvents;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedsubscriberevents, events " +
                        "WHERE qsev_evs_eventsubscriberid = ? AND qsev_ev_eventid = ev_eventid " +
                        "ORDER BY ev_eventtime";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedsubscriberevents " +
                        "WHERE qsev_evs_eventsubscriberid = ? " +
                        "FOR UPDATE";
            }

            var ps = queuedSubscriberEventFactory.prepareStatement(query);

            ps.setLong(1, eventSubscriber.getPrimaryKey().getEntityId());

            queuedSubscriberEvents = queuedSubscriberEventFactory.getEntitiesFromQuery(entityPermission,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return queuedSubscriberEvents;
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
        var eventSubscriberEventType = eventSubscriberEventTypeFactory.create(session,
                eventSubscriber, eventType, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(eventSubscriber.getPrimaryKey(), EventTypes.MODIFY, eventSubscriberEventType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return eventSubscriberEventType;
    }

    private List<EventSubscriberEventType> getEventSubscriberEventTypes(EventType eventType, EntityPermission entityPermission) {
        List<EventSubscriberEventType> eventSubscriberEventTypes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribereventtypes, eventsubscribers, eventsubscriberdetails " +
                        "WHERE evsevt_evty_eventtypeid = ? AND evsevt_thrutime = ? " +
                        "AND evsevt_evs_eventsubscriberid = evs_eventsubscriberid AND evs_lastdetailid = evsdt_eventsubscriberdetailid " +
                        "ORDER BY evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscribereventtypes " +
                        "WHERE evsevt_evty_eventtypeid = ? AND evsevt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = eventSubscriberEventTypeFactory.prepareStatement(query);

            ps.setLong(1, eventType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            eventSubscriberEventTypes = eventSubscriberEventTypeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return eventSubscriberEventTypes;
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
        var eventSubscriberEntityType = eventSubscriberEntityTypeFactory.create(session,
                eventSubscriber, entityType, eventType, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(eventSubscriber.getPrimaryKey(), EventTypes.MODIFY, eventSubscriberEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return eventSubscriberEntityType;
    }

    private List<EventSubscriberEntityType> getEventSubscriberEntityTypes(EntityType entityType, EventType eventType,
            EntityPermission entityPermission) {
        List<EventSubscriberEntityType> eventSubscriberEntityTypes;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentitytypes, eventsubscribers, eventsubscriberdetails " +
                        "WHERE evset_ent_entitytypeid = ? AND evset_evty_eventtypeid = ? AND evset_thrutime = ? " +
                        "AND evset_evs_eventsubscriberid = evs_eventsubscriberid AND evs_lastdetailid = evsdt_eventsubscriberdetailid " +
                        "ORDER BY evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentitytypes " +
                        "WHERE evset_ent_entitytypeid = ? AND evset_evty_eventtypeid = ? AND evset_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = eventSubscriberEntityTypeFactory.prepareStatement(query);

            ps.setLong(1, entityType.getPrimaryKey().getEntityId());
            ps.setLong(2, eventType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            eventSubscriberEntityTypes = eventSubscriberEntityTypeFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return eventSubscriberEntityTypes;
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
        var eventSubscriberEntityInstance = eventSubscriberEntityInstanceFactory.create(session,
                eventSubscriber, entityInstance, eventType, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(eventSubscriber.getPrimaryKey(), EventTypes.MODIFY, eventSubscriberEntityInstance.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return eventSubscriberEntityInstance;
    }

    private List<EventSubscriberEntityInstance> getEventSubscriberEntityInstances(EntityInstance entityInstance,
            EventType eventType, EntityPermission entityPermission) {
        List<EventSubscriberEntityInstance> eventSubscriberEntityInstances;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentityinstances, eventsubscribers, eventsubscriberdetails " +
                        "WHERE evsei_eni_entityinstanceid = ? AND evsei_evty_eventtypeid = ? AND evsei_thrutime = ? " +
                        "AND evsei_evs_eventsubscriberid = evs_eventsubscriberid AND evs_lastdetailid = evsdt_eventsubscriberdetailid " +
                        "ORDER BY evsdt_eventsubscribername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM eventsubscriberentityinstances " +
                        "WHERE evsei_eni_entityinstanceid = ? AND evsei_evty_eventtypeid = ? AND evsei_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = eventSubscriberEntityInstanceFactory.prepareStatement(query);

            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, eventType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);

            eventSubscriberEntityInstances = eventSubscriberEntityInstanceFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return eventSubscriberEntityInstances;
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
            var entityInstanceUtils = EntityInstanceUtils.getInstance();

            getLog().info("entityInstance = " + entityInstanceUtils.getEntityRefByEntityInstance(entityInstance)
                    + ", eventType = " + eventType.getEventTypeName()
                    + ", relatedEntityInstance = " + entityInstanceUtils.getEntityRefByEntityInstance(relatedEntityInstance)
                    + ", relatedEventType = " + (relatedEventType == null ? "(null)" : relatedEventType.getEventTypeName())
                    + ", createdByEntityInstance = " + entityInstanceUtils.getEntityRefByEntityInstance(createdByEntityInstance));
        }

        final var eventTime = session.START_TIME_LONG;
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

            EventTopic.getInstance().sendEvent(event);

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

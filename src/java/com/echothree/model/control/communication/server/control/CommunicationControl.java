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

package com.echothree.model.control.communication.server.control;

import com.echothree.model.control.communication.common.choice.CommunicationEventPurposeChoicesBean;
import com.echothree.model.control.communication.common.transfer.CommunicationEmailSourceTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventPurposeDescriptionTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventPurposeTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventRoleTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventRoleTypeTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventTypeTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationSourceDescriptionTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationSourceTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationSourceTypeTransfer;
import com.echothree.model.control.communication.server.transfer.CommunicationEmailSourceTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationEventPurposeDescriptionTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationEventPurposeTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationEventRoleTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationEventRoleTypeTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationEventTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationEventTypeTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationSourceDescriptionTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationSourceTransferCache;
import com.echothree.model.control.communication.server.transfer.CommunicationSourceTypeTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.communication.common.pk.CommunicationEventPK;
import com.echothree.model.data.communication.common.pk.CommunicationEventPurposePK;
import com.echothree.model.data.communication.common.pk.CommunicationEventRoleTypePK;
import com.echothree.model.data.communication.common.pk.CommunicationEventTypePK;
import com.echothree.model.data.communication.common.pk.CommunicationSourcePK;
import com.echothree.model.data.communication.common.pk.CommunicationSourceTypePK;
import com.echothree.model.data.communication.server.entity.CommunicationEmailSource;
import com.echothree.model.data.communication.server.entity.CommunicationEvent;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurposeDescription;
import com.echothree.model.data.communication.server.entity.CommunicationEventRole;
import com.echothree.model.data.communication.server.entity.CommunicationEventRoleType;
import com.echothree.model.data.communication.server.entity.CommunicationEventRoleTypeDescription;
import com.echothree.model.data.communication.server.entity.CommunicationEventType;
import com.echothree.model.data.communication.server.entity.CommunicationEventTypeDescription;
import com.echothree.model.data.communication.server.entity.CommunicationSource;
import com.echothree.model.data.communication.server.entity.CommunicationSourceDescription;
import com.echothree.model.data.communication.server.entity.CommunicationSourceType;
import com.echothree.model.data.communication.server.entity.CommunicationSourceTypeDescription;
import com.echothree.model.data.communication.server.factory.CommunicationEmailSourceFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventDetailFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventPurposeDescriptionFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventPurposeDetailFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventPurposeFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventRoleFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventRoleTypeDescriptionFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventRoleTypeFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventTypeDescriptionFactory;
import com.echothree.model.data.communication.server.factory.CommunicationEventTypeFactory;
import com.echothree.model.data.communication.server.factory.CommunicationSourceDescriptionFactory;
import com.echothree.model.data.communication.server.factory.CommunicationSourceDetailFactory;
import com.echothree.model.data.communication.server.factory.CommunicationSourceFactory;
import com.echothree.model.data.communication.server.factory.CommunicationSourceTypeDescriptionFactory;
import com.echothree.model.data.communication.server.factory.CommunicationSourceTypeFactory;
import com.echothree.model.data.communication.server.value.CommunicationEmailSourceValue;
import com.echothree.model.data.communication.server.value.CommunicationEventDetailValue;
import com.echothree.model.data.communication.server.value.CommunicationEventPurposeDescriptionValue;
import com.echothree.model.data.communication.server.value.CommunicationEventPurposeDetailValue;
import com.echothree.model.data.communication.server.value.CommunicationSourceDescriptionValue;
import com.echothree.model.data.communication.server.value.CommunicationSourceDetailValue;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import static com.echothree.model.jooq.server.keys.communication.CommunicationForeignKeys.COMMUNICATION_EVENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.communication.CommunicationForeignKeys.COMMUNICATION_EVENTS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.communication.CommunicationForeignKeys.COMMUNICATION_EVENT_PURPOSES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.communication.CommunicationForeignKeys.COMMUNICATION_EVENT_PURPOSE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.communication.CommunicationForeignKeys.COMMUNICATION_EVENT_ROLES_COMMUNICATION_EVENT_FK;
import static com.echothree.model.jooq.server.keys.communication.CommunicationForeignKeys.COMMUNICATION_EVENT_ROLES_COMMUNICATION_EVENT_ROLE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.communication.CommunicationForeignKeys.COMMUNICATION_EVENT_ROLES_PARTY_FK;
import static com.echothree.model.jooq.server.keys.communication.CommunicationForeignKeys.COMMUNICATION_SOURCES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.communication.CommunicationForeignKeys.COMMUNICATION_SOURCE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.party.PartyForeignKeys.PARTIES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEmailSources.CommunicationEmailSources;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEventDetails.CommunicationEventDetails;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEventPurposeDescriptions.CommunicationEventPurposeDescriptions;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEventPurposeDetails.CommunicationEventPurposeDetails;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEventPurposes.CommunicationEventPurposes;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEventRoleTypeDescriptions.CommunicationEventRoleTypeDescriptions;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEventRoleTypes.CommunicationEventRoleTypes;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEventRoles.CommunicationEventRoles;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEventTypeDescriptions.CommunicationEventTypeDescriptions;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEventTypes.CommunicationEventTypes;
import static com.echothree.model.jooq.server.tables.communication.CommunicationEvents.CommunicationEvents;
import static com.echothree.model.jooq.server.tables.communication.CommunicationSourceDescriptions.CommunicationSourceDescriptions;
import static com.echothree.model.jooq.server.tables.communication.CommunicationSourceDetails.CommunicationSourceDetails;
import static com.echothree.model.jooq.server.tables.communication.CommunicationSourceTypeDescriptions.CommunicationSourceTypeDescriptions;
import static com.echothree.model.jooq.server.tables.communication.CommunicationSourceTypes.CommunicationSourceTypes;
import static com.echothree.model.jooq.server.tables.communication.CommunicationSources.CommunicationSources;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EncryptionUtils;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class CommunicationControl
        extends BaseModelControl {
    
    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of CommunicationControl */
    protected CommunicationControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Purposes
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationEventPurposeDetailFactory communicationEventPurposeDetailFactory;

    @Inject
    protected CommunicationEventPurposeFactory communicationEventPurposeFactory;

    @Inject
    CommunicationEventPurposeTransferCache communicationEventPurposeTransferCache;

    public CommunicationEventPurpose createCommunicationEventPurpose(String communicationEventPurposeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultCommunicationEventPurpose = getDefaultCommunicationEventPurpose();
        var defaultFound = defaultCommunicationEventPurpose != null;
        
        if(defaultFound && isDefault) {
            var defaultCommunicationEventPurposeDetailValue = getDefaultCommunicationEventPurposeDetailValueForUpdate();
            
            defaultCommunicationEventPurposeDetailValue.setIsDefault(false);
            updateCommunicationEventPurposeFromValue(defaultCommunicationEventPurposeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var communicationEventPurpose = communicationEventPurposeFactory.create();
        var communicationEventPurposeDetail = communicationEventPurposeDetailFactory.create(
                communicationEventPurpose, communicationEventPurposeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        communicationEventPurpose = communicationEventPurposeFactory.getEntityFromPK(EntityPermission.READ_WRITE, communicationEventPurpose.getPrimaryKey());
        communicationEventPurpose.setActiveDetail(communicationEventPurposeDetail);
        communicationEventPurpose.setLastDetail(communicationEventPurposeDetail);
        communicationEventPurpose.store();
        
        sendEvent(communicationEventPurpose.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return communicationEventPurpose;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CommunicationEventPurpose */
    public CommunicationEventPurpose getCommunicationEventPurposeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CommunicationEventPurposePK(entityInstance.getEntityUniqueId());

        return communicationEventPurposeFactory.getEntityFromPK(entityPermission, pk);
    }

    public CommunicationEventPurpose getCommunicationEventPurposeByEntityInstance(EntityInstance entityInstance) {
        return getCommunicationEventPurposeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CommunicationEventPurpose getCommunicationEventPurposeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCommunicationEventPurposeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCommunicationEventPurposes() {
        return session.getDslContext()
                .selectCount()
                .from(CommunicationEventPurposes)
                .join(CommunicationEventPurposeDetails).onKey(COMMUNICATION_EVENT_PURPOSES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private CommunicationEventPurpose getCommunicationEventPurposeByName(String communicationEventPurposeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationEventPurposes.fields())
                .from(CommunicationEventPurposes)
                .join(CommunicationEventPurposeDetails).onKey(COMMUNICATION_EVENT_PURPOSES_ACTIVE_DETAIL_FK)
                .where(CommunicationEventPurposeDetails.COMMUNICATION_EVENT_PURPOSE_NAME.eq(communicationEventPurposeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationEventPurposeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommunicationEventPurpose getCommunicationEventPurposeByName(String communicationEventPurposeName) {
        return getCommunicationEventPurposeByName(communicationEventPurposeName, EntityPermission.READ_ONLY);
    }
    
    public CommunicationEventPurpose getCommunicationEventPurposeByNameForUpdate(String communicationEventPurposeName) {
        return getCommunicationEventPurposeByName(communicationEventPurposeName, EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventPurposeDetailValue getCommunicationEventPurposeDetailValueForUpdate(CommunicationEventPurpose communicationEventPurpose) {
        return communicationEventPurpose == null? null: communicationEventPurpose.getLastDetailForUpdate().getCommunicationEventPurposeDetailValue().clone();
    }
    
    public CommunicationEventPurposeDetailValue getCommunicationEventPurposeDetailValueByNameForUpdate(String communicationEventPurposeName) {
        return getCommunicationEventPurposeDetailValueForUpdate(getCommunicationEventPurposeByNameForUpdate(communicationEventPurposeName));
    }
    
    private CommunicationEventPurpose getDefaultCommunicationEventPurpose(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationEventPurposes.fields())
                .from(CommunicationEventPurposes)
                .join(CommunicationEventPurposeDetails).onKey(COMMUNICATION_EVENT_PURPOSES_ACTIVE_DETAIL_FK)
                .where(CommunicationEventPurposeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationEventPurposeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommunicationEventPurpose getDefaultCommunicationEventPurpose() {
        return getDefaultCommunicationEventPurpose(EntityPermission.READ_ONLY);
    }
    
    public CommunicationEventPurpose getDefaultCommunicationEventPurposeForUpdate() {
        return getDefaultCommunicationEventPurpose(EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventPurposeDetailValue getDefaultCommunicationEventPurposeDetailValueForUpdate() {
        return getDefaultCommunicationEventPurposeForUpdate().getLastDetailForUpdate().getCommunicationEventPurposeDetailValue().clone();
    }
    
    private List<CommunicationEventPurpose> getCommunicationEventPurposes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationEventPurposes.fields())
                .from(CommunicationEventPurposes)
                .join(CommunicationEventPurposeDetails).onKey(COMMUNICATION_EVENT_PURPOSES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CommunicationEventPurposeDetails.SORT_ORDER, CommunicationEventPurposeDetails.COMMUNICATION_EVENT_PURPOSE_NAME),
                    CommunicationEventPurposeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationEventPurposeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommunicationEventPurpose> getCommunicationEventPurposes() {
        return getCommunicationEventPurposes(EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationEventPurpose> getCommunicationEventPurposesForUpdate() {
        return getCommunicationEventPurposes(EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventPurposeTransfer getCommunicationEventPurposeTransfer(UserVisit userVisit, CommunicationEventPurpose communicationEventPurpose) {
        return communicationEventPurposeTransferCache.getCommunicationEventPurposeTransfer(userVisit, communicationEventPurpose);
    }
    
    public List<CommunicationEventPurposeTransfer> getCommunicationEventPurposeTransfers(UserVisit userVisit, Collection<CommunicationEventPurpose> communicationEventPurposes) {
        List<CommunicationEventPurposeTransfer> communicationEventPurposeTransfers = new ArrayList<>(communicationEventPurposes.size());

        communicationEventPurposes.forEach((communicationEventPurpose) ->
                communicationEventPurposeTransfers.add(communicationEventPurposeTransferCache.getCommunicationEventPurposeTransfer(userVisit, communicationEventPurpose))
        );

        return communicationEventPurposeTransfers;
    }

    public List<CommunicationEventPurposeTransfer> getCommunicationEventPurposeTransfers(UserVisit userVisit) {
        return getCommunicationEventPurposeTransfers(userVisit, getCommunicationEventPurposes());
    }
    
    public CommunicationEventPurposeChoicesBean getCommunicationEventPurposeChoices(String defaultCommunicationEventPurposeChoice, Language language,
            boolean allowNullChoice) {
        var communicationEventPurposes = getCommunicationEventPurposes();
        var size = communicationEventPurposes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultCommunicationEventPurposeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var communicationEventPurpose : communicationEventPurposes) {
            var communicationEventPurposeDetail = communicationEventPurpose.getLastDetail();
            
            var label = getBestCommunicationEventPurposeDescription(communicationEventPurpose, language);
            var value = communicationEventPurposeDetail.getCommunicationEventPurposeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultCommunicationEventPurposeChoice != null && defaultCommunicationEventPurposeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && communicationEventPurposeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new CommunicationEventPurposeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateCommunicationEventPurposeFromValue(CommunicationEventPurposeDetailValue communicationEventPurposeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(communicationEventPurposeDetailValue.hasBeenModified()) {
            var communicationEventPurpose = communicationEventPurposeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     communicationEventPurposeDetailValue.getCommunicationEventPurposePK());
            var communicationEventPurposeDetail = communicationEventPurpose.getActiveDetailForUpdate();
            
            communicationEventPurposeDetail.setThruTime(session.getStartTime());
            communicationEventPurposeDetail.store();

            var communicationEventPurposePK = communicationEventPurposeDetail.getCommunicationEventPurposePK();
            var communicationEventPurposeName = communicationEventPurposeDetailValue.getCommunicationEventPurposeName();
            var isDefault = communicationEventPurposeDetailValue.getIsDefault();
            var sortOrder = communicationEventPurposeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultCommunicationEventPurpose = getDefaultCommunicationEventPurpose();
                var defaultFound = defaultCommunicationEventPurpose != null && !defaultCommunicationEventPurpose.equals(communicationEventPurpose);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCommunicationEventPurposeDetailValue = getDefaultCommunicationEventPurposeDetailValueForUpdate();
                    
                    defaultCommunicationEventPurposeDetailValue.setIsDefault(false);
                    updateCommunicationEventPurposeFromValue(defaultCommunicationEventPurposeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            communicationEventPurposeDetail = communicationEventPurposeDetailFactory.create(communicationEventPurposePK,
                    communicationEventPurposeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            communicationEventPurpose.setActiveDetail(communicationEventPurposeDetail);
            communicationEventPurpose.setLastDetail(communicationEventPurposeDetail);
            
            sendEvent(communicationEventPurposePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateCommunicationEventPurposeFromValue(CommunicationEventPurposeDetailValue communicationEventPurposeDetailValue, BasePK updatedBy) {
        updateCommunicationEventPurposeFromValue(communicationEventPurposeDetailValue, true, updatedBy);
    }
    
    public void deleteCommunicationEventPurpose(CommunicationEventPurpose communicationEventPurpose, BasePK deletedBy) {
        deleteCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(communicationEventPurpose, deletedBy);

        var communicationEventPurposeDetail = communicationEventPurpose.getLastDetailForUpdate();
        communicationEventPurposeDetail.setThruTime(session.getStartTime());
        communicationEventPurpose.setActiveDetail(null);
        communicationEventPurpose.store();
        
        // Check for default, and pick one if necessary
        var defaultCommunicationEventPurpose = getDefaultCommunicationEventPurpose();
        if(defaultCommunicationEventPurpose == null) {
            var communicationEventPurposes = getCommunicationEventPurposesForUpdate();
            
            if(!communicationEventPurposes.isEmpty()) {
                Iterator iter = communicationEventPurposes.iterator();
                if(iter.hasNext()) {
                    defaultCommunicationEventPurpose = (CommunicationEventPurpose)iter.next();
                }
                var communicationEventPurposeDetailValue = Objects.requireNonNull(defaultCommunicationEventPurpose).getLastDetailForUpdate().getCommunicationEventPurposeDetailValue().clone();
                
                communicationEventPurposeDetailValue.setIsDefault(true);
                updateCommunicationEventPurposeFromValue(communicationEventPurposeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(communicationEventPurpose.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Purpose Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationEventPurposeDescriptionFactory communicationEventPurposeDescriptionFactory;

    @Inject
    CommunicationEventPurposeDescriptionTransferCache communicationEventPurposeDescriptionTransferCache;

    public CommunicationEventPurposeDescription createCommunicationEventPurposeDescription(CommunicationEventPurpose communicationEventPurpose,
            Language language, String description, BasePK createdBy) {
        var communicationEventPurposeDescription = communicationEventPurposeDescriptionFactory.create(
                communicationEventPurpose, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(communicationEventPurpose.getPrimaryKey(), EventTypes.MODIFY, communicationEventPurposeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return communicationEventPurposeDescription;
    }
    
    private CommunicationEventPurposeDescription getCommunicationEventPurposeDescription(CommunicationEventPurpose communicationEventPurpose,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationEventPurposeDescriptions.fields())
                .from(CommunicationEventPurposeDescriptions)
                .where(CommunicationEventPurposeDescriptions.COMMUNICATION_EVENT_PURPOSE.eq(communicationEventPurpose.getPrimaryKey()),
                        CommunicationEventPurposeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CommunicationEventPurposeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationEventPurposeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommunicationEventPurposeDescription getCommunicationEventPurposeDescription(CommunicationEventPurpose communicationEventPurpose, Language language) {
        return getCommunicationEventPurposeDescription(communicationEventPurpose, language, EntityPermission.READ_ONLY);
    }
    
    public CommunicationEventPurposeDescription getCommunicationEventPurposeDescriptionForUpdate(CommunicationEventPurpose communicationEventPurpose, Language language) {
        return getCommunicationEventPurposeDescription(communicationEventPurpose, language, EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventPurposeDescriptionValue getCommunicationEventPurposeDescriptionValue(CommunicationEventPurposeDescription communicationEventPurposeDescription) {
        return communicationEventPurposeDescription == null? null: communicationEventPurposeDescription.getCommunicationEventPurposeDescriptionValue().clone();
    }
    
    public CommunicationEventPurposeDescriptionValue getCommunicationEventPurposeDescriptionValueForUpdate(CommunicationEventPurpose communicationEventPurpose, Language language) {
        return getCommunicationEventPurposeDescriptionValue(getCommunicationEventPurposeDescriptionForUpdate(communicationEventPurpose, language));
    }
    
    private List<CommunicationEventPurposeDescription> getCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(CommunicationEventPurpose communicationEventPurpose, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommunicationEventPurposeDescriptions.fields())
                    .from(CommunicationEventPurposeDescriptions)
                    .join(Languages).onKey(COMMUNICATION_EVENT_PURPOSE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CommunicationEventPurposeDescriptions.COMMUNICATION_EVENT_PURPOSE.eq(communicationEventPurpose.getPrimaryKey()),
                            CommunicationEventPurposeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CommunicationEventPurposeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommunicationEventPurposeDescriptions.fields())
                    .from(CommunicationEventPurposeDescriptions)
                    .where(CommunicationEventPurposeDescriptions.COMMUNICATION_EVENT_PURPOSE.eq(communicationEventPurpose.getPrimaryKey()),
                            CommunicationEventPurposeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return communicationEventPurposeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommunicationEventPurposeDescription> getCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(CommunicationEventPurpose communicationEventPurpose) {
        return getCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(communicationEventPurpose, EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationEventPurposeDescription> getCommunicationEventPurposeDescriptionsByCommunicationEventPurposeForUpdate(CommunicationEventPurpose communicationEventPurpose) {
        return getCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(communicationEventPurpose, EntityPermission.READ_WRITE);
    }
    
    public String getBestCommunicationEventPurposeDescription(CommunicationEventPurpose communicationEventPurpose, Language language) {
        String description;
        var communicationEventPurposeDescription = getCommunicationEventPurposeDescription(communicationEventPurpose, language);
        
        if(communicationEventPurposeDescription == null && !language.getIsDefault()) {
            communicationEventPurposeDescription = getCommunicationEventPurposeDescription(communicationEventPurpose, partyControl.getDefaultLanguage());
        }
        
        if(communicationEventPurposeDescription == null) {
            description = communicationEventPurpose.getLastDetail().getCommunicationEventPurposeName();
        } else {
            description = communicationEventPurposeDescription.getDescription();
        }
        
        return description;
    }
    
    public CommunicationEventPurposeDescriptionTransfer getCommunicationEventPurposeDescriptionTransfer(UserVisit userVisit, CommunicationEventPurposeDescription communicationEventPurposeDescription) {
        return communicationEventPurposeDescriptionTransferCache.getCommunicationEventPurposeDescriptionTransfer(userVisit, communicationEventPurposeDescription);
    }
    
    public List<CommunicationEventPurposeDescriptionTransfer> getCommunicationEventPurposeDescriptionTransfers(UserVisit userVisit, CommunicationEventPurpose communicationEventPurpose) {
        var communicationEventPurposeDescriptions = getCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(communicationEventPurpose);
        List<CommunicationEventPurposeDescriptionTransfer> communicationEventPurposeDescriptionTransfers = new ArrayList<>(communicationEventPurposeDescriptions.size());
        
        communicationEventPurposeDescriptions.forEach((communicationEventPurposeDescription) ->
                communicationEventPurposeDescriptionTransfers.add(communicationEventPurposeDescriptionTransferCache.getCommunicationEventPurposeDescriptionTransfer(userVisit, communicationEventPurposeDescription))
        );
        
        return communicationEventPurposeDescriptionTransfers;
    }
    
    public void updateCommunicationEventPurposeDescriptionFromValue(CommunicationEventPurposeDescriptionValue communicationEventPurposeDescriptionValue, BasePK updatedBy) {
        if(communicationEventPurposeDescriptionValue.hasBeenModified()) {
            var communicationEventPurposeDescription = communicationEventPurposeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, communicationEventPurposeDescriptionValue.getPrimaryKey());
            
            communicationEventPurposeDescription.setThruTime(session.getStartTime());
            communicationEventPurposeDescription.store();

            var communicationEventPurpose = communicationEventPurposeDescription.getCommunicationEventPurpose();
            var language = communicationEventPurposeDescription.getLanguage();
            var description = communicationEventPurposeDescriptionValue.getDescription();
            
            communicationEventPurposeDescription = communicationEventPurposeDescriptionFactory.create(communicationEventPurpose, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(communicationEventPurpose.getPrimaryKey(), EventTypes.MODIFY, communicationEventPurposeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommunicationEventPurposeDescription(CommunicationEventPurposeDescription communicationEventPurposeDescription, BasePK deletedBy) {
        communicationEventPurposeDescription.setThruTime(session.getStartTime());
        
        sendEvent(communicationEventPurposeDescription.getCommunicationEventPurposePK(), EventTypes.MODIFY, communicationEventPurposeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteCommunicationEventPurposeDescriptionsByCommunicationEventPurpose(CommunicationEventPurpose communicationEventPurpose, BasePK deletedBy) {
        var communicationEventPurposeDescriptions = getCommunicationEventPurposeDescriptionsByCommunicationEventPurposeForUpdate(communicationEventPurpose);
        
        communicationEventPurposeDescriptions.forEach((communicationEventPurposeDescription) -> 
                deleteCommunicationEventPurposeDescription(communicationEventPurposeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Role Types
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationEventRoleTypeFactory communicationEventRoleTypeFactory;

    @Inject
    CommunicationEventRoleTypeTransferCache communicationEventRoleTypeTransferCache;

    public CommunicationEventRoleType createCommunicationEventRoleType(String communicationEventRoleTypeName, Integer sortOrder) {
        return communicationEventRoleTypeFactory.create(communicationEventRoleTypeName, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CommunicationEventRoleType */
    public CommunicationEventRoleType getCommunicationEventRoleTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CommunicationEventRoleTypePK(entityInstance.getEntityUniqueId());

        return communicationEventRoleTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public CommunicationEventRoleType getCommunicationEventRoleTypeByEntityInstance(EntityInstance entityInstance) {
        return getCommunicationEventRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CommunicationEventRoleType getCommunicationEventRoleTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCommunicationEventRoleTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCommunicationEventRoleTypes() {
        return session.getDslContext()
                .selectCount()
                .from(CommunicationEventRoleTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public CommunicationEventRoleType getCommunicationEventRoleTypeByName(String communicationEventRoleTypeName) {
        var query = session.getDslContext()
                .select(CommunicationEventRoleTypes.fields())
                .from(CommunicationEventRoleTypes)
                .where(CommunicationEventRoleTypes.COMMUNICATION_EVENT_ROLE_TYPE_NAME.eq(communicationEventRoleTypeName));

        return communicationEventRoleTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<CommunicationEventRoleType> getCommunicationEventRoleTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(CommunicationEventRoleTypes.fields())
                .from(CommunicationEventRoleTypes)
                .orderBy(CommunicationEventRoleTypes.SORT_ORDER, CommunicationEventRoleTypes.COMMUNICATION_EVENT_ROLE_TYPE_NAME),
                CommunicationEventRoleTypeFactory.class);

        return communicationEventRoleTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public CommunicationEventRoleTypeTransfer getCommunicationEventRoleTypeTransfer(UserVisit userVisit,
            CommunicationEventRoleType communicationEventRoleType) {
        return communicationEventRoleTypeTransferCache.getCommunicationEventRoleTypeTransfer(userVisit, communicationEventRoleType);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Role Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationEventRoleTypeDescriptionFactory communicationEventRoleTypeDescriptionFactory;

    public CommunicationEventRoleTypeDescription createCommunicationEventRoleTypeDescription(CommunicationEventRoleType communicationEventRoleType,
            Language language, String description) {
        return communicationEventRoleTypeDescriptionFactory.create(communicationEventRoleType, language, description);
    }
    
    public CommunicationEventRoleTypeDescription getCommunicationEventRoleTypeDescription(CommunicationEventRoleType communicationEventRoleType,
            Language language) {
        var query = session.getDslContext()
                .select(CommunicationEventRoleTypeDescriptions.fields())
                .from(CommunicationEventRoleTypeDescriptions)
                .where(CommunicationEventRoleTypeDescriptions.COMMUNICATION_EVENT_ROLE_TYPE.eq(communicationEventRoleType.getPrimaryKey()),
                        CommunicationEventRoleTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return communicationEventRoleTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestCommunicationEventRoleTypeDescription(CommunicationEventRoleType communicationEventRoleType, Language language) {
        String description;
        var communicationEventRoleTypeDescription = getCommunicationEventRoleTypeDescription(communicationEventRoleType, language);
        
        if(communicationEventRoleTypeDescription == null && !language.getIsDefault()) {
            communicationEventRoleTypeDescription = getCommunicationEventRoleTypeDescription(communicationEventRoleType, partyControl.getDefaultLanguage());
        }
        
        if(communicationEventRoleTypeDescription == null) {
            description = communicationEventRoleType.getCommunicationEventRoleTypeName();
        } else {
            description = communicationEventRoleTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Types
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationEventTypeFactory communicationEventTypeFactory;

    @Inject
    CommunicationEventTypeTransferCache communicationEventTypeTransferCache;

    public CommunicationEventType createCommunicationEventType(String communicationEventTypeName, Boolean isDefault,
            Integer sortOrder) {
        return communicationEventTypeFactory.create(communicationEventTypeName, isDefault, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CommunicationEventType */
    public CommunicationEventType getCommunicationEventTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CommunicationEventTypePK(entityInstance.getEntityUniqueId());

        return communicationEventTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public CommunicationEventType getCommunicationEventTypeByEntityInstance(EntityInstance entityInstance) {
        return getCommunicationEventTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CommunicationEventType getCommunicationEventTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCommunicationEventTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCommunicationEventTypes() {
        return session.getDslContext()
                .selectCount()
                .from(CommunicationEventTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public CommunicationEventType getCommunicationEventTypeByName(String communicationEventTypeName) {
        var query = session.getDslContext()
                .select(CommunicationEventTypes.fields())
                .from(CommunicationEventTypes)
                .where(CommunicationEventTypes.COMMUNICATION_EVENT_TYPE_NAME.eq(communicationEventTypeName));

        return communicationEventTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<CommunicationEventType> getCommunicationEventTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(CommunicationEventTypes.fields())
                .from(CommunicationEventTypes)
                .orderBy(CommunicationEventTypes.SORT_ORDER, CommunicationEventTypes.COMMUNICATION_EVENT_TYPE_NAME),
                CommunicationEventTypeFactory.class);

        return communicationEventTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public CommunicationEventTypeTransfer getCommunicationEventTypeTransfer(UserVisit userVisit,
            CommunicationEventType communicationEventType) {
        return communicationEventTypeTransferCache.getCommunicationEventTypeTransfer(userVisit, communicationEventType);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationEventTypeDescriptionFactory communicationEventTypeDescriptionFactory;

    public CommunicationEventTypeDescription createCommunicationEventTypeDescription(CommunicationEventType communicationEventType,
            Language language, String description) {
        return communicationEventTypeDescriptionFactory.create(communicationEventType, language, description);
    }
    
    public CommunicationEventTypeDescription getCommunicationEventTypeDescription(CommunicationEventType communicationEventType,
            Language language) {
        var query = session.getDslContext()
                .select(CommunicationEventTypeDescriptions.fields())
                .from(CommunicationEventTypeDescriptions)
                .where(CommunicationEventTypeDescriptions.COMMUNICATION_EVENT_TYPE.eq(communicationEventType.getPrimaryKey()),
                        CommunicationEventTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return communicationEventTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestCommunicationEventTypeDescription(CommunicationEventType communicationEventType, Language language) {
        String description;
        var communicationEventTypeDescription = getCommunicationEventTypeDescription(communicationEventType, language);
        
        if(communicationEventTypeDescription == null && !language.getIsDefault()) {
            communicationEventTypeDescription = getCommunicationEventTypeDescription(communicationEventType, partyControl.getDefaultLanguage());
        }
        
        if(communicationEventTypeDescription == null) {
            description = communicationEventType.getCommunicationEventTypeName();
        } else {
            description = communicationEventTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Events
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationEventDetailFactory communicationEventDetailFactory;

    @Inject
    protected CommunicationEventFactory communicationEventFactory;

    @Inject
    CommunicationEventTransferCache communicationEventTransferCache;

    public CommunicationEvent createCommunicationEvent(CommunicationEventType communicationEventType,
            CommunicationSource communicationSource, CommunicationEventPurpose communicationEventPurpose,
            CommunicationEvent originalCommunicationEvent, CommunicationEvent parentCommunicationEvent,
            PartyContactMechanism partyContactMechanism, Document document, BasePK createdBy) {
        var sequence = sequenceControl.getDefaultSequence(sequenceControl.getSequenceTypeByName(SequenceTypes.COMMUNICATION_EVENT.name()));
        var communicationEventName = sequenceGeneratorLogic.getNextSequenceValue(sequence);
        
        return createCommunicationEvent(communicationEventName, communicationEventType, communicationSource, communicationEventPurpose,
                originalCommunicationEvent, parentCommunicationEvent, partyContactMechanism, document, createdBy);
    }
    
    public CommunicationEvent createCommunicationEvent(String communicationEventName, CommunicationEventType communicationEventType,
            CommunicationSource communicationSource, CommunicationEventPurpose communicationEventPurpose,
            CommunicationEvent originalCommunicationEvent, CommunicationEvent parentCommunicationEvent,
            PartyContactMechanism partyContactMechanism, Document document, BasePK createdBy) {
        var communicationEvent = communicationEventFactory.create();
        var communicationEventDetail = communicationEventDetailFactory.create(
                communicationEvent, communicationEventName, communicationEventType, communicationSource, communicationEventPurpose,
                originalCommunicationEvent, parentCommunicationEvent, partyContactMechanism, document,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        communicationEvent = communicationEventFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                communicationEvent.getPrimaryKey());
        communicationEvent.setActiveDetail(communicationEventDetail);
        communicationEvent.setLastDetail(communicationEventDetail);
        communicationEvent.store();
        
        sendEvent(communicationEvent.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return communicationEvent;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CommunicationEvent */
    public CommunicationEvent getCommunicationEventByEntityInstance(EntityInstance entityInstance) {
        var pk = new CommunicationEventPK(entityInstance.getEntityUniqueId());
        var communicationEvent = communicationEventFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return communicationEvent;
    }

    private List<CommunicationEvent> getCommunicationEventsByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommunicationEvents.fields())
                    .from(CommunicationEvents)
                    .join(CommunicationEventRoles).onKey(COMMUNICATION_EVENT_ROLES_COMMUNICATION_EVENT_FK)
                    .join(CommunicationEventDetails).onKey(COMMUNICATION_EVENTS_ACTIVE_DETAIL_FK)
                    .where(CommunicationEventRoles.PARTY.eq(party.getPrimaryKey()),
                            CommunicationEventRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CommunicationEventDetails.COMMUNICATION_EVENT_NAME),
                    CommunicationEventFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommunicationEvents.fields())
                    .from(CommunicationEvents)
                    .join(CommunicationEventRoles).onKey(COMMUNICATION_EVENT_ROLES_COMMUNICATION_EVENT_FK)
                    .where(CommunicationEventRoles.PARTY.eq(party.getPrimaryKey()),
                            CommunicationEventRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return communicationEventFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommunicationEvent> getCommunicationEventsByParty(Party party) {
        return getCommunicationEventsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationEvent> getCommunicationEventsByPartyForUpdate(Party party) {
        return getCommunicationEventsByParty(party, EntityPermission.READ_WRITE);
    }
    
    public long countCommunicationEventsByParty(Party party) {
        return session.getDslContext()
                .selectCount()
                .from(CommunicationEventRoles)
                .where(CommunicationEventRoles.PARTY.eq(party.getPrimaryKey()),
                        CommunicationEventRoles.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private CommunicationEvent getCommunicationEventByName(String communicationEventName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationEvents.fields())
                .from(CommunicationEvents)
                .join(CommunicationEventDetails).onKey(COMMUNICATION_EVENTS_ACTIVE_DETAIL_FK)
                .where(CommunicationEventDetails.COMMUNICATION_EVENT_NAME.eq(communicationEventName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationEventFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommunicationEvent getCommunicationEventByName(String communicationEventName) {
        return getCommunicationEventByName(communicationEventName, EntityPermission.READ_ONLY);
    }
    
    public CommunicationEvent getCommunicationEventByNameForUpdate(String communicationEventName) {
        return getCommunicationEventByName(communicationEventName, EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventDetailValue getCommunicationEventDetailValueForUpdate(CommunicationEvent communicationEvent) {
        return communicationEvent == null? null: communicationEvent.getLastDetailForUpdate().getCommunicationEventDetailValue().clone();
    }
    
    public CommunicationEventDetailValue getCommunicationEventDetailValueByNameForUpdate(String communicationEventName) {
        return getCommunicationEventDetailValueForUpdate(getCommunicationEventByNameForUpdate(communicationEventName));
    }
    
    public CommunicationEventTransfer getCommunicationEventTransfer(UserVisit userVisit, CommunicationEvent communicationEvent) {
        return communicationEventTransferCache.getCommunicationEventTransfer(userVisit, communicationEvent);
    }
    
    public List<CommunicationEventTransfer> getCommunicationEventTransfers(UserVisit userVisit, Collection<CommunicationEvent> communicationEvents) {
        List<CommunicationEventTransfer> communicationEventTransfers = new ArrayList<>(communicationEvents.size());
        
        communicationEvents.forEach((communicationEvent) ->
                communicationEventTransfers.add(communicationEventTransferCache.getCommunicationEventTransfer(userVisit, communicationEvent))
        );
        
        return communicationEventTransfers;
    }
    
    public List<CommunicationEventTransfer> getCommunicationEventTransfersByParty(UserVisit userVisit, Party party) {
        return getCommunicationEventTransfers(userVisit, getCommunicationEventsByParty(party));
    }
    
    public void deleteCommunicationEventsByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        // TODO
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Event Roles
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationEventRoleFactory communicationEventRoleFactory;

    @Inject
    CommunicationEventRoleTransferCache communicationEventRoleTransferCache;

    public CommunicationEventRole createCommunicationEventRole(CommunicationEvent communicationEvent, Party party,
            CommunicationEventRoleType communicationEventRoleType, BasePK createdBy) {
        var communicationEventRole = communicationEventRoleFactory.create(
                communicationEvent, party, communicationEventRoleType, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(communicationEvent.getPrimaryKey(), EventTypes.MODIFY,
                communicationEventRole.getPrimaryKey(), null, createdBy);
        
        return communicationEventRole;
    }
    
    private CommunicationEventRole getCommunicationEventRole(CommunicationEvent communicationEvent, Party party,
            CommunicationEventRoleType communicationEventRoleType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationEventRoles.fields())
                .from(CommunicationEventRoles)
                .where(CommunicationEventRoles.COMMUNICATION_EVENT.eq(communicationEvent.getPrimaryKey()),
                        CommunicationEventRoles.PARTY.eq(party.getPrimaryKey()),
                        CommunicationEventRoles.COMMUNICATION_EVENT_ROLE_TYPE.eq(communicationEventRoleType.getPrimaryKey()),
                        CommunicationEventRoles.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationEventRoleFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommunicationEventRole getCommunicationEventRole(CommunicationEvent communicationEvent, Party party,
            CommunicationEventRoleType communicationEventRoleType) {
        return getCommunicationEventRole(communicationEvent, party, communicationEventRoleType, EntityPermission.READ_ONLY);
    }
    
    public CommunicationEventRole getCommunicationEventRoleForUpdate(CommunicationEvent communicationEvent, Party party,
            CommunicationEventRoleType communicationEventRoleType) {
        return getCommunicationEventRole(communicationEvent, party, communicationEventRoleType, EntityPermission.READ_WRITE);
    }
    
    private List<CommunicationEventRole> getCommunicationEventRolesByCommunicationEvent(CommunicationEvent communicationEvent,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommunicationEventRoles.fields())
                    .from(CommunicationEventRoles)
                    .join(CommunicationEventRoleTypes).onKey(COMMUNICATION_EVENT_ROLES_COMMUNICATION_EVENT_ROLE_TYPE_FK)
                    .join(Parties).onKey(COMMUNICATION_EVENT_ROLES_PARTY_FK)
                    .join(PartyDetails).onKey(PARTIES_LAST_DETAIL_FK)
                    .where(CommunicationEventRoles.COMMUNICATION_EVENT.eq(communicationEvent.getPrimaryKey()),
                            CommunicationEventRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CommunicationEventRoleTypes.SORT_ORDER, CommunicationEventRoleTypes.COMMUNICATION_EVENT_ROLE_TYPE_NAME, PartyDetails.PARTY_NAME),
                    CommunicationEventRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommunicationEventRoles.fields())
                    .from(CommunicationEventRoles)
                    .where(CommunicationEventRoles.COMMUNICATION_EVENT.eq(communicationEvent.getPrimaryKey()),
                            CommunicationEventRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return communicationEventRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommunicationEventRole> getCommunicationEventRole(CommunicationEvent communicationEvent) {
        return getCommunicationEventRolesByCommunicationEvent(communicationEvent, EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationEventRole> getCommunicationEventRoleForUpdate(CommunicationEvent communicationEvent) {
        return getCommunicationEventRolesByCommunicationEvent(communicationEvent, EntityPermission.READ_WRITE);
    }
    
    private List<CommunicationEventRole> getCommunicationEventRolesByPartyAndCommunicationEventRoleType(Party party,
            CommunicationEventRoleType communicationEventRoleType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommunicationEventRoles.fields())
                    .from(CommunicationEventRoles)
                    .join(CommunicationEvents).onKey(COMMUNICATION_EVENT_ROLES_COMMUNICATION_EVENT_FK)
                    .join(CommunicationEventDetails).onKey(COMMUNICATION_EVENTS_LAST_DETAIL_FK)
                    .where(CommunicationEventRoles.PARTY.eq(party.getPrimaryKey()),
                            CommunicationEventRoles.COMMUNICATION_EVENT_ROLE_TYPE.eq(communicationEventRoleType.getPrimaryKey()),
                            CommunicationEventRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(CommunicationEventDetails.COMMUNICATION_EVENT_NAME),
                    CommunicationEventRoleFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommunicationEventRoles.fields())
                    .from(CommunicationEventRoles)
                    .where(CommunicationEventRoles.PARTY.eq(party.getPrimaryKey()),
                            CommunicationEventRoles.COMMUNICATION_EVENT_ROLE_TYPE.eq(communicationEventRoleType.getPrimaryKey()),
                            CommunicationEventRoles.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return communicationEventRoleFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommunicationEventRole> getCommunicationEventRole(Party party,
            CommunicationEventRoleType communicationEventRoleType) {
        return getCommunicationEventRolesByPartyAndCommunicationEventRoleType(party, communicationEventRoleType,
                EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationEventRole> getCommunicationEventRoleForUpdate(Party party,
            CommunicationEventRoleType communicationEventRoleType) {
        return getCommunicationEventRolesByPartyAndCommunicationEventRoleType(party, communicationEventRoleType,
                EntityPermission.READ_WRITE);
    }
    
    public CommunicationEventRoleTransfer getCommunicationEventRoleTransfer(UserVisit userVisit,
            CommunicationEventRole communicationEventRole) {
        return communicationEventRoleTransferCache.getCommunicationEventRoleTransfer(userVisit, communicationEventRole);
    }
    
    public void deleteCommunicationEventRole(CommunicationEventRole communicationEventRole, BasePK deletedBy) {
        communicationEventRole.setThruTime(session.getStartTime());
        
        sendEvent(communicationEventRole.getCommunicationEventPK(), EventTypes.MODIFY,
                communicationEventRole.getPrimaryKey(), null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Source Types
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationSourceTypeFactory communicationSourceTypeFactory;

    @Inject
    CommunicationSourceTypeTransferCache communicationSourceTypeTransferCache;

    public CommunicationSourceType createCommunicationSourceType(String communicationSourceTypeName, Boolean isDefault,
            Integer sortOrder) {
        return communicationSourceTypeFactory.create(communicationSourceTypeName, isDefault, sortOrder);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CommunicationSourceType */
    public CommunicationSourceType getCommunicationSourceTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CommunicationSourceTypePK(entityInstance.getEntityUniqueId());

        return communicationSourceTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public CommunicationSourceType getCommunicationSourceTypeByEntityInstance(EntityInstance entityInstance) {
        return getCommunicationSourceTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CommunicationSourceType getCommunicationSourceTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCommunicationSourceTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCommunicationSourceTypes() {
        return session.getDslContext()
                .selectCount()
                .from(CommunicationSourceTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public CommunicationSourceType getCommunicationSourceTypeByName(String communicationSourceTypeName) {
        var query = session.getDslContext()
                .select(CommunicationSourceTypes.fields())
                .from(CommunicationSourceTypes)
                .where(CommunicationSourceTypes.COMMUNICATION_SOURCE_TYPE_NAME.eq(communicationSourceTypeName));

        return communicationSourceTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public List<CommunicationSourceType> getCommunicationSourceTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(CommunicationSourceTypes.fields())
                .from(CommunicationSourceTypes)
                .orderBy(CommunicationSourceTypes.SORT_ORDER, CommunicationSourceTypes.COMMUNICATION_SOURCE_TYPE_NAME),
                CommunicationSourceTypeFactory.class);

        return communicationSourceTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public CommunicationSourceTypeTransfer getCommunicationSourceTypeTransfer(UserVisit userVisit,
            CommunicationSourceType communicationSourceType) {
        return communicationSourceTypeTransferCache.getCommunicationSourceTypeTransfer(userVisit, communicationSourceType);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Source Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationSourceTypeDescriptionFactory communicationSourceTypeDescriptionFactory;

    public CommunicationSourceTypeDescription createCommunicationSourceTypeDescription(CommunicationSourceType communicationSourceType,
            Language language, String description) {
        return communicationSourceTypeDescriptionFactory.create(communicationSourceType, language, description);
    }
    
    public CommunicationSourceTypeDescription getCommunicationSourceTypeDescription(CommunicationSourceType communicationSourceType,
            Language language) {
        var query = session.getDslContext()
                .select(CommunicationSourceTypeDescriptions.fields())
                .from(CommunicationSourceTypeDescriptions)
                .where(CommunicationSourceTypeDescriptions.COMMUNICATION_SOURCE_TYPE.eq(communicationSourceType.getPrimaryKey()),
                        CommunicationSourceTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return communicationSourceTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestCommunicationSourceTypeDescription(CommunicationSourceType communicationSourceType, Language language) {
        String description;
        var communicationSourceTypeDescription = getCommunicationSourceTypeDescription(communicationSourceType, language);
        
        if(communicationSourceTypeDescription == null && !language.getIsDefault()) {
            communicationSourceTypeDescription = getCommunicationSourceTypeDescription(communicationSourceType, partyControl.getDefaultLanguage());
        }
        
        if(communicationSourceTypeDescription == null) {
            description = communicationSourceType.getCommunicationSourceTypeName();
        } else {
            description = communicationSourceTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Sources
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationSourceDetailFactory communicationSourceDetailFactory;

    @Inject
    protected CommunicationSourceFactory communicationSourceFactory;

    @Inject
    CommunicationSourceTransferCache communicationSourceTransferCache;

    public CommunicationSource createCommunicationSource(String communicationSourceName, CommunicationSourceType communicationSourceType,
            Integer sortOrder, BasePK createdBy) {
        var communicationSource = communicationSourceFactory.create();
        var communicationSourceDetail = communicationSourceDetailFactory.create(
                communicationSource, communicationSourceName, communicationSourceType, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        communicationSource = communicationSourceFactory.getEntityFromPK(EntityPermission.READ_WRITE, communicationSource.getPrimaryKey());
        communicationSource.setActiveDetail(communicationSourceDetail);
        communicationSource.setLastDetail(communicationSourceDetail);
        communicationSource.store();
        
        sendEvent(communicationSource.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return communicationSource;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CommunicationSource */
    public CommunicationSource getCommunicationSourceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new CommunicationSourcePK(entityInstance.getEntityUniqueId());

        return communicationSourceFactory.getEntityFromPK(entityPermission, pk);
    }

    public CommunicationSource getCommunicationSourceByEntityInstance(EntityInstance entityInstance) {
        return getCommunicationSourceByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public CommunicationSource getCommunicationSourceByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getCommunicationSourceByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countCommunicationSources() {
        return session.getDslContext()
                .selectCount()
                .from(CommunicationSources)
                .join(CommunicationSourceDetails).onKey(COMMUNICATION_SOURCES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countCommunicationSourcesByCommunicationSourceType(CommunicationSourceType communicationSourceType) {
        return session.getDslContext()
                .selectCount()
                .from(CommunicationSources)
                .join(CommunicationSourceDetails).onKey(COMMUNICATION_SOURCES_ACTIVE_DETAIL_FK)
                .where(CommunicationSourceDetails.COMMUNICATION_SOURCE_TYPE.eq(communicationSourceType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private CommunicationSource getCommunicationSourceByName(String communicationSourceName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationSources.fields())
                .from(CommunicationSources)
                .join(CommunicationSourceDetails).onKey(COMMUNICATION_SOURCES_ACTIVE_DETAIL_FK)
                .where(CommunicationSourceDetails.COMMUNICATION_SOURCE_NAME.eq(communicationSourceName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationSourceFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommunicationSource getCommunicationSourceByName(String communicationSourceName) {
        return getCommunicationSourceByName(communicationSourceName, EntityPermission.READ_ONLY);
    }
    
    public CommunicationSource getCommunicationSourceByNameForUpdate(String communicationSourceName) {
        return getCommunicationSourceByName(communicationSourceName, EntityPermission.READ_WRITE);
    }
    
    public CommunicationSourceDetailValue getCommunicationSourceDetailValueForUpdate(CommunicationSource communicationSource) {
        return communicationSource == null? null: communicationSource.getLastDetailForUpdate().getCommunicationSourceDetailValue().clone();
    }
    
    public CommunicationSourceDetailValue getCommunicationSourceDetailValueByNameForUpdate(String communicationSourceName) {
        return getCommunicationSourceDetailValueForUpdate(getCommunicationSourceByNameForUpdate(communicationSourceName));
    }
    
    private List<CommunicationSource> getCommunicationSources(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationSources.fields())
                .from(CommunicationSources)
                .join(CommunicationSourceDetails).onKey(COMMUNICATION_SOURCES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CommunicationSourceDetails.SORT_ORDER, CommunicationSourceDetails.COMMUNICATION_SOURCE_NAME),
                    CommunicationSourceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationSourceFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommunicationSource> getCommunicationSources() {
        return getCommunicationSources(EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationSource> getCommunicationSourcesForUpdate() {
        return getCommunicationSources(EntityPermission.READ_WRITE);
    }
    
    private List<CommunicationSource> getCommunicationSourcesByCommunicationSourceType(CommunicationSourceType communicationSourceType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationSources.fields())
                .from(CommunicationSources)
                .join(CommunicationSourceDetails).onKey(COMMUNICATION_SOURCES_ACTIVE_DETAIL_FK)
                .where(CommunicationSourceDetails.COMMUNICATION_SOURCE_TYPE.eq(communicationSourceType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(CommunicationSourceDetails.SORT_ORDER, CommunicationSourceDetails.COMMUNICATION_SOURCE_NAME),
                    CommunicationSourceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationSourceFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommunicationSource> getCommunicationSourcesByCommunicationSourceType(CommunicationSourceType communicationSourceType) {
        return getCommunicationSourcesByCommunicationSourceType(communicationSourceType, EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationSource> getCommunicationSourcesByCommunicationSourceTypeForUpdate(CommunicationSourceType communicationSourceType) {
        return getCommunicationSourcesByCommunicationSourceType(communicationSourceType, EntityPermission.READ_WRITE);
    }
    
    public CommunicationSourceTransfer getCommunicationSourceTransfer(UserVisit userVisit, CommunicationSource communicationSource) {
        return communicationSourceTransferCache.getCommunicationSourceTransfer(userVisit, communicationSource);
    }
    
    public List<CommunicationSourceTransfer> getCommunicationSourceTransfers(UserVisit userVisit, Collection<CommunicationSource> communicationSources) {
        List<CommunicationSourceTransfer> communicationSourceTransfers = new ArrayList<>(communicationSources.size());
        
        communicationSources.forEach((communicationSource) ->
                communicationSourceTransfers.add(communicationSourceTransferCache.getCommunicationSourceTransfer(userVisit, communicationSource))
        );
        
        return communicationSourceTransfers;
    }
    
    public List<CommunicationSourceTransfer> getCommunicationSourceTransfers(UserVisit userVisit) {
        return getCommunicationSourceTransfers(userVisit, getCommunicationSources());
    }
    
    public List<CommunicationSourceTransfer> getCommunicationSourceTransfersByCommunicationSourceType(UserVisit userVisit,
            CommunicationSourceType communicationSourceType) {
        return getCommunicationSourceTransfers(userVisit, getCommunicationSourcesByCommunicationSourceType(communicationSourceType));
    }
    
    public void updateCommunicationSourceFromValue(CommunicationSourceDetailValue communicationSourceDetailValue, BasePK updatedBy) {
        if(communicationSourceDetailValue.hasBeenModified()) {
            var communicationSource = communicationSourceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     communicationSourceDetailValue.getCommunicationSourcePK());
            var communicationSourceDetail = communicationSource.getActiveDetailForUpdate();
            
            communicationSourceDetail.setThruTime(session.getStartTime());
            communicationSourceDetail.store();

            var communicationSourcePK = communicationSourceDetail.getCommunicationSourcePK();
            var communicationSourceName = communicationSourceDetailValue.getCommunicationSourceName();
            var communicationSourceTypePK = communicationSourceDetail.getCommunicationSourceTypePK(); // Not updated
            var sortOrder = communicationSourceDetailValue.getSortOrder();
            
            communicationSourceDetail = communicationSourceDetailFactory.create(communicationSourcePK,
                    communicationSourceName, communicationSourceTypePK, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            communicationSource.setActiveDetail(communicationSourceDetail);
            communicationSource.setLastDetail(communicationSourceDetail);
            
            sendEvent(communicationSourcePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteCommunicationSource(CommunicationSource communicationSource, BasePK deletedBy) {
        deleteCommunicationSourceDescriptionsByCommunicationSource(communicationSource, deletedBy);
        deleteCommunicationEmailSourceByCommunicationSource(communicationSource, deletedBy);

        var communicationSourceDetail = communicationSource.getLastDetailForUpdate();
        communicationSourceDetail.setThruTime(session.getStartTime());
        communicationSource.setActiveDetail(null);
        communicationSource.store();
        
        sendEvent(communicationSource.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Source Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationSourceDescriptionFactory communicationSourceDescriptionFactory;

    @Inject
    CommunicationSourceDescriptionTransferCache communicationSourceDescriptionTransferCache;

    public CommunicationSourceDescription createCommunicationSourceDescription(CommunicationSource communicationSource,
            Language language, String description, BasePK createdBy) {
        var communicationSourceDescription = communicationSourceDescriptionFactory.create(
                communicationSource, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(communicationSource.getPrimaryKey(), EventTypes.MODIFY, communicationSourceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return communicationSourceDescription;
    }
    
    private CommunicationSourceDescription getCommunicationSourceDescription(CommunicationSource communicationSource,
            Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationSourceDescriptions.fields())
                .from(CommunicationSourceDescriptions)
                .where(CommunicationSourceDescriptions.COMMUNICATION_SOURCE.eq(communicationSource.getPrimaryKey()),
                        CommunicationSourceDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        CommunicationSourceDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationSourceDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommunicationSourceDescription getCommunicationSourceDescription(CommunicationSource communicationSource, Language language) {
        return getCommunicationSourceDescription(communicationSource, language, EntityPermission.READ_ONLY);
    }
    
    public CommunicationSourceDescription getCommunicationSourceDescriptionForUpdate(CommunicationSource communicationSource, Language language) {
        return getCommunicationSourceDescription(communicationSource, language, EntityPermission.READ_WRITE);
    }
    
    public CommunicationSourceDescriptionValue getCommunicationSourceDescriptionValue(CommunicationSourceDescription communicationSourceDescription) {
        return communicationSourceDescription == null? null: communicationSourceDescription.getCommunicationSourceDescriptionValue().clone();
    }
    
    public CommunicationSourceDescriptionValue getCommunicationSourceDescriptionValueForUpdate(CommunicationSource communicationSource, Language language) {
        return getCommunicationSourceDescriptionValue(getCommunicationSourceDescriptionForUpdate(communicationSource, language));
    }
    
    private List<CommunicationSourceDescription> getCommunicationSourceDescriptionsByCommunicationSource(CommunicationSource communicationSource, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(CommunicationSourceDescriptions.fields())
                    .from(CommunicationSourceDescriptions)
                    .join(Languages).onKey(COMMUNICATION_SOURCE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(CommunicationSourceDescriptions.COMMUNICATION_SOURCE.eq(communicationSource.getPrimaryKey()),
                            CommunicationSourceDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    CommunicationSourceDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(CommunicationSourceDescriptions.fields())
                    .from(CommunicationSourceDescriptions)
                    .where(CommunicationSourceDescriptions.COMMUNICATION_SOURCE.eq(communicationSource.getPrimaryKey()),
                            CommunicationSourceDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return communicationSourceDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<CommunicationSourceDescription> getCommunicationSourceDescriptionsByCommunicationSource(CommunicationSource communicationSource) {
        return getCommunicationSourceDescriptionsByCommunicationSource(communicationSource, EntityPermission.READ_ONLY);
    }
    
    public List<CommunicationSourceDescription> getCommunicationSourceDescriptionsByCommunicationSourceForUpdate(CommunicationSource communicationSource) {
        return getCommunicationSourceDescriptionsByCommunicationSource(communicationSource, EntityPermission.READ_WRITE);
    }
    
    public String getBestCommunicationSourceDescription(CommunicationSource communicationSource, Language language) {
        String description;
        var communicationSourceDescription = getCommunicationSourceDescription(communicationSource, language);
        
        if(communicationSourceDescription == null && !language.getIsDefault()) {
            communicationSourceDescription = getCommunicationSourceDescription(communicationSource, partyControl.getDefaultLanguage());
        }
        
        if(communicationSourceDescription == null) {
            description = communicationSource.getLastDetail().getCommunicationSourceName();
        } else {
            description = communicationSourceDescription.getDescription();
        }
        
        return description;
    }
    
    public CommunicationSourceDescriptionTransfer getCommunicationSourceDescriptionTransfer(UserVisit userVisit, CommunicationSourceDescription communicationSourceDescription) {
        return communicationSourceDescriptionTransferCache.getCommunicationSourceDescriptionTransfer(userVisit, communicationSourceDescription);
    }
    
    public List<CommunicationSourceDescriptionTransfer> getCommunicationSourceDescriptionTransfers(UserVisit userVisit, CommunicationSource communicationSource) {
        var communicationSourceDescriptions = getCommunicationSourceDescriptionsByCommunicationSource(communicationSource);
        List<CommunicationSourceDescriptionTransfer> communicationSourceDescriptionTransfers = new ArrayList<>(communicationSourceDescriptions.size());
        
        communicationSourceDescriptions.forEach((communicationSourceDescription) ->
                communicationSourceDescriptionTransfers.add(communicationSourceDescriptionTransferCache.getCommunicationSourceDescriptionTransfer(userVisit, communicationSourceDescription))
        );
        
        return communicationSourceDescriptionTransfers;
    }
    
    public void updateCommunicationSourceDescriptionFromValue(CommunicationSourceDescriptionValue communicationSourceDescriptionValue, BasePK updatedBy) {
        if(communicationSourceDescriptionValue.hasBeenModified()) {
            var communicationSourceDescription = communicationSourceDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, communicationSourceDescriptionValue.getPrimaryKey());
            
            communicationSourceDescription.setThruTime(session.getStartTime());
            communicationSourceDescription.store();

            var communicationSource = communicationSourceDescription.getCommunicationSource();
            var language = communicationSourceDescription.getLanguage();
            var description = communicationSourceDescriptionValue.getDescription();
            
            communicationSourceDescription = communicationSourceDescriptionFactory.create(communicationSource, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(communicationSource.getPrimaryKey(), EventTypes.MODIFY, communicationSourceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteCommunicationSourceDescription(CommunicationSourceDescription communicationSourceDescription, BasePK deletedBy) {
        communicationSourceDescription.setThruTime(session.getStartTime());
        
        sendEvent(communicationSourceDescription.getCommunicationSourcePK(), EventTypes.MODIFY, communicationSourceDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteCommunicationSourceDescriptionsByCommunicationSource(CommunicationSource communicationSource, BasePK deletedBy) {
        var communicationSourceDescriptions = getCommunicationSourceDescriptionsByCommunicationSourceForUpdate(communicationSource);
        
        communicationSourceDescriptions.forEach((communicationSourceDescription) -> 
                deleteCommunicationSourceDescription(communicationSourceDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Communication Email Sources
    // --------------------------------------------------------------------------------

    @Inject
    protected CommunicationEmailSourceFactory communicationEmailSourceFactory;

    @Inject
    CommunicationEmailSourceTransferCache communicationEmailSourceTransferCache;

    public CommunicationEmailSource createCommunicationEmailSource(CommunicationSource communicationSource, Server server,
            String username, String password, WorkEffortScope receiveWorkEffortScope, WorkEffortScope sendWorkEffortScope,
            Selector reviewEmployeeSelector, BasePK createdBy) {
        var initializationVector = EncryptionUtils.getInstance().generateRowInitializationVector();
        var communicationEmailSource = communicationEmailSourceFactory.create(
                communicationSource, server, username, encodeCommunicationEmailSourcePassword(initializationVector, password), receiveWorkEffortScope,
                sendWorkEffortScope, reviewEmployeeSelector, initializationVector, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(communicationSource.getPrimaryKey(), EventTypes.MODIFY, communicationEmailSource.getPrimaryKey(), null, createdBy);
        
        return communicationEmailSource;
    }
    
    private CommunicationEmailSource getCommunicationEmailSource(CommunicationSource communicationSource, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(CommunicationEmailSources.fields())
                .from(CommunicationEmailSources)
                .where(CommunicationEmailSources.COMMUNICATION_SOURCE.eq(communicationSource.getPrimaryKey()),
                        CommunicationEmailSources.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return communicationEmailSourceFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public CommunicationEmailSource getCommunicationEmailSource(CommunicationSource communicationSource) {
        return getCommunicationEmailSource(communicationSource, EntityPermission.READ_ONLY);
    }
    
    public CommunicationEmailSource getCommunicationEmailSourceForUpdate(CommunicationSource communicationSource) {
        return getCommunicationEmailSource(communicationSource, EntityPermission.READ_WRITE);
    }
    
    public CommunicationEmailSourceValue getCommunicationEmailSourceValue(CommunicationEmailSource communicationEmailSource) {
        return communicationEmailSource == null? null: communicationEmailSource.getCommunicationEmailSourceValue().clone();
    }
    
    public CommunicationEmailSourceValue getCommunicationEmailSourceValueForUpdate(CommunicationSource communicationSource) {
        return getCommunicationEmailSourceValue(getCommunicationEmailSourceForUpdate(communicationSource));
    }
    
    public CommunicationEmailSourceTransfer getCommunicationEmailSourceTransfer(UserVisit userVisit, CommunicationEmailSource communicationEmailSource) {
        return communicationEmailSourceTransferCache.getCommunicationEmailSourceTransfer(userVisit, communicationEmailSource);
    }

    public String encodeCommunicationEmailSourcePassword(CommunicationEmailSource communicationEmailSource, String password) {
        return EncryptionUtils.getInstance().encrypt(communicationEmailSourceFactory,
                communicationEmailSource.getInitializationVector(), password);
    }

    public String encodeCommunicationEmailSourcePassword(String initializationVector, String password) {
        return EncryptionUtils.getInstance().encrypt(communicationEmailSourceFactory,
                initializationVector, password);
    }

    public String decodeCommunicationEmailSourcePassword(CommunicationEmailSource communicationEmailSource) {
        return EncryptionUtils.getInstance().decrypt(communicationEmailSourceFactory,
                communicationEmailSource.getInitializationVector(), communicationEmailSource.getPassword());
    }

    public String decodeCommunicationEmailSourcePassword(String initializationVector, String password) {
        return EncryptionUtils.getInstance().decrypt(communicationEmailSourceFactory,
                initializationVector, password);
    }

    public void updateCommunicationEmailSourceFromValue(CommunicationEmailSourceValue communicationEmailSourceValue, BasePK updatedBy) {
        if(communicationEmailSourceValue.hasBeenModified()) {
            var communicationEmailSource = communicationEmailSourceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     communicationEmailSourceValue.getPrimaryKey());
            
            communicationEmailSource.setThruTime(session.getStartTime());
            communicationEmailSource.store();

            // Always generate a new initialization vector on updates.
            var decodedPassword = decodeCommunicationEmailSourcePassword(communicationEmailSourceValue.getInitializationVector(),
                    communicationEmailSourceValue.getPassword());
            var initializationVector = EncryptionUtils.getInstance().generateRowInitializationVector();

            var communicationSource = communicationEmailSource.getCommunicationSource();
            var serverPK = communicationEmailSourceValue.getServerPK();
            var username = communicationEmailSourceValue.getUsername();
            var password = encodeCommunicationEmailSourcePassword(initializationVector, decodedPassword);
            var receiveWorkEffortScopePK = communicationEmailSourceValue.getReceiveWorkEffortScopePK();
            var sendWorkEffortScopePK = communicationEmailSourceValue.getSendWorkEffortScopePK();
            var reviewEmployeeSelectorPK = communicationEmailSourceValue.getReviewEmployeeSelectorPK();

            communicationEmailSource = communicationEmailSourceFactory.create(
                    communicationSource.getPrimaryKey(), serverPK, username, password, receiveWorkEffortScopePK,
                    sendWorkEffortScopePK, reviewEmployeeSelectorPK, initializationVector, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(communicationSource.getPrimaryKey(), EventTypes.MODIFY,
                    communicationEmailSource.getPrimaryKey(), null, updatedBy);
        }
    }
    
    public void deleteCommunicationEmailSource(CommunicationEmailSource communicationEmailSource, BasePK deletedBy) {
        communicationEmailSource.setThruTime(session.getStartTime());
        
        sendEvent(communicationEmailSource.getCommunicationSource().getPrimaryKey(), EventTypes.MODIFY,
                communicationEmailSource.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteCommunicationEmailSourceByCommunicationSource(CommunicationSource communicationSource, BasePK deletedBy) {
        var communicationEmailSource = getCommunicationEmailSourceForUpdate(communicationSource);
        
        if(communicationEmailSource != null) {
            deleteCommunicationEmailSource(communicationEmailSource, deletedBy);
        }
    }
    
}

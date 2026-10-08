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

package com.echothree.model.control.track.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.track.common.choice.TrackChoicesBean;
import com.echothree.model.control.track.common.choice.TrackStatusChoicesBean;
import com.echothree.model.control.track.common.transfer.TrackDescriptionTransfer;
import com.echothree.model.control.track.common.transfer.TrackTransfer;
import com.echothree.model.control.track.common.transfer.UserVisitTrackTransfer;
import com.echothree.model.control.track.common.workflow.TrackStatusConstants;
import com.echothree.model.control.track.server.transfer.TrackDescriptionTransferCache;
import com.echothree.model.control.track.server.transfer.TrackTransferCache;
import com.echothree.model.control.track.server.transfer.UserVisitTrackTransferCache;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.track.common.pk.TrackPK;
import com.echothree.model.data.track.server.entity.Track;
import com.echothree.model.data.track.server.entity.TrackDescription;
import com.echothree.model.data.track.server.entity.UserVisitTrack;
import com.echothree.model.data.track.server.factory.TrackDescriptionFactory;
import com.echothree.model.data.track.server.factory.TrackDetailFactory;
import com.echothree.model.data.track.server.factory.TrackFactory;
import com.echothree.model.data.track.server.factory.UserVisitTrackFactory;
import com.echothree.model.data.track.server.value.TrackDescriptionValue;
import com.echothree.model.data.track.server.value.TrackDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.track.TrackForeignKeys.TRACKS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.track.TrackForeignKeys.TRACK_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.track.TrackForeignKeys.USER_VISIT_TRACKS_USER_VISIT_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.track.TrackDescriptions.TrackDescriptions;
import static com.echothree.model.jooq.server.tables.track.TrackDetails.TrackDetails;
import static com.echothree.model.jooq.server.tables.track.Tracks.Tracks;
import static com.echothree.model.jooq.server.tables.track.UserVisitTracks.UserVisitTracks;
import static com.echothree.model.jooq.server.tables.user.UserVisits.UserVisits;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class TrackControl
        extends BaseModelControl {
    
    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected UserControl userControl;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;
    
    @Inject
    Sha1Utils sha1Utils;

    /** Creates a new instance of TrackControl */
    protected TrackControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Tracks
    // --------------------------------------------------------------------------------

    @Inject
    protected TrackDetailFactory trackDetailFactory;

    @Inject
    protected TrackFactory trackFactory;

    @Inject
    private TrackTransferCache trackTransferCache;

    public Track createTrack(String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.TRACK.name());
        var trackName = sequenceGeneratorLogic.getNextSequenceValue(sequence);
        
        return createTrack(trackName, value, isDefault, sortOrder, createdBy);
    }
    
    public Track createTrack(String trackName, String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var valueSha1Hash = sha1Utils.hash(value.toLowerCase(Locale.getDefault()));
        var defaultTrack = getDefaultTrack();
        var defaultFound = defaultTrack != null;

        if(defaultFound && isDefault) {
            var defaultTrackDetailValue = getDefaultTrackDetailValueForUpdate();

            defaultTrackDetailValue.setIsDefault(false);
            updateTrackFromValue(defaultTrackDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var track = trackFactory.create();
        var trackDetail = trackDetailFactory.create(track, trackName, valueSha1Hash, value,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        track = trackFactory.getEntityFromPK(EntityPermission.READ_WRITE, track.getPrimaryKey());
        track.setActiveDetail(trackDetail);
        track.setLastDetail(trackDetail);
        track.store();

        var trackPK = track.getPrimaryKey();
        sendEvent(trackPK, EventTypes.CREATE, null, null, createdBy);

        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(trackPK);
        workflowControl.addEntityToWorkflowUsingNames(null, TrackStatusConstants.Workflow_TRACK_STATUS,
                TrackStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);
        
        return track;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Track */
    public Track getTrackByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new TrackPK(entityInstance.getEntityUniqueId());

        return trackFactory.getEntityFromPK(entityPermission, pk);
    }

    public Track getTrackByEntityInstance(EntityInstance entityInstance) {
        return getTrackByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Track getTrackByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getTrackByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countTracks() {
        return session.getDslContext()
                .selectCount()
                .from(Tracks)
                .join(TrackDetails).onKey(TRACKS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Track getTrackByName(String trackName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Tracks.fields())
                .from(Tracks)
                .join(TrackDetails).onKey(TRACKS_ACTIVE_DETAIL_FK)
                .where(TrackDetails.TRACK_NAME.eq(trackName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trackFactory.getEntityFromQuery(entityPermission, query);
    }

    public Track getTrackByName(String trackName) {
        return getTrackByName(trackName, EntityPermission.READ_ONLY);
    }

    public Track getTrackByNameForUpdate(String trackName) {
        return getTrackByName(trackName, EntityPermission.READ_WRITE);
    }

    private Track getTrackByValue(String value, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Tracks.fields())
                .from(Tracks)
                .join(TrackDetails).onKey(TRACKS_ACTIVE_DETAIL_FK)
                .where(TrackDetails.VALUE_SHA1_HASH.eq(sha1Utils.hash(value.toLowerCase(Locale.getDefault()))));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trackFactory.getEntityFromQuery(entityPermission, query);
    }

    public Track getTrackByValue(String value) {
        return getTrackByValue(value, EntityPermission.READ_ONLY);
    }

    public Track getTrackByValueForUpdate(String value) {
        return getTrackByValue(value, EntityPermission.READ_WRITE);
    }

    public TrackDetailValue getTrackDetailValueForUpdate(Track track) {
        return track == null? null: track.getLastDetailForUpdate().getTrackDetailValue().clone();
    }

    public TrackDetailValue getTrackDetailValueByNameForUpdate(String trackName) {
        return getTrackDetailValueForUpdate(getTrackByNameForUpdate(trackName));
    }

    private Track getDefaultTrack(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Tracks.fields())
                .from(Tracks)
                .join(TrackDetails).onKey(TRACKS_ACTIVE_DETAIL_FK)
                .where(TrackDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trackFactory.getEntityFromQuery(entityPermission, query);
    }

    public Track getDefaultTrack() {
        return getDefaultTrack(EntityPermission.READ_ONLY);
    }

    public Track getDefaultTrackForUpdate() {
        return getDefaultTrack(EntityPermission.READ_WRITE);
    }

    public TrackDetailValue getDefaultTrackDetailValueForUpdate() {
        return getDefaultTrackForUpdate().getLastDetailForUpdate().getTrackDetailValue().clone();
    }

    private List<Track> getTracks(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Tracks.fields())
                .from(Tracks)
                .join(TrackDetails).onKey(TRACKS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(TrackDetails.SORT_ORDER, TrackDetails.TRACK_NAME),
                    TrackFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trackFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Track> getTracks() {
        return getTracks(EntityPermission.READ_ONLY);
    }

    public List<Track> getTracksForUpdate() {
        return getTracks(EntityPermission.READ_WRITE);
    }

    public TrackStatusChoicesBean getTrackStatusChoices(String defaultTrackStatusChoice, Language language,
            boolean allowNullChoice, Track track, PartyPK partyPK) {
        var employeeStatusChoicesBean = new TrackStatusChoicesBean();
        
        if(track == null) {
            workflowControl.getWorkflowEntranceChoices(employeeStatusChoicesBean, defaultTrackStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(TrackStatusConstants.Workflow_TRACK_STATUS), partyPK);
        } else {
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(track.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(TrackStatusConstants.Workflow_TRACK_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(employeeStatusChoicesBean, defaultTrackStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return employeeStatusChoicesBean;
    }
    
    public void setTrackStatus(ExecutionErrorAccumulator eea, Party party, String employeeStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(TrackStatusConstants.Workflow_TRACK_STATUS,
                entityInstance);
        var workflowDestination = employeeStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeStatusChoice);
        
        if(workflowDestination != null || employeeStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownTrackStatusChoice.name(), employeeStatusChoice);
        }
    }
    
    public TrackTransfer getTrackTransfer(UserVisit userVisit, Track track) {
        return trackTransferCache.getTrackTransfer(userVisit, track);
    }

    public List<TrackTransfer> getTrackTransfers(UserVisit userVisit, Collection<Track> tracks) {
        List<TrackTransfer> trackTransfers = new ArrayList<>(tracks.size());

        tracks.forEach((track) ->
                trackTransfers.add(trackTransferCache.getTrackTransfer(userVisit, track))
        );

        return trackTransfers;
    }

    public List<TrackTransfer> getTrackTransfers(UserVisit userVisit) {
        var tracks = getTracks();
        List<TrackTransfer> trackTransfers = new ArrayList<>(tracks.size());

        tracks.forEach((track) ->
                trackTransfers.add(trackTransferCache.getTrackTransfer(userVisit, track))
        );

        return trackTransfers;
    }

    public TrackChoicesBean getTrackChoices(String defaultTrackChoice, Language language, boolean allowNullChoice) {
        var tracks = getTracks();
        var size = tracks.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultTrackChoice == null) {
                defaultValue = "";
            }
        }

        for(var track : tracks) {
            var trackDetail = track.getLastDetail();

            var label = getBestTrackDescription(track, language);
            var value = trackDetail.getTrackName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultTrackChoice != null && defaultTrackChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && trackDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new TrackChoicesBean(labels, values, defaultValue);
    }

    private void updateTrackFromValue(TrackDetailValue trackDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(trackDetailValue.hasBeenModified()) {
            var track = trackFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     trackDetailValue.getTrackPK());
            var trackDetail = track.getActiveDetailForUpdate();

            trackDetail.setThruTime(session.getStartTime());
            trackDetail.store();

            var trackPK = trackDetail.getTrackPK(); // Not updated
            var trackName = trackDetailValue.getTrackName();
            var value = trackDetailValue.getValue();
            var valueSha1Hash = sha1Utils.hash(value.toLowerCase(Locale.getDefault()));
            var isDefault = trackDetailValue.getIsDefault();
            var sortOrder = trackDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultTrack = getDefaultTrack();
                var defaultFound = defaultTrack != null && !defaultTrack.equals(track);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultTrackDetailValue = getDefaultTrackDetailValueForUpdate();

                    defaultTrackDetailValue.setIsDefault(false);
                    updateTrackFromValue(defaultTrackDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            trackDetail = trackDetailFactory.create(trackPK, trackName, valueSha1Hash, value, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            track.setActiveDetail(trackDetail);
            track.setLastDetail(trackDetail);

            sendEvent(trackPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateTrackFromValue(TrackDetailValue trackDetailValue, BasePK updatedBy) {
        updateTrackFromValue(trackDetailValue, true, updatedBy);
    }

    private void deleteTrack(Track track, boolean checkDefault, BasePK deletedBy) {
        var trackDetail = track.getLastDetailForUpdate();

        deleteUserVisitTracksByTrack(track);
        deleteTrackDescriptionsByTrack(track, deletedBy);

        trackDetail.setThruTime(session.getStartTime());
        track.setActiveDetail(null);
        track.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultTrack = getDefaultTrack();

            if(defaultTrack == null) {
                var tracks = getTracksForUpdate();

                if(!tracks.isEmpty()) {
                    var iter = tracks.iterator();
                    if(iter.hasNext()) {
                        defaultTrack = iter.next();
                    }
                    var trackDetailValue = Objects.requireNonNull(defaultTrack).getLastDetailForUpdate().getTrackDetailValue().clone();

                    trackDetailValue.setIsDefault(true);
                    updateTrackFromValue(trackDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(track.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteTrack(Track track, BasePK deletedBy) {
        deleteTrack(track, true, deletedBy);
    }

    private void deleteTracks(List<Track> tracks, boolean checkDefault, BasePK deletedBy) {
        tracks.forEach((track) -> deleteTrack(track, checkDefault, deletedBy));
    }

    public void deleteTracks(List<Track> tracks, BasePK deletedBy) {
        deleteTracks(tracks, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Track Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected TrackDescriptionFactory trackDescriptionFactory;

    @Inject
    private TrackDescriptionTransferCache trackDescriptionTransferCache;

    public TrackDescription createTrackDescription(Track track, Language language, String description, BasePK createdBy) {
        var trackDescription = trackDescriptionFactory.create(track, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(track.getPrimaryKey(), EventTypes.MODIFY, trackDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return trackDescription;
    }

    private TrackDescription getTrackDescription(Track track, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(TrackDescriptions.fields())
                .from(TrackDescriptions)
                .where(TrackDescriptions.TRACK.eq(track.getPrimaryKey()),
                        TrackDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        TrackDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return trackDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public TrackDescription getTrackDescription(Track track, Language language) {
        return getTrackDescription(track, language, EntityPermission.READ_ONLY);
    }

    public TrackDescription getTrackDescriptionForUpdate(Track track, Language language) {
        return getTrackDescription(track, language, EntityPermission.READ_WRITE);
    }

    public TrackDescriptionValue getTrackDescriptionValue(TrackDescription trackDescription) {
        return trackDescription == null? null: trackDescription.getTrackDescriptionValue().clone();
    }

    public TrackDescriptionValue getTrackDescriptionValueForUpdate(Track track, Language language) {
        return getTrackDescriptionValue(getTrackDescriptionForUpdate(track, language));
    }

    private List<TrackDescription> getTrackDescriptionsByTrack(Track track, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(TrackDescriptions.fields())
                    .from(TrackDescriptions)
                    .join(Languages).onKey(TRACK_DESCRIPTIONS_LANGUAGE_FK)
                    .where(TrackDescriptions.TRACK.eq(track.getPrimaryKey()),
                            TrackDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    TrackDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(TrackDescriptions.fields())
                    .from(TrackDescriptions)
                    .where(TrackDescriptions.TRACK.eq(track.getPrimaryKey()),
                            TrackDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return trackDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<TrackDescription> getTrackDescriptionsByTrack(Track track) {
        return getTrackDescriptionsByTrack(track, EntityPermission.READ_ONLY);
    }

    public List<TrackDescription> getTrackDescriptionsByTrackForUpdate(Track track) {
        return getTrackDescriptionsByTrack(track, EntityPermission.READ_WRITE);
    }

    public String getBestTrackDescription(Track track, Language language) {
        String description;
        var trackDescription = getTrackDescription(track, language);

        if(trackDescription == null && !language.getIsDefault()) {
            trackDescription = getTrackDescription(track, partyControl.getDefaultLanguage());
        }

        if(trackDescription == null) {
            description = track.getLastDetail().getTrackName();
        } else {
            description = trackDescription.getDescription();
        }

        return description;
    }

    public TrackDescriptionTransfer getTrackDescriptionTransfer(UserVisit userVisit, TrackDescription trackDescription) {
        return trackDescriptionTransferCache.getTrackDescriptionTransfer(userVisit, trackDescription);
    }

    public List<TrackDescriptionTransfer> getTrackDescriptionTransfersByTrack(UserVisit userVisit, Track track) {
        var trackDescriptions = getTrackDescriptionsByTrack(track);
        List<TrackDescriptionTransfer> trackDescriptionTransfers = new ArrayList<>(trackDescriptions.size());

        trackDescriptions.forEach((trackDescription) ->
                trackDescriptionTransfers.add(trackDescriptionTransferCache.getTrackDescriptionTransfer(userVisit, trackDescription))
        );

        return trackDescriptionTransfers;
    }

    public void updateTrackDescriptionFromValue(TrackDescriptionValue trackDescriptionValue, BasePK updatedBy) {
        if(trackDescriptionValue.hasBeenModified()) {
            var trackDescription = trackDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    trackDescriptionValue.getPrimaryKey());

            trackDescription.setThruTime(session.getStartTime());
            trackDescription.store();

            var track = trackDescription.getTrack();
            var language = trackDescription.getLanguage();
            var description = trackDescriptionValue.getDescription();

            trackDescription = trackDescriptionFactory.create(track, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(track.getPrimaryKey(), EventTypes.MODIFY, trackDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteTrackDescription(TrackDescription trackDescription, BasePK deletedBy) {
        trackDescription.setThruTime(session.getStartTime());

        sendEvent(trackDescription.getTrackPK(), EventTypes.MODIFY, trackDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteTrackDescriptionsByTrack(Track track, BasePK deletedBy) {
        var trackDescriptions = getTrackDescriptionsByTrackForUpdate(track);

        trackDescriptions.forEach((trackDescription) -> 
                deleteTrackDescription(trackDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   User Visit Tracks
    // --------------------------------------------------------------------------------

    @Inject
    protected UserVisitTrackFactory userVisitTrackFactory;

    @Inject
    private UserVisitTrackTransferCache userVisitTrackTransferCache;

    public UserVisitTrack createUserVisitTrack(UserVisit userVisit, Long time, Track track) {
        var userVisitStatus = userControl.getUserVisitStatusForUpdate(userVisit);
        Integer userVisitTrackSequence = userVisitStatus.getUserVisitTrackSequence()+ 1;
        
        userVisitStatus.setUserVisitTrackSequence(userVisitTrackSequence);
        
        return createUserVisitTrack(userVisit, userVisitTrackSequence, time, track);
    }

    public UserVisitTrack createUserVisitTrack(UserVisit userVisit, Integer userVisitTrackSequence, Long time, Track track) {
        var userVisitTrack = userVisitTrackFactory.create(userVisit, userVisitTrackSequence, time, track,
                session.getStartTime(), Session.MAX_TIME);

        return userVisitTrack;
    }
    
    public Integer getMinimumUserVisitTrackSequence(UserVisit userVisit) {
        return session.getDslContext()
                .select(org.jooq.impl.DSL.min(UserVisitTracks.USER_VISIT_TRACK_SEQUENCE))
                .from(UserVisitTracks)
                .where(UserVisitTracks.USER_VISIT.eq(userVisit.getPrimaryKey()),
                        UserVisitTracks.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOne(0, Integer.class);
    }

    private UserVisitTrack getUserVisitTrack(UserVisit userVisit, Integer userVisitTrackSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserVisitTracks.fields())
                .from(UserVisitTracks)
                .where(UserVisitTracks.USER_VISIT.eq(userVisit.getPrimaryKey()),
                        UserVisitTracks.USER_VISIT_TRACK_SEQUENCE.eq(userVisitTrackSequence),
                        UserVisitTracks.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userVisitTrackFactory.getEntityFromQuery(entityPermission, query);
    }

    public UserVisitTrack getUserVisitTrack(UserVisit userVisit, Integer userVisitTrackSequence) {
        return getUserVisitTrack(userVisit, userVisitTrackSequence, EntityPermission.READ_ONLY);
    }

    public UserVisitTrack getUserVisitTrackForUpdate(UserVisit userVisit, Integer userVisitTrackSequence) {
        return getUserVisitTrack(userVisit, userVisitTrackSequence, EntityPermission.READ_WRITE);
    }

    private List<UserVisitTrack> getUserVisitTracksByUserVisit(UserVisit userVisit, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserVisitTracks.fields())
                .from(UserVisitTracks)
                .where(UserVisitTracks.USER_VISIT.eq(userVisit.getPrimaryKey()),
                        UserVisitTracks.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UserVisitTracks.USER_VISIT_TRACK_SEQUENCE),
                    UserVisitTrackFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userVisitTrackFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<UserVisitTrack> getUserVisitTracksByUserVisit(UserVisit userVisit) {
        return getUserVisitTracksByUserVisit(userVisit, EntityPermission.READ_ONLY);
    }

    public List<UserVisitTrack> getUserVisitTracksByUserVisitForUpdate(UserVisit userVisit) {
        return getUserVisitTracksByUserVisit(userVisit, EntityPermission.READ_WRITE);
    }

    private List<UserVisitTrack> getUserVisitTracksByTrack(Track track, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UserVisitTracks.fields())
                .from(UserVisitTracks)
                .where(UserVisitTracks.TRACK.eq(track.getPrimaryKey()),
                        UserVisitTracks.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UserVisitTracks.fields())
                    .from(UserVisitTracks)
                    .join(UserVisits).onKey(USER_VISIT_TRACKS_USER_VISIT_FK)
                    .where(UserVisitTracks.TRACK.eq(track.getPrimaryKey()),
                            UserVisitTracks.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UserVisits.FROM_TIME, UserVisitTracks.USER_VISIT_TRACK_SEQUENCE),
                    UserVisitTrackFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return userVisitTrackFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<UserVisitTrack> getUserVisitTracksByTrackForUpdate(Track track) {
        return getUserVisitTracksByTrack(track, EntityPermission.READ_WRITE);
    }

    public UserVisitTrackTransfer getUserVisitTrackTransfer(UserVisit userVisit, UserVisitTrack userVisitTrack) {
        return userVisitTrackTransferCache.getUserVisitTrackTransfer(userVisit, userVisitTrack);
    }

    public List<UserVisitTrackTransfer> getUserVisitTrackTransfers(UserVisit userVisit, Collection<UserVisitTrack> userVisitTracks) {
        var userVisitTrackTransfers = new ArrayList<UserVisitTrackTransfer>(userVisitTracks.size());

        userVisitTracks.forEach((userVisitTrack) ->
                userVisitTrackTransfers.add(userVisitTrackTransferCache.getUserVisitTrackTransfer(userVisit, userVisitTrack))
        );

        return userVisitTrackTransfers;
    }
    
    public List<UserVisitTrackTransfer> getUserVisitTrackTransfersByUserVisit(UserVisit userVisit, UserVisit userVisitEntity) {
        return getUserVisitTrackTransfers(userVisit, getUserVisitTracksByUserVisit(userVisitEntity));
    }
    
    public void deleteUserVisitTrack(UserVisitTrack userVisitTrack) {
        userVisitTrack.setThruTime(session.getStartTime());
        userVisitTrack.store();
    }
    
    public void deleteUserVisitTracks(List<UserVisitTrack> userVisitTracks) {
        userVisitTracks.forEach((userVisitTrack) -> {
            deleteUserVisitTrack(userVisitTrack);
        });
    }
    
    public void deleteUserVisitTracksByUserVisit(UserVisit userVisit) {
        deleteUserVisitTracks(getUserVisitTracksByUserVisitForUpdate(userVisit));
    }
    
    public void deleteUserVisitTracksByTrack(Track track) {
        deleteUserVisitTracks(getUserVisitTracksByTrackForUpdate(track));
    }
    
    public void removeUserVisitTrack(UserVisitTrack userVisitTrack) {
        userVisitTrack.remove();
    }
    
    public void removeUserVisitTracks(List<UserVisitTrack> userVisitTracks) {
        userVisitTracks.forEach((userVisitTrack) -> {
            removeUserVisitTrack(userVisitTrack);
        });
    }
    
    public void removeUserVisitTracksByUserVisit(UserVisit userVisit) {
        removeUserVisitTracks(getUserVisitTracksByUserVisitForUpdate(userVisit));
    }
    
    public void removeUserVisitTracksByTrack(Track track) {
        removeUserVisitTracks(getUserVisitTracksByTrackForUpdate(track));
    }
    
}

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

package com.echothree.model.control.track.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.track.common.choice.TrackChoicesBean;
import com.echothree.model.control.track.common.choice.TrackStatusChoicesBean;
import com.echothree.model.control.track.common.transfer.TrackDescriptionTransfer;
import com.echothree.model.control.track.common.transfer.TrackTransfer;
import com.echothree.model.control.track.common.transfer.UserVisitTrackTransfer;
import com.echothree.model.control.track.common.workflow.TrackStatusConstants;
import com.echothree.model.control.track.server.transfer.TrackTransferCaches;
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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TrackControl
        extends BaseModelControl {
    
    /** Creates a new instance of TrackControl */
    protected TrackControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Track Transfer Caches
    // --------------------------------------------------------------------------------
    
    private TrackTransferCaches trackTransferCaches;
    
    public TrackTransferCaches getTrackTransferCaches() {
        if(trackTransferCaches == null) {
            trackTransferCaches = new TrackTransferCaches(this);
        }
        
        return trackTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Tracks
    // --------------------------------------------------------------------------------

    public Track createTrack(String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.TRACK.name());
        var trackName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createTrack(trackName, value, isDefault, sortOrder, createdBy);
    }
    
    public Track createTrack(String trackName, String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
        var defaultTrack = getDefaultTrack();
        var defaultFound = defaultTrack != null;

        if(defaultFound && isDefault) {
            var defaultTrackDetailValue = getDefaultTrackDetailValueForUpdate();

            defaultTrackDetailValue.setIsDefault(false);
            updateTrackFromValue(defaultTrackDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var track = TrackFactory.getInstance().create();
        var trackDetail = TrackDetailFactory.getInstance().create(track, trackName, valueSha1Hash, value,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        track = TrackFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, track.getPrimaryKey());
        track.setActiveDetail(trackDetail);
        track.setLastDetail(trackDetail);
        track.store();

        var trackPK = track.getPrimaryKey();
        sendEvent(trackPK, EventTypes.CREATE, null, null, createdBy);

        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(trackPK);
        workflowControl.addEntityToWorkflowUsingNames(null, TrackStatusConstants.Workflow_TRACK_STATUS,
                TrackStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);
        
        return track;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Track */
    public Track getTrackByEntityInstance(EntityInstance entityInstance) {
        var pk = new TrackPK(entityInstance.getEntityUniqueId());
        var track = TrackFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return track;
    }

    private static final Map<EntityPermission, String> getTrackByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM tracks, trackdetails " +
                "WHERE trk_activedetailid = trkdt_trackdetailid " +
                "AND trkdt_trackname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM tracks, trackdetails " +
                "WHERE trk_activedetailid = trkdt_trackdetailid " +
                "AND trkdt_trackname = ? " +
                "FOR UPDATE");
        getTrackByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Track getTrackByName(String trackName, EntityPermission entityPermission) {
        return TrackFactory.getInstance().getEntityFromQuery(entityPermission, getTrackByNameQueries, trackName);
    }

    public Track getTrackByName(String trackName) {
        return getTrackByName(trackName, EntityPermission.READ_ONLY);
    }

    public Track getTrackByNameForUpdate(String trackName) {
        return getTrackByName(trackName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getTrackByValueQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM tracks, trackdetails " +
                "WHERE trk_activedetailid = trkdt_trackdetailid " +
                "AND trkdt_valuesha1hash = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM tracks, trackdetails " +
                "WHERE trk_activedetailid = trkdt_trackdetailid " +
                "AND trkdt_valuesha1hash = ? " +
                "FOR UPDATE");
        getTrackByValueQueries = Collections.unmodifiableMap(queryMap);
    }

    private Track getTrackByValue(String value, EntityPermission entityPermission) {
        return TrackFactory.getInstance().getEntityFromQuery(entityPermission, getTrackByValueQueries, 
                Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault())));
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

    private static final Map<EntityPermission, String> getDefaultTrackQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM tracks, trackdetails " +
                "WHERE trk_activedetailid = trkdt_trackdetailid " +
                "AND trkdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM tracks, trackdetails " +
                "WHERE trk_activedetailid = trkdt_trackdetailid " +
                "AND trkdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultTrackQueries = Collections.unmodifiableMap(queryMap);
    }

    private Track getDefaultTrack(EntityPermission entityPermission) {
        return TrackFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultTrackQueries);
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

    private static final Map<EntityPermission, String> getTracksQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM tracks, trackdetails " +
                "WHERE trk_activedetailid = trkdt_trackdetailid " +
                "ORDER BY trkdt_sortorder, trkdt_trackname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM tracks, trackdetails " +
                "WHERE trk_activedetailid = trkdt_trackdetailid " +
                "FOR UPDATE");
        getTracksQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Track> getTracks(EntityPermission entityPermission) {
        return TrackFactory.getInstance().getEntitiesFromQuery(entityPermission, getTracksQueries);
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
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
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
        return getTrackTransferCaches().getTrackTransferCache().getTrackTransfer(userVisit, track);
    }

    public List<TrackTransfer> getTrackTransfers(UserVisit userVisit) {
        var tracks = getTracks();
        List<TrackTransfer> trackTransfers = new ArrayList<>(tracks.size());
        var trackTransferCache = getTrackTransferCaches(userVisit).getTrackTransferCache();

        tracks.forEach((track) ->
                trackTransfers.add(trackTransferCache.getTrackTransfer(track))
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
            var track = TrackFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     trackDetailValue.getTrackPK());
            var trackDetail = track.getActiveDetailForUpdate();

            trackDetail.setThruTime(session.START_TIME_LONG);
            trackDetail.store();

            var trackPK = trackDetail.getTrackPK(); // Not updated
            var trackName = trackDetailValue.getTrackName();
            var value = trackDetailValue.getValue();
            var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
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

            trackDetail = TrackDetailFactory.getInstance().create(trackPK, trackName, valueSha1Hash, value, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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

        trackDetail.setThruTime(session.START_TIME_LONG);
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

    public TrackDescription createTrackDescription(Track track, Language language, String description, BasePK createdBy) {
        var trackDescription = TrackDescriptionFactory.getInstance().create(track, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(track.getPrimaryKey(), EventTypes.MODIFY, trackDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return trackDescription;
    }

    private static final Map<EntityPermission, String> getTrackDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trackdescriptions " +
                "WHERE trkd_trk_trackid = ? AND trkd_lang_languageid = ? AND trkd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trackdescriptions " +
                "WHERE trkd_trk_trackid = ? AND trkd_lang_languageid = ? AND trkd_thrutime = ? " +
                "FOR UPDATE");
        getTrackDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private TrackDescription getTrackDescription(Track track, Language language, EntityPermission entityPermission) {
        return TrackDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getTrackDescriptionQueries,
                track, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getTrackDescriptionsByTrackQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM trackdescriptions, languages " +
                "WHERE trkd_trk_trackid = ? AND trkd_thrutime = ? AND trkd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM trackdescriptions " +
                "WHERE trkd_trk_trackid = ? AND trkd_thrutime = ? " +
                "FOR UPDATE");
        getTrackDescriptionsByTrackQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<TrackDescription> getTrackDescriptionsByTrack(Track track, EntityPermission entityPermission) {
        return TrackDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getTrackDescriptionsByTrackQueries,
                track, Session.MAX_TIME);
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
        return getTrackTransferCaches().getTrackDescriptionTransferCache().getTrackDescriptionTransfer(userVisit, trackDescription);
    }

    public List<TrackDescriptionTransfer> getTrackDescriptionTransfersByTrack(UserVisit userVisit, Track track) {
        var trackDescriptions = getTrackDescriptionsByTrack(track);
        List<TrackDescriptionTransfer> trackDescriptionTransfers = new ArrayList<>(trackDescriptions.size());
        var trackDescriptionTransferCache = getTrackTransferCaches(userVisit).getTrackDescriptionTransferCache();

        trackDescriptions.forEach((trackDescription) ->
                trackDescriptionTransfers.add(trackDescriptionTransferCache.getTrackDescriptionTransfer(trackDescription))
        );

        return trackDescriptionTransfers;
    }

    public void updateTrackDescriptionFromValue(TrackDescriptionValue trackDescriptionValue, BasePK updatedBy) {
        if(trackDescriptionValue.hasBeenModified()) {
            var trackDescription = TrackDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    trackDescriptionValue.getPrimaryKey());

            trackDescription.setThruTime(session.START_TIME_LONG);
            trackDescription.store();

            var track = trackDescription.getTrack();
            var language = trackDescription.getLanguage();
            var description = trackDescriptionValue.getDescription();

            trackDescription = TrackDescriptionFactory.getInstance().create(track, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(track.getPrimaryKey(), EventTypes.MODIFY, trackDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteTrackDescription(TrackDescription trackDescription, BasePK deletedBy) {
        trackDescription.setThruTime(session.START_TIME_LONG);

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

    public UserVisitTrack createUserVisitTrack(UserVisit userVisit, Long time, Track track) {
        var userControl = Session.getModelController(UserControl.class);
        var userVisitStatus = userControl.getUserVisitStatusForUpdate(userVisit);
        Integer userVisitTrackSequence = userVisitStatus.getUserVisitTrackSequence()+ 1;
        
        userVisitStatus.setUserVisitTrackSequence(userVisitTrackSequence);
        
        return createUserVisitTrack(userVisit, userVisitTrackSequence, time, track);
    }

    public UserVisitTrack createUserVisitTrack(UserVisit userVisit, Integer userVisitTrackSequence, Long time, Track track) {
        var userVisitTrack = UserVisitTrackFactory.getInstance().create(userVisit, userVisitTrackSequence, time, track,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        return userVisitTrack;
    }
    
    public Integer getMinimumUserVisitTrackSequence(UserVisit userVisit) {
        return session.queryForInteger(
                "SELECT MIN(uvistrk_uservisittracksequence) " +
                "FROM uservisittracks " +
                "WHERE uvistrk_uvis_uservisitid = ? AND uvistrk_thrutime = ?",
                userVisit, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getUserVisitTrackQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM uservisittracks " +
                "WHERE uvistrk_uvis_uservisitid = ? AND uvistrk_uservisittracksequence = ? AND uvistrk_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM uservisittracks " +
                "WHERE uvistrk_uvis_uservisitid = ? AND uvistrk_uservisittracksequence = ? AND uvistrk_thrutime = ? " +
                "FOR UPDATE");
        getUserVisitTrackQueries = Collections.unmodifiableMap(queryMap);
    }

    private UserVisitTrack getUserVisitTrack(UserVisit userVisit, Integer userVisitTrackSequence, EntityPermission entityPermission) {
        return UserVisitTrackFactory.getInstance().getEntityFromQuery(entityPermission, getUserVisitTrackQueries,
                userVisit, userVisitTrackSequence, Session.MAX_TIME);
    }

    public UserVisitTrack getUserVisitTrack(UserVisit userVisit, Integer userVisitTrackSequence) {
        return getUserVisitTrack(userVisit, userVisitTrackSequence, EntityPermission.READ_ONLY);
    }

    public UserVisitTrack getUserVisitTrackForUpdate(UserVisit userVisit, Integer userVisitTrackSequence) {
        return getUserVisitTrack(userVisit, userVisitTrackSequence, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserVisitTracksByUserVisitQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM uservisittracks " +
                "WHERE uvistrk_uvis_uservisitid = ? AND uvistrk_thrutime = ? " +
                "ORDER BY uvistrk_uservisittracksequence");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM uservisittracks " +
                "WHERE uvistrk_uvis_uservisitid = ? AND uvistrk_thrutime = ? " +
                "FOR UPDATE");
        getUserVisitTracksByUserVisitQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitTrack> getUserVisitTracksByUserVisit(UserVisit userVisit, EntityPermission entityPermission) {
        return UserVisitTrackFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitTracksByUserVisitQueries,
                userVisit, Session.MAX_TIME);
    }

    public List<UserVisitTrack> getUserVisitTracksByUserVisit(UserVisit userVisit) {
        return getUserVisitTracksByUserVisit(userVisit, EntityPermission.READ_ONLY);
    }

    public List<UserVisitTrack> getUserVisitTracksByUserVisitForUpdate(UserVisit userVisit) {
        return getUserVisitTracksByUserVisit(userVisit, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserVisitTracksByTrackQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM uservisittracks " +
                "WHERE uvistrk_trk_trackid = ? AND uvistrk_thrutime = ? " +
                "FOR UPDATE");
        getUserVisitTracksByTrackQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitTrack> getUserVisitTracksByTrack(Track track, EntityPermission entityPermission) {
        return UserVisitTrackFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitTracksByTrackQueries,
                track, Session.MAX_TIME);
    }

    public List<UserVisitTrack> getUserVisitTracksByTrackForUpdate(Track track) {
        return getUserVisitTracksByTrack(track, EntityPermission.READ_WRITE);
    }

    public UserVisitTrackTransfer getUserVisitTrackTransfer(UserVisit userVisit, UserVisitTrack userVisitTrack) {
        return getTrackTransferCaches().getUserVisitTrackTransferCache().getUserVisitTrackTransfer(userVisit, userVisitTrack);
    }

    public List<UserVisitTrackTransfer> getUserVisitTrackTransfers(UserVisit userVisit, Collection<UserVisitTrack> userVisitTracks) {
        var userVisitTrackTransfers = new ArrayList<UserVisitTrackTransfer>(userVisitTracks.size());
        var userVisitTrackTransferCache = getTrackTransferCaches(userVisit).getUserVisitTrackTransferCache();

        userVisitTracks.forEach((userVisitTrack) ->
                userVisitTrackTransfers.add(userVisitTrackTransferCache.getUserVisitTrackTransfer(userVisitTrack))
        );

        return userVisitTrackTransfers;
    }
    
    public List<UserVisitTrackTransfer> getUserVisitTrackTransfersByUserVisit(UserVisit userVisit, UserVisit userVisitEntity) {
        return getUserVisitTrackTransfers(userVisit, getUserVisitTracksByUserVisit(userVisitEntity));
    }
    
    public void deleteUserVisitTrack(UserVisitTrack userVisitTrack) {
        userVisitTrack.setThruTime(session.START_TIME_LONG);
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

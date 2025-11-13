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

package com.echothree.model.control.track.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.track.common.TrackOptions;
import com.echothree.model.control.track.common.transfer.TrackTransfer;
import com.echothree.model.control.track.common.workflow.TrackStatusConstants;
import com.echothree.model.control.track.server.control.TrackControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.track.server.entity.Track;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TrackTransferCache
        extends BaseTrackTransferCache<Track, TrackTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of TrackTransferCache */
    public TrackTransferCache(TrackControl trackControl) {
        super(trackControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(TrackOptions.TrackIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public TrackTransfer getTrackTransfer(UserVisit userVisit, Track track) {
        var trackTransfer = get(track);

        if(trackTransfer == null) {
            var trackDetail = track.getLastDetail();
            var trackName = trackDetail.getTrackName();
            var valueSha1Hash = trackDetail.getValueSha1Hash();
            var value = trackDetail.getValue();
            var isDefault = trackDetail.getIsDefault();
            var sortOrder = trackDetail.getSortOrder();
            var description = trackControl.getBestTrackDescription(track, getLanguage(userVisit));

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(track.getPrimaryKey());
            var trackStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    TrackStatusConstants.Workflow_TRACK_STATUS, entityInstance);
            
            trackTransfer = new TrackTransfer(trackName, valueSha1Hash, value, isDefault, sortOrder, description,
                    trackStatusTransfer);
            put(userVisit, track, trackTransfer);
        }

        return trackTransfer;
    }

}

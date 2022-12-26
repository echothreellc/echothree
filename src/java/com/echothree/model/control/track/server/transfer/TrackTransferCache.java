// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.track.common.TrackOptions;
import com.echothree.model.control.track.common.transfer.TrackTransfer;
import com.echothree.model.control.track.server.control.TrackControl;
import com.echothree.model.control.track.common.workflow.TrackStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.track.server.entity.Track;
import com.echothree.model.data.track.server.entity.TrackDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class TrackTransferCache
        extends BaseTrackTransferCache<Track, TrackTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of TrackTransferCache */
    public TrackTransferCache(UserVisit userVisit, TrackControl trackControl) {
        super(userVisit, trackControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(TrackOptions.TrackIncludeKey));
            setIncludeGuid(options.contains(TrackOptions.TrackIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public TrackTransfer getTrackTransfer(Track track) {
        TrackTransfer trackTransfer = get(track);

        if(trackTransfer == null) {
            TrackDetail trackDetail = track.getLastDetail();
            String trackName = trackDetail.getTrackName();
            String valueSha1Hash = trackDetail.getValueSha1Hash();
            String value = trackDetail.getValue();
            Boolean isDefault = trackDetail.getIsDefault();
            Integer sortOrder = trackDetail.getSortOrder();
            String description = trackControl.getBestTrackDescription(track, getLanguage());

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(track.getPrimaryKey());
            WorkflowEntityStatusTransfer trackStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    TrackStatusConstants.Workflow_TRACK_STATUS, entityInstance);
            
            trackTransfer = new TrackTransfer(trackName, valueSha1Hash, value, isDefault, sortOrder, description,
                    trackStatusTransfer);
            put(track, trackTransfer);
        }

        return trackTransfer;
    }

}

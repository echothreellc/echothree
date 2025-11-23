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

import com.echothree.model.control.track.common.transfer.UserVisitTrackTransfer;
import com.echothree.model.control.track.server.control.TrackControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.track.server.entity.UserVisitTrack;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UserVisitTrackTransferCache
        extends BaseTrackTransferCache<UserVisitTrack, UserVisitTrackTransfer> {

    TrackControl trackControl = Session.getModelController(TrackControl.class);
    UserControl userControl = Session.getModelController(UserControl.class);
    
    /** Creates a new instance of UserVisitTrackTransferCache */
    protected UserVisitTrackTransferCache() {
        super();
    }

    public UserVisitTrackTransfer getUserVisitTrackTransfer(UserVisit userVisit, UserVisitTrack userVisitTrack) {
        var userVisitTrackTransfer = get(userVisitTrack);

        if(userVisitTrackTransfer == null) {
            var userVisitTransfer = userControl.getUserVisitTransfer(userVisit, userVisit);
            var userVisitTrackSequence = userVisitTrack.getUserVisitTrackSequence();
            var unformattedTime = userVisitTrack.getTime();
            var time = formatTypicalDateTime(userVisit, unformattedTime);
            var track = userVisitTrack.getTrack();
            var trackTransfer = track == null ? null : trackControl.getTrackTransfer(userVisit, track);

            userVisitTrackTransfer = new UserVisitTrackTransfer(userVisitTransfer, userVisitTrackSequence, unformattedTime, time, trackTransfer);
            put(userVisit, userVisitTrack, userVisitTrackTransfer);
        }

        return userVisitTrackTransfer;
    }

}

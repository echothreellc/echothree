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

import com.echothree.model.control.track.common.transfer.TrackDescriptionTransfer;
import com.echothree.model.control.track.server.control.TrackControl;
import com.echothree.model.data.track.server.entity.TrackDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TrackDescriptionTransferCache
        extends BaseTrackDescriptionTransferCache<TrackDescription, TrackDescriptionTransfer> {

    TrackControl trackControl = Session.getModelController(TrackControl.class);

    /** Creates a new instance of TrackDescriptionTransferCache */
    public TrackDescriptionTransferCache() {
        super();
    }
    
    public TrackDescriptionTransfer getTrackDescriptionTransfer(UserVisit userVisit, TrackDescription trackDescription) {
        var trackDescriptionTransfer = get(trackDescription);
        
        if(trackDescriptionTransfer == null) {
            var trackTransfer = trackControl.getTrackTransfer(userVisit, trackDescription.getTrack());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, trackDescription.getLanguage());
            
            trackDescriptionTransfer = new TrackDescriptionTransfer(languageTransfer, trackTransfer, trackDescription.getDescription());
            put(userVisit, trackDescription, trackDescriptionTransfer);
        }
        return trackDescriptionTransfer;
    }
    
}

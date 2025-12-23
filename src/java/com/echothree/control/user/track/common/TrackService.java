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

package com.echothree.control.user.track.common;

import com.echothree.control.user.track.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface TrackService
        extends TrackForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Tracks
    // --------------------------------------------------------------------------------
    
    CommandResult createTrack(UserVisitPK userVisitPK, CreateTrackForm form);
    
    CommandResult getTrackChoices(UserVisitPK userVisitPK, GetTrackChoicesForm form);
    
    CommandResult getTrack(UserVisitPK userVisitPK, GetTrackForm form);
    
    CommandResult getTracks(UserVisitPK userVisitPK, GetTracksForm form);
    
    CommandResult setDefaultTrack(UserVisitPK userVisitPK, SetDefaultTrackForm form);
    
    CommandResult getTrackStatusChoices(UserVisitPK userVisitPK, GetTrackStatusChoicesForm form);
    
    CommandResult setTrackStatus(UserVisitPK userVisitPK, SetTrackStatusForm form);
    
    CommandResult editTrack(UserVisitPK userVisitPK, EditTrackForm form);
    
    CommandResult deleteTrack(UserVisitPK userVisitPK, DeleteTrackForm form);
    
    // --------------------------------------------------------------------------------
    //   Track Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createTrackDescription(UserVisitPK userVisitPK, CreateTrackDescriptionForm form);
    
    CommandResult getTrackDescription(UserVisitPK userVisitPK, GetTrackDescriptionForm form);
    
    CommandResult getTrackDescriptions(UserVisitPK userVisitPK, GetTrackDescriptionsForm form);
    
    CommandResult editTrackDescription(UserVisitPK userVisitPK, EditTrackDescriptionForm form);
    
    CommandResult deleteTrackDescription(UserVisitPK userVisitPK, DeleteTrackDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   User Visit Tracks
    // --------------------------------------------------------------------------------
    
    CommandResult createUserVisitTrack(UserVisitPK userVisitPK, CreateUserVisitTrackForm form);
    
}

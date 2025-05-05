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

package com.echothree.control.user.track.server;

import com.echothree.control.user.track.common.TrackRemote;
import com.echothree.control.user.track.common.form.*;
import com.echothree.control.user.track.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class TrackBean
        extends TrackFormsImpl
        implements TrackRemote, TrackLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "TrackBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Tracks
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrack(UserVisitPK userVisitPK, CreateTrackForm form) {
        return new CreateTrackCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrackChoices(UserVisitPK userVisitPK, GetTrackChoicesForm form) {
        return new GetTrackChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrack(UserVisitPK userVisitPK, GetTrackForm form) {
        return new GetTrackCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTracks(UserVisitPK userVisitPK, GetTracksForm form) {
        return new GetTracksCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTrack(UserVisitPK userVisitPK, SetDefaultTrackForm form) {
        return new SetDefaultTrackCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrackStatusChoices(UserVisitPK userVisitPK, GetTrackStatusChoicesForm form) {
        return new GetTrackStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setTrackStatus(UserVisitPK userVisitPK, SetTrackStatusForm form) {
        return new SetTrackStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrack(UserVisitPK userVisitPK, EditTrackForm form) {
        return new EditTrackCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrack(UserVisitPK userVisitPK, DeleteTrackForm form) {
        return new DeleteTrackCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Track Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrackDescription(UserVisitPK userVisitPK, CreateTrackDescriptionForm form) {
        return new CreateTrackDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrackDescription(UserVisitPK userVisitPK, GetTrackDescriptionForm form) {
        return new GetTrackDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrackDescriptions(UserVisitPK userVisitPK, GetTrackDescriptionsForm form) {
        return new GetTrackDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrackDescription(UserVisitPK userVisitPK, EditTrackDescriptionForm form) {
        return new EditTrackDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrackDescription(UserVisitPK userVisitPK, DeleteTrackDescriptionForm form) {
        return new DeleteTrackDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Tracks
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserVisitTrack(UserVisitPK userVisitPK, CreateUserVisitTrackForm form) {
        return new CreateUserVisitTrackCommand().run(userVisitPK, form);
    }
    
}

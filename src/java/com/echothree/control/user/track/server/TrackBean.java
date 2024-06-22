// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
        return new CreateTrackCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrackChoices(UserVisitPK userVisitPK, GetTrackChoicesForm form) {
        return new GetTrackChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrack(UserVisitPK userVisitPK, GetTrackForm form) {
        return new GetTrackCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTracks(UserVisitPK userVisitPK, GetTracksForm form) {
        return new GetTracksCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultTrack(UserVisitPK userVisitPK, SetDefaultTrackForm form) {
        return new SetDefaultTrackCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrackStatusChoices(UserVisitPK userVisitPK, GetTrackStatusChoicesForm form) {
        return new GetTrackStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setTrackStatus(UserVisitPK userVisitPK, SetTrackStatusForm form) {
        return new SetTrackStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrack(UserVisitPK userVisitPK, EditTrackForm form) {
        return new EditTrackCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrack(UserVisitPK userVisitPK, DeleteTrackForm form) {
        return new DeleteTrackCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Track Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrackDescription(UserVisitPK userVisitPK, CreateTrackDescriptionForm form) {
        return new CreateTrackDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrackDescription(UserVisitPK userVisitPK, GetTrackDescriptionForm form) {
        return new GetTrackDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTrackDescriptions(UserVisitPK userVisitPK, GetTrackDescriptionsForm form) {
        return new GetTrackDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTrackDescription(UserVisitPK userVisitPK, EditTrackDescriptionForm form) {
        return new EditTrackDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTrackDescription(UserVisitPK userVisitPK, DeleteTrackDescriptionForm form) {
        return new DeleteTrackDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Tracks
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserVisitTrack(UserVisitPK userVisitPK, CreateUserVisitTrackForm form) {
        return new CreateUserVisitTrackCommand(userVisitPK, form).run();
    }
    
}

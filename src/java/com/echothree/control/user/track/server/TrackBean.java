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

package com.echothree.control.user.track.server;

import com.echothree.control.user.track.common.TrackRemote;
import com.echothree.control.user.track.common.form.*;
import com.echothree.control.user.track.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateTrackCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrackChoices(UserVisitPK userVisitPK, GetTrackChoicesForm form) {
        return CDI.current().select(GetTrackChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrack(UserVisitPK userVisitPK, GetTrackForm form) {
        return CDI.current().select(GetTrackCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTracks(UserVisitPK userVisitPK, GetTracksForm form) {
        return CDI.current().select(GetTracksCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTrack(UserVisitPK userVisitPK, SetDefaultTrackForm form) {
        return CDI.current().select(SetDefaultTrackCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrackStatusChoices(UserVisitPK userVisitPK, GetTrackStatusChoicesForm form) {
        return CDI.current().select(GetTrackStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setTrackStatus(UserVisitPK userVisitPK, SetTrackStatusForm form) {
        return CDI.current().select(SetTrackStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrack(UserVisitPK userVisitPK, EditTrackForm form) {
        return CDI.current().select(EditTrackCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrack(UserVisitPK userVisitPK, DeleteTrackForm form) {
        return CDI.current().select(DeleteTrackCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Track Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTrackDescription(UserVisitPK userVisitPK, CreateTrackDescriptionForm form) {
        return CDI.current().select(CreateTrackDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrackDescription(UserVisitPK userVisitPK, GetTrackDescriptionForm form) {
        return CDI.current().select(GetTrackDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTrackDescriptions(UserVisitPK userVisitPK, GetTrackDescriptionsForm form) {
        return CDI.current().select(GetTrackDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTrackDescription(UserVisitPK userVisitPK, EditTrackDescriptionForm form) {
        return CDI.current().select(EditTrackDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTrackDescription(UserVisitPK userVisitPK, DeleteTrackDescriptionForm form) {
        return CDI.current().select(DeleteTrackDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   User Visit Tracks
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createUserVisitTrack(UserVisitPK userVisitPK, CreateUserVisitTrackForm form) {
        return CDI.current().select(CreateUserVisitTrackCommand.class).get().run(userVisitPK, form);
    }
    
}

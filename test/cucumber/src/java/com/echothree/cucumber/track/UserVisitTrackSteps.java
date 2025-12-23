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

package com.echothree.cucumber.track;

import com.echothree.control.user.track.common.TrackUtil;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class UserVisitTrackSteps implements En {

    public UserVisitTrackSteps() {
        When("^the user begins entering a new user visit track$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createUserVisitTrackForm).isNull();

                    persona.createUserVisitTrackForm = TrackUtil.getHome().getCreateUserVisitTrackForm();
                });

        When("^the user adds the new user visit track$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createUserVisitTrackForm = persona.createUserVisitTrackForm;

                    assertThat(createUserVisitTrackForm).isNotNull();

                    var commandResult = TrackUtil.getHome().createUserVisitTrack(persona.userVisitPK, createUserVisitTrackForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.createUserVisitTrackForm = null;
                });

        When("^the user sets the user visit track's track value to \"([^\"]*)\"$",
                (String trackValue) -> {
                    var persona = CurrentPersona.persona;
                    var createUserVisitTrackForm = persona.createUserVisitTrackForm;

                    assertThat(createUserVisitTrackForm).isNotNull();

                    createUserVisitTrackForm.setTrackValue(trackValue);
                });
    }

}

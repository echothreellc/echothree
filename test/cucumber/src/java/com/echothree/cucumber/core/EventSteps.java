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

package com.echothree.cucumber.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class EventSteps implements En {

    public EventSteps() {
        When("^the user begins entering a new event$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.sendEventForm).isNull();

                    persona.sendEventForm = CoreUtil.getHome().getSendEventForm();
                });

        When("^the user sends the new event$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var sendEventForm = persona.sendEventForm;

                    assertThat(sendEventForm).isNotNull();

                    LastCommandResult.commandResult = CoreUtil.getHome().sendEvent(persona.userVisitPK, sendEventForm);

                    persona.sendEventForm = null;
                });

        When("^the user sets the event's entity instance to the last entity instance added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var sendEventForm = persona.sendEventForm;
                    var lastEntityRef = persona.lastEntityRef;

                    assertThat(sendEventForm != null && lastEntityRef != null).isTrue();

                    sendEventForm.setEntityRef(lastEntityRef);
                });

        When("^the user sets the event's event type to ([a-zA-Z0-9-_]*)$",
                (String eventTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var sendEventForm = persona.sendEventForm;

                    assertThat(sendEventForm != null).isTrue();

                    sendEventForm.setEventTypeName(eventTypeName);
                });
    }

}
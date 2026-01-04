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

package com.echothree.cucumber.campaign;

import com.echothree.control.user.campaign.common.CampaignUtil;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class UserVisitCampaignSteps implements En {

    public UserVisitCampaignSteps() {
        When("^the user begins entering a new user visit campaign$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createUserVisitCampaignForm).isNull();

                    persona.createUserVisitCampaignForm = CampaignUtil.getHome().getCreateUserVisitCampaignForm();
                });

        When("^the user adds the new user visit campaign$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createUserVisitCampaignForm = persona.createUserVisitCampaignForm;

                    assertThat(createUserVisitCampaignForm).isNotNull();

                    var commandResult = CampaignUtil.getHome().createUserVisitCampaign(persona.userVisitPK, createUserVisitCampaignForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.createUserVisitCampaignForm = null;
                });

        When("^the user sets the user visit campaign's campaign value to \"([^\"]*)\"$",
                (String campaignValue) -> {
                    var persona = CurrentPersona.persona;
                    var createUserVisitCampaignForm = persona.createUserVisitCampaignForm;

                    assertThat(createUserVisitCampaignForm).isNotNull();

                    createUserVisitCampaignForm.setCampaignValue(campaignValue);
                });

        When("^the user sets the user visit campaign's campaign source value to \"([^\"]*)\"$",
                (String campaignSourceValue) -> {
                    var persona = CurrentPersona.persona;
                    var createUserVisitCampaignForm = persona.createUserVisitCampaignForm;

                    assertThat(createUserVisitCampaignForm).isNotNull();

                    createUserVisitCampaignForm.setCampaignSourceValue(campaignSourceValue);
                });

        When("^the user sets the user visit campaign's campaign medium value to \"([^\"]*)\"$",
                (String campaignMediumValue) -> {
                    var persona = CurrentPersona.persona;
                    var createUserVisitCampaignForm = persona.createUserVisitCampaignForm;

                    assertThat(createUserVisitCampaignForm).isNotNull();

                    createUserVisitCampaignForm.setCampaignMediumValue(campaignMediumValue);
                });

        When("^the user sets the user visit campaign's campaign term value to \"([^\"]*)\"$",
                (String campaignTermValue) -> {
                    var persona = CurrentPersona.persona;
                    var createUserVisitCampaignForm = persona.createUserVisitCampaignForm;

                    assertThat(createUserVisitCampaignForm).isNotNull();

                    createUserVisitCampaignForm.setCampaignTermValue(campaignTermValue);
                });

        When("^the user sets the user visit campaign's campaign content value to \"([^\"]*)\"$",
                (String CampaignContentValue) -> {
                    var persona = CurrentPersona.persona;
                    var createUserVisitCampaignForm = persona.createUserVisitCampaignForm;

                    assertThat(createUserVisitCampaignForm).isNotNull();

                    createUserVisitCampaignForm.setCampaignContentValue(CampaignContentValue);
                });
    }

}

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

package com.echothree.cucumber.core;

import com.echothree.control.user.core.client.helper.BaseKeysHelper;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseEncryptionKeysSteps implements En {

    public BaseEncryptionKeysSteps() {
        When("^the user loads the existing base encryption keys$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createItemForm).isNull();

                    BaseKeysHelper.getInstance().handleLoadBaseKeys(persona.userVisitPK);
                });

        When("^the user changes the base encryption keys$",
                () -> {
                    var persona = CurrentPersona.persona;

                    BaseKeysHelper.getInstance().handleChangeBaseKeys(persona.userVisitPK);
                });
    }

}

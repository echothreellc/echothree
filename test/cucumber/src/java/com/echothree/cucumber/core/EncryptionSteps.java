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
import com.echothree.control.user.core.common.result.EncryptResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.google.common.io.BaseEncoding;
import io.cucumber.java8.En;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class EncryptionSteps implements En {

    private String entityEncryptionKeyName;
    private final Map<String, EncryptResult> encryptedValues = new HashMap<>();
    private String decryptedValue;

    public EncryptionSteps() {
        Given("the user uses entity encryption key name {string}", (String name) -> entityEncryptionKeyName = name);

        When("the user encrypts {string} and remembers it as {string}", (String value, String name) -> {
            var form = CoreUtil.getHome().getEncryptForm();

            form.setEntityEncryptionKeyName(entityEncryptionKeyName);
            form.setValue(value);

            var commandResult = CoreUtil.getHome().encrypt(CurrentPersona.persona.userVisitPK, form);
            LastCommandResult.commandResult = commandResult;

            assertThat(commandResult.hasErrors()).isFalse();

            var result = commandResult.getExecutionResult().getResult();
            assertThat(result.getValue()).isNotNull().isNotEqualTo(value);
            assertThat(result.getInitializationVector()).isNotNull();

            var iv = BaseEncoding.base64().decode(result.getInitializationVector());
            assertThat(iv).hasSize(16);
            assertThat(result.getInitializationVector()).isEqualTo(BaseEncoding.base64().encode(iv));

            encryptedValues.put(name, result);
        });

        Then("the encrypted values {string} and {string} have different IVs and ciphertext",
                (String first, String second) -> {
                    var firstResult = encryptedValue(first);
                    var secondResult = encryptedValue(second);

                    assertThat(firstResult.getInitializationVector()).isNotEqualTo(secondResult.getInitializationVector());
                    assertThat(firstResult.getValue()).isNotEqualTo(secondResult.getValue());
                });

        When("the user decrypts {string} using its returned IV", (String name) ->
                decrypt(name, encryptedValue(name).getInitializationVector()));

        When("the user decrypts {string} using its returned IV without padding", (String name) -> {
            var iv = BaseEncoding.base64().decode(encryptedValue(name).getInitializationVector());

            decrypt(name, BaseEncoding.base64().omitPadding().encode(iv));
        });

        When("the user decrypts {string} without an IV", (String name) -> decrypt(name, null));

        When("the user decrypts {string} using IV {string}", (String name, String iv) -> decrypt(name, iv));

        Then("the decrypted value is {string}", (String expected) -> assertThat(decryptedValue).isEqualTo(expected));

        Then("the IV validation error {string} should occur", (String error) -> {
            var commandResult = LastCommandResult.commandResult;

            assertThat(commandResult.hasValidationErrors()).isTrue();
            assertThat(commandResult.getValidationResult().getValidationMessages()
                    .containsKey("InitializationVector", error)).isTrue();
            assertThat(decryptedValue).isNull();
        });
    }

    private EncryptResult encryptedValue(String name) {
        var result = encryptedValues.get(name);

        assertThat(result).as("Remembered encrypted value %s", name).isNotNull();

        return result;
    }

    private void decrypt(String name, String iv) throws Exception {
        var form = CoreUtil.getHome().getDecryptForm();

        form.setEntityEncryptionKeyName(entityEncryptionKeyName);
        form.setValue(encryptedValue(name).getValue());
        form.setInitializationVector(iv);

        decryptedValue = null;

        var commandResult = CoreUtil.getHome().decrypt(CurrentPersona.persona.userVisitPK, form);
        LastCommandResult.commandResult = commandResult;

        if(!commandResult.hasErrors()) {
            decryptedValue = commandResult.getExecutionResult().getResult().getValue();
        }
    }

}

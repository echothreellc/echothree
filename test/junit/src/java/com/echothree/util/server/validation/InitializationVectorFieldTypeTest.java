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

package com.echothree.util.server.validation;

import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.persistence.CryptoTestUtils;
import com.google.common.io.BaseEncoding;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InitializationVectorFieldTypeTest {

    private static final FieldDefinition REQUIRED_IV = new FieldDefinition("InitializationVector",
            FieldType.INITIALIZATION_VECTOR, true, null, null);

    @Test
    public void generatedInitializationVectorIsAccepted() {
        var form = CoreFormFactory.getDecryptForm();
        var iv = CryptoTestUtils.encryptionUtils().generateRowInitializationVector();
        form.setInitializationVector(iv);

        assertNull(new Validator(null).validateField(form, REQUIRED_IV));
        assertEquals(iv, form.getInitializationVector());
        assertEquals(16, BaseEncoding.base64().decode(iv).length);
    }

    @Test
    public void unpaddedAndSurroundingWhitespaceAreNormalized() {
        var encoded = BaseEncoding.base64().encode(new byte[16]);
        for(var value : List.of(encoded, encoded.substring(0, 22), "  " + encoded + "  ")) {
            var form = CoreFormFactory.getDecryptForm();
            form.setInitializationVector(value);

            assertNull(new Validator(null).validateField(form, REQUIRED_IV));
            assertEquals(encoded, form.getInitializationVector());
        }
    }

    @Test
    public void missingRequiredInitializationVectorIsRejected() {
        for(var value : new String[] {null, "", "  "}) {
            var form = CoreFormFactory.getDecryptForm();
            form.setInitializationVector(value);
            var messages = new Validator(null).validateField(form, REQUIRED_IV);

            assertNotNull(messages);
            assertTrue(messages.containsKey("InitializationVector", Validator.ERROR_REQUIRED_FIELD));
            assertNull(form.getInitializationVector());
        }
    }

    @Test
    public void optionalMissingInitializationVectorIsAccepted() {
        var form = CoreFormFactory.getDecryptForm();
        var optionalIv = new FieldDefinition("InitializationVector", FieldType.INITIALIZATION_VECTOR, false, null, null);

        assertNull(new Validator(null).validateField(form, optionalIv));
    }

    @Test
    public void invalidEncodingAndDecodedLengthsAreRejected() {
        for(var value : List.of("parpmcc_number", "_".repeat(22), "-".repeat(22), "!".repeat(24),
                "A".repeat(21) + " ", "A".repeat(21) + "=", "A".repeat(21) + "B==",
                "A".repeat(10) + " " + "A".repeat(11) + "==",
                BaseEncoding.base64().encode(new byte[15]), BaseEncoding.base64().encode(new byte[17]))) {
            var form = CoreFormFactory.getDecryptForm();
            form.setInitializationVector(value);
            var messages = new Validator(null).validateField(form, REQUIRED_IV);

            assertNotNull(messages, value);
            assertTrue(messages.containsKey("InitializationVector", Validator.ERROR_INVALID_FORMAT), value);
            assertNull(form.getInitializationVector(), value);
        }
    }

}

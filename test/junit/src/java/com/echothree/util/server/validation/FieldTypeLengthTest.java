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

import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.validation.fieldtype.BaseFieldType;
import com.echothree.util.server.validation.fieldtype.EntityNameFieldType;
import com.echothree.util.server.validation.fieldtype.MimeTypeFieldType;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FieldTypeLengthTest {

    private Messages validate(String fieldTypeName, String value, Long minimumLength, Long maximumLength)
            throws Exception {
        var messages = new Messages();
        var definition = new FieldDefinition("Value", FieldType.STRING, true, minimumLength, maximumLength);
        var fieldTypeClass = Class.forName("com.echothree.util.server.validation.fieldtype."
                + fieldTypeName + "FieldType");
        var constructor = fieldTypeClass.getConstructor(Validator.class, BaseForm.class, Messages.class,
                String.class, String[].class, FieldDefinition.class);
        var fieldType = (BaseFieldType)constructor.newInstance(null, null, messages, value,
                new String[]{"Value"}, definition);
        assertNull(fieldType.validate());

        return messages;
    }

    @Test
    public void rejectsLengthViolationsBeforeCheckingFormat() throws Exception {
        for(var name : List.of("CommandName", "EntityName", "EntityName2", "EntityNames",
                "EntityTypeName", "HostName", "Key", "MimeType", "SequenceMask", "Tag", "TimeZoneName")) {
            var tooLong = validate(name, "!".repeat(100_000), 1L, 40L);
            assertTrue(tooLong.containsKey("Value", Validator.ERROR_MAXIMUM_LENGTH), name);
            assertFalse(tooLong.containsKey("Value", Validator.ERROR_INVALID_FORMAT), name);
            var tooShort = validate(name, "!", 2L, 40L);
            assertTrue(tooShort.containsKey("Value", Validator.ERROR_MINIMUM_LENGTH), name);
            assertFalse(tooShort.containsKey("Value", Validator.ERROR_INVALID_FORMAT), name);
        }
        assertFalse(EntityNameFieldType.isValidName("a".repeat(100_000)));
        assertTrue(EntityNameFieldType.isValidName("a".repeat(40)));
    }

    @Test
    public void stillRejectsMalformedValuesWithinLengthLimits() throws Exception {
        for(var name : List.of("CommandName", "EntityName", "EntityName2", "EntityNames",
                "EntityTypeName", "HostName", "Key", "MimeType", "SequenceMask", "Tag", "TimeZoneName")) {
            var messages = validate(name, "!", 1L, 40L);
            assertTrue(messages.containsKey("Value", Validator.ERROR_INVALID_FORMAT), name);
        }
    }

    @Test
    public void mimeTypeRetainsTheDefaultFortyCharacterLimit() throws Exception {
        var accepted = "application/" + "a".repeat(28);
        var messages = new Messages();
        var definition = new FieldDefinition("Value", FieldType.MIME_TYPE, true, null, null);
        var fieldType = new MimeTypeFieldType(null, null, messages, accepted, new String[]{"Value"}, definition);

        assertEquals(40, accepted.length());
        assertEquals(accepted, fieldType.validate());
        assertTrue(messages.isEmpty());
        var rejected = validate("MimeType", accepted + "a", null, null);
        assertTrue(rejected.containsKey("Value", Validator.ERROR_MAXIMUM_LENGTH));
    }
}

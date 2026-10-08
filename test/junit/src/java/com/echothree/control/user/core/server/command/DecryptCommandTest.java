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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.result.DecryptResult;
import com.echothree.util.common.exception.DecryptionBadPaddingException;
import com.echothree.util.common.exception.PersistenceEncryptionException;
import com.echothree.util.common.exception.UnknownEntityEncryptionKeyException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.persistence.EncryptionUtils;
import javax.crypto.BadPaddingException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DecryptCommandTest {

    private static final String KEY_NAME = "TEST.Missing";
    private static final String IV = "AAAAAAAAAAAAAAAAAAAAAA==";
    private static final String CIPHERTEXT = "AQEBAQEBAQEBAQEBAQEBAQ==";

    private static class TestCommand extends DecryptCommand {
        @SuppressWarnings("UnnecessaryAssignment") // Supply the CDI dependency without a container.
        TestCommand(EncryptionUtils encryption) {
            encryptionUtils = encryption;
            form = CoreFormFactory.getDecryptForm();
            form.setEntityEncryptionKeyName(KEY_NAME);
            form.setInitializationVector(IV);
            form.setValue(CIPHERTEXT);
        }
    }

    private EncryptionUtils failingEncryption(RuntimeException failure) {
        return new EncryptionUtils() {
            @Override
            public String decrypt(String keyName, String iv, Boolean external, String value) {
                assertEquals(KEY_NAME, keyName);
                assertEquals(IV, iv);
                assertTrue(external);
                assertEquals(CIPHERTEXT, value);
                throw failure;
            }
        };
    }

    @Test
    public void missingKeyBecomesExecutionErrorWithSuppliedName() {
        var command = new TestCommand(failingEncryption(new UnknownEntityEncryptionKeyException(KEY_NAME)));
        var result = (DecryptResult)command.execute();

        assertNull(result.getValue());
        assertTrue(command.hasExecutionErrors());
        var errors = command.getExecutionErrors();
        assertEquals(1, errors.size());
        var error = errors.get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownEntityEncryptionKeyName.name(), error.getKey());
        assertArrayEquals(new Object[] {KEY_NAME}, error.getValues());
    }

    @Test
    public void badDecryptionPaddingBecomesExecutionErrorWithSuppliedName() {
        var command = new TestCommand(failingEncryption(new DecryptionBadPaddingException(new BadPaddingException())));
        var result = (DecryptResult)command.execute();

        assertNull(result.getValue());
        assertTrue(command.hasExecutionErrors());
        var errors = command.getExecutionErrors();
        assertEquals(1, errors.size());
        var error = errors.get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnableToDecryptValue.name(), error.getKey());
        assertArrayEquals(new Object[] {KEY_NAME}, error.getValues());
    }

    @Test
    public void otherEncryptionFailuresPropagateWithoutBecomingExecutionErrors() {
        for(var failure : new PersistenceEncryptionException[] {
                new PersistenceEncryptionException("Base Encryption Keys Missing"),
                new PersistenceEncryptionException(new BadPaddingException("Invalid padding"))}) {
            var command = new TestCommand(failingEncryption(failure));

            assertSame(failure, assertThrows(PersistenceEncryptionException.class, command::execute));
            assertFalse(command.hasExecutionErrors());
        }
    }

    @Test
    public void successfulDecryptionReturnsValueWithoutErrors() {
        var command = new TestCommand(new EncryptionUtils() {
            @Override
            public String decrypt(String keyName, String iv, Boolean external, String value) {
                return "decrypted value";
            }
        });
        var result = (DecryptResult)command.execute();

        assertEquals("decrypted value", result.getValue());
        assertFalse(command.hasExecutionErrors());
    }

}

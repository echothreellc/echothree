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

package com.echothree.util.server.persistence;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShaUtilsContractTest {

    private final List<ShaUtils> implementations = List.of(CryptoTestUtils.sha1Utils(), CryptoTestUtils.sha256Utils());

    @Test
    public void generatedSaltsAreFreshAndUsable() {
        for(var sha : implementations) {
            var salts = new HashSet<String>();
            for(var i = 0; i < 32; i++) {
                var salt = sha.generateSalt();
                assertTrue(salts.add(salt), "Repeated salt from " + sha.getClass().getSimpleName());
                assertEquals(salt, Base64.getEncoder().encodeToString(Base64.getDecoder().decode(salt)));
                assertNotNull(sha.encode(salt, "password"));
            }
        }
    }

    @Test
    public void unpaddedSaltIsEquivalentToPaddedSalt() {
        for(var sha : implementations) {
            var salt = sha.generateSalt();
            assertEquals(sha.encode(salt, "password"), sha.encode(salt.replace("=", ""), "password"));
        }
    }

    @Test
    public void malformedSaltIsRejected() {
        for(var sha : implementations) {
            for(var salt : List.of("!", "A", "AA A", "____")) {
                assertThrows(IllegalArgumentException.class, () -> sha.encode(salt, "password"), salt);
            }
        }
    }

    @Test
    public void concurrentHashAndPasswordCallsDoNotShareDigestState() throws Exception {
        try(var executor = Executors.newFixedThreadPool(4)) {
            var tasks = new ArrayList<Callable<Void>>();
            for(var sha : implementations) {
                var salt = sha.generateSalt();
                var inputs = List.of("", "abc", "café 日本語", "a".repeat(65));
                var hashes = inputs.stream().map(sha::hash).toList();
                var passwords = inputs.stream().map(value -> sha.encode(salt, value)).toList();
                for(var repetition = 0; repetition < 8; repetition++) {
                    for(var i = 0; i < inputs.size(); i++) {
                        var index = i;
                        tasks.add(() -> {
                            assertEquals(hashes.get(index), sha.hash(inputs.get(index)));
                            assertEquals(passwords.get(index), sha.encode(salt, inputs.get(index)));
                            return null;
                        });
                    }
                }
            }
            for(var future : executor.invokeAll(tasks, 10, TimeUnit.SECONDS)) {
                assertFalse(future.isCancelled(), "Concurrent SHA calls timed out");
                future.get();
            }
        }
    }

}

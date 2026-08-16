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

package com.echothree.ui.cli.database.util.definition;

import com.echothree.ui.cli.database.util.DatabaseUtilitiesForJooq;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class DatabaseUtilitiesForJooqTest {

    @TempDir
    Path temporaryDirectory;

    private String expectedXml() throws Exception {
        try(var inputStream = getClass().getResourceAsStream("/RegressionXMLDatabase.xml")) {
            assertNotNull(inputStream);
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).replace("\r\n", "\n");
        }
    }

    @Test
    public void generatedXmlMatchesTheGoldenFileAndIsIdempotent() throws Exception {
        var database = DatabaseRegressionTestSupport.parseRegressionDatabase();
        var utilities = new DatabaseUtilitiesForJooq(false, database);
        var generatedFile = temporaryDirectory.resolve("XMLDatabase.xml");

        utilities.exportJooq(temporaryDirectory.toString());
        var firstGeneration = Files.readString(generatedFile, StandardCharsets.UTF_8).replace("\r\n", "\n");
        assertEquals(expectedXml(), firstGeneration);

        utilities.exportJooq(temporaryDirectory.toString());
        var secondGeneration = Files.readString(generatedFile, StandardCharsets.UTF_8).replace("\r\n", "\n");
        assertEquals(firstGeneration, secondGeneration);
    }

}

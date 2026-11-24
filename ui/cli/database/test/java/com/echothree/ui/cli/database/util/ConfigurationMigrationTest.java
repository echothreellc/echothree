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

package com.echothree.ui.cli.database.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ConfigurationMigrationTest {

    @TempDir
    Path temporaryDirectory;

    @ParameterizedTest
    @ValueSource(strings = { "amazon", "database", "mailtransfer" })
    public void configurationDefinitionsPreserveLegacyPropertyBehavior(String toolName) throws Exception {
        var definitionFile = temporaryDirectory.resolve("config.xml");
        Files.copy(Path.of("..", toolName, "src", "metadata", "config.xml"), definitionFile);
        Files.writeString(temporaryDirectory.resolve("echothree-" + toolName + ".properties"), """
                configuration.migration.override=file
                configuration.migration.base=example
                configuration.migration.interpolated=${configuration.migration.base}/path
                configuration.migration.enabled=true
                configuration.migration.list=first,second
                configuration.migration.escaped=first\\,second
                """);

        var overrideProperty = "configuration.migration.override";
        var previousValue = System.setProperty(overrideProperty, "system");

        try {
            var configurations = new Configurations();
            // The database CLI loads a URL; the Amazon and mail transfer CLIs load a path.
            var configuration = toolName.equals("database")
                    ? configurations.combined(definitionFile.toUri().toURL())
                    : configurations.combined(definitionFile.toString());

            assertEquals("system", configuration.getString(overrideProperty));
            assertEquals("example/path", configuration.getString("configuration.migration.interpolated"));
            assertTrue(configuration.getBoolean("configuration.migration.enabled"));
            assertTrue(configuration.getBoolean("configuration.migration.missing", true));
            assertEquals(List.of("first", "second"), configuration.getList("configuration.migration.list"));
            assertEquals("first,second", configuration.getString("configuration.migration.escaped"));
        } finally {
            if(previousValue == null) {
                System.clearProperty(overrideProperty);
            } else {
                System.setProperty(overrideProperty, previousValue);
            }
        }
    }
}

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

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ConfigurationMigrationTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    public void missingPropertiesFailExplicitly() {
        assertThrows(ConfigurationException.class, () -> ConfigurationLoader.load((URL) null));
        assertThrows(ConfigurationException.class,
                () -> ConfigurationLoader.load(temporaryDirectory.resolve("missing.properties").toString()));
    }

    @Test
    public void fileNameLookupFindsClasspathProperties() throws Exception {
        var propertiesFile = temporaryDirectory.resolve("classpath-configuration.properties");
        Files.writeString(propertiesFile, "configuration.migration.classpath=loaded\n");
        var currentThread = Thread.currentThread();
        var originalClassLoader = currentThread.getContextClassLoader();

        try(var classLoader = new URLClassLoader(new URL[] { temporaryDirectory.toUri().toURL() }, originalClassLoader)) {
            currentThread.setContextClassLoader(classLoader);
            var configuration = ConfigurationLoader.load(propertiesFile.getFileName().toString());
            assertEquals("loaded", configuration.getString("configuration.migration.classpath"));
        } finally {
            currentThread.setContextClassLoader(originalClassLoader);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "file", "url", "jar" })
    public void directLoadingPreservesLegacyPropertyBehavior(String sourceType) throws Exception {
        var propertiesFile = temporaryDirectory.resolve("configuration-loader.properties");
        Files.writeString(propertiesFile, """
                configuration.migration.override=file
                configuration.migration.base=example
                configuration.migration.interpolated=${configuration.migration.base}/path
                configuration.migration.systemInterpolated=${configuration.migration.override}/path
                configuration.migration.enabled=true
                configuration.migration.list=first,second
                configuration.migration.escaped=first\\,second
                """);

        var overrideProperty = "configuration.migration.override";
        var previousValue = System.setProperty(overrideProperty, "system");

        try {
            var configuration = switch(sourceType) {
                case "file" -> ConfigurationLoader.load(propertiesFile.toString());
                case "url" -> ConfigurationLoader.load(propertiesFile.toUri().toURL());
                case "jar" -> {
                    var jarFile = temporaryDirectory.resolve("configuration.jar");
                    try(var jarOutput = new JarOutputStream(Files.newOutputStream(jarFile))) {
                        jarOutput.putNextEntry(new JarEntry(propertiesFile.getFileName().toString()));
                        Files.copy(propertiesFile, jarOutput);
                        jarOutput.closeEntry();
                    }
                    yield ConfigurationLoader.load(URI.create("jar:" + jarFile.toUri()
                            + "!/" + propertiesFile.getFileName()).toURL());
                }
                default -> throw new IllegalArgumentException("Unknown source type: " + sourceType);
            };

            assertEquals("system", configuration.getString(overrideProperty));
            assertEquals("example/path", configuration.getString("configuration.migration.interpolated"));
            assertEquals("system/path", configuration.getString("configuration.migration.systemInterpolated"));
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
